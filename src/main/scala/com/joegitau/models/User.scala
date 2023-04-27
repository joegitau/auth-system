package com.joegitau.models

import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.{NonEmptyString, TrimmedString}

import java.time.Instant
import java.util.UUID

case class User(
  id:              Option[PosLong],
  firstName:       NonEmptyString,
  lastName:        NonEmptyString,
  email:           TrimmedString,
  password:        NonEmptyString,
  username:        TrimmedString,
  roles:           List[Role],
  active:          Boolean,
  lastLogin:       Option[Instant],
  activationToken: Option[UUID],
  resetToken:      Option[UUID],
  created:         Instant,
  modified:        Option[Instant]
) extends Base
