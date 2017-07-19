package controllers.api

import javax.inject.Inject

import models.data.CommonReadWriters._
import models.dto.TodoDto
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, BodyParsers}
import service.TodoService

class ApiTodoController @Inject()(todoService: TodoService, val messagesApi: MessagesApi) extends BaseApiController with I18nSupport {

  def createOrUpdate = Action(BodyParsers.parse.json) { request =>
    request.body.validate[TodoDto].fold(
      errors => validationErrors(errors),
      todoDto => {
        if (todoDto.id.isDefined) {
          todoService.update(todoDto) match {
            case true => successMessage(s"Successfully updated todo")
            case _ => validationErrorsByMessages(Seq("invalid.error"))
          }
        } else {
          todoService.create(todoDto) match {
            case Some(id) => successMessage(s"Successfully todo created, ID = $id")
            case _ => validationErrorsByMessages(Seq("invalid.error"))
          }
        }
      })
  }

  def findAll = Action {
    Ok(Json.toJson(todoService.findAll()))
  }

  def findAllCreatedBy(userId: Long) = Action {
    Ok(Json.toJson(todoService.findAllCreatedBy(userId)))
  }

  def findAllAssignedTo(userId: Long) = Action {
    Ok(Json.toJson(todoService.findAllAssignedTo(userId)))
  }

  def complete(todoId: Long) = Action {
    Ok(Json.toJson(todoService.complete(todoId)))
  }

  def resume(todoId: Long) = Action {
    Ok(Json.toJson(todoService.resume(todoId)))
  }

  def delete(id: Long) = Action {
    Ok(Json.toJson(todoService.delete(id)))
  }

}
