package com.joegitau
package dao

import com.joegitau.models.Role
import com.joegitau.slick.CustomPostgresProfile.api._
import eu.timepit.refined.types.numeric.PosLong

import scala.concurrent.{ExecutionContext, Future}

trait UserRoleDAO[F[_]] {
  // FIXME: differentiate userId from roleId
  def insert(userId: PosLong, roleId: PosLong): F[String]
  def getRolesByUserId(userId: PosLong): F[List[Role]]
  def delete(userId: PosLong, roleId: PosLong): F[String]
}

class UserRoleDAOImpl(db: Database)(implicit ec: ExecutionContext) extends UserRoleDAO[Future] {
  override def insert(userId: PosLong, roleId: PosLong): Future[String] = ???

  override def getRolesByUserId(userId: PosLong): Future[List[Role]] = ???

  override def delete(userId: PosLong, roleId: PosLong): Future[String] = ???
}
