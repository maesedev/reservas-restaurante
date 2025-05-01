// app/page.tsx

import Image from 'next/image';
import Link from 'next/link';
import { Button } from "@/app/ui/button";

export default function Main() {
  return (
    <main className="min-h-screen flex flex-col">
      {/* Header */}
      <header className="bg-white shadow">
        <div className="container mx-auto flex items-center justify-between py-4 px-6">
          {/* Nav links with logos */}
          <nav className="flex items-center space-x-6">
            <Link href="/home" className="flex items-center space-x-2 hover:opacity-80">
              <Image src="/images/logo.svg" alt="Home" width={28} height={28} />
              <span className="font-semibold">Home</span>
            </Link>
            <Link href="/home/reservas" className="flex items-center space-x-2 hover:opacity-80">
              <Image src="/images/reservas.svg" alt="Reservas" width={28} height={28} />
              <span className="font-semibold">Reservas</span>
            </Link>
          </nav>
          {/* Login link wrapped with a Button component from shadcn/ui */}
          <Link href="/login">
            <Button className="rounded-full">Log In</Button>
          </Link>
        </div>
      </header>

      {/* Hero Section */}
      <section className="flex-1 relative">
        <Image
          src="/images/restaurante.avif"
          alt="Gourmet Restaurant"
          fill
          className="object-cover filter-[brightness(0.5)] "
          priority
        />
        <div className="absolute inset-0 flex flex-col items-center justify-center text-center text-white px-4">
          <h1 className="text-5xl md:text-6xl font-extrabold mb-6 drop-shadow-lg">
            Bienvenido a <span className="text-emerald-400">Cielo Gourmet</span>
          </h1>
          <p className="max-w-2xl text-lg md:text-2xl mb-8">
            Experiencia de alta cocina al alcance de todos.
          </p>
          <Link href="/home/reservas"  passHref>
            <Button  className="bg-emerald-500 hover:bg-emerald-600 text-white rounded-full px-8 py-3 text-lg font-semibold">
              Reservar ahora
            </Button>
          </Link>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-900 text-gray-400 text-center py-4">
        © {new Date().getFullYear()} Cielo Gourmet. All rights reserved.
      </footer>
    </main>
  );
}
