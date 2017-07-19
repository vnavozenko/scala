package models.dao

import java.util.Date

import anorm._
import com.google.inject.{ImplementedBy, Inject}
import models.domain.Todo
import models.domain.TodoParsers._
import models.dto.TodoDto
import play.api.db._
import utils.Const

@ImplementedBy(classOf[TodoDaoImpl])
trait TodoDao {

  /**
    * Create todo object
    *
    * @param dto data
    * @return id or empty if error
    */
  def create(dto: TodoDto): Option[Long]

  /**
    * Update todo object
    *
    * @param dto data
    * @return boolean value, updated or not
    */
  def update(dto: TodoDto): Boolean

  /**
    * Get All Todos
    *
    * @return list of existing todos
    */
  def findAll(): List[Todo]

  /**
    * Get all Todos created by user
    *
    * @param createdById user ID
    * @return list of todos created by specified user
    */
  def findAllCreatedBy(createdById: Long): List[Todo]

  /**
    * Get all Todos assigned to user
    *
    * @param assignedToId assigned user id
    * @return list of todos assigned to specified user
    */
  def findAllAssignedTo(assignedToId: Long): List[Todo]

  /**
    * Complete todo
    *
    * @param id todo ID
    * @return boolean value, completed or not
    */
  def complete(id: Long): Boolean

  /**
    * Uncheck complete and resume todo
    *
    * @param id todo ID
    * @return boolean value, resumed or not
    */
  def resume(id: Long): Boolean

  /**
    * Delete Todo object by ID
    *
    * @param id Object identifier
    * @return deleted or not
    */
  def delete(id: Long): Boolean

}

class TodoDaoImpl @Inject()(db: Database) extends BaseDao(db) with TodoDao with Const {

  override def create(dto: TodoDto): Option[Long] = db.withConnection { implicit connection =>
    SQL(
      s"""
          INSERT INTO ${TODO_TB}(title, description, created_by_id, assigned_to_id, created, completed)
              VALUES ({title}, {description}, {createdById}, {assignedToId}, {created}, {completed})
      """
    ).on(
      "title" -> dto.title,
      "description" -> dto.description,
      "createdById" -> dto.createdById,
      "assignedToId" -> dto.assignedToId,
      "created" -> new Date(),
      "completed" -> false
    ).executeInsert()
  }

  override def update(dto: TodoDto): Boolean = db.withConnection { implicit connection =>
    val updated = SQL(
      s"""
          UPDATE ${TODO_TB} SET title={title}, description={description}, created_by_id={createdById}, assigned_to_id={assignedToId}, updated={updated} WHERE id = {id}
      """
    ).on(
      "id" -> dto.id,
      "title" -> dto.title,
      "description" -> dto.description,
      "createdById" -> dto.createdById,
      "assignedToId" -> dto.assignedToId,
      "updated" -> new Date()
    ).executeUpdate()

    updated > 0
  }

  override def findAll(): List[Todo] = db.withConnection { implicit connection =>
    SQL(s"SELECT * FROM ${TODO_TB}").as(todoParser.*)
  }

  override def findAllCreatedBy(createdById: Long): List[Todo] = db.withConnection { implicit connection =>
    SQL(s"SELECT * FROM ${TODO_TB} WHERE created_by_id = {id}")
      .on('id -> createdById)
      .as(todoParser.*)
  }

  override def findAllAssignedTo(assignedToId: Long): List[Todo] = db.withConnection { implicit connection =>
    SQL(s"SELECT * FROM ${TODO_TB} WHERE assigned_to_id = {id}")
      .on('id -> assignedToId)
      .as(todoParser.*)
  }

  override def complete(id: Long): Boolean = db.withConnection { implicit connection =>
    val updated = SQL(s"UPDATE ${TODO_TB} SET completed={completed}, completed_date={completed_date} WHERE id = {id}")
      .on('id -> id, 'completed -> true, 'completed_date -> new Date())
      .executeUpdate()

    updated > 0
  }

  override def resume(id: Long): Boolean = db.withConnection { implicit connection =>
    val updated = SQL(s"UPDATE ${TODO_TB} SET completed={completed}, completed_date=NULL WHERE id = {id}")
      .on('id -> id, 'completed -> false)
      .executeUpdate()

    updated > 0
  }

  override def delete(id: Long): Boolean = {
    db.withConnection { implicit connection =>
      SQL(s"DELETE FROM ${COMMENT_TB} WHERE todo_id = {id}")
        .on('id -> id)
        .executeUpdate()
    }

    deleteItemById(id, TODO_TB)
  }

}
