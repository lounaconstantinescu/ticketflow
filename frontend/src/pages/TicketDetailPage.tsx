import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { addComment, getTicket, listComments, updateTicket } from "../api/tickets";
import type { CommentDto, TicketDto, TicketPriority, TicketStatus } from "../api/types";

export default function TicketDetailPage() {
  const { id } = useParams();
  const ticketId = Number(id);

  const [ticket, setTicket] = useState<TicketDto | null>(null);
  const [comments, setComments] = useState<CommentDto[]>([]);
  const [author, setAuthor] = useState("admin");
  const [message, setMessage] = useState("");
  const [error, setError] = useState<string | null>(null);

  async function load() {
    setError(null);
    try {
      const t = await getTicket(ticketId);
      const c = await listComments(ticketId);
      setTicket(t);
      setComments(c);
    } catch (e: any) {
      setError(e.message);
    }
  }

  useEffect(() => { if (ticketId) load(); }, [ticketId]);

  async function onChangeStatus(s: TicketStatus) {
    if (!ticket) return;
    const updated = await updateTicket(ticket.id, { status: s });
    setTicket(updated);
  }

  async function onChangePriority(p: TicketPriority) {
    if (!ticket) return;
    const updated = await updateTicket(ticket.id, { priority: p });
    setTicket(updated);
  }

  async function onAddComment(e: React.FormEvent) {
    e.preventDefault();
    if (!message.trim()) return;
    const c = await addComment(ticketId, { author, message });
    setComments(prev => [...prev, c]);
    setMessage("");
  }

  if (error) return <div className="card"><p style={{ color: "crimson" }}>{error}</p></div>;
  if (!ticket) return <div className="card"><p>Chargement...</p></div>;

  return (
    <div>
      <div className="card">
        <div className="row">
          <h3 style={{ margin: 0 }}>{ticket.title}</h3>
          <span className="badge">{ticket.status}</span>
          <span className="badge">{ticket.priority}</span>
        </div>
        <p>{ticket.description}</p>

        <div className="row">
          <label>
            Statut
            <select value={ticket.status} onChange={(e) => onChangeStatus(e.target.value as TicketStatus)}>
              <option value="OPEN">OPEN</option>
              <option value="IN_PROGRESS">IN_PROGRESS</option>
              <option value="DONE">DONE</option>
            </select>
          </label>
          <label>
            Priorité
            <select value={ticket.priority} onChange={(e) => onChangePriority(e.target.value as TicketPriority)}>
              <option value="P1">P1</option>
              <option value="P2">P2</option>
              <option value="P3">P3</option>
            </select>
          </label>
          <button onClick={load}>Rafraîchir</button>
        </div>
      </div>

      <div className="card">
        <h3>Commentaires</h3>
        {comments.length === 0 && <p className="small">Aucun commentaire.</p>}
        {comments.map(c => (
          <div key={c.id} className="card">
            <div className="row">
              <strong>{c.author}</strong>
              <span className="small">{new Date(c.createdAt).toLocaleString()}</span>
            </div>
            <p style={{ margin: "6px 0 0 0" }}>{c.message}</p>
          </div>
        ))}

        <form onSubmit={onAddComment} style={{ marginTop: 10 }}>
          <div className="row">
            <input value={author} onChange={(e) => setAuthor(e.target.value)} placeholder="Auteur" />
          </div>
          <div style={{ marginTop: 8 }}>
            <textarea value={message} onChange={(e) => setMessage(e.target.value)} placeholder="Votre commentaire" />
          </div>
          <div className="row" style={{ marginTop: 8 }}>
            <button className="primary" type="submit">Ajouter</button>
          </div>
        </form>
      </div>
    </div>
  );
}
