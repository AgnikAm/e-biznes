package controllers

import play.api.mvc._
import play.api.libs.json._
import models.{Cart, CartItem}
import repositories.CartRepository
import javax.inject._

@Singleton
class CartController @Inject()(
  val controllerComponents: ControllerComponents,
  cartRepo: CartRepository
) extends BaseController {

  import models.Cart.{cartFormat, cartItemFormat}

  def getByUser(userId: Long): Action[AnyContent] = Action {
    cartRepo.getByUser(userId) match {
      case Some(cart) => Ok(Json.toJson(cart))
      case None => NotFound(Json.obj("error" -> "Cart not found"))
    }
  }

  def addItem(userId: Long): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[CartItem].fold(
      errors => BadRequest(Json.obj("error" -> JsError.toJson(errors))),
      item => {
        cartRepo.addItem(userId, item) match {
          case Some(cart) => Ok(Json.toJson(cart))
          case None => NotFound
        }
      }
    )
  }

  def removeItem(userId: Long, productId: Long): Action[AnyContent] = Action {
    cartRepo.removeItem(userId, productId) match {
      case Some(cart) => Ok(Json.toJson(cart))
      case None => NotFound
    }
  }
}