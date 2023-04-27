package com.joegitau.utils

import at.favre.lib.crypto.bcrypt.BCrypt
import eu.timepit.refined.auto.autoUnwrap
import eu.timepit.refined.types.string.NonEmptyString

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait PasswordHasher[F[_]] {
  def hashPassword(password: NonEmptyString)(implicit ec: ExecutionContext): F[String]
  def verify(password: NonEmptyString, hashed: NonEmptyString)(implicit ec: ExecutionContext): F[Boolean]
}

object BcryptPasswordHasher extends PasswordHasher[Future] {
  override def hashPassword(password: NonEmptyString)
                           (implicit ec: ExecutionContext): Future[String] = {
    val BCRYPT_COST = 12

    Future.fromTry {
      Try(BCrypt.withDefaults().hashToString(BCRYPT_COST, password.toCharArray))
    }
  }

  override def verify(password: NonEmptyString, hashed: NonEmptyString)
                     (implicit ec: ExecutionContext): Future[Boolean] = {
    Future.fromTry {
      Try(BCrypt.verifyer().verify(password.toCharArray, hashed).verified)
    }
  }
}
