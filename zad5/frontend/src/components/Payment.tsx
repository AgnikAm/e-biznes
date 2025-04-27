import React, { useState } from "react";
import { Product } from "../App";
import axios from "axios"; // <<--- DODANE

interface Props {
  cart: Product[];
  cartId: number;
  resetCart: () => void;
}

export default function Payment({ cart, cartId, resetCart }: Props) {
  const total = cart.reduce((sum, p) => sum + p.price, 0);
  const [cardNumber, setCardNumber] = useState("");

  const handlePayment = () => {
    if (!cardNumber) {
      alert("Proszę podać numer karty.");
      return;
    }

    const payload = {
      cartId: cartId,
      cardNumber: cardNumber,
    };

    axios.post("http://localhost:8080/payment", payload)
      .then((res) => {
        alert("Płatność zakończona sukcesem! 🎉");
        resetCart();
      })
      .catch((err) => {
        console.error("Błąd płatności:", err);
        alert("Błąd płatności.");
      });
  };

  return (
    <div className="bg-white p-6 rounded shadow max-w-md mx-auto text-left">
      <h2 className="text-xl font-semibold mb-4 text-center">💳 Płatność</h2>

      <p className="mb-4 text-center">
        Do zapłaty: <strong>{total.toFixed(2)} zł</strong>
      </p>

      <label className="block mb-2 font-medium">Numer karty:</label>
      <input
        type="text"
        value={cardNumber}
        onChange={(e) => setCardNumber(e.target.value)}
        className="w-full border px-3 py-2 rounded mb-4"
        placeholder="1234 5678 9012 3456"
      />

      <button
        className="w-full bg-green-600 text-white py-2 rounded hover:bg-green-700 transition"
        onClick={handlePayment}
      >
        Zapłać
      </button>
    </div>
  );
}
