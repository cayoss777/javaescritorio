package bd_dos.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        String url = null, user = null, pass = null;
        Properties props = new Properties();
        try (InputStream in = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (in != null) {
                props.load(in);
                url = props.getProperty("db.url");
                user = props.getProperty("db.user");
                pass = props.getProperty("db.password");
            }
        } catch (IOException e) {
            System.err.println("Error loading db.properties: " + e.getMessage());
        }

        URL = (url != null) ? url
                : System.getenv().getOrDefault("BD_DOS_URL",
                        "jdbc:mysql://localhost:3306/bd_dos?useSSL=false&serverTimezone=UTC");
        USER = (user != null) ? user
                : System.getenv().getOrDefault("BD_DOS_USER", "root");
        PASSWORD = (pass != null) ? pass
                : System.getenv().getOrDefault("BD_DOS_PASSWORD", "");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
