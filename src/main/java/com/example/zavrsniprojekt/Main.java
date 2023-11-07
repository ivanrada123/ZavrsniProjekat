package com.example.zavrsniprojekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 200);
        stage.setTitle("Završni projekt - Ivan Radojević ");
        stage.setX(300);
        stage.setY(200);
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(false);
    }

    public static void main(String[] args) {
        launch();
    }
}