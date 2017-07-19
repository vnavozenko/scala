package models.dao

import anorm.SQL
import com.google.inject.{ImplementedBy, Inject}
import models.domain.User
import models.domain.UserParsers._
import play.api.db.Database
import utils.Const

@ImplementedBy(classOf[UserDaoImpl])
trait UserDao {

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

class UserDaoImpl @Inject()(db: Database) extends BaseDao(db) with UserDao with Const {

  override def create(email: String): Option[Long] = db.withConnection { implicit connection =>
    SQL(s"INSERT INTO ${USER_TB}(email) VALUES ({email})")
      .on("email" -> email).executeInsert()
  }

  override def findAll(): List[User] = db.withConnection { implicit connection =>
    SQL(s"SELECT * FROM ${USER_TB}").as(userParser.*)
  }

}