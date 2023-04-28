package com.joegitau
package dao

import com.joegitau.models.Permission
import com.joegitau.slick.CustomPostgresProfile.api._
import eu.timepit.refined.types.numeric.PosLong

import scala.concurrent.{ExecutionContext, Future}

trait RolePermissionDAO[F[_]] {
  // FIXME: differentiate roleId form permissionId
  def insert(roleId: PosLong, permissionId: PosLong): F[String]
  def delete(roleId: PosLong, permissionId: PosLong): F[String]
  def findPermissionsByRoleId(roleId: PosLong): F[List[Permission]]
}

class RolePermissionDAOImpl(db: Database)(implicit ec: ExecutionContext) extends RolePermissionDAO[Future] {
  override def insert(roleId: PosLong, permissionId: PosLong): Future[String] = ???

  override def delete(roleId: PosLong, permissionId: PosLong): Future[String] = ???

  override def findPermissionsByRoleId(roleId: PosLong): Future[List[Permission]] = ???
}
