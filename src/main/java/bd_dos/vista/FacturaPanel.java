package bd_dos.vista;

import bd_dos.control.ClienteController;
import bd_dos.control.FacturaController;
import bd_dos.control.ProductoController;
import bd_dos.control.VendedorController;
import bd_dos.modelo.Cliente;
import bd_dos.modelo.Factura;
import bd_dos.modelo.Producto;
import bd_dos.modelo.Vendedor;
import bd_dos.modelo.Venta;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class FacturaPanel extends JPanel {

    private FacturaController controller;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JTextField txtBuscar;
    private JButton btnBuscar, btnNueva, btnVerDetalle, btnEliminar;
    private JButton btnPrimera, btnAnterior, btnSiguiente, btnUltima;
    private JLabel lblPagina;
    private int paginaActual = 1;
    private int totalPaginas = 1;

    public FacturaPanel() {
        controller = new FacturaController();
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
            new String[]{"No. Factura", "Cliente", "Vendedor", "Fecha", "Total ($)"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnNueva = new JButton("Nueva Factura");
        btnVerDetalle = new JButton("Ver Detalle");
        btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnNueva);
        panelBotones.add(btnVerDetalle);
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
        btnNueva.addActionListener(e -> nuevaFactura());
        btnVerDetalle.addActionListener(e -> verDetalle());
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
        for (Factura f : controller.listar(paginaActual, filtro)) {
            modeloTabla.addRow(new Object[]{
                f.getNoFactura(),
                f.getCliente().getNombreCompleto(),
                f.getVendedor().getNombre(),
                f.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                String.format("%.2f", f.getTotal())
            });
        }
    }

    private void nuevaFactura() {
        FacturaDialog dialog = new FacturaDialog(null);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            cargarDatos();
        }
    }

    private void verDetalle() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una factura");
            return;
        }
        int noFactura = (int) modeloTabla.getValueAt(fila, 0);
        Factura f = controller.obtenerPorId(noFactura);
        if (f == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("Factura No: ").append(f.getNoFactura()).append("\n");
        sb.append("Cliente: ").append(f.getCliente().getNombreCompleto()).append("\n");
        sb.append("Vendedor: ").append(f.getVendedor().getNombre()).append("\n");
        sb.append("Fecha: ").append(f.getFecha()).append("\n");
        sb.append("----------------------------------------\n");
        sb.append(String.format("%-20s %4s %8s %10s\n", "Producto", "Cant", "Precio", "Importe"));
        sb.append("----------------------------------------\n");
        for (Venta v : f.getVentas()) {
            sb.append(String.format("%-20s %4d $%6d $%8.2f\n",
                    v.getProducto().getNombre(),
                    v.getCantidad(),
                    v.getProducto().getPrecio(),
                    v.getImporte()));
        }
        sb.append("----------------------------------------\n");
        sb.append(String.format("TOTAL: $%.2f\n", f.getTotal()));
        JOptionPane.showMessageDialog(this, sb.toString(), "Detalle de Factura",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una factura");
            return;
        }
        int noFactura = (int) modeloTabla.getValueAt(fila, 0);
        if (JOptionPane.showConfirmDialog(this,
                "Eliminar factura No. " + noFactura + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controller.eliminar(noFactura);
            cargarDatos();
        }
    }

    // --- Factura creation dialog ---
    private static class FacturaDialog extends JDialog {

        private final FacturaController facturaController;
        private final ClienteController clienteController;
        private final ProductoController productoController;
        private final VendedorController vendedorController;

        private JTextField txtNoFactura, txtFecha, txtTotal;
        private JComboBox<Cliente> cmbCliente;
        private JComboBox<Vendedor> cmbVendedor;
        private DefaultTableModel modeloTabla;
        private JTable tablaDetalle;
        private boolean saved = false;

        FacturaDialog(java.awt.Frame parent) {
            super(parent, "Nueva Factura", true);
            facturaController = new FacturaController();
            clienteController = new ClienteController();
            productoController = new ProductoController();
            vendedorController = new VendedorController();
            initComponents();
            llenarCombos();
            pack();
            setLocationRelativeTo(parent);
        }

        boolean isSaved() { return saved; }

        private void initComponents() {
            setLayout(new BorderLayout(10, 10));

            // Header
            JPanel panelHeader = new JPanel(new GridLayout(4, 2, 5, 5));
            txtNoFactura = new JTextField(String.valueOf(facturaController.siguienteNumero()));
            txtNoFactura.setEditable(false);
            cmbCliente = new JComboBox<>();
            cmbVendedor = new JComboBox<>();
            txtFecha = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            panelHeader.add(new JLabel("No. Factura:"));
            panelHeader.add(txtNoFactura);
            panelHeader.add(new JLabel("Cliente:"));
            panelHeader.add(cmbCliente);
            panelHeader.add(new JLabel("Vendedor:"));
            panelHeader.add(cmbVendedor);
            panelHeader.add(new JLabel("Fecha (yyyy-MM-dd):"));
            panelHeader.add(txtFecha);

            // Detail table
            modeloTabla = new DefaultTableModel(
                new String[]{"Producto", "Cantidad", "Precio Unit.", "Importe"}, 0
            ) {
                @Override public boolean isCellEditable(int r, int c) { return false; }
            };
            tablaDetalle = new JTable(modeloTabla);

            // Detail buttons
            JPanel panelDetalleBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton btnAgregarLinea = new JButton("Agregar Producto");
            JButton btnQuitarLinea = new JButton("Quitar Producto");
            panelDetalleBtns.add(btnAgregarLinea);
            panelDetalleBtns.add(btnQuitarLinea);

            // Total
            JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            txtTotal = new JTextField(10);
            txtTotal.setEditable(false);
            panelTotal.add(new JLabel("Total: $"));
            panelTotal.add(txtTotal);

            // Save/Cancel
            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton btnGuardar = new JButton("Guardar");
            JButton btnCancelar = new JButton("Cancelar");
            panelBotones.add(btnGuardar);
            panelBotones.add(btnCancelar);

            JPanel panelDetail = new JPanel(new BorderLayout(5, 5));
            panelDetail.add(new JScrollPane(tablaDetalle), BorderLayout.CENTER);
            panelDetail.add(panelDetalleBtns, BorderLayout.NORTH);
            panelDetail.add(panelTotal, BorderLayout.SOUTH);

            JPanel panelSur = new JPanel(new BorderLayout());
            panelSur.add(panelBotones, BorderLayout.CENTER);

            add(panelHeader, BorderLayout.NORTH);
            add(panelDetail, BorderLayout.CENTER);
            add(panelSur, BorderLayout.SOUTH);

            btnAgregarLinea.addActionListener(e -> agregarLinea());
            btnQuitarLinea.addActionListener(e -> {
                int fila = tablaDetalle.getSelectedRow();
                if (fila >= 0) {
                    modeloTabla.removeRow(fila);
                    recalcularTotal();
                }
            });
            btnGuardar.addActionListener(e -> guardar());
            btnCancelar.addActionListener(e -> dispose());
        }

        private void llenarCombos() {
            for (Cliente c : clienteController.listarTodos()) {
                cmbCliente.addItem(c);
            }
            for (Vendedor v : vendedorController.listarTodos()) {
                cmbVendedor.addItem(v);
            }
        }

        private void agregarLinea() {
            List<Producto> productos = productoController.listarTodos();
            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos disponibles");
                return;
            }
            JComboBox<Producto> cmbProducto = new JComboBox<>(
                new DefaultComboBoxModel<>(productos.toArray(new Producto[0]))
            );
            JTextField txtCantidad = new JTextField("1", 10);
            JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
            panel.add(new JLabel("Producto:"));
            panel.add(cmbProducto);
            panel.add(new JLabel("Cantidad:"));
            panel.add(txtCantidad);
            if (JOptionPane.showConfirmDialog(this, panel, "Agregar Producto a Factura",
                    JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    Producto p = (Producto) cmbProducto.getSelectedItem();
                    int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                    if (cantidad <= 0) throw new NumberFormatException();
                    double importe = p.getPrecio() * cantidad;
                    modeloTabla.addRow(new Object[]{p, cantidad, p.getPrecio(), importe});
                    recalcularTotal();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Cantidad inv\u00e1lida",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void recalcularTotal() {
            double total = 0;
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                total += (double) modeloTabla.getValueAt(i, 3);
            }
            txtTotal.setText(String.format("%.2f", total));
        }

        private void guardar() {
            if (cmbCliente.getSelectedIndex() < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente");
                return;
            }
            if (cmbVendedor.getSelectedIndex() < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un vendedor");
                return;
            }
            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Agregue al menos un producto");
                return;
            }
            LocalDate fecha;
            try {
                fecha = LocalDate.parse(txtFecha.getText().trim(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this,
                        "Fecha inv\u00e1lida (use yyyy-MM-dd)", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Factura f = new Factura();
            f.setNoFactura(Integer.parseInt(txtNoFactura.getText()));
            f.setCliente((Cliente) cmbCliente.getSelectedItem());
            f.setVendedor((Vendedor) cmbVendedor.getSelectedItem());
            f.setFecha(fecha);

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                Venta v = new Venta();
                v.setProducto((Producto) modeloTabla.getValueAt(i, 0));
                v.setCantidad((int) modeloTabla.getValueAt(i, 1));
                v.setImporte((double) modeloTabla.getValueAt(i, 3));
                f.getVentas().add(v);
            }
            f.recalcularTotal();

            try {
                facturaController.guardar(f);
                JOptionPane.showMessageDialog(this, "Factura guardada exitosamente");
                saved = true;
                dispose();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
