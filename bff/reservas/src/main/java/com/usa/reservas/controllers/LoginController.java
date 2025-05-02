
package com.usa.reservas.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.usa.reservas.DAO.UsuarioDAO;
import com.usa.reservas.model.LoginRequest;
import com.usa.reservas.model.Usuario;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final Key key;
    private final UsuarioDAO usuarioDAO;

    public LoginController(UsuarioDAO usuarioDAO,
                           @Value("${jwt.secret}") String secret) {
        this.usuarioDAO = usuarioDAO;
        // Build a raw HMAC-SHA256 key from the UTF-8 bytes of your secret
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody LoginRequest loginRequest) {

        boolean valid = SistemaReservasController.getInstance()
                            .iniciarSesion(
                                loginRequest.getEmail(),
                                loginRequest.getPassword()
                            );

        if (!valid) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                        "status",  "error",
                        "message", "Invalid credentials"
                    ));
        }

        Usuario user = usuarioDAO.findByEmail(loginRequest.getEmail());
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                        "status",  "error",
                        "message", "User not found"
                    ));
        }

        String token = generateToken(user.getId(), user.getRol(), user.getNombre());
        return ResponseEntity.ok(Map.of("token", token));
    }

    private String generateToken(int cedula, String role, String name) {
        long nowMillis = System.currentTimeMillis();
        Date issuedAt  = new Date(nowMillis);
        Date expiresAt = new Date(nowMillis + 40 * 60 * 1000);  // 40 minutes
    
        return Jwts.builder()
                   .setSubject(Integer.toString(cedula))
                   .claim("name", name)
                   .claim("role", role)
                   .setIssuedAt(issuedAt)
                   .setExpiration(expiresAt)
                   .signWith(key)
                   .compact();
    }
}
