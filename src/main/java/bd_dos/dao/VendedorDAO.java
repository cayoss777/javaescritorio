package bd_dos.dao;

import bd_dos.config.DatabaseConfig;
import bd_dos.modelo.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendedorDAO {

    public int contar(String filtro) {
        String sql = "SELECT COUNT(*) FROM table_vendedor WHERE CONCAT(nombreVendedor, password) LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + filtro + "%");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error contar vendedores", e);
        }
        return 0;
    }

    public List<Vendedor> listar(int offset, int limit, String filtro) {
        List<Vendedor> lista = new ArrayList<>();
        String sql = "SELECT idVendedor, nombreVendedor, password "
                + "FROM table_vendedor "
                + "WHERE CONCAT(nombreVendedor, password) LIKE ? "
                + "ORDER BY idVendedor LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + filtro + "%");
            pst.setInt(2, limit);
            pst.setInt(3, offset);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lista.add(new Vendedor(
                        rs.getInt("idVendedor"),
                        rs.getString("nombreVendedor"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listar vendedores", e);
        }
        return lista;
    }

    public List<Vendedor> listarTodos() {
        List<Vendedor> lista = new ArrayList<>();
        String sql = "SELECT idVendedor, nombreVendedor, password FROM table_vendedor ORDER BY nombreVendedor";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                lista.add(new Vendedor(
                        rs.getInt("idVendedor"),
                        rs.getString("nombreVendedor"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listar todos vendedores", e);
        }
        return lista;
    }

    public Vendedor obtenerPorId(int id) {
        String sql = "SELECT idVendedor, nombreVendedor, password FROM table_vendedor WHERE idVendedor = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Vendedor(
                        rs.getInt("idVendedor"),
                        rs.getString("nombreVendedor"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error obtener vendedor", e);
        }
        return null;
    }

    public void guardar(Vendedor v) {
        String sql = "INSERT INTO table_vendedor (nombreVendedor, password) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, v.getNombre());
            pst.setString(2, v.getPassword());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error guardar vendedor", e);
        }
    }

    public void actualizar(Vendedor v) {
        String sql = "UPDATE table_vendedor SET nombreVendedor = ?, password = ? WHERE idVendedor = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, v.getNombre());
            pst.setString(2, v.getPassword());
            pst.setInt(3, v.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizar vendedor", e);
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM table_vendedor WHERE idVendedor = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminar vendedor", e);
        }
    }
}
