package com.joegitau
package slick.tables

import com.joegitau.models.{RolePermissionRelation, UserRoleRelation}
import com.joegitau.slick.CustomPostgresProfile.api._
import com.joegitau.slick.tables.PermissionTable.Permissions
import com.joegitau.slick.tables.RoleTable.Roles
import com.joegitau.slick.tables.UserTable.Users
import eu.timepit.refined.types.numeric.PosLong

import java.time.Instant

object RelationsTable {
  class UserRoleRelationTable(tag: Tag) extends Table[UserRoleRelation](tag, "user_role_relations") {
    def userId   = column[PosLong]("user_id")
    def roleId   = column[PosLong]("role_id")
    def created  = column[Instant]("created")
    def modified = column[Option[Instant]]("modified")

    def * = (userId, roleId, created, modified) <> (UserRoleRelation.tupled, UserRoleRelation.unapply)

    def userRolePK = primaryKey("pk_user_role", (userId, roleId))

    def userRoleIdx = index("idx_user_role", (userId, roleId), unique = true)

    def userFK      = foreignKey("fk_user", userId, Users)(_.id.getOrElse(PosLong(0)), onDelete = ForeignKeyAction.Cascade)
    def roleFK      = foreignKey("fk_role", roleId, Roles)(_.id.getOrElse(PosLong(0)), onDelete = ForeignKeyAction.Cascade)
  }

  object UserRoleRelationTable {
    lazy val UserRoleRelations = TableQuery[UserRoleRelationTable]
  }

  class RolePermissionRelationTable(tag: Tag) extends Table[RolePermissionRelation](tag, "role_permission_relations") {
    def roleId       = column[PosLong]("role_id")
    def permissionId = column[PosLong]("permission_id")
    def created      = column[Instant]("created")
    def modified     = column[Option[Instant]]("modified")

    def * = (roleId, permissionId, created, modified) <> (RolePermissionRelation.tupled, UserRoleRelation.unapply)

    def rolePermissionPK = primaryKey("pk_role_permission", (roleId, permissionId))

    def rolePermissionIdx = index("idx_role_permission", (roleId, permissionId), unique = true)

    def roleFK       = foreignKey("fk_role", roleId, Roles)(_.id.getOrElse(PosLong(0)), onDelete = ForeignKeyAction.Cascade)
    def permissionFK = foreignKey("fk_permissions", permissionId, Permissions)(_.id.getOrElse(PosLong(0)), onDelete = ForeignKeyAction.Cascade)
  }

  object RolePermissionRelationTable {
    lazy val RolePermissionRelations = TableQuery[RolePermissionRelationTable]
  }
}
