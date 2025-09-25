package com.bloodmate.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/com/bloodmate/desktop/view/MainView.fxml"));
		Scene scene = new Scene(root, 1200, 800);
		
		// Load CSS stylesheet
		String css = getClass().getResource("/styles/main.css").toExternalForm();
		scene.getStylesheets().add(css);
		
		stage.setTitle("🩸 BloodMate - Blood Bank Management System");
		stage.setMinWidth(1000);
		stage.setMinHeight(700);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
