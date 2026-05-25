package VistaVentas;

import ControlProducto.Control_Producto;
import ModeloProducto.Producto;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class frmProductos extends javax.swing.JFrame {

    private Control_Producto control;
    private DefaultTableModel modeloTabla;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmProductos.class.getName());

    public frmProductos() {
        control = new Control_Producto();
        initComponents();
        configurarTabla();
        cargarDatos();
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Precio", "Stock"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProductos.setModel(modeloTabla);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Producto> lista = control.listar();
        for (Producto p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getPrecio(), p.getStock()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gesti\u00f3n de Productos");

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Nombre", "Precio", "Stock"}
        ));
        jScrollPane1.setViewportView(tblProductos);

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(this::btnCerrarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnCerrar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del producto:");
        if (nombre == null || nombre.trim().isEmpty()) return;
        String precioStr = JOptionPane.showInputDialog(this, "Precio:");
        if (precioStr == null) return;
        String stockStr = JOptionPane.showInputDialog(this, "Stock:");
        if (stockStr == null) return;

        try {
            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);
            Producto p = new Producto();
            p.setNombre(nombre.trim());
            p.setPrecio(precio);
            p.setStock(stock);
            if (control.guardar(p)) {
                JOptionPane.showMessageDialog(this, "Producto guardado.");
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio y stock deben ser n\u00fameros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tblProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = JOptionPane.showInputDialog(this, "Nombre:", modeloTabla.getValueAt(fila, 1));
        if (nombre == null || nombre.trim().isEmpty()) return;
        String precioStr = JOptionPane.showInputDialog(this, "Precio:", modeloTabla.getValueAt(fila, 2));
        if (precioStr == null) return;
        String stockStr = JOptionPane.showInputDialog(this, "Stock:", modeloTabla.getValueAt(fila, 3));
        if (stockStr == null) return;

        try {
            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);
            Producto p = new Producto(id, nombre.trim(), precio, stock);
            if (control.actualizar(p)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado.");
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio y stock deben ser n\u00fameros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tblProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Eliminar producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (control.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado.");
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new frmProductos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProductos;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnCerrar;
    // End of variables declaration//GEN-END:variables
}
