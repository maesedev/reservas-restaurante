"use client"
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";

export default function SignOut() {
    const [loading, setLoading] = useState(true);
    const router = useRouter();

    useEffect(() => {
        const sessionJWT = localStorage.getItem("SESSION_JWT");
        if (!sessionJWT) {
            router.push("/login");
            return;
        }
        const timer = setTimeout(() => {
            localStorage.removeItem("SESSION_JWT");
            setLoading(false);
        }, 2000);
        return () => clearTimeout(timer);
    }, [router]);

    if (loading) {
        return (
            <div className="flex items-center justify-center min-h-screen bg-gray-100">
                {/* Spinner styling can be enhanced as needed */}
                <div className="text-xl font-semibold">Cerrando sesión...</div>
            </div>
        );
    }

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
            <h1 className="text-2xl font-bold mb-4">Has cerrado sesión</h1>
            <Link href="/" className="text-blue-500 hover:underline">
                volver al home
            </Link>
        </div>
    );
}