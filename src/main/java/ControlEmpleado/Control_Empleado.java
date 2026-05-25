package ControlEmpleado;

import ConexionBD.ConexionMySql;
import ModeloEmpleado.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Control_Empleado {

    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT idEmpleado, nombreEmpleado, password, rol FROM tabla_empleado";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setId(rs.getInt("idEmpleado"));
                e.setNombre(rs.getString("nombreEmpleado"));
                e.setPassword(rs.getString("password"));
                e.setRol(rs.getString("rol"));
                lista.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Error listar empleados: " + e.getMessage());
        }
        return lista;
    }

    public Empleado obtenerPorId(int id) {
        String sql = "SELECT idEmpleado, nombreEmpleado, password, rol FROM tabla_empleado WHERE idEmpleado = ?";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Empleado e = new Empleado();
                e.setId(rs.getInt("idEmpleado"));
                e.setNombre(rs.getString("nombreEmpleado"));
                e.setPassword(rs.getString("password"));
                e.setRol(rs.getString("rol"));
                return e;
            }
        } catch (SQLException e) {
            System.out.println("Error obtener empleado: " + e.getMessage());
        }
        return null;
    }

    public boolean guardar(Empleado e) {
        String sql = "INSERT INTO tabla_empleado (nombreEmpleado, password, rol) VALUES (?, ?, ?)";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, e.getNombre());
            pst.setString(2, e.getPassword());
            pst.setString(3, e.getRol());
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error guardar empleado: " + ex.getMessage());
            return false;
        }
    }

    public boolean actualizar(Empleado e) {
        String sql = "UPDATE tabla_empleado SET nombreEmpleado = ?, password = ?, rol = ? WHERE idEmpleado = ?";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, e.getNombre());
            pst.setString(2, e.getPassword());
            pst.setString(3, e.getRol());
            pst.setInt(4, e.getId());
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error actualizar empleado: " + ex.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM tabla_empleado WHERE idEmpleado = ?";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminar empleado: " + e.getMessage());
            return false;
        }
    }
}
