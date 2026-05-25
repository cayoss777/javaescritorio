package ControlProducto;

import ConexionBD.ConexionMySql;
import ModeloProducto.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Control_Producto {

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT idProducto, nombreProducto, precio, stock FROM tabla_producto";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("idProducto"));
                p.setNombre(rs.getString("nombreProducto"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error listar productos: " + e.getMessage());
        }
        return lista;
    }

    public Producto obtenerPorId(int id) {
        String sql = "SELECT idProducto, nombreProducto, precio, stock FROM tabla_producto WHERE idProducto = ?";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("idProducto"));
                p.setNombre(rs.getString("nombreProducto"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Error obtener producto: " + e.getMessage());
        }
        return null;
    }

    public boolean guardar(Producto p) {
        String sql = "INSERT INTO tabla_producto (nombreProducto, precio, stock) VALUES (?, ?, ?)";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, p.getNombre());
            pst.setDouble(2, p.getPrecio());
            pst.setInt(3, p.getStock());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error guardar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Producto p) {
        String sql = "UPDATE tabla_producto SET nombreProducto = ?, precio = ?, stock = ? WHERE idProducto = ?";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, p.getNombre());
            pst.setDouble(2, p.getPrecio());
            pst.setInt(3, p.getStock());
            pst.setInt(4, p.getId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM tabla_producto WHERE idProducto = ?";
        try (Connection conn = ConexionMySql.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminar producto: " + e.getMessage());
            return false;
        }
    }
}
