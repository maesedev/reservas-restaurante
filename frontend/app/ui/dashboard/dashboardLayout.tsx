// app/(dashboard)/layout.tsx
"use client";

import SideNav from "@/app/ui/dashboard/sidenav";
import { SessionProvider } from "@/lib/SessionContext";
import AuthGuard from "@/app/ui/dashboard/authGuard";

export default function DashboardLayout({ children }: { children: React.ReactNode }) {
  return (
    <SessionProvider>
      <AuthGuard>
        <div className="flex h-screen flex-col md:flex-row md:overflow-hidden">
          <aside className="w-full md:w-64">
            <SideNav />
          </aside>
          <main className="flex-grow md:overflow-y-auto">{children}</main>
        </div>
      </AuthGuard>
    </SessionProvider>
  );
}
