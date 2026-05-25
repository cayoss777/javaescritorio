package bd_dos.control;

import bd_dos.dao.ClienteDAO;
import bd_dos.modelo.Cliente;
import java.util.List;

public class ClienteController {

    private final ClienteDAO dao;
    private static final int PAGE_SIZE = 10;

    public ClienteController() {
        this.dao = new ClienteDAO();
    }

    public int totalPaginas(String filtro) {
        int total = dao.contar(filtro);
        return (int) Math.ceil((double) total / PAGE_SIZE);
    }

    public int totalRegistros(String filtro) {
        return dao.contar(filtro);
    }

    public List<Cliente> listar(int pagina, String filtro) {
        int offset = (pagina - 1) * PAGE_SIZE;
        return dao.listar(offset, PAGE_SIZE, filtro);
    }

    public List<Cliente> listarTodos() {
        List<Cliente> todos = new java.util.ArrayList<>();
        int total = dao.contar("");
        int pages = (int) Math.ceil((double) total / PAGE_SIZE);
        for (int p = 1; p <= pages; p++) {
            todos.addAll(dao.listar((p - 1) * PAGE_SIZE, PAGE_SIZE, ""));
        }
        return todos;
    }

    public Cliente obtenerPorId(int id) {
        return dao.obtenerPorId(id);
    }

    public void guardar(Cliente c) {
        if (c.getNombre() == null || c.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (c.getApellido() == null || c.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }
        if (c.getCedula() <= 0) {
            throw new IllegalArgumentException("La cedula debe ser un numero positivo");
        }
        dao.guardar(c);
    }

    public void actualizar(Cliente c) {
        if (c.getNombre() == null || c.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (c.getApellido() == null || c.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }
        if (c.getCedula() <= 0) {
            throw new IllegalArgumentException("La cedula debe ser un numero positivo");
        }
        dao.actualizar(c);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }
}
