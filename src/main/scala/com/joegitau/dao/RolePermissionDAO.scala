package com.joegitau
package dao

import com.joegitau.models.{Permission, RolePermission}
import com.joegitau.slick.tables._
import com.joegitau.slick.CustomPostgresProfile.api._
import com.joegitau.slick.tables.RelationsTable.RolePermissionTable.RolePermissionRelations
import eu.timepit.refined.types.numeric.PosLong

import java.time.Instant
import scala.concurrent.{ExecutionContext, Future}

trait RolePermissionDAO[F[_]] {
  // FIXME: differentiate roleId form permissionId
  def insert(roleId: PosLong, permissionId: PosLong): F[String]
  def getPermissionsByRoleId(roleId: PosLong): F[List[Permission]]
  def delete(roleId: PosLong, permissionId: PosLong): F[String]
}

class RolePermissionDAOImpl(db: Database)(implicit ec: ExecutionContext) extends RolePermissionDAO[Future] {
  override def insert(roleId: PosLong, permissionId: PosLong): Future[String] = {
    val rolePerm = RolePermission(roleId, permissionId, Instant.now, None)
    val action   = RolePermissionRelations += rolePerm

    db.run(action)
      .map(_ => s"Successfully established relation between $roleId & $permissionId!")
      .recover { case ex: Exception => ex.getMessage }
  }

  override def getPermissionsByRoleId(roleId: PosLong): Future[List[Permission]] = {
    val query = for {
      rolePerm   <- RolePermissionRelations if rolePerm.roleId === roleId
      permission <- rolePerm.permissionFK
    } yield permission

    db.run(query.result).map(_.toList)
  }

  override def delete(roleId: PosLong, permissionId: PosLong): Future[String] = {
    val query = RolePermissionRelations.filter(r => r.roleId === roleId && r.permissionId === permissionId)

    db.run(query.delete)
      .map(_ => s"Successfully deleted $roleId & $permissionId relation!")
      .recover { case ex: Exception => ex.getMessage }
  }

}
