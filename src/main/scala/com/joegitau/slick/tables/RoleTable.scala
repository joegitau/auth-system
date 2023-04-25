package com.joegitau.slick.tables

import com.joegitau.models.Role
import com.joegitau.slick.CustomPostgresProfile.api._
import eu.timepit.refined.types.string.NonEmptyString
import slick.lifted.ProvenShape

import java.time.Instant

class RoleTable(tag: Tag) extends Table[Role](tag, "roles"){
  private def id       = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
  private def name     = column[NonEmptyString]("name")
  private def created  = column[Instant]("created")
  private def modified = column[Instant]("modified")

  override def * : ProvenShape[Role] = (id, name, created.?, modified.?) <> (Role.tupled, Role.unapply)
}

object Roles {
  lazy val roles = TableQuery[Role]
}
