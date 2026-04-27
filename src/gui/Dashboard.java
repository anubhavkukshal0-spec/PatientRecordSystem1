package gui;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {
        setTitle("Patient Record System - Dashboard");
        setSize(620, 480);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UiTheme.BG);

        JPanel root = UiTheme.pagePanel(new BorderLayout(12, 12));
        JPanel card = UiTheme.cardPanel(new BorderLayout(18, 18));

        JPanel headingPanel = new JPanel(new GridLayout(2, 1));
        headingPanel.setBackground(UiTheme.SURFACE);
        headingPanel.add(UiTheme.heading("Dashboard"));
        headingPanel.add(UiTheme.subheading("Manage patient records quickly and securely"));
        card.add(headingPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 12));
        buttonPanel.setBackground(UiTheme.SURFACE);
        JButton addBtn = UiTheme.primaryButton("Add Patient");
        JButton viewBtn = UiTheme.secondaryButton("View Patients");
        JButton logoutBtn = UiTheme.dangerButton("Logout");
        
        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(logoutBtn);
        card.add(buttonPanel, BorderLayout.CENTER);

        addBtn.addActionListener(e -> new AddPatientForm());
        viewBtn.addActionListener(e -> new ViewPatients());
        logoutBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        root.add(card, BorderLayout.CENTER);
        add(root, BorderLayout.CENTER);
        setVisible(true);
    }
}
