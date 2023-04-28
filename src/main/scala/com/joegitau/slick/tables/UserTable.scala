package com.joegitau.slick
package tables

import com.joegitau.models.{Role, User}
import com.joegitau.slick.CustomPostgresProfile.api._
import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.{NonEmptyString, TrimmedString}
import slick.lifted.ProvenShape

import java.time.Instant
import java.util.UUID

class UserTable(tag: Tag) extends Table[User](tag, "users"){
  def id              = column[Option[PosLong]]("id", O.AutoInc, O.PrimaryKey)
  def firstName       = column[NonEmptyString]("first_name")
  def lastName        = column[NonEmptyString]("last_name")
  def email           = column[TrimmedString]("email")
  def password        = column[NonEmptyString]("password")
  def username        = column[TrimmedString]("username")
  def roles           = column[List[Role]]("roles")
  def active          = column[Boolean]("active")
  def lastLogin       = column[Option[Instant]]("last_login")
  def activationToken = column[Option[UUID]]("activation_token")
  def resetToken      = column[Option[UUID]]("reset_token")
  def created         = column[Instant]("created")
  def modified        = column[Option[Instant]]("modified")

  override def * : ProvenShape[User] = (
    id,
    firstName,
    lastName,
    email,
    password,
    username,
    roles,
    active,
    lastLogin,
    activationToken,
    resetToken,
    created,
    modified
  ) <> (User.tupled, User.unapply)
}

object UserTable {
  lazy val Users = TableQuery[UserTable]
}
