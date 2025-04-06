package repositories

import models.{Cart, CartItem}
import scala.collection.mutable
import javax.inject._

@Singleton
class CartRepository @Inject()() {
  private var nextId: Long = 2
  private val carts = mutable.ListBuffer(
    Cart(1, 1, Seq(CartItem(1, 2), CartItem(3, 1)))
  )

  // GET /cart/{user-id}
  def getByUser(userId: Long): Option[Cart] = carts.find(_.userId == userId)

  // POST /cart/{user-id}/items
  def addItem(userId: Long, item: CartItem): Option[Cart] = {
    carts.indexWhere(_.userId == userId) match {
      case -1 => None
      case index =>
        val cart = carts(index)
        val updatedItems = cart.items :+ item
        val updatedCart = cart.copy(items = updatedItems)
        carts.update(index, updatedCart)
        Some(updatedCart)
    }
  }

  // DELETE /cart/{user-id}/items/{product-id}
  def removeItem(userId: Long, productId: Long): Option[Cart] = {
    carts.indexWhere(_.userId == userId) match {
      case -1 => None
      case index =>
        val cart = carts(index)
        val updatedItems = cart.items.filter(_.productId != productId)
        val updatedCart = cart.copy(items = updatedItems)
        carts.update(index, updatedCart)
        Some(updatedCart)
    }
  }
}