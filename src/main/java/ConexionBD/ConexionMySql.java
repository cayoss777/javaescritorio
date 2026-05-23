/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USUARIO
 */
public class ConexionMySql {

    private static final String URL
            = "jdbc:mysql://localhost:3306/bd_uai_login?"
            + "useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";
    // Bloque estático para registrar el driver (opcional en Java 8+)

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

}
