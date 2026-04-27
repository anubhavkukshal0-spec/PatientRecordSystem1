import database.DBConnection;
import java.sql.Connection;
import java.sql.Statement;

public class DBCreator {
    public static void main(String[] args) {
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement()) {
             
            System.out.println("Connected to DB successfully.");

            String createPatient = "CREATE TABLE IF NOT EXISTS Patient (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "age INT NOT NULL, " +
                    "gender VARCHAR(50), " +
                    "phone VARCHAR(50), " +
                    "disease VARCHAR(255))";
            stmt.executeUpdate(createPatient);
            System.out.println("Patient table checked/created.");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
