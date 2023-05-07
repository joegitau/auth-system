package com.joegitau
package services

import com.joegitau.dao.UserDAO
import com.joegitau.models.User
import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString

import java.util.UUID
import scala.concurrent.Future

trait UserService[F[_]] {
  def register(user: User): F[User]
  def login(username: NonEmptyString, password: NonEmptyString): F[Option[String]] // return an optional JWT token
  def getUserByUsername(username: String): F[Option[User]]
  def getUserById(id: PosLong): F[Option[User]]
  def getAllUsers: F[List[User]]
  def updateUser(id: PosLong, newUser: User): F[Option[User]]
  def activateUserAccount(token: UUID): F[String]
  def resetUserPassword(token: UUID, newPassword: NonEmptyString): F[String]
  def deleteUser(id: PosLong): F[String]
}

class UserServiceImpl(userDAO: UserDAO[Future]) extends UserService[Future] {
  override def register(user: User): Future[User] = ???

  override def login(username: NonEmptyString, password: NonEmptyString): Future[Option[String]] = ???

  override def getUserByUsername(username: String): Future[Option[User]] = ???

  override def getUserById(id: PosLong): Future[Option[User]] = ???

  override def getAllUsers: Future[List[User]] = ???

  override def updateUser(id: PosLong, newUser: User): Future[Option[User]] = ???

  override def activateUserAccount(token: UUID): Future[String] = ???

  override def resetUserPassword(token: UUID, newPassword: NonEmptyString): Future[String] = ???

  override def deleteUser(id: PosLong): Future[String] = ???
}
