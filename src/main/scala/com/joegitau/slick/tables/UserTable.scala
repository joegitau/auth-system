package com.joegitau.slick.tables

import com.joegitau.models.User
import com.joegitau.slick.CustomPostgresProfile.api._
import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString
import slick.lifted.ProvenShape

import java.time.Instant

class UserTable(tag: Tag) extends Table[User](tag, "users"){
  def id                = column[Option[PosLong]]("id", O.AutoInc, O.PrimaryKey)
  private def firstName = column[NonEmptyString]("first_name")
  private def lastName  = column[NonEmptyString]("last_name")
  private def email     = column[NonEmptyString]("email")
  private def password  = column[NonEmptyString]("password")
  private def username  = column[NonEmptyString]("username")
  private def role      = column[NonEmptyString]("role")
  private def active    = column[Boolean]("active")
  private def created   = column[Instant]("created")  // Option[Instant]
  private def modified  = column[Instant]("modified") // Option[Instant]


  override def * : ProvenShape[User] = (
    id,
    firstName,
    lastName,
    email,
    password,
    username,
    role,
    active,
    created.?,
    modified.?
  ) <> (User.tupled, User.unapply)
}

object UserTable {
  lazy val Users = TableQuery[UserTable]
}
