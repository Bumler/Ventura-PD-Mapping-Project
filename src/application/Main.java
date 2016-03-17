package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	//Hello Github!
	FileChooser fileChooser;
	File file;
	 @Override
	    public void start(Stage primaryStage) {
	        primaryStage.setTitle("Hello World!");
	        Button btn = new Button();
	        btn.setText("Choose File");
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	            	//Opens a File Dialog. And sets active file to the one chose.
	                System.out.println("File Chooser Opened");
	                fileChooser = new FileChooser();
	                fileChooser.setTitle("File Chooser");
	                file = fileChooser.showOpenDialog(primaryStage);
	            }
	        });
	        
	        Button btn2 = new Button();
	        btn2.setText("Clean");
	        btn2.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					AddressCleaner cleaner = new AddressCleaner();
					ArrayList<String> addresses = cleaner.getCleanedAddresses(file);
				}
	        	
	        });
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));
	        
	        grid.add(btn, 0, 0);
	        grid.add(btn2, 4, 0);
	        primaryStage.setScene(new Scene(grid, 300, 250));
	        primaryStage.show();
	    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
