package com.example.myshiftapp_new;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SetAvailabilityController {

    @FXML
    private Button goBack;

    @FXML
    private Button addNewAvailability;

    @FXML
    private TextField enterSunday;

    @FXML
    private TextField enterMonday;

    @FXML
    private TextField enterTuesday;

    @FXML
    private TextField enterWednesday;

    @FXML
    private TextField enterThursday;

    @FXML
    private TextField enterFriday;

    @FXML
    private TextField enterSaturday;

    @FXML
    private Label errorLabel;

    @FXML
    private TableView<Availability> viewAvailability;

    @FXML
    private TableColumn<Availability, String> sunday;

    @FXML
    private TableColumn<Availability, String> monday;

    @FXML
    private TableColumn<Availability, String> tuesday;

    @FXML
    private TableColumn<Availability, String> wednesday;

    @FXML
    private TableColumn<Availability, String> thursday;

    @FXML
    private TableColumn<Availability, String> friday;

    @FXML
    private TableColumn<Availability, String> saturday;

    private final ObservableList<Availability> AvailabilityList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        viewAvailability.setEditable(false);

        sunday.setCellValueFactory(new PropertyValueFactory<>("sunday"));
        monday.setCellValueFactory(new PropertyValueFactory<>("monday"));
        tuesday.setCellValueFactory(new PropertyValueFactory<>("tuesday"));
        wednesday.setCellValueFactory(new PropertyValueFactory<>("wednesday"));
        thursday.setCellValueFactory(new PropertyValueFactory<>("thursday"));
        friday.setCellValueFactory(new PropertyValueFactory<>("friday"));
        saturday.setCellValueFactory(new PropertyValueFactory<>("saturday"));

        displayAvailability();
        checkExistingAvailability();
    }

    @FXML
    private void addAvailability(){
        String sunday = enterSunday.getText();
        String monday = enterMonday.getText();
        String tuesday = enterTuesday.getText();
        String wednesday = enterWednesday.getText();
        String thursday = enterThursday.getText();
        String friday = enterFriday.getText();
        String saturday = enterSaturday.getText();

        User currentUser = CurrentUser.getInstance().getLoggedInUser();

        String addAvailability = "INSERT INTO EmployeeAvailability (EmployeeID, Sunday, Monday, " +
                                 "Tuesday, Wednesday, " +
                                 "Thursday, Friday, Saturday)" +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(addAvailability)){

            statement.setInt(1, currentUser.getID());
            statement.setString(2, sunday);
            statement.setString(3, monday);
            statement.setString(4, tuesday);
            statement.setString(5, wednesday);
            statement.setString(6, thursday);
            statement.setString(7, friday);
            statement.setString(8, saturday);


            int added = statement.executeUpdate();

            enterSunday.clear();
            enterMonday.clear();
            enterTuesday.clear();
            enterWednesday.clear();
            enterThursday.clear();
            enterFriday.clear();
            enterSaturday.clear();

            if (added > 0){
                errorLabel.setText("Availability Successfully Changed!");
                addNewAvailability.setDisable(true);

                AvailabilityList.clear();
                displayAvailability();
            }
            else{
                errorLabel.setText("Availability Could Not Be Changed! Please Try Again!");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void updateAvailability() {
        String sunday = enterSunday.getText();
        String monday = enterMonday.getText();
        String tuesday = enterTuesday.getText();
        String wednesday = enterWednesday.getText();
        String thursday = enterThursday.getText();
        String friday = enterFriday.getText();
        String saturday = enterSaturday.getText();

        User currentUser = CurrentUser.getInstance().getLoggedInUser();

        String updateAvailability = "UPDATE EmployeeAvailability SET Sunday = ?, Monday = ?, Tuesday = ?," +
                                    "Wednesday = ?, Thursday = ?, Friday = ?, Saturday = ?" +
                                    "WHERE EmployeeID = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(updateAvailability)){

            statement.setString(1, sunday);
            statement.setString(2, monday);
            statement.setString(3, tuesday);
            statement.setString(4, wednesday);
            statement.setString(5, thursday);
            statement.setString(6, friday);
            statement.setString(7, saturday);
            statement.setInt(8, currentUser.getID());

            int updated = statement.executeUpdate();

            enterSunday.clear();
            enterMonday.clear();
            enterTuesday.clear();
            enterWednesday.clear();
            enterThursday.clear();
            enterFriday.clear();
            enterSaturday.clear();

            if (updated > 0){
                errorLabel.setText("Availability Successfully Changed!");

                AvailabilityList.clear();
                displayAvailability();
            }
            else{
                errorLabel.setText("Availability Could Not Be Changed! Please Try Again!");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void displayAvailability(){

        User currentUser = CurrentUser.getInstance().getLoggedInUser();
        String getAvailability = "SELECT * FROM EmployeeAvailability WHERE EmployeeID = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(getAvailability)){

            statement.setInt(1, currentUser.getID());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                String sunday = resultSet.getString("Sunday");
                String monday = resultSet.getString("Monday");
                String tuesday = resultSet.getString("Tuesday");
                String wednesday = resultSet.getString("Wednesday");
                String thursday = resultSet.getString("Thursday");
                String friday = resultSet.getString("Friday");
                String saturday = resultSet.getString("Saturday");

                AvailabilityList.add(new Availability(sunday, monday, tuesday, wednesday, thursday, friday, saturday));
            }
            viewAvailability.setItems(AvailabilityList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void checkExistingAvailability(){
        User currentUser = CurrentUser.getInstance().getLoggedInUser();

        String checkRecord = "SELECT * FROM EmployeeAvailability WHERE EmployeeID = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(checkRecord)) {

            statement.setInt(1, currentUser.getID());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                addNewAvailability.setDisable(true);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBackMainScreen() throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "EmployeeMainScreen.fxml");
    }
}
