"use client";
import {jwtDecode} from "jwt-decode";

const useGetSession = () => {
    const getSessionPayload = () => {
        const token = localStorage.getItem("SESSION_JWT");
        if (!token) {
            return null;
        }
        try {
            const payload = jwtDecode(token);
            
            return payload;
        } catch (error) {
            console.error("Invalid JWT token:", error);
            return null;
        }
    };

    return { getSessionPayload };
};

export default useGetSession;

export type TSessionPayload = {
    name: string;
    role: string;
    iat: number;
    exp: number;
    sub: string;
    originIp: string;
    ip: string;
};
