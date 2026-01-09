import { http } from "./http";

export async function login(username: string, password: string): Promise<void> {
  const res = await http<{ token: string; tokenType: string }>("/auth/login", {
    method: "POST",
    body: JSON.stringify({ username, password })
  });
  localStorage.setItem("tf_token", res.token);
}

export function logout(): void {
  localStorage.removeItem("tf_token");
}

export function isLoggedIn(): boolean {
  return !!localStorage.getItem("tf_token");
}
