package bd_dos.modelo;

import java.util.Objects;

public class Venta {
    private int id;
    private int noFactura;
    private Producto producto;
    private int cantidad;
    private double importe;

    public Venta() {}

    public Venta(int id, int noFactura, Producto producto,
                 int cantidad, double importe) {
        this.id = id;
        this.noFactura = noFactura;
        this.producto = producto;
        this.cantidad = cantidad;
        this.importe = importe;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getNoFactura() { return noFactura; }
    public void setNoFactura(int noFactura) { this.noFactura = noFactura; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getImporte() { return importe; }
    public void setImporte(double importe) { this.importe = importe; }

    public void calcularImporte() {
        if (producto != null) {
            this.importe = producto.getPrecio() * cantidad;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venta)) return false;
        Venta venta = (Venta) o;
        return id == venta.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
