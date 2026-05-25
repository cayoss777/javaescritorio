package bd_dos.dao;

import bd_dos.config.DatabaseConfig;
import bd_dos.modelo.Factura;
import bd_dos.modelo.Venta;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {

    public int contar(String filtro) {
        String sql = "SELECT COUNT(*) FROM table_facturas f "
                + "JOIN table_cliente c ON f.cliente = c.idCliente "
                + "JOIN table_vendedor v ON f.vendedor = v.idVendedor "
                + "WHERE CONCAT(f.No_Facturas, c.Nombre_Cliente, c.Apellido_Cliente, v.nombreVendedor) LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + filtro + "%");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error contar facturas", e);
        }
        return 0;
    }

    public List<Factura> listar(int offset, int limit, String filtro) {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT f.No_Facturas, f.cliente, f.fecha, f.vendedor, f.totals, "
                + "c.Nombre_Cliente, c.Apellido_Cliente, c.Cedula_Cliente, "
                + "v.nombreVendedor "
                + "FROM table_facturas f "
                + "JOIN table_cliente c ON f.cliente = c.idCliente "
                + "JOIN table_vendedor v ON f.vendedor = v.idVendedor "
                + "WHERE CONCAT(f.No_Facturas, c.Nombre_Cliente, c.Apellido_Cliente, v.nombreVendedor) LIKE ? "
                + "ORDER BY f.No_Facturas DESC LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + filtro + "%");
            pst.setInt(2, limit);
            pst.setInt(3, offset);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Factura f = new Factura();
                f.setNoFactura(rs.getInt("No_Facturas"));
                f.setFecha(rs.getDate("fecha").toLocalDate());
                f.setTotal(rs.getDouble("totals"));
                f.setCliente(new bd_dos.modelo.Cliente(
                        rs.getInt("cliente"),
                        rs.getString("Nombre_Cliente"),
                        rs.getString("Apellido_Cliente"),
                        rs.getLong("Cedula_Cliente")
                ));
                f.setVendedor(new bd_dos.modelo.Vendedor(
                        rs.getInt("vendedor"),
                        rs.getString("nombreVendedor"),
                        null
                ));
                lista.add(f);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listar facturas", e);
        }
        return lista;
    }

    public Factura obtenerPorId(int noFactura) {
        String sql = "SELECT f.No_Facturas, f.cliente, f.fecha, f.vendedor, f.totals, "
                + "c.Nombre_Cliente, c.Apellido_Cliente, c.Cedula_Cliente, "
                + "v.nombreVendedor "
                + "FROM table_facturas f "
                + "JOIN table_cliente c ON f.cliente = c.idCliente "
                + "JOIN table_vendedor v ON f.vendedor = v.idVendedor "
                + "WHERE f.No_Facturas = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, noFactura);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Factura f = new Factura();
                f.setNoFactura(rs.getInt("No_Facturas"));
                f.setFecha(rs.getDate("fecha").toLocalDate());
                f.setTotal(rs.getDouble("totals"));
                f.setCliente(new bd_dos.modelo.Cliente(
                        rs.getInt("cliente"),
                        rs.getString("Nombre_Cliente"),
                        rs.getString("Apellido_Cliente"),
                        rs.getLong("Cedula_Cliente")
                ));
                f.setVendedor(new bd_dos.modelo.Vendedor(
                        rs.getInt("vendedor"),
                        rs.getString("nombreVendedor"),
                        null
                ));
                return f;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error obtener factura", e);
        }
        return null;
    }

    public int siguienteNumero() {
        String sql = "SELECT COALESCE(MAX(No_Facturas), 0) + 1 FROM table_facturas";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error obtener siguiente numero", e);
        }
        return 1;
    }

    public void guardarConVentas(Factura f) {
        String sqlFactura = "INSERT INTO table_facturas (No_Facturas, cliente, fecha, vendedor, totals) VALUES (?, ?, ?, ?, ?)";
        String sqlVenta = "INSERT INTO table_ventas (No_Facturas, Productos, cantidad, importe) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(sqlFactura)) {
                pst.setInt(1, f.getNoFactura());
                pst.setInt(2, f.getCliente().getId());
                pst.setDate(3, Date.valueOf(f.getFecha()));
                pst.setInt(4, f.getVendedor().getId());
                pst.setDouble(5, f.getTotal());
                pst.executeUpdate();
            }

            try (PreparedStatement pst = conn.prepareStatement(sqlVenta)) {
                for (Venta v : f.getVentas()) {
                    pst.setInt(1, f.getNoFactura());
                    pst.setInt(2, v.getProducto().getId());
                    pst.setInt(3, v.getCantidad());
                    pst.setDouble(4, v.getImporte());
                    pst.addBatch();
                }
                pst.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {
                    throw new RuntimeException("Error en rollback", ex);
                }
            }
            throw new RuntimeException("Error guardar factura con ventas", e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {
                    throw new RuntimeException("Error cerrando conexion", e);
                }
            }
        }
    }

    public void eliminar(int noFactura) {
        String sqlVentas = "DELETE FROM table_ventas WHERE No_Facturas = ?";
        String sqlFactura = "DELETE FROM table_facturas WHERE No_Facturas = ?";

        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(sqlVentas)) {
                pst.setInt(1, noFactura);
                pst.executeUpdate();
            }

            try (PreparedStatement pst = conn.prepareStatement(sqlFactura)) {
                pst.setInt(1, noFactura);
                pst.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {
                    throw new RuntimeException("Error en rollback", ex);
                }
            }
            throw new RuntimeException("Error eliminar factura", e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {
                    throw new RuntimeException("Error cerrando conexion", e);
                }
            }
        }
    }
}
