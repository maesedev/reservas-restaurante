/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usa.reservas.DAO;

/**
 *
 * @author ASUS
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.usa.reservas.model.Usuario;


@Repository
public class UsuarioDAO {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/appdb";
    private static final String USER   = "root";
    private static final String PASSWORD = "rootpw";


    /**
     * Busca un usuario por su ID
     *
     * @param id ID del usuario
     * @return Usuario encontrado o null
     */
    public Usuario findById(int id) {
        Usuario usuario = null;
        String sql = "SELECT id, nombre, apellido, email, password, telefono, rol, fecha_registro, estado FROM usuarios WHERE id = ?";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword(rs.getString("password"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setRol(rs.getString("rol"));
                usuario.setFechaRegistro(rs.getDate("fecha_registro"));
                usuario.setEstado(rs.getBoolean("estado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    /**
     * Busca un usuario por su email
     *
     * @param email Email del usuario
     * @return Usuario encontrado o null
     */
    public Usuario findByEmail(String email) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE email = ?";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword(rs.getString("password"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setRol(rs.getString("rol"));
                usuario.setFechaRegistro(rs.getDate("fecha_registro"));
                usuario.setEstado(rs.getBoolean("estado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    /**
     * Busca un usuario por su email y contraseña
     *
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario autenticado o null
     */
    public Usuario findByEmailAndPassword(String email, String password) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password); // En producción debería usarse hash
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword(rs.getString("password"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setRol(rs.getString("rol"));
                usuario.setFechaRegistro(rs.getDate("fecha_registro"));
                usuario.setEstado(rs.getBoolean("estado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    /**
     * Inserta un nuevo usuario en la base de datos
     *
     * @param usuario Usuario a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insert(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, password, telefono, rol, fecha_registro, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getRol());
            stmt.setDate(7, new java.sql.Date(usuario.getFechaRegistro().getTime()));
            stmt.setBoolean(8, usuario.getEstado());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            try ( ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza un usuario existente en la base de datos
     *
     * @param usuario Usuario a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean update(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, email = ?, password = ?, "
                + "telefono = ?, rol = ?, estado = ? WHERE id = ?";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getRol());
            stmt.setBoolean(7, usuario.getEstado());
            stmt.setInt(8, usuario.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
