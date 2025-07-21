package com.example.myshiftapp_new;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ForgotPasswordController {

    @FXML
    private TextField confirmUsername;

    @FXML
    private TextField confirmBirthday;

    @FXML
    private TextField confirmPin;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label resetMessageLabel;

    @FXML
    private VBox resetForm;

    private String verifiedEmployee;

    private String verifiedAdmin;

    @FXML
    private void verifyUser() throws IOException {
        String username = confirmUsername.getText();
        String birthday = confirmBirthday.getText();
        String pin = confirmPin.getText();

        String selectEmployee = "SELECT * FROM Employees WHERE username = ?";
        String selectAdmin = "SELECT * FROM Management WHERE adminUsername = ?";

        try (Connection connect = new DatabaseConnection().getConnection()) {

            try (PreparedStatement employeeStatement = connect.prepareStatement(selectEmployee)) {
                employeeStatement.setString(1, username);
                ResultSet empResult = employeeStatement.executeQuery();

                if (empResult.next()) {
                    String confirmEmployee = "SELECT * FROM Employees WHERE username = ? AND birthday = ? AND Pin = ?";
                    try (PreparedStatement statement = connect.prepareStatement(confirmEmployee)) {
                        statement.setString(1, username);
                        statement.setString(2, birthday);
                        statement.setString(3, pin);

                        ResultSet result = statement.executeQuery();
                        if (result.next()) {
                            verifiedEmployee = username;
                            resetMessageLabel.setText("User Identity Confirmed. Please Enter Your New Password!");
                            confirmUsername.setDisable(true);
                            confirmBirthday.setDisable(true);
                            confirmPin.setDisable(true);
                            resetForm.setVisible(true);
                            return;
                        }
                    }
                }
            }

            try (PreparedStatement adminStatement = connect.prepareStatement(selectAdmin)) {
                adminStatement.setString(1, username);
                ResultSet adminResult = adminStatement.executeQuery();

                if (adminResult.next()) {
                    String confirmAdmin = "SELECT * FROM Management WHERE adminUsername = ? AND adminBirthday = ? AND Pin = ?";
                    try (PreparedStatement statement = connect.prepareStatement(confirmAdmin)) {
                        statement.setString(1, username);
                        statement.setString(2, birthday);
                        statement.setString(3, pin);

                        ResultSet result = statement.executeQuery();
                        if (result.next()) {
                            verifiedAdmin = username;
                            resetMessageLabel.setText("User Identity Confirmed. Please Enter Your New Password!");
                            confirmUsername.setDisable(true);
                            confirmBirthday.setDisable(true);
                            confirmPin.setDisable(true);
                            resetForm.setVisible(true);
                            return;
                        }
                    }
                }
            }
            resetMessageLabel.setText("Failed To Confirm User Identity!");
            resetForm.setVisible(false);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void resetPassword() throws IOException{
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!newPassword.equals(confirmPassword)) {
            resetMessageLabel.setText("Passwords Do Not Match!");
            return;
        }

        try (Connection connect = new DatabaseConnection().getConnection()) {

            if (verifiedEmployee != null) {
                String updateEmployee = "UPDATE Employees SET password = ? WHERE username = ?";
                try (PreparedStatement statement = connect.prepareStatement(updateEmployee)) {
                    statement.setString(1, newPassword);
                    statement.setString(2, verifiedEmployee);

                    int update = statement.executeUpdate();

                    if (update > 0) {
                        resetMessageLabel.setText("Password Reset Successful! You May Close This Window And Log In!");
                        newPasswordField.setDisable(true);
                        confirmPasswordField.setDisable(true);
                        newPasswordField.clear();
                        confirmPasswordField.clear();
                    } else {
                        resetMessageLabel.setText("Password Reset Failed!");
                    }
                }

            }
            else if (verifiedAdmin != null) {
                String updateAdmin = "UPDATE Management SET adminPassword = ? WHERE adminUsername = ?";
                try (PreparedStatement statement = connect.prepareStatement(updateAdmin)) {
                    statement.setString(1, newPassword);
                    statement.setString(2, verifiedAdmin);

                    int update = statement.executeUpdate();

                    if (update > 0) {
                        resetMessageLabel.setText("Password Reset Successful! You May Close This Window And Log In!");
                        newPasswordField.setDisable(true);
                        confirmPasswordField.setDisable(true);
                        newPasswordField.clear();
                        confirmPasswordField.clear();
                    } else {
                        resetMessageLabel.setText("Password Reset Failed!");
                    }
                }

            }
            else {
                resetMessageLabel.setText("An Error Occurred When Verifying Account!");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
