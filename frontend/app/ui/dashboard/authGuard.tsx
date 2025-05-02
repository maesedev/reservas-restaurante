// components/AuthGuard.tsx
"use client";

import { useSession } from "@/lib/SessionContext";
import { useRouter } from "next/navigation";
import { useEffect, ReactNode } from "react";

export default function AuthGuard({ children }: { children: ReactNode }) {
  const { session, loading } = useSession();
  const router = useRouter();

  useEffect(() => {
    if (!loading && !session) {
      router.push("/login");
    }
  }, [loading, session, router]);

  // Mientras carga o no hay sesión, no renderices nada
  if (loading || !session) return null;

  return <>{children}</>;
}
