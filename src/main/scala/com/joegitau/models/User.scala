package com.joegitau.models

import java.time.Instant

case class User(
  id:        Option[Long],
  firstName: String,
  lastName:  String,
  email:     String,
  role:      String,
  created:   Option[Instant] = Some(Instant.now()),
  modified:  Option[Instant] = None
)
