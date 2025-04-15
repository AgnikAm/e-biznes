import React, { useEffect, useState } from "react";
import { Product } from "../App";

interface Props {
  addToCart: (product: Product) => void;
}

export default function ProductList({ addToCart }: Props) {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/products")
      .then((res) => res.json())
      .then((data) => setProducts(data))
      .catch((err) => console.error("Błąd ładowania produktów:", err));
  }, []);

  return (
    <div>
      <h2 className="text-xl font-semibold mb-2">Produkty</h2>
      <div className="grid gap-4 md:grid-cols-2">
        {products.map((product) => (
          <div
            key={product.id}
            className="border rounded p-4 shadow-sm bg-white"
          >
            <h3 className="font-bold text-lg">{product.name}</h3>
            <p className="text-gray-700">{product.price.toFixed(2)} zł</p>
            <button
              className="mt-2 bg-blue-600 text-white px-3 py-1 rounded"
              onClick={() => addToCart(product)}
            >
              Dodaj do koszyka
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
