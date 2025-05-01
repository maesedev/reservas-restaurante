"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";
import SideNav from '@/app/ui/dashboard/sidenav';
import useGetSession from "@/hooks/useGetSession"; // Ajusta la ruta al hook segun tu estructura

export default function Layout({ children }: { children: React.ReactNode }) {
  const { getSessionPayload } = useGetSession();
  const router = useRouter();

  useEffect(() => {
    const payload = getSessionPayload();
    if (!payload) {
      router.push("/login");
    }
  }, [router, getSessionPayload]);

  return (
    <div className="flex h-screen flex-col md:flex-row md:overflow-hidden">
      <div className="w-full flex-none md:w-64">
        <SideNav />
      </div>
      <div className="flex-grow md:overflow-y-auto">{children}</div>
    </div>
  );
}