package com.example.hrmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.JOptionPane;
import java.io.IOException;

public class HelloController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorMessage;

    private static final String CORRECT_USERNAME = "chahat";
    private static final String CORRECT_PASSWORD = "456";
    private static final int MAX_ATTEMPTS = 3;
    private int failedAttempts = 0;

    @FXML
    protected void login() {
        String uname = usernameField.getText();
        String upass = passwordField.getText();

        if (uname.isEmpty() || upass.isEmpty()) {
            errorMessage.setText("Please provide both username and password.");
            return;
        }

        if (uname.equals(CORRECT_USERNAME) && upass.equals(CORRECT_PASSWORD)) {
            JOptionPane.showMessageDialog(null, "Login Successful!");
            failedAttempts = 0; // Reset failed attempts after successful login
            loadDashboard(); // Proceed to load the dashboard scene
        } else {
            failedAttempts++;
            errorMessage.setText("Invalid Username or Password. Attempt " + failedAttempts + " of " + MAX_ATTEMPTS);

            // Lock account if maximum attempts reached
            if (failedAttempts >= MAX_ATTEMPTS) {
                errorMessage.setText("Account locked due to too many failed attempts.");
                disableLoginFields();
            }
        }
    }

    // Disables login fields after maximum failed attempts
    private void disableLoginFields() {
        usernameField.setDisable(true);
        passwordField.setDisable(true);
    }

    // Loads the dashboard scene upon successful login
    private void loadDashboard() {
        try {
            Parent dashboardView = FXMLLoader.load(getClass().getResource("dash-view.fxml"));
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Dashboard");
            dashboardStage.setScene(new Scene(dashboardView));

            // Close the current (login) stage
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();

            // Show the dashboard stage
            dashboardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage.setText("Failed to load dashboard.");
        }
    }
}
