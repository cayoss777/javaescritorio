package bd_dos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Factura {
    private int noFactura;
    private Cliente cliente;
    private LocalDate fecha;
    private Vendedor vendedor;
    private double total;
    private List<Venta> ventas;

    public Factura() {
        this.ventas = new ArrayList<>();
    }

    public Factura(int noFactura, Cliente cliente, LocalDate fecha,
                   Vendedor vendedor, double total) {
        this.noFactura = noFactura;
        this.cliente = cliente;
        this.fecha = fecha;
        this.vendedor = vendedor;
        this.total = total;
        this.ventas = new ArrayList<>();
    }

    public int getNoFactura() { return noFactura; }
    public void setNoFactura(int noFactura) { this.noFactura = noFactura; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Vendedor getVendedor() { return vendedor; }
    public void setVendedor(Vendedor vendedor) { this.vendedor = vendedor; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public List<Venta> getVentas() { return ventas; }
    public void setVentas(List<Venta> ventas) { this.ventas = ventas; }

    public void recalcularTotal() {
        total = ventas.stream().mapToDouble(Venta::getImporte).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Factura)) return false;
        Factura factura = (Factura) o;
        return noFactura == factura.noFactura;
    }

    @Override
    public int hashCode() {
        return Objects.hash(noFactura);
    }
}
