package com.joegitau
package dao

import com.joegitau.models.Permission
import com.joegitau.slick.CustomPostgresProfile.api._
import com.joegitau.slick.tables.PermissionTable.Permissions
import com.joegitau.slick.tables.posLongColumnType
import eu.timepit.refined.types.numeric.PosLong

import scala.concurrent.{ExecutionContext, Future}

trait PermissionDAO[F[_]] {
  def insert(permission: Permission): F[Permission]
  def getById(id: PosLong): F[Option[Permission]]
  def getAll: F[List[Permission]]
  def update(id: PosLong, newPermission: Permission): F[Option[Permission]]
  def delete(id: PosLong): F[String]
}

class PermissionDAOImpl(db: Database)(implicit ec: ExecutionContext) extends PermissionDAO[Future] {
  private def queryById(id: PosLong) = Compiled(Permissions.filter(_.id === id))

  override def insert(permission: Permission): Future[Permission] = {
    val insertAction = Permissions returning Permissions.map(_.id) into ((perm, id) => perm.copy(id = id)) += permission
    db.run(insertAction)
  }

  override def getById(id: PosLong): Future[Option[Permission]] =
    db.run(queryById(id).result.headOption)

  override def getAll: Future[List[Permission]] =
    db.run(Permissions.result.map(_.toList))

  override def delete(id: PosLong): Future[String] = {
    db.run(queryById(id).delete)
      .map(_ => s"Successfully deleted permission with id: $id")
  }

  override def update(id: PosLong, newPermission: Permission): Future[Option[Permission]] = {
    // query by id
    // if found, we update it, if not return an option
    val permToUpdate = queryById(id).result.headOption
    permToUpdate match {
      case perm => ???

    }
  }
}
