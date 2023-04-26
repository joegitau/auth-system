package com.joegitau.models

import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString

import java.time.Instant

case class User(
  id:        Option[PosLong],
  firstName: NonEmptyString,
  lastName:  NonEmptyString,
  email:     NonEmptyString,
  password:  NonEmptyString,
  username:  NonEmptyString,
  role:      NonEmptyString,
  active:    Boolean,
  created:   Instant,
  modified:  Option[Instant]
) extends Base
