import { useState } from "react";

const useLogin = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || "localhost:4000";
    const login = async (email: string, password: string): Promise<boolean> => {
        try {
            const response = await fetch(baseUrl+"/api/v1/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ email, password }),
            });

            if (response.status === 403) {
                return false;
            }

            if (response.ok) {
                const data = await response.json();
                const token = data.token;

                if (token) {
                    localStorage.setItem("SESSION_JWT", token);
                    setIsLoggedIn(true);
                    return true;
                }
            }

            return false;
        } catch (error) {
            console.error("Login failed:", error);
            return false;
        }
    };

    return { isLoggedIn, login };
};

export default useLogin;