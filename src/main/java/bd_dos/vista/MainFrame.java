package bd_dos.vista;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;
    private ClientePanel clientePanel;
    private ProductoPanel productoPanel;
    private VendedorPanel vendedorPanel;
    private FacturaPanel facturaPanel;

    public MainFrame() {
        initComponents();
        setTitle("Sistema de Gesti\u00f3n - bd_dos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        clientePanel = new ClientePanel();
        productoPanel = new ProductoPanel();
        vendedorPanel = new VendedorPanel();
        facturaPanel = new FacturaPanel();

        tabbedPane.addTab("Clientes", clientePanel);
        tabbedPane.addTab("Productos", productoPanel);
        tabbedPane.addTab("Vendedores", vendedorPanel);
        tabbedPane.addTab("Facturas", facturaPanel);

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
