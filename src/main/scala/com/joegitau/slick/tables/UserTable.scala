package com.joegitau.slick.tables

import com.joegitau.models.User
import com.joegitau.slick.CustomPostgresProfile.api._
import eu.timepit.refined.types.string.NonEmptyString
import slick.lifted.ProvenShape

import java.time.Instant

class UserTable(tag: Tag) extends Table[User](tag, "users"){
  private def id        = column[Option[Long]]("id", O.AutoInc, O.PrimaryKey)
  private def firstName = column[NonEmptyString]("first_name")
  private def lastName  = column[NonEmptyString]("last_name")
  private def email     = column[NonEmptyString]("email")
  private def role      = column[NonEmptyString]("role")
  private def active    = column[Boolean]("active")
  private def created   = column[Instant]("created")  // Option[Instant]
  private def modified  = column[Instant]("modified") // Option[Instant]


  override def * : ProvenShape[User] = (
    id,
    firstName,
    lastName,
    email,
    role,
    active,
    created.?,
    modified.?
  ) <> (User.tupled, User.unapply)
}

object UserTable {
  lazy val users = TableQuery[UserTable]
}
