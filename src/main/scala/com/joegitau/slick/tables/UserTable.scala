package com.joegitau.slick.tables

import com.joegitau.models.User
import com.joegitau.slick.CustomPostgresProfile.api._
import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString
import slick.lifted.ProvenShape

import java.time.Instant

class UserTable(tag: Tag) extends Table[User](tag, "users"){
  def id                = column[Option[PosLong]]("id", O.AutoInc, O.PrimaryKey)
  def firstName = column[NonEmptyString]("first_name")
  def lastName  = column[NonEmptyString]("last_name")
  def email     = column[NonEmptyString]("email")
  def password  = column[NonEmptyString]("password")
  def username  = column[NonEmptyString]("username")
  def role      = column[NonEmptyString]("role")
  def active    = column[Boolean]("active")
  def created   = column[Instant]("created")
  def modified  = column[Option[Instant]]("modified")

  override def * : ProvenShape[User] = (
    id,
    firstName,
    lastName,
    email,
    password,
    username,
    role,
    active,
    created,
    modified
  ) <> (User.tupled, User.unapply)
}

object UserTable {
  lazy val Users = TableQuery[UserTable]
}
