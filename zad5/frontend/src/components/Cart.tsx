import React from "react";
import { Product } from "../App";

interface Props {
  cart: Product[];
}

export default function Cart({ cart }: Props) {
  const total = cart.reduce((sum, p) => sum + p.price, 0);

  return (
    <div>
      <h2 className="text-xl font-semibold mb-2">🛒 Koszyk</h2>
      {cart.length === 0 ? (
        <p>Koszyk jest pusty.</p>
      ) : (
        <ul className="space-y-2">
          {cart.map((product, index) => (
            <li key={index} className="border p-2 rounded">
              {product.name} - {product.price.toFixed(2)} zł
            </li>
          ))}
        </ul>
      )}
      <p className="mt-4 font-bold">Suma: {total.toFixed(2)} zł</p>
    </div>
  );
}
