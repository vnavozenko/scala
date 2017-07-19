package controllers.api

import javax.inject.Inject

import models.data.CommonReadWriters._
import models.dto.UserDto
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, BodyParsers}
import service.UserService

class ApiUserController @Inject()(userService: UserService, val messagesApi: MessagesApi) extends BaseApiController with I18nSupport {

  def findAll = Action {
    Ok(Json.toJson(userService.findAll()))
  }

  def create = Action(BodyParsers.parse.json) { request =>
    request.body.validate[UserDto].fold(
      errors => validationErrors(errors),
      userDto => {
        userService.create(userDto.email) match {
          case Some(id) => successMessage(s"Successfully user created, ID = $id")
          case _ => validationErrorsByMessages(Seq("invalid.error"))
        }
      })
  }

}
