package repositories

import models.Category
import scala.collection.mutable
import javax.inject._

@Singleton
class CategoryRepository @Inject()() {
  private var nextId: Long = 4
  private val categories = mutable.ListBuffer(
    Category(1, "Fiction"),
    Category(2, "Fantasy"),
    Category(3, "Science Fiction")
  )

  def getAll: Seq[Category] = categories.toSeq

  def getById(id: Long): Option[Category] = categories.find(_.id == id)
  
  def add(category: Category): Category = {
    val newCategory = category.copy(id = nextId)
    nextId += 1
    categories += newCategory
    newCategory
  }

  def update(id: Long, category: Category): Option[Category] = {
    val index = categories.indexWhere(_.id == id)
    if (index >= 0) {
      val updated = category.copy(id = id)
      categories.update(index, updated)
      Some(updated)
    } else None
  }

  def delete(id: Long): Boolean = {
    val index = categories.indexWhere(_.id == id)
    if (index >= 0) {
      categories.remove(index)
      true
    } else false
  }
}