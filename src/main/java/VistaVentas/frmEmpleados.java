package VistaVentas;

import ControlEmpleado.Control_Empleado;
import ModeloEmpleado.Empleado;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class frmEmpleados extends javax.swing.JFrame {

    private Control_Empleado control;
    private DefaultTableModel modeloTabla;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmEmpleados.class.getName());

    public frmEmpleados() {
        control = new Control_Empleado();
        initComponents();
        configurarTabla();
        cargarDatos();
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Rol"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblEmpleados.setModel(modeloTabla);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Empleado> lista = control.listar();
        for (Empleado e : lista) {
            modeloTabla.addRow(new Object[]{
                e.getId(), e.getNombre(), e.getRol()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gesti\u00f3n de Empleados");

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Nombre", "Rol"}
        ));
        jScrollPane1.setViewportView(tblEmpleados);

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
        String nombre = JOptionPane.showInputDialog(this, "Nombre del empleado:");
        if (nombre == null || nombre.trim().isEmpty()) return;
        String password = JOptionPane.showInputDialog(this, "Contrase\u00f1a:");
        if (password == null || password.trim().isEmpty()) return;
        String rol = JOptionPane.showInputDialog(this, "Rol:");
        if (rol == null || rol.trim().isEmpty()) return;

        Empleado e = new Empleado();
        e.setNombre(nombre.trim());
        e.setPassword(password.trim());
        e.setRol(rol.trim());
        if (control.guardar(e)) {
            JOptionPane.showMessageDialog(this, "Empleado guardado.");
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tblEmpleados.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = JOptionPane.showInputDialog(this, "Nombre:", modeloTabla.getValueAt(fila, 1));
        if (nombre == null || nombre.trim().isEmpty()) return;
        String password = JOptionPane.showInputDialog(this, "Contrase\u00f1a:");
        if (password == null) return;
        String rol = JOptionPane.showInputDialog(this, "Rol:", modeloTabla.getValueAt(fila, 2));
        if (rol == null || rol.trim().isEmpty()) return;

        Empleado e = new Empleado(id, nombre.trim(), password.trim(), rol.trim());
        if (control.actualizar(e)) {
            JOptionPane.showMessageDialog(this, "Empleado actualizado.");
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tblEmpleados.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Eliminar empleado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (control.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado.");
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
        java.awt.EventQueue.invokeLater(() -> new frmEmpleados().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnCerrar;
    // End of variables declaration//GEN-END:variables
}
