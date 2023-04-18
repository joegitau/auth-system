package com.joegitau.slick.tables

import com.joegitau.models.User
import com.joegitau.slick.CustomPostgresProfile.api._
import slick.lifted.ProvenShape

import java.time.Instant

class UserTable(tag: Tag) extends Table[User](tag, "users"){
  private def id        = column[Option[Long]]("id", O.AutoInc, O.PrimaryKey)
  private def firstName = column[String]("first_name")
  private def lastName  = column[String]("last_name")
  private def email     = column[String]("email")
  private def role      = column[String]("role")
  private def created   = column[Instant]("created")  // Option[Instant]
  private def modified  = column[Instant]("modified") // Option[Instant]


  override def * : ProvenShape[User] = (id, firstName, lastName, email, role, created.?, modified.?) <> (User.tupled, User.unapply)
}

object UserTable {
  lazy val users = TableQuery[UserTable]
}
