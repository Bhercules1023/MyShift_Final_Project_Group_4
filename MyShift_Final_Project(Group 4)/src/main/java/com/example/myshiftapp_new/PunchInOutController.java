package com.example.myshiftapp_new;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.IOException;

public class PunchInOutController {

    @FXML
    private TextField enterDate;

    @FXML
    private Label messageLabel;

    @FXML
    private Button goBack;

    @FXML
    private TableView<TimeChart> displayEmployeeTimeChart;

    @FXML
    private TableColumn<TimeChart, String> displayDate;

    @FXML
    private TableColumn<TimeChart, String> displayClockIn;

    @FXML
    private TableColumn<TimeChart, String> displayClockOut;

    private final ObservableList<TimeChart> TimeChartList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        displayEmployeeTimeChart.setEditable(false);

        displayDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        displayClockIn.setCellValueFactory(new PropertyValueFactory<>("clockIn"));
        displayClockOut.setCellValueFactory(new PropertyValueFactory<>("clockOut"));

        displayEmployeeTimeChart();
    }

    private void displayEmployeeTimeChart(){

        String getTimeChart = "SELECT * FROM EmployeeTimeSheet WHERE EmployeeID = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(getTimeChart)){

            User currentUser = CurrentUser.getInstance().getLoggedInUser();
            statement.setInt(1, currentUser.getID());

            ResultSet resultSet = statement.executeQuery();

            TimeChartList.clear();

            while (resultSet.next()){
                String date = resultSet.getString("Date");
                String clockIn = resultSet.getString("Clock_In");
                String clockOut = resultSet.getString("Clock_Out");

                TimeChartList.add(new TimeChart(date, clockIn, clockOut));
            }
            displayEmployeeTimeChart.setItems(TimeChartList);
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEmployeeClockIn(){
        String date = enterDate.getText();
        User currentUser = CurrentUser.getInstance().getLoggedInUser();

        if (date.isEmpty()){
            messageLabel.setText("Please Enter Valid Date!");
            return;
        }

        String insertClockIn = "INSERT INTO EmployeeTimeSheet(EmployeeID, Date, Clock_In) VALUES(?,?, TIME('now'))";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(insertClockIn)) {

            statement.setInt(1,currentUser.getID());
            statement.setString(2, date);

            int update = statement.executeUpdate();

            if(update > 0){
                messageLabel.setText("Successfully Clocked In!");
            }
            else{
                messageLabel.setText("Failed to Clocked In!");
            }

            enterDate.clear();
            displayEmployeeTimeChart();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEmployeeClockOut(){
        String date = enterDate.getText();
        User currentUser = CurrentUser.getInstance().getLoggedInUser();

        if (date.isEmpty()){
            messageLabel.setText("Please Enter Valid Date!");
            return;
        }

        String insertClockOut = "UPDATE EmployeeTimeSheet SET Clock_Out = TIME('now') WHERE EmployeeID = ? AND Date = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(insertClockOut)) {

            statement.setInt(1,currentUser.getID());
            statement.setString(2, date);

            int update = statement.executeUpdate();

            if(update > 0){
                messageLabel.setText("Successfully Clocked Out!");
            }
            else{
                messageLabel.setText("Failed to Clocked Out!");
            }

            enterDate.clear();
            displayEmployeeTimeChart();
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
