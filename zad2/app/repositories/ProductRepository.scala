package repositories

import models.Product
import scala.collection.mutable
import javax.inject._

@Singleton
class ProductRepository @Inject()() {
  private var nextId: Long = 6 // Starting after initial 5 books
  private val products = mutable.ListBuffer(
    Product(1, "Norwegian Wood", 24.99),
    Product(2, "Kafka on the Shore", 27.50),
    Product(3, "The City and Its Uncertain Walls", 32.00)
  )

  def getAll: Seq[Product] = products.toSeq

  def getById(id: Long): Option[Product] = products.find(_.id == id)

  def add(product: Product): Product = {
    val newProduct = product.copy(id = nextId)
    nextId += 1
    products += newProduct
    newProduct
  }

  def update(id: Long, product: Product): Option[Product] = {
    val index = products.indexWhere(_.id == id)
    if (index >= 0) {
      val updatedProduct = product.copy(id = id)
      products.update(index, updatedProduct)
      Some(updatedProduct)
    } else {
      None
    }
  }

  def delete(id: Long): Boolean = {
    val index = products.indexWhere(_.id == id)
    if (index >= 0) {
      products.remove(index)
      true
    } else {
      false
    }
  }
}