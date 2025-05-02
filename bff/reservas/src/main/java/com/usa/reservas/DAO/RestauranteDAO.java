/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usa.reservas.DAO;

/**
 *
 * @author ASUS
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.usa.reservas.model.Restaurante;

public class RestauranteDAO {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/appdb";
    private static final String USER   = "root";
    private static final String PASSWORD = "rootpw";

    /**
     * Busca un restaurante por su ID
     *
     * @param id ID del restaurante
     * @return Restaurante encontrado o null
     */
    public Restaurante findById(int id) {
        Restaurante restaurante = null;
        String sql = "SELECT * FROM restaurantes WHERE id = ?";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                restaurante = new Restaurante();
                restaurante.setId(rs.getInt("id"));
                restaurante.setNombre(rs.getString("nombre"));
                restaurante.setDireccion(rs.getString("direccion"));
                restaurante.setTelefono(rs.getString("telefono"));
                restaurante.setDescripcion(rs.getString("descripcion"));
                restaurante.setCalificacion(rs.getString("calificacion"));
                restaurante.setHorarioApertura(rs.getTime("horario_apertura"));
                restaurante.setHorarioCierre(rs.getTime("horario_cierre"));
                restaurante.setIdAdmin(rs.getInt("id_admin"));
                restaurante.setEstado(rs.getString("estado"));
                restaurante.setFechaApertura(rs.getDate("fecha_apertura"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurante;
    }

    /**
     * Obtiene todos los restaurantes
     *
     * @return Lista de restaurantes
     */
    public List<Restaurante> findAll() {
        List<Restaurante> restaurantes = new ArrayList<>();
        String sql = "SELECT * FROM restaurantes";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Restaurante restaurante = new Restaurante();
                restaurante.setId(rs.getInt("id"));
                restaurante.setNombre(rs.getString("nombre"));
                restaurante.setDireccion(rs.getString("direccion"));
                restaurante.setTelefono(rs.getString("telefono"));
                restaurante.setDescripcion(rs.getString("descripcion"));
                restaurante.setCalificacion(rs.getString("calificacion"));
                restaurante.setHorarioApertura(rs.getTime("horario_apertura"));
                restaurante.setHorarioCierre(rs.getTime("horario_cierre"));
                restaurante.setIdAdmin(rs.getInt("id_admin"));
                restaurante.setEstado(rs.getString("estado"));
                restaurante.setFechaApertura(rs.getDate("fecha_apertura"));

                restaurantes.add(restaurante);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurantes;
    }

    /**
     * Busca restaurantes por nombre
     *
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de restaurantes que coinciden con la búsqueda
     */
    public List<Restaurante> findByName(String nombre) {
        List<Restaurante> restaurantes = new ArrayList<>();
        String sql = "SELECT * FROM restaurantes WHERE nombre LIKE ?";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Restaurante restaurante = new Restaurante();
                restaurante.setId(rs.getInt("id"));
                restaurante.setNombre(rs.getString("nombre"));
                restaurante.setDireccion(rs.getString("direccion"));
                restaurante.setTelefono(rs.getString("telefono"));
                restaurante.setDescripcion(rs.getString("descripcion"));
                restaurante.setCalificacion(rs.getString("calificacion"));
                restaurante.setHorarioApertura(rs.getTime("horario_apertura"));
                restaurante.setHorarioCierre(rs.getTime("horario_cierre"));
                restaurante.setIdAdmin(rs.getInt("id_admin"));
                restaurante.setEstado(rs.getString("estado"));
                restaurante.setFechaApertura(rs.getDate("fecha_apertura"));

                restaurantes.add(restaurante);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurantes;
    }

    /**
     * Inserta un nuevo restaurante en la base de datos
     *
     * @param restaurante Restaurante a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insert(Restaurante restaurante) {
        String sql = "INSERT INTO restaurantes (nombre, direccion, telefono, descripcion, calificacion, "
                + "horario_apertura, horario_cierre, id_admin, estado, fecha_apertura) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, restaurante.getNombre());
            stmt.setString(2, restaurante.getDireccion());
            stmt.setString(3, restaurante.getTelefono());
            stmt.setString(4, restaurante.getDescripcion());
            stmt.setString(5, restaurante.getCalificacion());
            stmt.setTime(6, new java.sql.Time(restaurante.getHorarioApertura().getTime()));
            stmt.setTime(7, new java.sql.Time(restaurante.getHorarioCierre().getTime()));
            stmt.setInt(8, restaurante.getIdAdmin());
            stmt.setString(9, restaurante.getEstado());
            stmt.setDate(10, new java.sql.Date(restaurante.getFechaApertura().getTime()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            try ( ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    restaurante.setId(generatedKeys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza un restaurante existente en la base de datos
     *
     * @param restaurante Restaurante a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean update(Restaurante restaurante) {
        String sql = "UPDATE restaurantes SET nombre = ?, direccion = ?, telefono = ?, "
                + "descripcion = ?, calificacion = ?, horario_apertura = ?, "
                + "horario_cierre = ?, id_admin = ?, estado = ? WHERE id = ?";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, restaurante.getNombre());
            stmt.setString(2, restaurante.getDireccion());
            stmt.setString(3, restaurante.getTelefono());
            stmt.setString(4, restaurante.getDescripcion());
            stmt.setString(5, restaurante.getCalificacion());
            stmt.setTime(6, new java.sql.Time(restaurante.getHorarioApertura().getTime()));
            stmt.setTime(7, new java.sql.Time(restaurante.getHorarioCierre().getTime()));
            stmt.setInt(8, restaurante.getIdAdmin());
            stmt.setString(9, restaurante.getEstado());
            stmt.setInt(10, restaurante.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
