package bd_dos.control;

import bd_dos.dao.ProductoDAO;
import bd_dos.modelo.Producto;
import java.util.List;

public class ProductoController {

    private final ProductoDAO dao;
    private static final int PAGE_SIZE = 10;

    public ProductoController() {
        this.dao = new ProductoDAO();
    }

    public int totalPaginas(String filtro) {
        int total = dao.contar(filtro);
        return (int) Math.ceil((double) total / PAGE_SIZE);
    }

    public int totalRegistros(String filtro) {
        return dao.contar(filtro);
    }

    public List<Producto> listar(int pagina, String filtro) {
        int offset = (pagina - 1) * PAGE_SIZE;
        return dao.listar(offset, PAGE_SIZE, filtro);
    }

    public List<Producto> listarTodos() {
        return dao.listarTodos();
    }

    public Producto obtenerPorId(int id) {
        return dao.obtenerPorId(id);
    }

    public void guardar(Producto p) {
        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (p.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser un numero positivo");
        }
        dao.guardar(p);
    }

    public void actualizar(Producto p) {
        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (p.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser un numero positivo");
        }
        dao.actualizar(p);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }
}
