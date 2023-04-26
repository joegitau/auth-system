package com.joegitau.utils

import at.favre.lib.crypto.bcrypt.BCrypt
import eu.timepit.refined.auto.autoUnwrap
import eu.timepit.refined.types.string.NonEmptyString

import java.nio.charset.StandardCharsets
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait PasswordHasher[F[_]] {
  def hashPassword(password: NonEmptyString)(implicit ec: ExecutionContext): F[Array[Byte]]
  def verify(password: NonEmptyString, hashed: Array[Byte])(implicit ec: ExecutionContext): F[Boolean]
}

object BcryptPasswordHasher extends PasswordHasher[Future] {
  override def hashPassword(password: NonEmptyString)
                           (implicit ec: ExecutionContext): Future[Array[Byte]] =
    Future.fromTry {
      Try(BCrypt.withDefaults.hash(6, password.getBytes(StandardCharsets.UTF_8)))
    }

  override def verify(password: NonEmptyString, hashed: Array[Byte])
                     (implicit ec: ExecutionContext): Future[Boolean] =
    Future.fromTry {
      Try(BCrypt.verifyer().verify(password.getBytes(StandardCharsets.UTF_8), hashed).verified)
    }
}
