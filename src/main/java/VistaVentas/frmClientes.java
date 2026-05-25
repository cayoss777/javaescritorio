package VistaVentas;

import ControlCliente.Control_Cliente;
import ModeloCliente.Cliente;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class frmClientes extends javax.swing.JFrame {

    private Control_Cliente control;
    private DefaultTableModel modeloTabla;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmClientes.class.getName());

    public frmClientes() {
        control = new Control_Cliente();
        initComponents();
        configurarTabla();
        cargarDatos();
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Direcci\u00f3n", "Tel\u00e9fono"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblClientes.setModel(modeloTabla);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Cliente> lista = control.listar();
        for (Cliente c : lista) {
            modeloTabla.addRow(new Object[]{
                c.getId(), c.getNombre(), c.getDireccion(), c.getTelefono()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gesti\u00f3n de Clientes");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Nombre", "Direcci\u00f3n", "Tel\u00e9fono"}
        ));
        jScrollPane1.setViewportView(tblClientes);

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
        String nombre = JOptionPane.showInputDialog(this, "Nombre del cliente:");
        if (nombre == null || nombre.trim().isEmpty()) return;
        String direccion = JOptionPane.showInputDialog(this, "Direcci\u00f3n:");
        if (direccion == null) return;
        String telefono = JOptionPane.showInputDialog(this, "Tel\u00e9fono:");
        if (telefono == null) return;

        Cliente c = new Cliente();
        c.setNombre(nombre.trim());
        c.setDireccion(direccion.trim());
        c.setTelefono(telefono.trim());
        if (control.guardar(c)) {
            JOptionPane.showMessageDialog(this, "Cliente guardado.");
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tblClientes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = JOptionPane.showInputDialog(this, "Nombre:", modeloTabla.getValueAt(fila, 1));
        if (nombre == null || nombre.trim().isEmpty()) return;
        String direccion = JOptionPane.showInputDialog(this, "Direcci\u00f3n:", modeloTabla.getValueAt(fila, 2));
        if (direccion == null) return;
        String telefono = JOptionPane.showInputDialog(this, "Tel\u00e9fono:", modeloTabla.getValueAt(fila, 3));
        if (telefono == null) return;

        Cliente c = new Cliente(id, nombre.trim(), direccion.trim(), telefono.trim());
        if (control.actualizar(c)) {
            JOptionPane.showMessageDialog(this, "Cliente actualizado.");
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tblClientes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Eliminar cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (control.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado.");
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
        java.awt.EventQueue.invokeLater(() -> new frmClientes().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnCerrar;
    // End of variables declaration//GEN-END:variables
}
