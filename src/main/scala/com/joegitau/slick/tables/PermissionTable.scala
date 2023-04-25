package com.joegitau.slick.tables

import com.joegitau.slick.CustomPostgresProfile.api._
import com.joegitau.models.Permission
import eu.timepit.refined.types.string.NonEmptyString
import slick.lifted.ProvenShape

import java.time.Instant

class PermissionTable(tag: Tag) extends Table[Permission](tag, "permissions"){
  def id       = column[Option[Int]]("id")
  def name     = column[NonEmptyString]("name")
  def created  = column[Option[Instant]]("created")
  def modified = column[Option[Instant]]("modified")

  override def * : ProvenShape[Permission] = (id, name, created, modified) <> (Permission.tupled, Permission.unapply)
}

object PermissionTable {
  lazy val permissions = TableQuery[PermissionTable]
}
