import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../api/auth";

export default function LoginPage() {
  const [username, setUsername] = useState("admin");
  const [password, setPassword] = useState("admin");
  const [error, setError] = useState<string | null>(null);
  const nav = useNavigate();

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    try {
      await login(username, password);
      nav("/");
    } catch (err: any) {
      setError(err.message);
    }
  }

  return (
    <div className="card">
      <h3>Login</h3>
      <form onSubmit={onSubmit} className="row">
        <input value={username} onChange={(e) => setUsername(e.target.value)} placeholder="username" />
        <input value={password} onChange={(e) => setPassword(e.target.value)} placeholder="password" type="password" />
        <button className="primary" type="submit">Se connecter</button>
      </form>
      {error && <p className="small" style={{ color: "crimson" }}>{error}</p>}
      <p className="small">En dev, l'utilisateur <code>admin/admin</code> est créé automatiquement.</p>
    </div>
  );
}
