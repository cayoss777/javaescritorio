/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.uailogin;

import VistaVentas.frmLogin;
import VistaVentas.frmPrincipal;
import javax.swing.SwingUtilities;

/**
 *
 * @author USUARIO
 */
public class UaiLogin {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        // Usando la buena práctica con SwingUtilities
        SwingUtilities.invokeLater(() -> {
            // Suponiendo que tienes una clase llamada frmLogin
            //frmPrincipal principal = new frmPrincipal();
            //principal.setVisible(true);
            
            frmLogin login=  new frmLogin();
            login.setVisible(true);
        });

    }
}
