package com.joegitau.slick
package tables

import com.joegitau.slick.CustomPostgresProfile.api._
import com.joegitau.models.Permission
import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString
import slick.lifted.ProvenShape

import java.time.Instant

class PermissionTable(tag: Tag) extends Table[Permission](tag, "permissions"){
  def id       = column[Option[PosLong]]("id", O.PrimaryKey, O.AutoInc)
  def name     = column[NonEmptyString]("name")
  def created  = column[Instant]("created")
  def modified = column[Option[Instant]]("modified")

  def * : ProvenShape[Permission] = (id, name, created, modified) <> (Permission.tupled, Permission.unapply)
}

object PermissionTable {
  lazy val Permissions = TableQuery[PermissionTable]
}
