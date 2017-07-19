package controllers.api

import play.api.data.validation.ValidationError
import play.api.i18n.Messages
import play.api.libs.json.{JsPath, Json}
import play.api.mvc.{Controller, Result}

abstract class BaseApiController extends Controller {

  val defaultOk = Ok("OK")

  def successMessage(error: String)(implicit messages: Messages): Result = Ok(Json.obj("message" -> Messages(error)))

  def validationErrorsByMessages(errors: Seq[String])(implicit messages: Messages): Result = BadRequest(Json.obj("messages" -> errors.map(Messages(_))))

  def validationErrors(errors: Seq[(JsPath, Seq[ValidationError])])(implicit messages: Messages): Result = {
    val res = errors.map {
      case (path, validationError) =>
        val field = path.path.lastOption.map(v => v.toJsonString.substring(1, v.toJsonString.length))
        val msg = validationError.headOption.map { e => Messages(e.messages.headOption.getOrElse("error.invalid"), e.args: _*) }.getOrElse(Messages("error.invalid"))
        s"${field.map(f => s"${Messages(f)}: ").getOrElse("")}$msg"
    }
    validationErrorsByMessages(res)
  }

}
