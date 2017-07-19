package service

import com.google.inject.{ImplementedBy, Inject}
import models.dao.CommentDao
import models.domain.Comment
import models.dto.CommentDto
import utils.Const

@ImplementedBy(classOf[CommentServiceImpl])
trait CommentService {

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

class CommentServiceImpl @Inject()(commentDao: CommentDao) extends BaseService with CommentService with Const {

  def create(dto: CommentDto): Option[Long] = commentDao.create(dto)

  def findAll(todoId: Long): List[Comment] = commentDao.findAll(todoId)

  def delete(id: Long): Boolean = commentDao.delete(id)

}
