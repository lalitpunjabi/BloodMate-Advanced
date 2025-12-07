package com.bloodmate.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestSignUp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the sign up view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bloodmate/desktop/view/SignUpView.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root, 500, 800);
        
        // Load CSS stylesheet
        String css = getClass().getResource("/styles/main.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        primaryStage.setTitle("🩸 BloodMate - Sign Up Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}