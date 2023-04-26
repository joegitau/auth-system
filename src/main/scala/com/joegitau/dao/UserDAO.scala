package com.joegitau
package dao

import com.joegitau.models.User
import com.joegitau.slick.CustomPostgresProfile.api._
import com.joegitau.slick.tables.UserTable.Users
import com.joegitau.slick.tables.posLongColumnType
import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString

import java.time.Instant
import scala.concurrent.{ExecutionContext, Future}

trait UserDAO[F[_]] {
  def insert(user: User): F[User]
  def getById(id: PosLong): F[Option[User]]
  def getAll: F[List[User]]
  def update(user: User): F[Option[User]]
  def delete(id: PosLong): F[String]
  def updatePassword(id: PosLong, newPassword: NonEmptyString): F[Option[User]]
}

class UserDAOImpl(db: Database)(implicit ec: ExecutionContext) extends UserDAO[Future] {
  private def queryById(id: PosLong) = Compiled(Users.filter(_.id === id))

  override def insert(user: User): Future[User] = {
    val action = Users returning Users.map(_.id) into ((user, id) => user.copy(id = id)) += user
    db.run(action)
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

  override def delete(id: PosLong): Future[String] =
    db.run(queryById(id).delete).map(_ => s"User successfully deleted!")

  override def updatePassword(id: PosLong, newPassword: NonEmptyString): Future[Option[User]] = ???
}
