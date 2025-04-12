package routes

import (
	"zad4/controller"

	"github.com/labstack/echo/v4"
	"gorm.io/gorm"
)

func InitRoutes(e *echo.Echo, db *gorm.DB) {
	productController := &controller.ProductController{DB: db}

	e.POST("/products", productController.CreateProduct)
	e.GET("/products", productController.GetAllProducts)
	e.GET("/products/:id", productController.GetProductByID)
	e.PUT("/products/:id", productController.UpdateProduct)
	e.DELETE("/products/:id", productController.DeleteProduct)
}
