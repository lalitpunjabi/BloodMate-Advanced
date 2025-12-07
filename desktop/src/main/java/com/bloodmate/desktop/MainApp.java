package com.bloodmate.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Show login screen first
		showLoginScreen(primaryStage);
	}
	
	private void showLoginScreen(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bloodmate/desktop/view/LoginView.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root, 500, 700);
		
		// Load CSS stylesheet
		String css = getClass().getResource("/styles/main.css").toExternalForm();
		scene.getStylesheets().add(css);
		
		primaryStage.setTitle("🩸 BloodMate - Login");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
