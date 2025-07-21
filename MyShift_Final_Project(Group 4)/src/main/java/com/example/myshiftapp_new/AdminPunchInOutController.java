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

public class AdminPunchInOutController {

    @FXML
    private TextField enterAdminDate;

    @FXML
    private Label messageAdminLabel;

    @FXML
    private Button goBackAdmin;

    @FXML
    private TableView<AdminTimeChart> displayAdminTimeChart;

    @FXML
    private TableColumn<AdminTimeChart, String> displayAdminDate;

    @FXML
    private TableColumn<AdminTimeChart, String> displayAdminClockIn;

    @FXML
    private TableColumn<AdminTimeChart, String> displayAdminClockOut;

    private final ObservableList<AdminTimeChart> AdminTimeChartList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        displayAdminTimeChart.setEditable(false);

        displayAdminDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        displayAdminClockIn.setCellValueFactory(new PropertyValueFactory<>("clockIn"));
        displayAdminClockOut.setCellValueFactory(new PropertyValueFactory<>("clockOut"));

        displayAdminTimeChart();
    }

    private void displayAdminTimeChart(){

        String getAdminTimeChart = "SELECT * FROM ManagementTimeSheet WHERE ManagementID = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(getAdminTimeChart)){

            User currentUser = CurrentUser.getInstance().getLoggedInUser();
            statement.setInt(1, currentUser.getID());

            ResultSet resultSet = statement.executeQuery();

            AdminTimeChartList.clear();

            while (resultSet.next()){
                String date = resultSet.getString("Date");
                String clockIn = resultSet.getString("Clock_In");
                String clockOut = resultSet.getString("Clock_Out");

                AdminTimeChartList.add(new AdminTimeChart(date, clockIn, clockOut));
            }
            displayAdminTimeChart.setItems(AdminTimeChartList);
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdminClockIn(){
        String date = enterAdminDate.getText();
        User currentUser = CurrentUser.getInstance().getLoggedInUser();

        if (date.isEmpty()){
            messageAdminLabel.setText("Please Enter Valid Date!");
            return;
        }

        String insertAdminClockIn = "INSERT INTO ManagementTimeSheet(ManagementID, Date, Clock_In) VALUES(?,?, TIME('now'))";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(insertAdminClockIn)) {

            statement.setInt(1,currentUser.getID());
            statement.setString(2, date);

            statement.executeUpdate();
            enterAdminDate.clear();
            displayAdminTimeChart();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void handleAdminClockOut(){
        String date = enterAdminDate.getText();
        User currentUser = CurrentUser.getInstance().getLoggedInUser();

        if (date.isEmpty()){
            messageAdminLabel.setText("Please Enter Valid Date!");
            return;
        }

        String insertAdminClockOut = "UPDATE ManagementTimeSheet SET Clock_Out = TIME('now') WHERE ManagementID = ? AND Date = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(insertAdminClockOut)) {

            statement.setInt(1,currentUser.getID());
            statement.setString(2, date);

            statement.executeUpdate();
            enterAdminDate.clear();
            displayAdminTimeChart();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void handleAdminGoBack() throws IOException {
        Stage stage = (Stage) goBackAdmin.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminMainScreen.fxml");
    }

}

