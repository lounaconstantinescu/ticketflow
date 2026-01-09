import { describe, it, expect, vi, beforeEach } from "vitest";
import React from "react";
import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";

// Mock API
vi.mock("../api/tickets", () => ({
  listTickets: vi.fn(async () => ({
    content: [
      {
        id: 1,
        title: "T1",
        description: "D1",
        status: "OPEN",
        priority: "P2",
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
      }
    ],
    totalPages: 1,
    totalElements: 1,
    number: 0,
    size: 20
  }))
}));

import TicketListPage from "../pages/TicketListPage";

describe("TicketListPage", () => {
  beforeEach(() => vi.clearAllMocks());

  it("renders tickets", async () => {
    render(
      <MemoryRouter>
        <TicketListPage />
      </MemoryRouter>
    );

    expect(await screen.findByText("T1")).toBeInTheDocument();
  });
});
