package com.joegitau.models

import eu.timepit.refined.types.string.NonEmptyString

import java.time.Instant

case class Role(
  id:       Option[Int],
  name:     NonEmptyString,
  created:  Option[Instant] = Some(Instant.now()),
  modified: Option[Instant] = None
) extends Base
