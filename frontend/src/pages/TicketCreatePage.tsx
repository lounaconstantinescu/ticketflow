import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createTicket } from "../api/tickets";
import type { TicketPriority } from "../api/types";

export default function TicketCreatePage() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [priority, setPriority] = useState<TicketPriority>("P2");
  const [error, setError] = useState<string | null>(null);
  const nav = useNavigate();

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    try {
      const t = await createTicket({ title, description, priority });
      nav(`/tickets/${t.id}`);
    } catch (e: any) {
      setError(e.message);
    }
  }

  return (
    <div className="card">
      <h3>Nouveau ticket</h3>
      <form onSubmit={onSubmit}>
        <div className="row">
          <input value={title} onChange={(e) => setTitle(e.target.value)} placeholder="Titre" />
          <select value={priority} onChange={(e) => setPriority(e.target.value as TicketPriority)}>
            <option value="P1">P1</option>
            <option value="P2">P2</option>
            <option value="P3">P3</option>
          </select>
        </div>
        <div style={{ marginTop: 10 }}>
          <textarea value={description} onChange={(e) => setDescription(e.target.value)} placeholder="Description" />
        </div>
        <div className="row" style={{ marginTop: 10 }}>
          <button className="primary" type="submit">Créer</button>
        </div>
      </form>
      {error && <p className="small" style={{ color: "crimson" }}>{error}</p>}
    </div>
  );
}
