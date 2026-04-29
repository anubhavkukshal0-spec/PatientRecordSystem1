package service;
import java.sql.*;
import database.DBConnection;

public class PatientService{

    public boolean patientIdExists(int patientId) {
        if (patientId <= 0) {
            return false;
        }
        try (Connection con = DBConnection.getConnection()) {
            String idColumn = resolveIdColumn(con);
            String query = "SELECT 1 FROM Patient WHERE " + idColumn + " = ? LIMIT 1";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, patientId);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPatient(Integer patientId, String name, int age, String gender, String phone, String disease) {
        String cleanName = normalize(name);
        String cleanGender = normalize(gender);
        String cleanPhone = normalize(phone);
        String cleanDisease = normalize(disease);

        if (cleanName.isEmpty() || age <= 0) {
            return false;
        }
        if (patientId != null && patientId <= 0) {
            return false;
        }

        try (Connection con = DBConnection.getConnection()) {
            String idColumn = resolveIdColumn(con);
            String query;
            if (patientId == null) {
                query = "INSERT INTO Patient(name, age, gender, phone, disease) VALUES (?, ?, ?, ?, ?)";
            } else {
                query = "INSERT INTO Patient(" + idColumn + ", name, age, gender, phone, disease) VALUES (?, ?, ?, ?, ?, ?)";
            }

            try (PreparedStatement ps = con.prepareStatement(query)) {
                int index = 1;
                if (patientId != null) {
                    ps.setInt(index++, patientId);
                }
                ps.setString(index++, cleanName);
                ps.setInt(index++, age);
                ps.setString(index++, cleanGender);
                ps.setString(index++, cleanPhone);
                ps.setString(index++, cleanDisease);

                int rows = ps.executeUpdate();
                return rows > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
  
