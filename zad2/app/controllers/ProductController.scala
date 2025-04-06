package controllers

import play.api.mvc._
import play.api.libs.json._
import models.Product
import repositories.ProductRepository
import javax.inject._

@Singleton
class ProductController @Inject()(
  val controllerComponents: ControllerComponents,
  productRepository: ProductRepository
) extends BaseController {

  def getAll: Action[AnyContent] = Action {
    Ok(Json.toJson(productRepository.getAll))
  }

  def getById(id: Long): Action[AnyContent] = Action {
    productRepository.getById(id) match {
      case Some(product) => Ok(Json.toJson(product))
      case None => NotFound(Json.obj("error" -> "Product not found"))
    }
  }

  def create: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Product].fold(
      errors => BadRequest(Json.obj("error" -> JsError.toJson(errors))),
      product => {
        // Remove the ID from the request (will be generated by repository)
        val productWithoutId = product.copy(id = 0)
        val createdProduct = productRepository.add(productWithoutId)
        Created(Json.toJson(createdProduct))
      }
    )
  }

  def update(id: Long): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Product].fold(
      errors => BadRequest(Json.obj("error" -> JsError.toJson(errors))),
      product => {
        productRepository.update(id, product) match {
          case Some(updatedProduct) => Ok(Json.toJson(updatedProduct))
          case None => NotFound(Json.obj("error" -> "Product not found"))
        }
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action {
    if (productRepository.delete(id)) {
      NoContent
    } else {
      NotFound(Json.obj("error" -> "Product not found"))
    }
  }
}