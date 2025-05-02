"use client";

import React, { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import useLogin from "@/hooks/useLogin";
import useGetSession from "@/hooks/useGetSession";

const LoginPage: React.FC = () => {
  const { login } = useLogin();
  const { session, loading } = useGetSession();    // ya tenemos loading + session
  const router = useRouter();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [loadingRequest, setLoadingRequest] = useState(false);

  // 1) Cuando loading termine y haya session, redirige
  useEffect(() => {
    if (!loading && session) {
      router.push("/home");
    }
  }, [loading, session, router]);

  // 2) Mientras validamos la sesión o estamos ya autenticados, no mostramos el form
  if (loading || session) return null;

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setLoadingRequest(true);
    setError(null);

    const isSuccess = await login(email, password);
    setLoadingRequest(false);

    if (!isSuccess) {
      setError("Invalid credentials.");
    } else {
      router.push("/home");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
        <h1 className="text-2xl font-bold mb-6 text-center">Login</h1>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="email" className="block mb-1 font-medium">
              Email:
            </label>
            <input
              id="email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="w-full border border-gray-300 px-4 py-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div className="mb-4">
            <label htmlFor="password" className="block mb-1 font-medium">
              Password:
            </label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="w-full border border-gray-300 px-4 py-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          {error && (
            <div className="mb-4 text-red-500 text-sm">
              {error}
            </div>
          )}
          <button
            type="submit"
            disabled={loadingRequest}
            className="w-full bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            {loadingRequest ? "Logging in..." : "Log in"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
