package com.joegitau.models

import java.time.Instant

case class Role(
  id:       Option[Int],
  name:     String,
  created:  Option[Instant] = Some(Instant.now()),
  modified: Option[Instant] = None
)
