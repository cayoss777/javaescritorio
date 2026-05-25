package ControlCliente;

import ConexionBD.ConexionMySql;
import ModeloCliente.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Control_Cliente {

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT idCliente, nombreCliente, direccion, telefono FROM tabla_cliente";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("idCliente"));
                c.setNombre(rs.getString("nombreCliente"));
                c.setDireccion(rs.getString("direccion"));
                c.setTelefono(rs.getString("telefono"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error listar clientes: " + e.getMessage());
        }
        return lista;
    }

    public Cliente obtenerPorId(int id) {
        String sql = "SELECT idCliente, nombreCliente, direccion, telefono FROM tabla_cliente WHERE idCliente = ?";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("idCliente"));
                c.setNombre(rs.getString("nombreCliente"));
                c.setDireccion(rs.getString("direccion"));
                c.setTelefono(rs.getString("telefono"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println("Error obtener cliente: " + e.getMessage());
        }
        return null;
    }

    public boolean guardar(Cliente c) {
        String sql = "INSERT INTO tabla_cliente (nombreCliente, direccion, telefono) VALUES (?, ?, ?)";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, c.getNombre());
            pst.setString(2, c.getDireccion());
            pst.setString(3, c.getTelefono());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error guardar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Cliente c) {
        String sql = "UPDATE tabla_cliente SET nombreCliente = ?, direccion = ?, telefono = ? WHERE idCliente = ?";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, c.getNombre());
            pst.setString(2, c.getDireccion());
            pst.setString(3, c.getTelefono());
            pst.setInt(4, c.getId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM tabla_cliente WHERE idCliente = ?";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}
