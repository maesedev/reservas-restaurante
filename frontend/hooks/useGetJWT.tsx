"use client";
const useGetJWT = () => {
    const getToken = () => {
        const token = localStorage.getItem("SESSION_JWT");
        if (!token) {
            return null;
        }
        return token;
    };

    return { getToken };
};

export default useGetJWT;
