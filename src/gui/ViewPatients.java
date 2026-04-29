package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import database.DBConnection;
import javax.swing.table.DefaultTableModel;
import service.PatientService;

public class ViewPatients extends JFrame {
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField searchField;
    private final PatientService patientService = new PatientService();
    private final String idColumnName;

    public ViewPatients() {
        setTitle("Patients");
        setSize(940, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(UiTheme.BG);

        JPanel root = UiTheme.pagePanel(new BorderLayout(10, 10));
        JPanel card = UiTheme.cardPanel(new BorderLayout(10, 10));

        idColumnName = resolveIdColumnName();
        String[] columnNames = {"ID", "Name", "Age", "Gender", "Phone", "Disease"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        UiTheme.styleTable(table);

        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
        topPanel.setBackground(UiTheme.SURFACE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        JLabel heading = UiTheme.heading("Patient Directory");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        searchField = new JTextField();
        UiTheme.styleInput(searchField);
        JButton searchBtn = UiTheme.primaryButton("Search");
        JButton refreshBtn = UiTheme.secondaryButton("Refresh");
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topButtonPanel.setBackground(UiTheme.SURFACE);
        topButtonPanel.add(searchBtn);
        topButtonPanel.add(refreshBtn);
        JPanel searchPanel = new JPanel(new BorderLayout(8, 8));
        searchPanel.setBackground(UiTheme.SURFACE);
        JLabel searchLabel = new JLabel("Search by name, phone, or disease");
        UiTheme.styleLabel(searchLabel);
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(topButtonPanel, BorderLayout.EAST);
        topPanel.add(heading, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(UiTheme.SURFACE);
        JButton deleteBtn = UiTheme.dangerButton("Delete Selected");
        bottomPanel.add(deleteBtn);

        searchBtn.addActionListener(e -> loadPatients(searchField.getText().trim()));
        refreshBtn.addActionListener(e -> {
            searchField.setText("");
            loadPatients("");
        });
        searchField.addActionListener(e -> loadPatients(searchField.getText().trim()));
        deleteBtn.addActionListener(e -> deleteSelectedPatient());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UiTheme.BORDER));

        card.add(topPanel, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);
        root.add(card, BorderLayout.CENTER);
        add(root, BorderLayout.CENTER);
        loadPatients("");
        setVisible(true);
    }

    private void loadPatients(String keyword) {
        model.setRowCount(0);
        String query = "SELECT " + idColumnName + " AS patient_id, name, age, gender, phone, disease FROM Patient";
        boolean hasKeyword = keyword != null && !keyword.isEmpty();
        if (hasKeyword) {
            query += " WHERE name LIKE ? OR phone LIKE ? OR disease LIKE ?";
        }
        query += " ORDER BY patient_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            if (hasKeyword) {
                String likeValue = "%" + keyword + "%";
                ps.setString(1, likeValue);
                ps.setString(2, likeValue);
                ps.setString(3, likeValue);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("disease")
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to load patients.\n" + e.getMessage());
        }
    }

    private void deleteSelectedPatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete.");
            return;
        }

        int patientId = (int) model.getValueAt(selectedRow, 0);
        String patientName = String.valueOf(model.getValueAt(selectedRow, 1));
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete patient: " + patientName + " (ID " + patientId + ")?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = patientService.deletePatientById(patientId);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Patient deleted successfully.");
                loadPatients(searchField.getText().trim());
            } else {
                JOptionPane.showMessageDialog(this, "Could not delete the patient.");
            }
        }
    }

    private String resolveIdColumnName() {
        String[] candidateColumns = {"id", "patient_id", "pid"};
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SHOW COLUMNS FROM Patient");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String column = rs.getString("Field");
                for (String candidate : candidateColumns) {
                    if (candidate.equalsIgnoreCase(column)) {
                        return column;
                    }
                }
            }
        } catch (Exception ignored) {
            // Fall back to the most common key name when metadata is unavailable.
        }
        return "id";
    }
}
