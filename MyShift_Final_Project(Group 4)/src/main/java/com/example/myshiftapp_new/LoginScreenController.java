package com.example.myshiftapp_new;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginScreenController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginFail;

    @FXML
    private Hyperlink newUser;


    @FXML
    private void handleLogin() throws IOException {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()){
            loginFail.setText("Please Enter a Username and Password!");
            return;
        }

        User validEmployee = UserAuthentication.verifyEmployee(username, password);
        User validAdmin = UserAuthentication.verifyAdmin(username, password);

        if (validEmployee != null){
            CurrentUser.getInstance().setLoggedInUser(validEmployee);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            MyShiftApplication.switchScene(stage, "EmployeeMainScreen.fxml");
        }
        else if (validAdmin != null){
            CurrentUser.getInstance().setLoggedInUser(validAdmin);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            MyShiftApplication.switchScene(stage, "AdminMainScreen.fxml");
        }
        else{
            loginFail.setText("Invalid Username or Password!");
        }

    }

    @FXML
    private void handleNewUserPage() throws IOException{
        Stage stage = (Stage) newUser.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "CreateNewUser.fxml");

    }

    @FXML
    private void handleForgotPassword(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MyShiftApplication.class.getResource("ForgotPassword.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 700);
            Stage stage = new Stage();
            stage.setTitle("MyShift Manager");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}