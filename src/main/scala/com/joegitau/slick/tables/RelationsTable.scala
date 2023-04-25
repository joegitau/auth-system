package com.joegitau
package slick.tables

import com.joegitau.models.UserRoleRelation
import com.joegitau.slick.CustomPostgresProfile.api._
import com.joegitau.slick.tables.RoleTable.Roles
import com.joegitau.slick.tables.UserTable.Users
import eu.timepit.refined.types.numeric.PosLong

object RelationsTable {
  class UserRoleRelationTable(tag: Tag) extends Table[UserRoleRelation](tag, "user_role_relations") {
    private def userId = column[PosLong]("user_id")
    private def roleId = column[PosLong]("role_id")

    def * = (userId, roleId) <> (UserRoleRelation.tupled, UserRoleRelation.unapply)

    def userRolePK = primaryKey("pk_user_role", (userId, roleId))

    def userRoleIdx = index("idx_user_role", (userId, roleId), unique = true)

    def userFK      = foreignKey("fk_user", userId, Users)(_.id.getOrElse(PosLong(0)), onDelete = ForeignKeyAction.Cascade)
    def roleFK      = foreignKey("fk_role", roleId, Roles)(_.id.getOrElse(PosLong(0)), onDelete = ForeignKeyAction.Cascade)
  }
}
