package models

import "gorm.io/gorm"

type Category struct {
	gorm.Model
	Name     string    `json:"name"`
	Products []Product `gorm:"foreignKey:CategoryID"`
}

func WithProducts(db *gorm.DB) *gorm.DB {
	return db.Preload("Products")
}
