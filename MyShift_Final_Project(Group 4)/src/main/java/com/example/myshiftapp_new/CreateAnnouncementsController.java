package com.example.myshiftapp_new;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class CreateAnnouncementsController {

    @FXML
    private Label announcementError;

    @FXML
    private TextField enterDate;

    @FXML
    private TextField enterMessage;

    @FXML
    private TextField enterSender;

    @FXML
    private Button sendAnnouncement;

    @FXML
    private Button goBack;

    @FXML
    private void handleSendAnnouncement() throws IOException{
        String date = enterDate.getText();
        String message = enterMessage.getText();
        String sender = enterSender.getText();

        if (date.isEmpty() || message.isEmpty() || sender.isEmpty()){
            announcementError.setText("Please Enter Valid Announcement Information!");
            return;
        }

        DatabaseConnection database = new DatabaseConnection();
        Connection connect = database.getConnection();

        String insertAnnouncement = "INSERT INTO Announcements (DateAssigned, Message, Sender) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connect.prepareStatement(insertAnnouncement)) {
            statement.setString(1, date);
            statement.setString(2, message);
            statement.setString(3, sender);

            statement.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Stage stage = (Stage) sendAnnouncement.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminMainScreen.fxml");

    }

    @FXML
    private void handleGoBack() throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminMainScreen.fxml");
    }

}
