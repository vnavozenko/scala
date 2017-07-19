package models.dao

import java.util.Date

import anorm.SQL
import com.google.inject.{ImplementedBy, Inject}
import models.domain.Comment
import models.domain.TodoParsers.commentParser
import models.dto.CommentDto
import play.api.db.Database
import utils.Const

@ImplementedBy(classOf[CommentDaoImpl])
trait CommentDao {

  /**
    * Add comment to Todo
    *
    * @param dto data
    * @return created comment ID
    */
  def create(dto: CommentDto): Option[Long]

  /**
    * Get all comments for todo
    *
    * @param todoId todo ID
    * @return all comments assigned to todo
    */
  def findAll(todoId: Long): List[Comment]

  /**
    * Delete comment by ID
    *
    * @param id Object identifier
    * @return deleted or not
    */
  def delete(id: Long): Boolean

}

class CommentDaoImpl @Inject()(db: Database) extends BaseDao(db) with CommentDao with Const {

  override def create(dto: CommentDto): Option[Long] = db.withConnection { implicit connection =>
    SQL(
      s"""
          INSERT INTO ${COMMENT_TB}(description, todo_id, created_by_id, created)
              VALUES ({description}, {todoId}, {createdById}, {created})
      """
    ).on(
      "description" -> dto.description,
      "createdById" -> dto.createdById,
      "todoId" -> dto.todoId,
      "created" -> new Date()
    ).executeInsert()
  }

  override def findAll(todoId: Long): List[Comment] = db.withConnection { implicit connection =>
    SQL(s"SELECT * FROM ${COMMENT_TB} WHERE todo_id = {id}")
      .on('id -> todoId)
      .as(commentParser.*)
  }

  override def delete(id: Long): Boolean = deleteItemById(id, COMMENT_TB)

}
