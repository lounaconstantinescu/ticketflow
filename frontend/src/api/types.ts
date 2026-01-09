export type TicketStatus = "OPEN" | "IN_PROGRESS" | "DONE";
export type TicketPriority = "P1" | "P2" | "P3";

export interface TicketDto {
  id: number;
  title: string;
  description: string;
  status: TicketStatus;
  priority: TicketPriority;
  createdAt: string;
  updatedAt: string;
}

export interface CommentDto {
  id: number;
  author: string;
  message: string;
  createdAt: string;
}
