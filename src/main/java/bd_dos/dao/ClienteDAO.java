package bd_dos.dao;

import bd_dos.config.DatabaseConfig;
import bd_dos.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public int contar(String filtro) {
        String sql = "SELECT COUNT(*) FROM table_cliente WHERE CONCAT(Nombre_Cliente, ' ', Apellido_Cliente, Cedula_Cliente) LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + filtro + "%");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error contar clientes", e);
        }
        return 0;
    }

    public List<Cliente> listar(int offset, int limit, String filtro) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT idCliente, Nombre_Cliente, Apellido_Cliente, Cedula_Cliente "
                + "FROM table_cliente "
                + "WHERE CONCAT(Nombre_Cliente, ' ', Apellido_Cliente, Cedula_Cliente) LIKE ? "
                + "ORDER BY idCliente LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + filtro + "%");
            pst.setInt(2, limit);
            pst.setInt(3, offset);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("Nombre_Cliente"),
                        rs.getString("Apellido_Cliente"),
                        rs.getLong("Cedula_Cliente")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listar clientes", e);
        }
        return lista;
    }

    public Cliente obtenerPorId(int id) {
        String sql = "SELECT idCliente, Nombre_Cliente, Apellido_Cliente, Cedula_Cliente "
                + "FROM table_cliente WHERE idCliente = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("Nombre_Cliente"),
                        rs.getString("Apellido_Cliente"),
                        rs.getLong("Cedula_Cliente")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error obtener cliente", e);
        }
        return null;
    }

    public void guardar(Cliente c) {
        String sql = "INSERT INTO table_cliente (Nombre_Cliente, Apellido_Cliente, Cedula_Cliente) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, c.getNombre());
            pst.setString(2, c.getApellido());
            pst.setLong(3, c.getCedula());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error guardar cliente", e);
        }
    }

    public void actualizar(Cliente c) {
        String sql = "UPDATE table_cliente SET Nombre_Cliente = ?, Apellido_Cliente = ?, Cedula_Cliente = ? WHERE idCliente = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, c.getNombre());
            pst.setString(2, c.getApellido());
            pst.setLong(3, c.getCedula());
            pst.setInt(4, c.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizar cliente", e);
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM table_cliente WHERE idCliente = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminar cliente", e);
        }
    }
}
