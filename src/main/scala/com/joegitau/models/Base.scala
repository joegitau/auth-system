package com.joegitau.models

import java.time.Instant

trait Base {
  def created: Option[Instant]
  def modified: Option[Instant]
}
