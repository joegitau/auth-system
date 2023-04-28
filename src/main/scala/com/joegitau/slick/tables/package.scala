package com.joegitau.slick

import com.joegitau.models.{Permission, Role}
import com.joegitau.slick.CustomPostgresProfile.api._
import com.sun.jdi.InvalidTypeException
import eu.timepit.refined.types.numeric.{PosInt, PosLong}
import eu.timepit.refined.types.string.{NonEmptyString, TrimmedString}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.parser._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import slick.ast.BaseTypedType

package object tables {
  implicit val nonEmptyStringColumnType: BaseTypedType[NonEmptyString] =
    MappedColumnType.base[NonEmptyString, String](
      nes => nes.value,
      str => NonEmptyString(str)
    )

  implicit val posIntColumnType: BaseTypedType[PosInt] =
    MappedColumnType.base[PosInt, Int](
      pi => pi.value,
      i  => PosInt(i)
    )

  implicit val posLongColumnType: BaseTypedType[PosLong] =
    MappedColumnType.base[PosLong, Long](
      pl => pl.value,
      l => PosLong(l)
    )

  implicit val trimmedStrColumnType: BaseTypedType[TrimmedString] =
    MappedColumnType.base[TrimmedString, String](
      ts    => ts.value,
      jsStr => TrimmedString(jsStr)
    )

  implicit val roleColumnType: BaseColumnType[Role] =
    MappedColumnType.base[Role, String](
      role  => role.asJson.spaces2,
      jsStr => decode[Role](jsStr).getOrElse(throw new InvalidTypeException("Invalid [role] json string."))
    )

  implicit val roleListColumnType: BaseColumnType[List[Role]] =
    MappedColumnType.base[List[Role], String](
      roles => roles.asJson.spaces2,
      jsStr => decode[List[Role]](jsStr).getOrElse(Nil)
    )

  implicit val permissionColumnType: BaseColumnType[Permission] =
    MappedColumnType.base[Permission, String](
      perm  => perm.asJson.spaces2,
      jsStr => decode[Permission](jsStr).getOrElse(throw new InvalidTypeException("Invalid [permission] json string."))
    )

  implicit val permissionListColumnType: BaseColumnType[List[Permission]] =
    MappedColumnType.base[List[Permission], String](
      perms => perms.asJson.spaces2,
      jsStr => decode[List[Permission]](jsStr).getOrElse(Nil)
    )

  // encoders & decoders
  implicit val roleEncoder: Encoder[Role]             = deriveEncoder
  implicit val roleDecoder: Decoder[Role]             = deriveDecoder
  implicit val permissionEncoder: Encoder[Permission] = deriveEncoder
  implicit val permissionDecoder: Decoder[Permission] = deriveDecoder
}

/**
 * TIP OF THE DAY!!
 * "MappedColumnType" is a Slick type class that is used to define conversions between the Scala types
 * used in your code and the types used in your database.
 * It allows you to map a Scala type to a SQL type and vice versa, so that you can read and write data
 * to and from your database using the Scala types that you are most comfortable with.
 */
