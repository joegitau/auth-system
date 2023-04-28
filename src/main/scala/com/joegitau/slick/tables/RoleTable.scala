package com.joegitau.slick.tables

import com.joegitau.models.{Permission, Role}
import com.joegitau.slick.CustomPostgresProfile.api._
import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString
import slick.lifted.ProvenShape

import java.time.Instant

class RoleTable(tag: Tag) extends Table[Role](tag, "roles"){
  def id          = column[Option[PosLong]]("id", O.PrimaryKey, O.AutoInc)
  def name        = column[NonEmptyString]("name")
  def permissions = column[List[Permission]]("permissions")
  def created     = column[Instant]("created")
  def modified    = column[Option[Instant]]("modified")

  def * : ProvenShape[Role] = (id, name, permissions, created, modified) <> (Role.tupled, Role.unapply)
}

object RoleTable {
  lazy val Roles = TableQuery[RoleTable]
}
