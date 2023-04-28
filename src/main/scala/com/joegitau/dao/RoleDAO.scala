package com.joegitau
package dao

import com.joegitau.models.Role
import eu.timepit.refined.types.numeric.PosLong
import com.joegitau.slick.CustomPostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

trait RoleDAO[F[_]] {
  def insert(role: Role): F[Role]
  def getById(id: PosLong): F[Option[Role]]
  def getAll: F[List[Role]]
  def update(id: PosLong, newRole: Role): F[Option[Role]]
  def delete(id: PosLong): F[String]
}

class RoleDAOImpl(db: Database)(implicit ec: ExecutionContext) extends RoleDAO[Future] {
  override def insert(role: Role): Future[Role] = ???

  override def getById(id: PosLong): Future[Option[Role]] = ???

  override def getAll: Future[List[Role]] = ???

  override def update(id: PosLong, newRole: Role): Future[Option[Role]] = ???

  override def delete(id: PosLong): Future[String] = ???
}
