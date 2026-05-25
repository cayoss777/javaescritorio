package bd_dos.control;

import bd_dos.dao.VendedorDAO;
import bd_dos.modelo.Vendedor;
import java.util.List;

public class VendedorController {

    private final VendedorDAO dao;
    private static final int PAGE_SIZE = 10;

    public VendedorController() {
        this.dao = new VendedorDAO();
    }

    public int totalPaginas(String filtro) {
        int total = dao.contar(filtro);
        return (int) Math.ceil((double) total / PAGE_SIZE);
    }

    public int totalRegistros(String filtro) {
        return dao.contar(filtro);
    }

    public List<Vendedor> listar(int pagina, String filtro) {
        int offset = (pagina - 1) * PAGE_SIZE;
        return dao.listar(offset, PAGE_SIZE, filtro);
    }

    public List<Vendedor> listarTodos() {
        return dao.listarTodos();
    }

    public Vendedor obtenerPorId(int id) {
        return dao.obtenerPorId(id);
    }

    public void guardar(Vendedor v) {
        if (v.getNombre() == null || v.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        dao.guardar(v);
    }

    public void actualizar(Vendedor v) {
        if (v.getNombre() == null || v.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        dao.actualizar(v);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }
}
