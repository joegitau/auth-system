package com.joegitau.slick

import com.github.tminglei.slickpg._
import io.circe.Json
import io.circe.parser._
import io.circe.syntax._

trait CustomPostgresProfile extends ExPostgresProfile
  with PgArraySupport
  with PgDate2Support
  with PgHStoreSupport
  with PgJsonSupport
  with PgCirceJsonSupport {

  def pgjson: String = "jsonb"

  object CustomAPI extends API
    with ArrayImplicits
    with DateTimeImplicits
    with HStoreImplicits
    with CirceJsonPlainImplicits {

    implicit val strListTypeMapper: DriverJdbcType[List[String]] =
      new SimpleArrayJdbcType[String]("text").to(_.toList)

    implicit val longListTypeMapper: DriverJdbcType[List[Long]] =
      new SimpleArrayJdbcType[Long]("bigint").to(_.toList)

    implicit val circeJsonArrayTypeMapper: DriverJdbcType[List[Json]] =
      new AdvancedArrayJdbcType[Json](
        pgjson,
        s => decode[List[Json]](s).fold(throw _, identity),
        v => v.asJson.noSpaces
      ).to(_.toList)

  }
}

object CustomPostgresProfile extends CustomPostgresProfile

