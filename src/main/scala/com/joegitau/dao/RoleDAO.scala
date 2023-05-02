package com.joegitau
package dao

import com.joegitau.models.Role
import com.joegitau.slick.tables._
import eu.timepit.refined.types.numeric.PosLong
import com.joegitau.slick.CustomPostgresProfile.api._
import com.joegitau.slick.tables.RoleTable.Roles

import java.time.Instant
import scala.concurrent.{ExecutionContext, Future}

trait RoleDAO[F[_]] {
  def insert(role: Role): F[Role]
  def getById(id: PosLong): F[Option[Role]]
  def getAll: F[List[Role]]
  def update(id: PosLong, newRole: Role): F[Option[Role]]
  def delete(id: PosLong): F[String]
}

class RoleDAOImpl(db: Database)(implicit ec: ExecutionContext) extends RoleDAO[Future] {
  private def queryById(id: PosLong) = Compiled(Roles.filter(_.id === id))

  override def insert(role: Role): Future[Role] = {
    val insertAction = Roles returning Roles.map(_.id) into ((r, id) => r.copy(id = id)) += role
    db.run(insertAction)
  }

  override def getById(id: PosLong): Future[Option[Role]] =
    db.run(queryById(id).result.headOption)

  override def getAll: Future[List[Role]] =
    db.run(Roles.result.map(_.toList))

  override def update(id: PosLong, newRole: Role): Future[Option[Role]] = {
    val modifiedRole = newRole.copy(modified = Some(Instant.now))

    val action = for {
      exists <- Roles.filter(_.id === id).exists.result
      result <- if (exists) {
                  queryById(id)
                    .update(modifiedRole)
                    .map(_ => Some(modifiedRole))
                } else DBIO.successful(None)
    } yield result

    db.run(action.transactionally)
  }

  override def delete(id: PosLong): Future[String] = {
    val action = for {
      exists <- Roles.filter(_.id === id).exists.result
      result <- if (exists) {
                  queryById(id)
                    .delete
                    .map(_ => s"Role with id: $id successfully deleted!")
                } else DBIO.successful(s"Role with id: $id not deleted as it doesn't exist!")
    } yield result

    db.run(action)
  }

}
