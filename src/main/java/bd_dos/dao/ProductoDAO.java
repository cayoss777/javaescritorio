package bd_dos.dao;

import bd_dos.config.DatabaseConfig;
import bd_dos.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public int contar(String filtro) {
        String sql = "SELECT COUNT(*) FROM table_productos WHERE CONCAT(nombreProductos, preciosProductos) LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + filtro + "%");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error contar productos", e);
        }
        return 0;
    }

    public List<Producto> listar(int offset, int limit, String filtro) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT idProductos, nombreProductos, preciosProductos "
                + "FROM table_productos "
                + "WHERE CONCAT(nombreProductos, preciosProductos) LIKE ? "
                + "ORDER BY idProductos LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + filtro + "%");
            pst.setInt(2, limit);
            pst.setInt(3, offset);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("idProductos"),
                        rs.getString("nombreProductos"),
                        rs.getInt("preciosProductos")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listar productos", e);
        }
        return lista;
    }

    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT idProductos, nombreProductos, preciosProductos FROM table_productos ORDER BY nombreProductos";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("idProductos"),
                        rs.getString("nombreProductos"),
                        rs.getInt("preciosProductos")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listar todos productos", e);
        }
        return lista;
    }

    public Producto obtenerPorId(int id) {
        String sql = "SELECT idProductos, nombreProductos, preciosProductos FROM table_productos WHERE idProductos = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Producto(
                        rs.getInt("idProductos"),
                        rs.getString("nombreProductos"),
                        rs.getInt("preciosProductos")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error obtener producto", e);
        }
        return null;
    }

    public void guardar(Producto p) {
        String sql = "INSERT INTO table_productos (nombreProductos, preciosProductos) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, p.getNombre());
            pst.setInt(2, p.getPrecio());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error guardar producto", e);
        }
    }

    public void actualizar(Producto p) {
        String sql = "UPDATE table_productos SET nombreProductos = ?, preciosProductos = ? WHERE idProductos = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, p.getNombre());
            pst.setInt(2, p.getPrecio());
            pst.setInt(3, p.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizar producto", e);
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM table_productos WHERE idProductos = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminar producto", e);
        }
    }
}
