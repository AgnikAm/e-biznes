import React from "react";
import { Product } from "../App";

interface Props {
  cart: Product[];
  resetCart: () => void;
}

export default function Payment({ cart, resetCart }: Props) {
  const total = cart.reduce((sum, p) => sum + p.price, 0);

  const handlePayment = () => {
    const payload = {
      total: total,
      products: cart.map((p) => p.id),
    };

    fetch("http://localhost:8080/payment", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then((res) => {
        if (res.ok) {
          alert("Płatność zakończona sukcesem! 🎉");
          resetCart();
        } else {
          alert("Błąd płatności.");
        }
      })
      .catch(() => alert("Błąd połączenia z serwerem."));
  };

  return (
    <div>
      <h2 className="text-xl font-semibold mb-2">💳 Płatność</h2>
      <p>Do zapłaty: <strong>{total.toFixed(2)} zł</strong></p>
      <button
        className="mt-4 bg-green-600 text-white px-4 py-2 rounded"
        onClick={handlePayment}
      >
        Zapłać
      </button>
    </div>
  );
}
