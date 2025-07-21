package com.example.myshiftapp_new;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MyShiftApplication extends Application {

    public static void switchScene(Stage stage, String fxmlfile) throws IOException{
        FXMLLoader fxmlloader = new FXMLLoader(MyShiftApplication.class.getResource(fxmlfile));
        Scene scene = new Scene(fxmlloader.load(), 800, 600);
        stage.setScene(scene);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MyShiftApplication.class.getResource("LoginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("MyShift Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}