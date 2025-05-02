// lib/SessionContext.tsx
"use client";

import { createContext, useContext, ReactNode } from "react";
import useGetSession, { TSessionPayload } from "@/hooks/useGetSession";

type SessionContextType = {
  session: TSessionPayload | null;
  loading: boolean;
};

const SessionContext = createContext<SessionContextType>({
  session: null,
  loading: true,
});

export function SessionProvider({ children }: { children: ReactNode }) {
  const { session, loading } = useGetSession();
  return (
    <SessionContext.Provider value={{ session, loading }}>
      {children}
    </SessionContext.Provider>
  );
}

export function useSession() {
  return useContext(SessionContext);
}
