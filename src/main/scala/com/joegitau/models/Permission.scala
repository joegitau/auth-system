package com.joegitau.models

import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString

import java.time.Instant

case class Permission(
  id:       Option[PosLong],
  name:     NonEmptyString,
  created:  Option[Instant] = Some(Instant.now()),
  modified: Option[Instant] = None
) extends Base
