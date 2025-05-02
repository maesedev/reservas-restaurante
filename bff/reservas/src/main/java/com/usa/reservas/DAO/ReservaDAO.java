package com.usa.reservas.DAO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.usa.reservas.model.Mesa;
import com.usa.reservas.model.Reserva;

public class ReservaDAO {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/appdb";
    private static final String USER   = "root";
    private static final String PASSWORD = "rootpw";

    public Reserva findById(int id) {
        Reserva reserva = null;
        String sql = "SELECT * FROM Reservas WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setIdUsuario(rs.getInt("id_usuario"));
                reserva.setIdRestaurante(rs.getInt("id_restaurante"));
                reserva.setIdMesa(rs.getInt("id_mesa"));
                reserva.setFechaHora(rs.getTimestamp("fecha_hora"));
                reserva.setCantidadPersonas(rs.getInt("num_personas"));
                reserva.setEstado(rs.getString("estado"));
                reserva.setComentarios(rs.getString("comentarios"));
                // fecha_creacion can be set if needed
                reserva.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reserva;
    }

    public List<Reserva> findByUsuario(int idUsuario) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM Reservas WHERE id_usuario = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setIdUsuario(rs.getInt("id_usuario"));
                reserva.setIdRestaurante(rs.getInt("id_restaurante"));
                reserva.setIdMesa(rs.getInt("id_mesa"));
                reserva.setFechaHora(rs.getTimestamp("fecha_hora"));
                reserva.setCantidadPersonas(rs.getInt("num_personas"));
                reserva.setEstado(rs.getString("estado"));
                reserva.setComentarios(rs.getString("comentarios"));
                reserva.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    public List<Reserva> findAll() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM Reservas";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setIdUsuario(rs.getInt("id_usuario"));
                reserva.setIdRestaurante(rs.getInt("id_restaurante"));
                reserva.setIdMesa(rs.getInt("id_mesa"));
                reserva.setFechaHora(rs.getTimestamp("fecha_hora"));
                reserva.setCantidadPersonas(rs.getInt("num_personas"));
                reserva.setEstado(rs.getString("estado"));
                reserva.setComentarios(rs.getString("comentarios"));
                reserva.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    public List<Reserva> findByRestaurante(int idRestaurante) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM Reservas WHERE id_restaurante = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRestaurante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setIdUsuario(rs.getInt("id_usuario"));
                reserva.setIdRestaurante(rs.getInt("id_restaurante"));
                reserva.setIdMesa(rs.getInt("id_mesa"));
                reserva.setFechaHora(rs.getTimestamp("fecha_hora"));
                reserva.setCantidadPersonas(rs.getInt("num_personas"));
                reserva.setEstado(rs.getString("estado"));
                reserva.setComentarios(rs.getString("comentarios"));
                reserva.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    // If you wish to search by date (ignoring time) you can adjust the SQL as needed.
    public List<Reserva> findByFecha(java.util.Date fecha) {
        List<Reserva> reservas = new ArrayList<>();
        // Use DATE(fecha_hora) to compare only the date part
        String sql = "SELECT * FROM Reservas WHERE DATE(fecha_hora) = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(fecha.getTime()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setIdUsuario(rs.getInt("id_usuario"));
                reserva.setIdRestaurante(rs.getInt("id_restaurante"));
                reserva.setIdMesa(rs.getInt("id_mesa"));
                reserva.setFechaHora(rs.getTimestamp("fecha_hora"));
                reserva.setCantidadPersonas(rs.getInt("num_personas"));
                reserva.setEstado(rs.getString("estado"));
                reserva.setComentarios(rs.getString("comentarios"));
                reserva.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    public List<Reserva> findByEstado(String estado) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM Reservas WHERE estado = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setIdUsuario(rs.getInt("id_usuario"));
                reserva.setIdRestaurante(rs.getInt("id_restaurante"));
                reserva.setIdMesa(rs.getInt("id_mesa"));
                reserva.setFechaHora(rs.getTimestamp("fecha_hora"));
                reserva.setCantidadPersonas(rs.getInt("num_personas"));
                reserva.setEstado(rs.getString("estado"));
                reserva.setComentarios(rs.getString("comentarios"));
                reserva.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    public boolean insert(Reserva reserva) {
        String sql = "INSERT INTO Reservas (id_usuario, id_restaurante, id_mesa, fecha_hora, num_personas, estado, comentarios, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, reserva.getIdUsuario());
            stmt.setInt(2, reserva.getIdRestaurante());
            stmt.setInt(3, reserva.getIdMesa());
            // Assumes reserva.getFechaHora() returns a java.util.Date or Timestamp
            stmt.setTimestamp(4, new java.sql.Timestamp(reserva.getFechaHora().getTime()));
            stmt.setInt(5, reserva.getCantidadPersonas());
            stmt.setString(6, reserva.getEstado());
            stmt.setString(7, reserva.getComentarios());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reserva.setId(generatedKeys.getInt(1));
                }
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Reserva reserva) {
        String sql = "UPDATE Reservas SET id_usuario = ?, id_restaurante = ?, id_mesa = ?, fecha_hora = ?, num_personas = ?, estado = ?, comentarios = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reserva.getIdUsuario());
            stmt.setInt(2, reserva.getIdRestaurante());
            stmt.setInt(3, reserva.getIdMesa());
            stmt.setTimestamp(4, new java.sql.Timestamp(reserva.getFechaHora().getTime()));
            stmt.setInt(5, reserva.getCantidadPersonas());
            stmt.setString(6, reserva.getEstado());
            stmt.setString(7, reserva.getComentarios());
            stmt.setInt(8, reserva.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelReserva(int idReserva) {
        String sql = "UPDATE Reservas SET estado = 'CANCELADA' WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReserva);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Reservas WHERE id = ?";

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

    // To check available tables, we now need to combine fecha and hora into a single timestamp.
    public List<Mesa> getMesasDisponibles(int idRestaurante, java.util.Date fecha, String hora, int numPersonas) {
        List<Mesa> mesasDisponibles = new ArrayList<>();
        String sql = "SELECT m.* FROM Mesas m WHERE m.id_restaurante = ? AND m.capacidad >= ? "
                + "AND m.disponible = true AND m.id NOT IN "
                + "(SELECT r.id_mesa FROM Reservas r WHERE r.fecha_hora = ? AND r.estado != 'CANCELADA')";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, idRestaurante);
            stmt.setInt(2, numPersonas);
            // Combine fecha and hora; assuming hora is in "HH:mm:ss" format.
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            String datePart = sdfDate.format(fecha);
            String dateTime = datePart + " " + hora;
            Timestamp fechaHoraTs = Timestamp.valueOf(dateTime);
            stmt.setTimestamp(3, fechaHoraTs);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setId(rs.getInt("id"));
                mesa.setNumero(rs.getInt("numero"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                mesa.setUbicacion(rs.getString("ubicacion"));
                mesa.setDisponible(rs.getBoolean("disponible"));
                mesa.setIdRestaurante(rs.getInt("id_restaurante"));
                mesasDisponibles.add(mesa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesasDisponibles;
    }
}
