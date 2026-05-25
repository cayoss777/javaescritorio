package bd_dos.modelo;

import java.util.Objects;

public class Vendedor {
    private int id;
    private String nombre;
    private String password;

    public Vendedor() {}

    public Vendedor(int id, String nombre, String password) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendedor)) return false;
        Vendedor vendedor = (Vendedor) o;
        return id == vendedor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
