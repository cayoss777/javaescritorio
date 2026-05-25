package VistaVentas;

import ModeloVendedor.Vendedor;

public class frmVentas extends javax.swing.JFrame {

    private Vendedor vendedorActual;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmVentas.class.getName());

    public frmVentas() {
        initComponents();
    }

    public frmVentas(Vendedor v) {
        initComponents();
        this.vendedorActual = v;
        lblVendedor.setText("Vendedor: " + v.getNombre());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblVendedor = new javax.swing.JLabel();
        btnProductos = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ventas");

        lblVendedor.setText("Vendedor");

        btnProductos.setText("Productos");
        btnProductos.addActionListener(this::btnProductosActionPerformed);

        btnClientes.setText("Clientes");
        btnClientes.addActionListener(this::btnClientesActionPerformed);

        btnEmpleados.setText("Empleados");
        btnEmpleados.addActionListener(this::btnEmpleadosActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblVendedor)
                    .addComponent(btnProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(btnEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblVendedor)
                .addGap(30, 30, 30)
                .addComponent(btnProductos)
                .addGap(18, 18, 18)
                .addComponent(btnClientes)
                .addGap(18, 18, 18)
                .addComponent(btnEmpleados)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {
        frmProductos ventana = new frmProductos();
        ventana.setVisible(true);
    }

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {
        frmClientes ventana = new frmClientes();
        ventana.setVisible(true);
    }

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {
        frmEmpleados ventana = new frmEmpleados();
        ventana.setVisible(true);
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

        java.awt.EventQueue.invokeLater(() -> new frmVentas().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnEmpleados;
    // End of variables declaration//GEN-END:variables
}
