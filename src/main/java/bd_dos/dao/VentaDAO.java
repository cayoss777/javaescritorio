package bd_dos.dao;

import bd_dos.config.DatabaseConfig;
import bd_dos.modelo.Producto;
import bd_dos.modelo.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    public List<Venta> listarPorFactura(int noFactura) {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT v.idVentas, v.No_Facturas, v.Productos, v.cantidad, v.importe, "
                + "p.nombreProductos, p.preciosProductos "
                + "FROM table_ventas v "
                + "JOIN table_productos p ON v.Productos = p.idProductos "
                + "WHERE v.No_Facturas = ? "
                + "ORDER BY v.idVentas";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, noFactura);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Venta v = new Venta();
                v.setId(rs.getInt("idVentas"));
                v.setNoFactura(rs.getInt("No_Facturas"));
                v.setCantidad(rs.getInt("cantidad"));
                v.setImporte(rs.getDouble("importe"));
                v.setProducto(new Producto(
                        rs.getInt("Productos"),
                        rs.getString("nombreProductos"),
                        rs.getInt("preciosProductos")
                ));
                lista.add(v);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listar ventas por factura", e);
        }
        return lista;
    }
}
