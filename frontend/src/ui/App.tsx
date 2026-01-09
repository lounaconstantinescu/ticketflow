import React from "react";
import { Link, Route, Routes, useNavigate } from "react-router-dom";
import { isLoggedIn, logout } from "../api/auth";
import LoginPage from "../pages/LoginPage";
import TicketListPage from "../pages/TicketListPage";
import TicketDetailPage from "../pages/TicketDetailPage";
import TicketCreatePage from "../pages/TicketCreatePage";

export default function App() {
  const nav = useNavigate();

  function onLogout() {
    logout();
    nav("/login");
  }

  return (
    <div className="container">
      <header className="row">
        <h2 style={{ margin: 0 }}>TicketFlow</h2>
        <div className="spacer" />
        <nav className="row">
          <Link to="/">Tickets</Link>
          <Link to="/tickets/new">Nouveau</Link>
          {isLoggedIn() ? (
            <button onClick={onLogout}>Logout</button>
          ) : (
            <Link to="/login">Login</Link>
          )}
        </nav>
      </header>

      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/" element={<TicketListPage />} />
        <Route path="/tickets/new" element={<TicketCreatePage />} />
        <Route path="/tickets/:id" element={<TicketDetailPage />} />
      </Routes>

      <footer className="small" style={{ marginTop: 24 }}>
        API: <code>{(window as any).APP_CONFIG?.API_BASE_URL || import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api"}</code>
      </footer>
    </div>
  );
}
