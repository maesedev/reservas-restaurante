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

import com.usa.reservas.model.Horario;

public class HorarioDAO {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/appdb";
    private static final String USER   = "root";
    private static final String PASSWORD = "rootpw";
    
    /**
     * Busca un horario por su ID
     * @param id ID del horario
     * @return Horario encontrado o null
     */
    public Horario findById(int id) {
        Horario horario = null;
        String sql = "SELECT * FROM horarios WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                horario = new Horario();
                horario.setId(rs.getInt("id"));
                horario.setIdRestaurante(rs.getInt("id_restaurante"));
                horario.setDiaSemana(rs.getString("dia_semana"));
                horario.setHoraApertura(rs.getTime("hora_apertura"));
                horario.setHoraCierre(rs.getTime("hora_cierre"));
                horario.setEstado(rs.getString("estado"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return horario;
    }
    
    /**
     * Busca todos los horarios de un restaurante
     * @param idRestaurante ID del restaurante
     * @return Lista de horarios del restaurante
     */
    public List<Horario> findByRestaurante(int idRestaurante) {
        List<Horario> horarios = new ArrayList<>();
        String sql = "SELECT * FROM horarios WHERE id_restaurante = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idRestaurante);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Horario horario = new Horario();
                horario.setId(rs.getInt("id"));
                horario.setIdRestaurante(rs.getInt("id_restaurante"));
                horario.setDiaSemana(rs.getString("dia_semana"));
                horario.setHoraApertura(rs.getTime("hora_apertura"));
                horario.setHoraCierre(rs.getTime("hora_cierre"));
                horario.setEstado(rs.getString("estado"));
                
                horarios.add(horario);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return horarios;
    }
    
    /**
     * Busca el horario de un restaurante para un día específico
     * @param idRestaurante ID del restaurante
     * @param diaSemana Día de la semana (ej: "LUNES", "MARTES", etc.)
     * @return Horario encontrado o null
     */
    public Horario findByRestauranteAndDia(int idRestaurante, String diaSemana) {
        Horario horario = null;
        String sql = "SELECT * FROM horarios WHERE id_restaurante = ? AND dia_semana = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idRestaurante);
            stmt.setString(2, diaSemana);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                horario = new Horario();
                horario.setId(rs.getInt("id"));
                horario.setIdRestaurante(rs.getInt("id_restaurante"));
                horario.setDiaSemana(rs.getString("dia_semana"));
                horario.setHoraApertura(rs.getTime("hora_apertura"));
                horario.setHoraCierre(rs.getTime("hora_cierre"));
                horario.setEstado(rs.getString("estado"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return horario;
    }
    
    /**
     * Verifica si un restaurante está abierto en un día y hora específicos
     * @param idRestaurante ID del restaurante
     * @param diaSemana Día de la semana
     * @param hora Hora a verificar
     * @return true si está abierto, false si está cerrado
     */
    public boolean isOpen(int idRestaurante, String diaSemana, Time hora) {
        String sql = "SELECT COUNT(*) FROM horarios WHERE id_restaurante = ? AND dia_semana = ? " +
                     "AND hora_apertura <= ? AND hora_cierre >= ? AND estado = 'ACTIVO'";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idRestaurante);
            stmt.setString(2, diaSemana);
            stmt.setTime(3, hora);
            stmt.setTime(4, hora);
            
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
     * Inserta un nuevo horario en la base de datos
     * @param horario Horario a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insert(Horario horario) {
        String sql = "INSERT INTO horarios (id_restaurante, dia_semana, hora_apertura, hora_cierre, estado) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, horario.getIdRestaurante());
            stmt.setString(2, horario.getDiaSemana());
            stmt.setTime(3, new java.sql.Time(horario.getHoraApertura().getTime()));
            stmt.setTime(4, new java.sql.Time(horario.getHoraCierre().getTime()));
            stmt.setString(5, horario.getEstado());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                return false;
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    horario.setId(generatedKeys.getInt(1));
                }
            }
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Actualiza un horario existente en la base de datos
     * @param horario Horario a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean update(Horario horario) {
        String sql = "UPDATE horarios SET id_restaurante = ?, dia_semana = ?, hora_apertura = ?, " +
                     "hora_cierre = ?, estado = ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, horario.getIdRestaurante());
            stmt.setString(2, horario.getDiaSemana());
            stmt.setTime(3, new java.sql.Time(horario.getHoraApertura().getTime()));
            stmt.setTime(4, new java.sql.Time(horario.getHoraCierre().getTime()));
            stmt.setString(5, horario.getEstado());
            stmt.setInt(6, horario.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Elimina un horario de la base de datos
     * @param id ID del horario a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM horarios WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Actualiza el estado de un horario
     * @param id ID del horario
     * @param estado Nuevo estado
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean updateEstado(int id, String estado) {
        String sql = "UPDATE horarios SET estado = ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}