// hooks/useGetSession.tsx
"use client";

import { useState, useEffect } from "react";
import {jwtDecode} from "jwt-decode";

export default function useGetSession() {
  const [session, setSession] = useState<TSessionPayload | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Solo en cliente
    if (typeof window === "undefined") return;

    const token = localStorage.getItem("SESSION_JWT");
    if (token) {
      try {
        setSession(jwtDecode<TSessionPayload>(token));
      } catch {
        setSession(null);
      }
    }
    setLoading(false);
  }, []);

  return { session, loading };
}

export type TSessionPayload = {
    name: string;
    role: string;
    iat: number;
    exp: number;
    sub: string;
    originIp: string;
    ip: string;
};
