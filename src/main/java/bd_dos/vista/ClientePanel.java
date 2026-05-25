package bd_dos.vista;

import bd_dos.control.ClienteController;
import bd_dos.modelo.Cliente;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ClientePanel extends JPanel {

    private ClienteController controller;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JTextField txtBuscar;
    private JButton btnBuscar, btnAgregar, btnEditar, btnEliminar;
    private JButton btnPrimera, btnAnterior, btnSiguiente, btnUltima;
    private JLabel lblPagina;
    private int paginaActual = 1;
    private int totalPaginas = 1;

    public ClientePanel() {
        controller = new ClienteController();
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);

        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Apellido", "C\u00e9dula"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        JPanel panelPagina = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPrimera = new JButton("|<");
        btnAnterior = new JButton("<");
        lblPagina = new JLabel("P\u00e1gina 1 de 1");
        btnSiguiente = new JButton(">");
        btnUltima = new JButton(">|");
        panelPagina.add(btnPrimera);
        panelPagina.add(btnAnterior);
        panelPagina.add(lblPagina);
        panelPagina.add(btnSiguiente);
        panelPagina.add(btnUltima);

        JPanel panelSur = new JPanel(new GridLayout(2, 1));
        panelSur.add(panelBotones);
        panelSur.add(panelPagina);

        add(panelBusqueda, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> { paginaActual = 1; cargarDatos(); });
        btnAgregar.addActionListener(e -> agregar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        btnPrimera.addActionListener(e -> { paginaActual = 1; cargarDatos(); });
        btnAnterior.addActionListener(e -> { if (paginaActual > 1) { paginaActual--; cargarDatos(); } });
        btnSiguiente.addActionListener(e -> { if (paginaActual < totalPaginas) { paginaActual++; cargarDatos(); } });
        btnUltima.addActionListener(e -> { paginaActual = totalPaginas; cargarDatos(); });
    }

    private void cargarDatos() {
        String filtro = txtBuscar.getText().trim();
        totalPaginas = controller.totalPaginas(filtro);
        if (paginaActual > totalPaginas) paginaActual = Math.max(1, totalPaginas);
        lblPagina.setText("P\u00e1gina " + paginaActual + " de " + totalPaginas);
        modeloTabla.setRowCount(0);
        for (Cliente c : controller.listar(paginaActual, filtro)) {
            modeloTabla.addRow(new Object[]{c.getId(), c.getNombre(), c.getApellido(), c.getCedula()});
        }
    }

    private void agregar() {
        JTextField txtNombre = new JTextField(15);
        JTextField txtApellido = new JTextField(15);
        JTextField txtCedula = new JTextField(15);
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("C\u00e9dula:"));
        panel.add(txtCedula);
        if (JOptionPane.showConfirmDialog(this, panel, "Agregar Cliente",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Cliente c = new Cliente();
                c.setNombre(txtNombre.getText().trim());
                c.setApellido(txtApellido.getText().trim());
                c.setCedula(Long.parseLong(txtCedula.getText().trim()));
                controller.guardar(c);
                cargarDatos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "C\u00e9dula inv\u00e1lida", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        Cliente c = controller.obtenerPorId(id);
        if (c == null) return;

        JTextField txtNombre = new JTextField(c.getNombre(), 15);
        JTextField txtApellido = new JTextField(c.getApellido(), 15);
        JTextField txtCedula = new JTextField(String.valueOf(c.getCedula()), 15);
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("C\u00e9dula:"));
        panel.add(txtCedula);
        if (JOptionPane.showConfirmDialog(this, panel, "Editar Cliente",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                c.setNombre(txtNombre.getText().trim());
                c.setApellido(txtApellido.getText().trim());
                c.setCedula(Long.parseLong(txtCedula.getText().trim()));
                controller.actualizar(c);
                cargarDatos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "C\u00e9dula inv\u00e1lida", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        if (JOptionPane.showConfirmDialog(this,
                "Eliminar cliente " + modeloTabla.getValueAt(fila, 1) + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controller.eliminar(id);
            cargarDatos();
        }
    }
}
