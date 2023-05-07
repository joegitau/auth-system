package com.joegitau
package dao

import com.joegitau.models.{Role, User, UserRole}
import com.joegitau.slick.tables._
import com.joegitau.slick.CustomPostgresProfile.api._
import com.joegitau.slick.tables.RelationsTable.UserRoleTable.UserRoleRelations
import eu.timepit.refined.types.numeric.PosLong

import java.time.Instant
import scala.concurrent.{ExecutionContext, Future}

trait UserRoleDAO[F[_]] {
  // FIXME: differentiate userId from roleId
  def insert(userId: PosLong, roleId: PosLong): F[String]
  def getRolesByUserId(userId: PosLong): F[List[Role]]
  def getUsersByRoleId(roleId: PosLong): F[List[User]]
  def delete(userId: PosLong, roleId: PosLong): F[String]
}

class UserRoleDAOImpl(db: Database)(implicit ec: ExecutionContext) extends UserRoleDAO[Future] {
  override def insert(userId: PosLong, roleId: PosLong): Future[String] = {
    val userRole = UserRole(userId, roleId, Instant.now, None)
    val action   = UserRoleRelations += userRole

    db.run(action)
      .map(_ => "Successfully established a UserRole relation.")
      .recover { case e: Exception => e.getMessage }
  }

  override def getRolesByUserId(userId: PosLong): Future[List[Role]] = {
    val query = for {
      userRole <- UserRoleRelations if userRole.userId === userId
      role     <- userRole.roleFK
    } yield role

    db.run(query.result).map(_.toList)
  }

  override def getUsersByRoleId(roleId: PosLong): Future[List[User]] = {
    val query = for {
      userRole <- UserRoleRelations if  userRole.roleId === roleId
      user     <- userRole.userFK
    } yield user

    db.run(query.result).map(_.toList)
  }

  override def delete(userId: PosLong, roleId: PosLong): Future[String] = {
    val query = UserRoleRelations.filter(r => r.userId === userId && r.roleId === roleId)

    db.run(query.delete)
      .map(_ => s"Successfully deleted userId: $userId & roleId: $roleId relation!")
      .recover { case ex: Exception => ex.getMessage }
  }

}
