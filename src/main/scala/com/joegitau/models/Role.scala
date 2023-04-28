package com.joegitau
package models

import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString

import java.time.Instant

case class Role(
  id:          Option[PosLong],
  name:        NonEmptyString,
  permissions: List[Permission],
  created:     Instant,
  modified:    Option[Instant]
) extends Base
