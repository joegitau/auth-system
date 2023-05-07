package com.joegitau.utils

import eu.timepit.refined.types.numeric.PosLong
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, parser}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}

import java.time.Clock
import scala.util.Try

case class Claims(userId: PosLong)

object Claims {
  implicit val encoder: Encoder[Claims] = deriveEncoder
  implicit val decoder: Decoder[Claims] = deriveDecoder
}

object JwtTokenGenerator {
  implicit val clock: Clock = Clock.systemUTC()

  private val secretKey = "authSystemKey" // fixme: fetch from config
  private val algorithm = JwtAlgorithm.HS256

  def generateToken(userId: PosLong, expiry: Long): String = {
    val claims = Claims(userId)
    val claim  = JwtClaim(claims.asJson.noSpaces).expiresIn(expiry * 60)

    Jwt.encode(claim, secretKey, algorithm)
  }

  def validateToken(token: String): Option[PosLong] = {
    for {
      jwt    <- Jwt.decodeRaw(token, secretKey, Seq(algorithm)).toOption
      claims <- Try(parser.parse(jwt).flatMap(_.as[Claims])).toOption
    } yield claims.fold(throw _, _.userId)
  }

}
