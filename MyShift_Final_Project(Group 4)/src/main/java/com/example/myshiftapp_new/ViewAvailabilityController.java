package com.example.myshiftapp_new;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewAvailabilityController {

    @FXML
    private TableView<ViewAvailability> viewUserAvailability;

    @FXML
    private TableColumn<ViewAvailability, String> getUsername;

    @FXML
    private TableColumn<ViewAvailability, String> positionTitle;

    @FXML
    private TableColumn<ViewAvailability, String> sunday;

    @FXML
    private TableColumn<ViewAvailability, String> monday;

    @FXML
    private TableColumn<ViewAvailability, String> tuesday;

    @FXML
    private TableColumn<ViewAvailability, String> wednesday;

    @FXML
    private TableColumn<ViewAvailability, String> thursday;

    @FXML
    private TableColumn<ViewAvailability, String> friday;

    @FXML
    private TableColumn<ViewAvailability, String> saturday;

    private final ObservableList<ViewAvailability> ViewAvailabilityList = FXCollections.observableArrayList();


    @FXML
    private Button goBack;

    @FXML
    public void initialize(){
        viewUserAvailability.setEditable(false);

        getUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
        positionTitle.setCellValueFactory(new PropertyValueFactory<>("positionTitle"));
        sunday.setCellValueFactory(new PropertyValueFactory<>("sunday"));
        monday.setCellValueFactory(new PropertyValueFactory<>("monday"));
        tuesday.setCellValueFactory(new PropertyValueFactory<>("tuesday"));
        wednesday.setCellValueFactory(new PropertyValueFactory<>("wednesday"));
        thursday.setCellValueFactory(new PropertyValueFactory<>("thursday"));
        friday.setCellValueFactory(new PropertyValueFactory<>("friday"));
        saturday.setCellValueFactory(new PropertyValueFactory<>("saturday"));

        displayEmployeeAvailability();
    }

    private void displayEmployeeAvailability(){

        String viewAvailability = "SELECT Employees.ID, Employees.username AS getUsername, Employees.positionTitle," +
                                  "EmployeeAvailability.EmployeeID, EmployeeAvailability.Sunday, EmployeeAvailability.Monday," +
                                  "EmployeeAvailability.Tuesday, EmployeeAvailability.Wednesday, EmployeeAvailability.Thursday," +
                                  "EmployeeAvailability.Friday, EmployeeAvailability.Saturday FROM Employees INNER JOIN EmployeeAvailability ON " +
                                  "Employees.ID = EmployeeAvailability.EmployeeID";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(viewAvailability)){

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                String username = resultSet.getString("getUsername");
                String positionTitle = resultSet.getString("positionTitle");
                String sunday = resultSet.getString("sunday");
                String monday = resultSet.getString("monday");
                String tuesday = resultSet.getString("tuesday");
                String wednesday = resultSet.getString("wednesday");
                String thursday = resultSet.getString("thursday");
                String friday = resultSet.getString("friday");
                String saturday = resultSet.getString("saturday");

                ViewAvailabilityList.add(new ViewAvailability(username, positionTitle, sunday, monday, tuesday, wednesday, thursday, friday, saturday));
            }
            viewUserAvailability.setItems(ViewAvailabilityList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateSchedule() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyShiftApplication.class.getResource("CreateSchedule.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("MyShift Manager");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdminGoBack() throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminMainScreen.fxml");

        stage.setWidth(810);
        stage.setHeight(610);

    }
}
