package com.joegitau
package dao

import com.joegitau.models.User
import com.joegitau.slick.CustomPostgresProfile.api._
import com.joegitau.slick.tables.UserTable.Users
import com.joegitau.slick.tables._
import com.joegitau.utils.BcryptPasswordHasher
import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.{NonEmptyString, TrimmedString}

import java.time.Instant
import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

trait UserDAO[F[_]] {
  def insert(user: User): F[User]
  def getById(id: PosLong): F[Option[User]]
  def getAll: F[List[User]]
  def update(user: User): F[Option[User]]
  def updatePassword(id: PosLong, newPassword: NonEmptyString): F[String]
  def getByUsername(username: TrimmedString): F[Option[User]]
  def getByEmail(email: TrimmedString): F[Option[User]]
  def updateLastLogin(id: PosLong, lastLogin: Instant): F[String]
  def updateActivationToken(id: PosLong, token: UUID): F[String]
  def activateAccount(token: UUID): F[String]
  def updateResetToken(id: PosLong, token: UUID): F[String]
  def resetPassword(token: UUID, newPassword: NonEmptyString): F[String]
  def delete(id: PosLong): F[String]
}

class UserDAOImpl(db: Database)(implicit ec: ExecutionContext) extends UserDAO[Future] {
  private def queryById(id: PosLong) = Compiled(Users.filter(_.id === id))

  lazy val now: Instant = Instant.now()

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
    val modifiedUser = user.copy(modified = Some(Instant.now))

    val action = for {
      exists <- Users.filter(_.id === user.id).exists.result
      result <- if (exists) {
                  Users.filter(_.id === user.id)
                    .update(modifiedUser)
                    .map(_ => Some(modifiedUser))
                } else DBIO.successful(None)
    } yield result

    db.run(action.transactionally)
  }

  override def updatePassword(id: PosLong, newPassword: NonEmptyString): Future[String] = {
    for {
      hashedPassword <- BcryptPasswordHasher.hashPassword(newPassword)
      _              <- db.run(
        Users
          .filter(_.id === id)
          .map(user => (user.password, user.modified))
          .update((NonEmptyString(hashedPassword), Some(now)))
      )
    } yield "Password updated successfully!"
  }

  override def delete(id: PosLong): Future[String] =
    db.run(queryById(id).delete).map(_ => s"User successfully deleted!")

  override def getByUsername(username: TrimmedString): Future[Option[User]] =
    db.run(Users.filter(_.username === username).result.headOption)

  override def getByEmail(email: TrimmedString): Future[Option[User]] =
    db.run(Users.filter(_.email === email).result.headOption)

  override def updateLastLogin(id: PosLong, lastLogin: Instant): Future[String] = {
    val action = for {
      exists <- Users.filter(_.id === id).exists.result
      result <- if (exists) {
                    Users.filter(_.id === id)
                      .map(u => (u.lastLogin, u.modified))
                      .update(Some(lastLogin), Some(now))
                      .map(_ => s"Successfully modified last login for user with id: $id")
                } else DBIO.successful(s"User with id: $id not found!")
    } yield result

    db.run(action.transactionally)
  }

  override def updateActivationToken(id: PosLong, token: UUID): Future[String] = ???

  override def activateAccount(token: UUID): Future[String] = ???

  override def updateResetToken(id: PosLong, token: UUID): Future[String] = ???

  override def resetPassword(token: UUID, newPassword: NonEmptyString): Future[String] = ???
}
