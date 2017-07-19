package models.data

import java.util.Date

import models.domain.{Comment, Todo, User}
import models.dto.{CommentDto, TodoDto, UserDto}

object CommonReadWriters {

  import play.api.libs.functional.syntax._
  import play.api.libs.json.Reads._
  import play.api.libs.json._

  implicit val UserFormat: OFormat[User] = Json.format[User]
  implicit val TodoFormat: OFormat[Todo] = Json.format[Todo]
  implicit val CommentFormat: OFormat[Comment] = Json.format[Comment]

  implicit val createOrUpdateTodo: Reads[TodoDto] = (
    (__ \ "id").readNullable[Long] and
      (__ \ "title").read[String](minLength[String](4)) and
      (__ \ "description").read[String](minLength[String](12)) and
      (__ \ "createdById").read[Long] and
      (__ \ "assignedToId").readNullable[Long] and
      (__ \ "created").readNullable[Date] and
      (__ \ "updated").readNullable[Date] and
      (__ \ "completed").readNullable[Boolean] and
      (__ \ "completedDate").readNullable[Date]
    ) (TodoDto.apply _)

  implicit val createComment: Reads[CommentDto] = (
    (__ \ "id").readNullable[Long] and
      (__ \ "description").read[String](minLength[String](12)) and
      (__ \ "todoId").read[Long] and
      (__ \ "createdById").read[Long] and
      (__ \ "created").readNullable[Date]
    ) (CommentDto.apply _)

  implicit val createUser: Reads[UserDto] = (
    (__ \ "id").readNullable[Long] and
      (__ \ "email").read[String](email)
    ) (UserDto.apply _)

}
