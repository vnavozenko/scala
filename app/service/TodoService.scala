package service

import com.google.inject.{ImplementedBy, Inject}
import models.dao.TodoDao
import models.domain.Todo
import models.dto.TodoDto
import utils.Const

@ImplementedBy(classOf[TodoServiceImpl])
trait TodoService {

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

class TodoServiceImpl @Inject()(todoDao: TodoDao) extends BaseService with TodoService with Const {

  def create(dto: TodoDto): Option[Long] = todoDao.create(dto)

  def update(dto: TodoDto): Boolean = todoDao.update(dto)

  def findAll(): List[Todo] = todoDao.findAll()

  def findAllCreatedBy(createdById: Long): List[Todo] = todoDao.findAllCreatedBy(createdById)

  def findAllAssignedTo(assignedToId: Long): List[Todo] = todoDao.findAllAssignedTo(assignedToId)

  def complete(id: Long): Boolean = todoDao.complete(id)

  def resume(id: Long): Boolean = todoDao.resume(id)

  def delete(id: Long): Boolean = todoDao.delete(id)

}
