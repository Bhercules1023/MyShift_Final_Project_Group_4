package com.example.myshiftapp_new;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminBenefitsController {

    @FXML
    private Button goBack;

    @FXML
    private void handleMainGoBack() throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminMainScreen.fxml");
    }
}
