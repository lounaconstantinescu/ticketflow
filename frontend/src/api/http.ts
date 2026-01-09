const fromRuntime = () => window.APP_CONFIG?.API_BASE_URL;
const fromEnv = () => import.meta.env.VITE_API_BASE_URL as string | undefined;

export const API_BASE_URL = fromRuntime() || fromEnv() || "http://localhost:8080/api";

export function authHeader(): Record<string, string> {
  const token = localStorage.getItem("tf_token");
  if (!token) return {};
  return { Authorization: `Bearer ${token}` };
}

export async function http<T>(path: string, options: RequestInit = {}): Promise<T> {
  const res = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...authHeader(),
      ...(options.headers || {})
    }
  });

  if (!res.ok) {
    const txt = await res.text();
    throw new Error(`HTTP ${res.status}: ${txt}`);
  }
  return res.json() as Promise<T>;
}
