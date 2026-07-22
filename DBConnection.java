import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:reservation.db");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}