package com.example.myshiftapp_new;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeNotesController {

    @FXML
    private Button goBack;

    @FXML
    private ListView<String> displayNotes;

    @FXML
    private TextArea addNotes;

    String currentNote;

    private ObservableList<String> notesList = FXCollections.observableArrayList();

    @FXML
    private void handleAddNotes(){
        String note = addNotes.getText();

        User currentUser = CurrentUser.getInstance().getLoggedInUser();
        int userID = currentUser.getID();

        String insertNote = "INSERT INTO EmployeeNotes(EmployeeID, NoteContent) VALUES(?,?)";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(insertNote)) {
            statement.setInt(1,userID);
            statement.setString(2, note);
            statement.executeUpdate();

            notesList.add(note);
            addNotes.clear();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void handleDeleteNotes(){

        User currentUser = CurrentUser.getInstance().getLoggedInUser();
        int userID = currentUser.getID();

        String selectedNote = displayNotes.getSelectionModel().getSelectedItem();
        String deleteNote = "DELETE FROM EmployeeNotes WHERE EmployeeID = ? AND NoteContent = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(deleteNote)) {
            statement.setInt(1, userID);
            statement.setString(2, selectedNote);
            statement.executeUpdate();

            notesList.remove(selectedNote);
            addNotes.clear();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void refreshNotesList(){

        User currentUser = CurrentUser.getInstance().getLoggedInUser();
        int userID = currentUser.getID();

        String readNotes = "SELECT NoteContent FROM EmployeeNotes WHERE EmployeeID = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(readNotes)) {

            statement.setInt(1,userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String note = resultSet.getString("NoteContent");
                notesList.add(note);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() throws IOException {
        refreshNotesList();
        displayNotes.setItems(notesList);

        displayNotes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentNote = String.valueOf(displayNotes.getSelectionModel().getSelectedItems());
                addNotes.setText(currentNote);
            }
        });

    }

    @FXML
    private void handleGoBack() throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "EmployeeMainScreen.fxml");
    }

}
