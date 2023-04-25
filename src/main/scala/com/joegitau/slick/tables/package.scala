package com.joegitau.slick

import eu.timepit.refined.types.all.NonEmptyString
import slick.ast.BaseTypedType
import com.joegitau.slick.CustomPostgresProfile.api._
import eu.timepit.refined.types.numeric.{PosInt, PosLong}

package object tables {
  implicit val nonEmptyStringColumnType: BaseTypedType[NonEmptyString] =
    MappedColumnType.base[NonEmptyString, String](
      nes => nes.value,
      raw => NonEmptyString(raw)
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
}
