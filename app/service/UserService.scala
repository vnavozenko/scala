package service

import com.google.inject.{ImplementedBy, Inject}
import models.dao.UserDao
import models.domain.User
import utils.Const

@ImplementedBy(classOf[UserServiceImpl])
trait UserService {

  /**
    * Create user
    *
    * @param email email
    * @return created user ID
    */
  def create(email: String): Option[Long]

  /**
    * Get all users
    *
    * @return available users
    */
  def findAll(): List[User]

}

class UserServiceImpl @Inject()(userDao: UserDao) extends BaseService with UserService with Const {

  override def create(email: String): Option[Long] = userDao.create(email)

  override def findAll(): List[User] = userDao.findAll()

}