package com.joegitau
package slick.tables

import com.joegitau.models.{RolePermission, UserRole}
import com.joegitau.slick.CustomPostgresProfile.api._
import com.joegitau.slick.tables.PermissionTable.Permissions
import com.joegitau.slick.tables.RoleTable.Roles
import com.joegitau.slick.tables.UserTable.Users
import eu.timepit.refined.types.numeric.PosLong

import java.time.Instant

object RelationsTable {
  class UserRoleTable(tag: Tag) extends Table[UserRole](tag, "user_role") {
    def userId   = column[PosLong]("user_id")
    def roleId   = column[PosLong]("role_id")
    def created  = column[Instant]("created")
    def modified = column[Option[Instant]]("modified")

    def * = (userId, roleId, created, modified) <> (UserRole.tupled, UserRole.unapply)

    def userRolePK = primaryKey("pk_user_role", (userId, roleId))

    def userRoleIdx = index("idx_user_role", (userId, roleId), unique = true)

    def userFK      = foreignKey("fk_user", userId, Users)(_.id.getOrElse(PosLong(0)), onDelete = ForeignKeyAction.Cascade)
    def roleFK      = foreignKey("fk_role", roleId, Roles)(_.id.getOrElse(PosLong(0)), onDelete = ForeignKeyAction.Cascade)
  }

  object UserRoleTable {
    lazy val UserRoleRelations = TableQuery[UserRoleTable]
  }

  class RolePermissionTable(tag: Tag) extends Table[RolePermission](tag, "role_permission") {
    def roleId       = column[PosLong]("role_id")
    def permissionId = column[PosLong]("permission_id")
    def created      = column[Instant]("created")
    def modified     = column[Option[Instant]]("modified")

    def * = (roleId, permissionId, created, modified) <> (RolePermission.tupled, UserRole.unapply)

    def rolePermissionPK = primaryKey("pk_role_permission", (roleId, permissionId))

    def rolePermissionIdx = index("idx_role_permission", (roleId, permissionId), unique = true)

    def roleFK       = foreignKey("fk_role", roleId, Roles)(_.id.getOrElse(PosLong(0)), onDelete = ForeignKeyAction.Cascade)
    def permissionFK = foreignKey("fk_permissions", permissionId, Permissions)(_.id.getOrElse(PosLong(0)), onDelete = ForeignKeyAction.Cascade)
  }

  object RolePermissionTable {
    lazy val RolePermissionRelations = TableQuery[RolePermissionTable]
  }
}
