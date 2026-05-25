package bd_dos.control;

import bd_dos.dao.FacturaDAO;
import bd_dos.dao.VentaDAO;
import bd_dos.modelo.Factura;
import bd_dos.modelo.Venta;
import java.util.List;

public class FacturaController {

    private final FacturaDAO facturaDAO;
    private final VentaDAO ventaDAO;
    private static final int PAGE_SIZE = 10;

    public FacturaController() {
        this.facturaDAO = new FacturaDAO();
        this.ventaDAO = new VentaDAO();
    }

    public int totalPaginas(String filtro) {
        int total = facturaDAO.contar(filtro);
        return (int) Math.ceil((double) total / PAGE_SIZE);
    }

    public int totalRegistros(String filtro) {
        return facturaDAO.contar(filtro);
    }

    public List<Factura> listar(int pagina, String filtro) {
        int offset = (pagina - 1) * PAGE_SIZE;
        return facturaDAO.listar(offset, PAGE_SIZE, filtro);
    }

    public Factura obtenerPorId(int noFactura) {
        Factura f = facturaDAO.obtenerPorId(noFactura);
        if (f != null) {
            f.setVentas(ventaDAO.listarPorFactura(noFactura));
        }
        return f;
    }

    public int siguienteNumero() {
        return facturaDAO.siguienteNumero();
    }

    public void guardar(Factura f) {
        if (f.getCliente() == null) {
            throw new IllegalArgumentException("Debe seleccionar un cliente");
        }
        if (f.getVendedor() == null) {
            throw new IllegalArgumentException("Debe seleccionar un vendedor");
        }
        if (f.getFecha() == null) {
            throw new IllegalArgumentException("Debe seleccionar una fecha");
        }
        if (f.getVentas() == null || f.getVentas().isEmpty()) {
            throw new IllegalArgumentException("Debe agregar al menos un producto");
        }
        for (Venta v : f.getVentas()) {
            if (v.getProducto() == null) {
                throw new IllegalArgumentException("Todos los productos deben estar seleccionados");
            }
            if (v.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
            }
        }
        f.recalcularTotal();
        facturaDAO.guardarConVentas(f);
    }

    public void eliminar(int noFactura) {
        facturaDAO.eliminar(noFactura);
    }
}
