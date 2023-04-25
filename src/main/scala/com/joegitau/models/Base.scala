package com.joegitau.models

import eu.timepit.refined.types.numeric.PosLong

import java.time.Instant

trait Base {
  def created: Option[Instant]
  def modified: Option[Instant]
}

case class UserRoleRelation(userId: PosLong, roleId: PosLong)

case class RolePermissionRelation(roleId: PosLong, permissionId: PosLong)
