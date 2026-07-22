import java.sql.Connection;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            st.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "username TEXT," +
                            "password TEXT)"
            );

            st.execute(
                    "CREATE TABLE IF NOT EXISTS reservations (" +
                            "pnr TEXT PRIMARY KEY," +
                            "passengerName TEXT," +
                            "trainNo INTEGER," +
                            "trainName TEXT," +
                            "classType TEXT," +
                            "journeyDate TEXT," +
                            "source TEXT," +
                            "destination TEXT)"
            );

            st.executeUpdate(
                    "INSERT OR IGNORE INTO users(username,password) VALUES('admin','admin123')"
            );

            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        new LoginFrame();
    }
}