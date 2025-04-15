import React, { useState } from "react";
import ProductList from "./components/ProductList";
import Cart from "./components/Cart";
import Payment from "./components/Payment";

export interface Product {
  id: number;
  name: string;
  price: number;
}

export default function App() {
  const [cart, setCart] = useState<Product[]>([]);
  const [view, setView] = useState<"products" | "cart" | "payment">("products");

  const addToCart = (product: Product) => {
    setCart([...cart, product]);
  };

  const resetCart = () => {
    setCart([]);
    setView("products");
  };

  return (
    <div className="p-6 font-sans">
      <h1 className="text-3xl font-bold mb-4">🛍️ Sklep Internetowy</h1>

      {view === "products" && (
        <>
          <ProductList addToCart={addToCart} />
          <button
            className="mt-4 bg-blue-500 text-white px-4 py-2 rounded"
            onClick={() => setView("cart")}
          >
            Przejdź do koszyka ({cart.length})
          </button>
        </>
      )}

      {view === "cart" && (
        <>
          <Cart cart={cart} />
          <div className="mt-4 space-x-2">
            <button
              className="bg-green-500 text-white px-4 py-2 rounded"
              onClick={() => setView("payment")}
            >
              Przejdź do płatności
            </button>
            <button
              className="bg-gray-300 px-4 py-2 rounded"
              onClick={() => setView("products")}
            >
              Powrót do produktów
            </button>
          </div>
        </>
      )}

      {view === "payment" && (
        <>
          <Payment cart={cart} resetCart={resetCart} />
          <button
            className="mt-4 bg-gray-300 px-4 py-2 rounded"
            onClick={() => setView("cart")}
          >
            Powrót do koszyka
          </button>
        </>
      )}
    </div>
  );
}
