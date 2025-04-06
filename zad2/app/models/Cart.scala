package models

import play.api.libs.json.{Json, OFormat}

case class CartItem(
  productId: Long,
  quantity: Int
)

case class Cart(
  id: Long,
  userId: Long,
  items: Seq[CartItem]
)

object Cart {
  implicit val cartItemFormat: OFormat[CartItem] = Json.format[CartItem]
  implicit val cartFormat: OFormat[Cart] = Json.format[Cart]
}