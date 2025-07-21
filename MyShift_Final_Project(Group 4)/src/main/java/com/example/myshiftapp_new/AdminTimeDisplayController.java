package com.example.myshiftapp_new;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AdminTimeDisplayController {
    @FXML
    private Label timeLabel;

    @FXML
    private Button goBack;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

    @FXML
    public void initialize(){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeLabel.setText(LocalTime.now().format(formatter));
            }
        };
        timer.start();
    }

    @FXML
    private void handleGoBack() throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminMainScreen.fxml");
    }
}
