package com.joegitau.models

import eu.timepit.refined.types.string.NonEmptyString

import java.time.Instant

case class User(
  id:        Option[Long],
  firstName: NonEmptyString,
  lastName:  NonEmptyString,
  email:     NonEmptyString,
  role:      NonEmptyString,
  active:    Boolean,
  created:   Option[Instant] = Some(Instant.now()),
  modified:  Option[Instant] = None
) extends Base
