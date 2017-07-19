package controllers.api

import javax.inject.Inject

import models.data.CommonReadWriters._
import models.dto.CommentDto
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, BodyParsers}
import service.CommentService

class ApiCommentController @Inject()(commentService: CommentService, val messagesApi: MessagesApi) extends BaseApiController with I18nSupport {

  def findAll(todoId: Long) = Action {
    Ok(Json.toJson(commentService.findAll(todoId)))
  }

  def create = Action(BodyParsers.parse.json) { request =>
    request.body.validate[CommentDto].fold(
      errors => validationErrors(errors),
      commentDto => {
        commentService.create(commentDto) match {
          case Some(id) => successMessage(s"Successfully comment added, ID = $id")
          case _ => validationErrorsByMessages(Seq("invalid.error"))
        }
      })
  }

  def delete(commentId: Long) = Action {
    Ok(Json.toJson(commentService.delete(commentId)))
  }

}
