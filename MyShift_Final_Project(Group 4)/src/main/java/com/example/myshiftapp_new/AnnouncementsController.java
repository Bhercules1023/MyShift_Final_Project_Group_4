package com.example.myshiftapp_new;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.io.IOException;

public class AnnouncementsController {

    @FXML
    private TableView<Announcement> displayAnnouncements;

    @FXML
    private TableColumn<Announcement, String> displayDate;

    @FXML
    private TableColumn<Announcement, String> displayMessage;

    @FXML
    private TableColumn<Announcement, String> displaySender;

    @FXML
    private Button goBack;

    private final ObservableList<Announcement> announcementList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        displayAnnouncements.setEditable(false);

        displayDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        displayMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        displaySender.setCellValueFactory(new PropertyValueFactory<>("sender"));

        displayAnnouncement();
    }

    private void displayAnnouncement(){
        DatabaseConnection database = new DatabaseConnection();
        Connection connect = database.getConnection();

        String getAnnouncement = "SELECT * FROM Announcements";

        try(Statement statement = connect.createStatement()){

            ResultSet resultSet = statement.executeQuery(getAnnouncement);

            while (resultSet.next()){
                String date = resultSet.getString("DateAssigned");
                String message = resultSet.getString("Message");
                String sender = resultSet.getString("Sender");

                announcementList.add(new Announcement(date, message, sender));
            }
            displayAnnouncements.setItems(announcementList);
        }

        catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void handleGoBack() throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "EmployeeMainScreen.fxml");
    }

}
