/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlVendedor;

import ConexionBD.ConexionMySql;
import ModeloVendedor.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author USUARIO
 */
public class Control_Vendedor {

    public Vendedor validarLogin(String nombre, String password) {
        String sql = "SELECT idVendedor, nombreVendedor FROM "
                + "tabla_vendedor WHERE nombreVendedor = ? "
                + "AND password = ?";
        try (Connection conn = ConexionMySql.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nombre);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Vendedor v = new Vendedor();
                v.setId(rs.getInt("idVendedor"));
                v.setNombre(rs.getString("nombreVendedor"));
                return v;
            }
        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        return null;
    }

}
