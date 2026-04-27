			package gui;
			
			import javax.swing.*;
			import java.awt.*;
			import service.PatientService;
			
			public class AddPatientForm extends JFrame {
			
			    public AddPatientForm() {
			        setTitle("Add Patient");
			        setSize(620, 550);
			        setLayout(new BorderLayout());
			        setLocationRelativeTo(null);
			        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			        getContentPane().setBackground(UiTheme.BG);
			
			        JPanel root = UiTheme.pagePanel(new BorderLayout(10, 10));
			        JPanel card = UiTheme.cardPanel(new BorderLayout(12, 12));
			
			        JPanel headingPanel = new JPanel(new GridLayout(2, 1));
			        headingPanel.setBackground(UiTheme.SURFACE);
			        headingPanel.add(UiTheme.heading("Register New Patient"));
			        headingPanel.add(UiTheme.subheading("Fields marked with * are required"));
			        card.add(headingPanel, BorderLayout.NORTH);
			
			        JPanel formPanel = new JPanel(new GridBagLayout());
			        formPanel.setBackground(UiTheme.SURFACE);
			        GridBagConstraints gbc = new GridBagConstraints();
			        gbc.insets = new Insets(8, 8, 8, 8);
			        gbc.fill = GridBagConstraints.HORIZONTAL;
			
			        JTextField patientId = new JTextField(18);
			        JTextField name = new JTextField(18);
			        JTextField age = new JTextField(18);
			        JComboBox<String> gender = new JComboBox<>(new String[]{"", "Male", "Female", "Other"});
			        JTextField phone = new JTextField(18);
			        JTextField disease = new JTextField(18);
			        UiTheme.styleInput(patientId);
			        UiTheme.styleInput(name);
			        UiTheme.styleInput(age);
			        UiTheme.styleInput(gender);
			        UiTheme.styleInput(phone);
			        UiTheme.styleInput(disease);
			
			
			
			        JLabel patientIdLabel = new JLabel("Patient ID");
			        JLabel nameLabel = new JLabel("Name*");
			        JLabel ageLabel = new JLabel("Age*");
			        JLabel genderLabel = new JLabel("Gender");
			        JLabel phoneLabel = new JLabel("Phone");
			        JLabel diseaseLabel = new JLabel("Disease");
			        UiTheme.styleLabel(patientIdLabel);
			        UiTheme.styleLabel(nameLabel);
			        UiTheme.styleLabel(ageLabel);
			        UiTheme.styleLabel(genderLabel);
			        UiTheme.styleLabel(phoneLabel);
			        UiTheme.styleLabel(diseaseLabel);
			
			        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; formPanel.add(patientIdLabel, gbc);
			        gbc.weightx = 1.0;
			        gbc.gridx = 1; formPanel.add(patientId, gbc);
			        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; formPanel.add(nameLabel, gbc);
			        gbc.weightx = 1.0;
			        gbc.gridx = 1; formPanel.add(name, gbc);
			        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; formPanel.add(ageLabel, gbc);
			        gbc.weightx = 1.0;
			        gbc.gridx = 1; formPanel.add(age, gbc);
			        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; formPanel.add(genderLabel, gbc);
			        gbc.weightx = 1.0;
			        gbc.gridx = 1; formPanel.add(gender, gbc);
			        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0; formPanel.add(phoneLabel, gbc);
			        gbc.weightx = 1.0;
			        gbc.gridx = 1; formPanel.add(phone, gbc);
			        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0; formPanel.add(diseaseLabel, gbc);
			        gbc.weightx = 1.0;
			        gbc.gridx = 1; formPanel.add(disease, gbc);
			
			        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			        actionPanel.setBackground(UiTheme.SURFACE);
			        JButton clear = UiTheme.secondaryButton("Clear");
			        JButton submit = UiTheme.primaryButton("Save Patient");
			        actionPanel.add(clear);
			        actionPanel.add(submit);
			        gbc.gridy = 6;
			        gbc.gridx = 0;
			        gbc.gridwidth = 2;
			        formPanel.add(actionPanel, gbc);
			
			        clear.addActionListener(e -> {
			            patientId.setText("");
			            name.setText("");
			            age.setText("");
			            gender.setSelectedIndex(0);
			            phone.setText("");
			            disease.setText("");
			        });
			
			        submit.addActionListener(e -> {
			            try {
			                String nameValue = name.getText().trim();
			                String patientIdText = patientId.getText().trim();
			                String ageText = age.getText().trim();
			                String genderValue = String.valueOf(gender.getSelectedItem()).trim();
			                String phoneValue = phone.getText().trim();
			                String diseaseValue = disease.getText().trim();
			
			                if (nameValue.isEmpty() || ageText.isEmpty()) {
			                    JOptionPane.showMessageDialog(this, "Please fill all required fields");
			                    return;
			                }
			
			                Integer patientIdValue = null;
			                if (!patientIdText.isEmpty()) {
			                    patientIdValue = Integer.parseInt(patientIdText);
			                    if (patientIdValue <= 0) {
			                        JOptionPane.showMessageDialog(this, "Patient ID must be greater than 0");
			                        return;
			                    }
			                    if (new PatientService().patientIdExists(patientIdValue)) {
			                        JOptionPane.showMessageDialog(this, "Patient ID already exists!");
			                        return;
			                    }
			                }
			
			                int ageValue = Integer.parseInt(ageText);
			                if (ageValue <= 0) {
			                    JOptionPane.showMessageDialog(this, "Age must be greater than 0");
			                    return;
			                }
			                if (!phoneValue.isEmpty() && !phoneValue.matches("[+()\\-\\d\\s]{7,20}")) {
			                    JOptionPane.showMessageDialog(this, "Phone format is invalid");
			                    return;
			                }
			
			                boolean saved = new PatientService().addPatient(
			                    patientIdValue,
			                    nameValue,
			                    ageValue,
			                    genderValue,
			                    phoneValue,
			                    diseaseValue
			                );
			
			                if (!saved) {
			                    JOptionPane.showMessageDialog(this, "Unable to add patient. Please try again.");
			                    return;
			                }
			
			                JOptionPane.showMessageDialog(this, "Patient Added Successfully");
			                clear.doClick();
			
			            } catch(NumberFormatException ex) {
			                JOptionPane.showMessageDialog(this, "Patient ID and Age must be valid numbers");
			            }
			        });
			
			        card.add(formPanel, BorderLayout.CENTER);
			        root.add(card, BorderLayout.CENTER);
			        add(root, BorderLayout.CENTER);
			        setVisible(true);
			    }
			}