package gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin";

    public LoginFrame() {
        setTitle("Patient Record System - Login");
        setSize(430, 360);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(UiTheme.BG);

        JPanel wrapper = UiTheme.pagePanel(new BorderLayout());
        JPanel card = UiTheme.cardPanel(new BorderLayout(0, 14));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(UiTheme.SURFACE);
        JLabel titleLabel = new JLabel("Patient Record System", SwingConstants.CENTER);
        titleLabel.setFont(UiTheme.TITLE_FONT);
        titleLabel.setForeground(UiTheme.TEXT);
        JLabel subTitle = new JLabel("Secure Staff Login", SwingConstants.CENTER);
        subTitle.setFont(UiTheme.SUBTITLE_FONT);
        subTitle.setForeground(UiTheme.MUTED);
        titlePanel.add(titleLabel);
        titlePanel.add(subTitle);
        card.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UiTheme.SURFACE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel user = new JLabel("Username");
        UiTheme.styleLabel(user);
        JTextField userField = new JTextField(16);
        UiTheme.styleInput(userField);
        JLabel pass = new JLabel("Password");
        UiTheme.styleLabel(pass);
        JPasswordField passField = new JPasswordField();
        UiTheme.styleInput(passField);
        JButton loginBtn = UiTheme.primaryButton("Sign In");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formPanel.add(user, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(pass, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(passField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());
            if (username.equals(DEFAULT_USERNAME) && password.equals(DEFAULT_PASSWORD)) {
                new Dashboard();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
            }
        });

        getRootPane().setDefaultButton(loginBtn);
        card.add(formPanel, BorderLayout.CENTER);
        wrapper.add(card, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
