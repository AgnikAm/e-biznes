package models

import play.api.libs.json.{Json, OFormat}

case class Product(
  id: Long,
  name: String,
  price: BigDecimal,
  categoryId: Long
)

object Product:
  given productFormat: OFormat[Product] = Json.format[Product]