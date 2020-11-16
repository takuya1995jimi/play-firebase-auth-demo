package controllers

import com.google.inject.{Inject, Singleton}
import dto.LiftTypeRequest
import org.json4s.DefaultFormats
import org.json4s.native.{JsonMethods, Serialization}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import usecase.UpdateLiftTypeService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UpdateLiftTypeController @Inject()
(controllerComponents: ControllerComponents, updateLiftTypeService: UpdateLiftTypeService)
(implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents) {

  implicit val formats: DefaultFormats.type = DefaultFormats

  def update(liftTypeId: Int): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    JsonMethods.parse(request.body.asJson.get.toString).extractOpt[LiftTypeRequest] match {
      case Some(value) => updateLiftTypeService(liftTypeId, value).map(resp => Ok(resp.toString))
      case None => Future(BadRequest(Serialization.write(Map("message" -> "bad request"))))
    }
  }
}
