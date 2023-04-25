package com.joegitau.slick

import eu.timepit.refined.types.all.NonEmptyString
import slick.ast.BaseTypedType
import com.joegitau.slick.CustomPostgresProfile.api._

package object tables {
  implicit val nonEmptyStringColumnType: BaseTypedType[NonEmptyString] =
    MappedColumnType.base[NonEmptyString, String](
      nes => nes.value,
      raw => NonEmptyString(raw)
    )

}
