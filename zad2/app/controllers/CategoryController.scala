package controllers

import play.api.mvc._
import play.api.libs.json._
import models.Category
import repositories.CategoryRepository
import javax.inject._

@Singleton
class CategoryController @Inject()(
  val controllerComponents: ControllerComponents,
  categoryRepository: CategoryRepository
) extends BaseController {

  implicit val categoryFormat: OFormat[Category] = Json.format[Category]

  // GET /categories
  def getAll: Action[AnyContent] = Action {
    val categories = categoryRepository.getAll
    Ok(Json.toJson(categories))
  }

  // GET /categories/{id}
  def getById(id: Long): Action[AnyContent] = Action {
    categoryRepository.getById(id) match {
      case Some(category) => Ok(Json.toJson(category))
      case None => NotFound(Json.obj("error" -> s"Category with id $id not found"))
    }
  }

  // POST /categories
  def create: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Category].fold(
      errors => {
        BadRequest(Json.obj(
          "error" -> "Invalid JSON format",
          "details" -> JsError.toJson(errors)
        ))
      },
      category => {
        // Ignore any provided ID and let repository generate it
        val categoryToAdd = category.copy(id = 0)
        val createdCategory = categoryRepository.add(categoryToAdd)
        Created(Json.toJson(createdCategory))
          .withHeaders(LOCATION -> s"/categories/${createdCategory.id}")
      }
    )
  }

  // PUT /categories/{id}
  def update(id: Long): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Category].fold(
      errors => {
        BadRequest(Json.obj(
          "error" -> "Invalid JSON format",
          "details" -> JsError.toJson(errors)
        ))
      },
      category => {
        // Ensure the ID in path matches the category ID
        if (category.id != 0 && category.id != id) {
          BadRequest(Json.obj("error" -> "Category ID in path and body don't match"))
        } else {
          val categoryToUpdate = category.copy(id = id)
          categoryRepository.update(id, categoryToUpdate) match {
            case Some(updated) => Ok(Json.toJson(updated))
            case None => NotFound(Json.obj("error" -> s"Category with id $id not found"))
          }
        }
      }
    )
  }

  // DELETE /categories/{id}
  def delete(id: Long): Action[AnyContent] = Action {
    if (categoryRepository.delete(id)) {
      NoContent
    } else {
      NotFound(Json.obj("error" -> s"Category with id $id not found"))
    }
  }
}