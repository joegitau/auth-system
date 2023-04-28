package com.joegitau
package models

import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString

import java.time.Instant

case class Permission(
  id:       Option[PosLong],
  name:     NonEmptyString,
  created:  Instant,
  modified: Option[Instant]
) extends Base
