import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VotingManagementSystem {
    private JFrame frame;
    private Connection connection;

    public VotingManagementSystem() {
        frame = new JFrame("Voting Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(240, 240, 240));
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing

        JLabel titleLabel = new JLabel("Welcome to Voting Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font and size
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, gbc);

        gbc.gridy++;

        JLabel voterLabel = new JLabel("For Voters:");
        voterLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(voterLabel, gbc);

        gbc.gridy++;

        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(200, 40)); // Set button size
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open registration screen
                openRegistrationScreen();
            }
        });
        panel.add(registerButton, gbc);

        gbc.gridy++; // Move to the next row

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 40)); // Set button size
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open login screen
                openLoginScreen();
            }
        });
        panel.add(loginButton, gbc);

        gbc.gridy++;

        JLabel adminLabel = new JLabel("For Admin:");
        adminLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(adminLabel, gbc);

        gbc.gridy++;

        JButton adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setPreferredSize(new Dimension(200, 40)); // Set button size
        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open admin login screen
                openAdminLoginScreen();
            }
        });
        panel.add(adminLoginButton, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void openRegistrationScreen() {
        JFrame registrationFrame = new JFrame("Registration");
        registrationFrame.setSize(600, 400);
        registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the frame is closed
        registrationFrame.setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for better control over component placement and spacing
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add insets for spacing

        // Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20); // Set preferred width
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(emailField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20); // Set preferred width
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        // Full Name
        JLabel fullNameLabel = new JLabel("Full Name:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(fullNameLabel, gbc);

        JTextField fullNameField = new JTextField(20); // Set preferred width
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(fullNameField, gbc);

        // Aadhar Card Number
        JLabel aadharCardLabel = new JLabel("Aadhar Card Number:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(aadharCardLabel, gbc);

        JTextField aadharCardField = new JTextField(20); // Set preferred width
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(aadharCardField, gbc);

        // PAN Card Number
        JLabel panCardLabel = new JLabel("PAN Card Number:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(panCardLabel, gbc);

        JTextField panCardField = new JTextField(20); // Set preferred width
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(panCardField, gbc);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Register user
                registerUser(emailField.getText(), new String(passwordField.getPassword()), fullNameField.getText(), aadharCardField.getText(), panCardField.getText());
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        panel.add(registerButton, gbc);

        registrationFrame.add(panel);
        registrationFrame.setVisible(true);
    }

    private void registerUser(String email, String password, String fullName, String aadharCardNumber, String panCardNumber) {

        Connection connection = null;
        try {

            String jdbcURL = "jdbc:mysql://localhost:3306/voting_management";
            String username = "root";
            String dbPassword = ""; // Rename password variable to avoid conflict
            connection = DriverManager.getConnection(jdbcURL, username, dbPassword);

            // Prepare SQL statement
            String sql = "INSERT INTO users (email, password, full_name, aadhar_card_number, pan_card_number) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, fullName);
            statement.setString(4, aadharCardNumber);
            statement.setString(5, panCardNumber);

            // Execute the statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "User registered successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to register user.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void openLoginScreen() {
        JPanel loginPanel = new LoginScreen().getLoginPanel();

        frame.getContentPane().removeAll();

        frame.add(loginPanel);

        frame.revalidate();
        frame.repaint();
    }

    private void openAdminLoginScreen() {
        // Hardcoded admin credentials
        String adminUsername = "admin";
        String adminPassword = "admin123";

        // Check if the admin login credentials are correct
        String inputUsername = JOptionPane.showInputDialog(frame, "Enter Admin Username:");
        String inputPassword = JOptionPane.showInputDialog(frame, "Enter Admin Password:");

        if (inputUsername != null && inputPassword != null &&
                inputUsername.equals(adminUsername) && inputPassword.equals(adminPassword)) {
            try {
                // Connect to MySQL database
                String jdbcURL = "jdbc:mysql://localhost:3306/voting_management";
                String username = "root";
                String dbPassword = ""; // Rename password variable to avoid conflict
                Connection connection = DriverManager.getConnection(jdbcURL, username, dbPassword);

                // Open admin view
                Admin admin = new Admin(frame, connection);
                admin.openAdminView();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Admin Credentials!");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VotingManagementSystem();
            }
        });
    }
}
