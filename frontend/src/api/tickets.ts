import { http } from "./http";
import type { TicketDto, TicketPriority, TicketStatus, CommentDto } from "./types";

export interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  number: number;
  size: number;
}

export async function listTickets(params: {
  status?: TicketStatus;
  priority?: TicketPriority;
  page?: number;
  size?: number;
}): Promise<Page<TicketDto>> {
  const q = new URLSearchParams();
  if (params.status) q.set("status", params.status);
  if (params.priority) q.set("priority", params.priority);
  q.set("page", String(params.page ?? 0));
  q.set("size", String(params.size ?? 10));
  return http<Page<TicketDto>>(`/tickets?${q.toString()}`);
}

export async function getTicket(id: number): Promise<TicketDto> {
  return http<TicketDto>(`/tickets/${id}`);
}

export async function createTicket(payload: {
  title: string;
  description: string;
  priority: TicketPriority;
}): Promise<TicketDto> {
  return http<TicketDto>("/tickets", {
    method: "POST",
    body: JSON.stringify(payload)
  });
}

export async function updateTicket(id: number, payload: Partial<{
  title: string;
  description: string;
  status: TicketStatus;
  priority: TicketPriority;
}>): Promise<TicketDto> {
  return http<TicketDto>(`/tickets/${id}`, {
    method: "PUT",
    body: JSON.stringify(payload)
  });
}

export async function listComments(ticketId: number): Promise<CommentDto[]> {
  return http<CommentDto[]>(`/tickets/${ticketId}/comments`);
}

export async function addComment(ticketId: number, payload: {
  author: string;
  message: string;
}): Promise<CommentDto> {
  return http<CommentDto>(`/tickets/${ticketId}/comments`, {
    method: "POST",
    body: JSON.stringify(payload)
  });
}
