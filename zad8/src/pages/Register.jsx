import { useState } from 'react';
import axios from 'axios';
import '../AuthForm.css';

function Register() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post('http://localhost:4000/auth/register', {
        email,
        password,
      });
      alert(res.data.message);
    } catch (err) {
      alert(err.response?.data?.message || 'Błąd rejestracji');
    }
  };

  return (
    <div className="auth-container">
      <form className="auth-card" onSubmit={handleRegister}>
        <h2>Rejestracja</h2>
        <input type="email" placeholder="Email" onChange={e => setEmail(e.target.value)} required />
        <input type="password" placeholder="Hasło" onChange={e => setPassword(e.target.value)} required />
        <button type="submit">Zarejestruj się</button>
      </form>
    </div>
  );
}

export default Register;
