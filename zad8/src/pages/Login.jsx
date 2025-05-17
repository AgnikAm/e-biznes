import { useState } from 'react';
import axios from 'axios';
import '../AuthForm.css';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post('http://localhost:4000/auth/login', {
        email,
        password,
      });
      alert('Zalogowano!');
      localStorage.setItem('token', res.data.token);
    } catch (err) {
      alert(err.response?.data?.message || 'Błąd logowania');
    }
  };

  return (
    <div className="auth-container">
      <form className="auth-card" onSubmit={handleLogin}>
        <h2>Logowanie</h2>
        <input type="email" placeholder="Email" onChange={e => setEmail(e.target.value)} required />
        <input type="password" placeholder="Hasło" onChange={e => setPassword(e.target.value)} required />
        <button type="submit">Zaloguj się</button>
      </form>
    </div>
  );
}

export default Login;
