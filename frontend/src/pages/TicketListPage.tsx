import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { listTickets } from "../api/tickets";
import type { TicketDto, TicketPriority, TicketStatus } from "../api/types";

export default function TicketListPage() {
  const [items, setItems] = useState<TicketDto[]>([]);
  const [status, setStatus] = useState<TicketStatus | "">("");
  const [priority, setPriority] = useState<TicketPriority | "">("");
  const [error, setError] = useState<string | null>(null);

  async function load() {
    setError(null);
    try {
      const page = await listTickets({
        status: status || undefined,
        priority: priority || undefined,
        page: 0,
        size: 20
      });
      setItems(page.content);
    } catch (e: any) {
      setError(e.message);
    }
  }

  useEffect(() => { load(); }, []);

  return (
    <div>
      <div className="card">
        <h3>Tickets</h3>
        <div className="row">
          <label>
            Statut
            <select value={status} onChange={(e) => setStatus(e.target.value as any)}>
              <option value="">(tous)</option>
              <option value="OPEN">OPEN</option>
              <option value="IN_PROGRESS">IN_PROGRESS</option>
              <option value="DONE">DONE</option>
            </select>
          </label>
          <label>
            Priorité
            <select value={priority} onChange={(e) => setPriority(e.target.value as any)}>
              <option value="">(toutes)</option>
              <option value="P1">P1</option>
              <option value="P2">P2</option>
              <option value="P3">P3</option>
            </select>
          </label>
          <button className="primary" onClick={load}>Rechercher</button>
        </div>
        {error && <p className="small" style={{ color: "crimson" }}>{error}</p>}
      </div>

      {items.map(t => (
        <div className="card" key={t.id}>
          <div className="row">
            <strong>{t.title}</strong>
            <span className="badge">{t.status}</span>
            <span className="badge">{t.priority}</span>
            <div className="spacer" />
            <Link to={`/tickets/${t.id}`}>Ouvrir</Link>
          </div>
          <p className="small" style={{ marginTop: 6 }}>{t.description}</p>
        </div>
      ))}
    </div>
  );
}
