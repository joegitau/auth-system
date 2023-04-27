package com.joegitau
package dao

import com.joegitau.models.User
import com.joegitau.slick.CustomPostgresProfile.api._
import com.joegitau.slick.tables.UserTable.Users
import com.joegitau.slick.tables.{nonEmptyStringColumnType, posLongColumnType}
import com.joegitau.utils.BcryptPasswordHasher
import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString

import java.time.Instant
import scala.concurrent.{ExecutionContext, Future}

trait UserDAO[F[_]] {
  def insert(user: User): F[User]
  def getById(id: PosLong): F[Option[User]]
  def getAll: F[List[User]]
  def update(user: User): F[Option[User]]
  def updatePassword(id: PosLong, newPassword: NonEmptyString): F[String]
  def delete(id: PosLong): F[String]
}

class UserDAOImpl(db: Database)(implicit ec: ExecutionContext) extends UserDAO[Future] {
  private def queryById(id: PosLong) = Compiled(Users.filter(_.id === id))

  override def insert(user: User): Future[User] = {
    val hashedPasswordFut = BcryptPasswordHasher.hashPassword(user.password)

    for {
      hashedPass         <- hashedPasswordFut
      userWithHashedPass = user.copy(password = NonEmptyString(hashedPass))
      insertedUser       <- db.run(
        Users returning Users.map(_.id) into ((user, id) => user.copy(id = id)) += userWithHashedPass
      )
    } yield insertedUser
  }

  override def getById(id: PosLong): Future[Option[User]] =
    db.run(queryById(id).result.headOption)

  override def getAll: Future[List[User]] =
    db.run(Users.result).map(_.toList)

  override def update(user: User): Future[Option[User]] = {
    val query = Users.filter(_.id === user.id)

    val updateAction = query.result.headOption
      .flatMap {
        case Some(_) =>
          query
            .update(user.copy(modified = Some(Instant.now)))
            .map(_ => Some(user))
        case None    => DBIO.successful(None)
      }

    db.run(updateAction)
  }

  override def updatePassword(id: PosLong, newPassword: NonEmptyString): Future[String] = {
    for {
      hashedPassword <- BcryptPasswordHasher.hashPassword(newPassword)
      modified       = Some(Instant.now())
      _              <- db.run(
        Users
          .filter(_.id === id)
          .map(user => (user.password, user.modified))
          .update((NonEmptyString(hashedPassword), modified))
      )
    } yield "Password updated successfully!"
  }

  override def delete(id: PosLong): Future[String] =
    db.run(queryById(id).delete).map(_ => s"User successfully deleted!")

}
