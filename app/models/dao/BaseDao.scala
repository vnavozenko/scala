package models.dao

import javax.inject.Inject

import anorm.SQL
import play.api.db.Database

abstract class BaseDao @Inject()(db: Database) {

  def deleteItemById(id: Long, tableName: String): Boolean = db.withConnection { implicit connection =>
    val deleted = SQL(s"DELETE FROM ${tableName} WHERE id = {id}")
      .on('id -> id)
      .executeUpdate()

    deleted > 0
  }

}
