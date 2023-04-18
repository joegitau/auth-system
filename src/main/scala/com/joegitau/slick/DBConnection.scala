package com.joegitau.slick

import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database


object DBConnection {
  val Db: JdbcBackend.Database = Database.forConfig("postgres")
}
