package com.joegitau
package models

import eu.timepit.refined.types.numeric.PosLong

import java.time.Instant

trait Base {
  def created: Instant
  def modified: Option[Instant]
}

case class UserRole(
  userId:   PosLong,
  roleId:   PosLong,
  created:  Instant,
  modified: Option[Instant]
) extends Base

case class RolePermission(
  roleId:       PosLong,
  permissionId: PosLong,
  created:      Instant,
  modified:     Option[Instant]
) extends Base

