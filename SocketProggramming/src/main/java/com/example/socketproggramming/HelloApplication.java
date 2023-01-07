package com.example.socketproggramming;


import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            primaryStage.setTitle("Messenger!");
            Scene scene = new Scene(root,330,560);
            //  scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}