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
import java.util.Date;
import java.util.List;

import com.usa.reservas.model.Mesa;

public class MesaDAO {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/appdb";
    private static final String USER   = "root";
    private static final String PASSWORD = "rootpw";

    /**
     * Busca una mesa por su ID
     *
     * @param id ID de la mesa
     * @return Mesa encontrada o null
     */
    public Mesa findById(int id) {
        Mesa mesa = null;
        String sql = "SELECT * FROM mesas WHERE id = ?";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                mesa = new Mesa();
                mesa.setId(rs.getInt("id"));
                mesa.setNumero(rs.getInt("numero"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                mesa.setUbicacion(rs.getString("ubicacion"));
                mesa.setDisponible(rs.getBoolean("disponible"));
                mesa.setIdRestaurante(rs.getInt("id_restaurante"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mesa;
    }

    /**
     * Busca todas las mesas de un restaurante
     *
     * @param idRestaurante ID del restaurante
     * @return Lista de mesas del restaurante
     */
    public List<Mesa> findByRestaurante(int idRestaurante) {
        List<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT * FROM mesas WHERE id_restaurante = ?";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRestaurante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setId(rs.getInt("id"));
                mesa.setNumero(rs.getInt("numero"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                mesa.setUbicacion(rs.getString("ubicacion"));
                mesa.setDisponible(rs.getBoolean("disponible"));
                mesa.setIdRestaurante(rs.getInt("id_restaurante"));

                mesas.add(mesa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mesas;
    }

    /**
     * Verifica si una mesa está reservada en una fecha y hora específica
     *
     * @param idMesa ID de la mesa
     * @param fecha Fecha de la reserva
     * @param hora Hora de la reserva
     * @return true si la mesa está reservada, false si está disponible
     */
    public boolean isReserved(int idMesa, Date fechaHora) {
        String sql = "SELECT COUNT(*) FROM reservas WHERE id_mesa = ? AND fecha_hora = ?";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMesa);
            stmt.setDate(2, new java.sql.Date(fechaHora.getTime()));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Inserta una nueva mesa en la base de datos
     *
     * @param mesa Mesa a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insert(Mesa mesa) {
        String sql = "INSERT INTO mesas (numero, capacidad, ubicacion, disponible, id_restaurante) "
                + "VALUES (?, ?, ?, ?, ?)";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, mesa.getNumero());
            stmt.setInt(2, mesa.getCapacidad());
            stmt.setString(3, mesa.getUbicacion());
            stmt.setBoolean(4, mesa.isDisponible());
            stmt.setInt(5, mesa.getIdRestaurante());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            try ( ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mesa.setId(generatedKeys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza una mesa existente en la base de datos
     *
     * @param mesa Mesa a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean update(Mesa mesa) {
        String sql = "UPDATE mesas SET numero = ?, capacidad = ?, ubicacion = ?, "
                + "disponible = ?, id_restaurante = ? WHERE id = ?";

        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mesa.getNumero());
            stmt.setInt(2, mesa.getCapacidad());
            stmt.setString(3, mesa.getUbicacion());
            stmt.setBoolean(4, mesa.isDisponible());
            stmt.setInt(5, mesa.getIdRestaurante());
            stmt.setInt(6, mesa.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
