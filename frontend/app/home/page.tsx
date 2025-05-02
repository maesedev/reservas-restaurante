
"use client";
import { useSession } from '@/lib/SessionContext';

export default function Home() {


  const {session} = useSession(); // Reemplaza esto con el nombre real del usuario
  const username = session?.name || "Usuario"; // Asegúrate de que el nombre del usuario esté disponible en la sesión
  return (
    <div className="h-[80vh] md:min-h-screen  flex flex-col items-center justify-center bg-gradient-to-br from-blue-500 to-purple-600 p-8">
      <div className="bg-white rounded-lg shadow-lg p-32 text-center">
        <h1 className="text-4xl font-bold mb-4">Bienvenido {username}</h1>
        <p className="text-lg text-gray-700">
          Desde este lugar podrás hacer y modificar reservas.
        </p>
      </div>
    </div>
  );
}
