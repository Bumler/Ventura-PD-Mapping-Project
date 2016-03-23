package application;
	
import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Main extends Application {
	//Hello Github!
	FileChooser fileChooser;
	File file;
	Button btn, btn2, btnSettings;
	 @Override
	    public void start(Stage primaryStage) {
		 	AddressCleaner cleaner = new AddressCleaner();
			cleaner.addStartList("#", "deleteW");
			cleaner.addFindList("/", "replaceW", "&");
			cleaner.addStartList("Bogus", "deleteC");
			
	        primaryStage.setTitle("Address Cleaner");
	        
	        btn = new Button();
	        btn.setText("Choose File");
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	            	//Opens a File Dialog. And sets active file to the one chose.
	                System.out.println("File Chooser Opened");
	                fileChooser = new FileChooser();
	                fileChooser.setTitle("File Chooser");
	                file = fileChooser.showOpenDialog(primaryStage);
	                if(file != null) {
	                	btn2.setDisable(false);
	                }	             
	            }
	        });
	        
	        btn2 = new Button();
	        btn2.setText("Clean");
	        btn2.setDisable(true);
	        btn2.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					ArrayList<String> addresses = cleaner.getCleanedAddresses(file);
					showAddresses(addresses);
				}
	        	
	        });
	        
	        btnSettings = new Button();
	        btnSettings.setText("Settings");
	        btnSettings.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					Stage stage = new Stage();
					stage.setTitle("Settings");
					
					GridPane grid = new GridPane();
			        grid.setAlignment(Pos.CENTER);
			        grid.setHgap(10);
			        grid.setVgap(10);
			        grid.setPadding(new Insets(25, 25, 25, 25));
			        
			        ArrayList<String[]> settings = cleaner.getSettings();
			        int i = 0;
			        for(String[] setting : settings) {
			        	
			        	ObservableList<String> options1 = 
			        		    FXCollections.observableArrayList(
			        		        "Starts With",
			        		    	"Find"
			        		    );
			        	ComboBox comboBox1 = new ComboBox(options1);
			        	TextField textField1 = new TextField();
			        	ObservableList<String> options2 = 
			        		    FXCollections.observableArrayList(
			        		        "Replace",
			        		    	"Delete",
			        		    	"Delete Cell"
			        		    );
			        	ComboBox comboBox2 = new ComboBox(options2);
			        	TextField textField2 = new TextField();
			        	
			        	switch(setting[0]) {
			        		case "start":
			        			comboBox1.setValue("Starts With");
			        			break;
			        		case "find":
			        			comboBox1.setValue("Find");
			        			break;
			        	}
			        		textField1.setText(setting[1]); 
			        	switch(setting[2]) {
			        		case "replaceW":
			        			comboBox2.setValue("Replace");
			        			break;
			        		case "deleteW":
			        			comboBox2.setValue("Delete");
			        			break;
			        		case "deleteC":
			        			comboBox2.setValue("Delete Cell");
			        			break;
			        	}		
			        	textField2.setText(setting[3]);
			        	
			        	Button deleteButton = new Button();
			        	deleteButton.setText("X");
			        	
			        	deleteButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent arg0) {
								final int i = grid.getRowIndex(deleteButton);
								ObservableList<Node> childrens = grid.getChildren();
								ArrayList<Node> deleteList = new ArrayList<>();
								ArrayList<Node> moveList = new ArrayList<>();
						        for(Node node : childrens) {
						        	if(grid.getRowIndex(node) == i) {						       
						        		deleteList.add(node);
						        	}
						        	if(grid.getRowIndex(node) > i) {
						        		moveList.add(node);
						        	}
						        }
						        for(Node node : deleteList) {
						        	grid.getChildren().remove(node);
						        }
						        for(Node node : moveList) {
						        	int rowIndex = grid.getRowIndex(node);
						        	int columnIndex = grid.getColumnIndex(node);
						        	grid.getChildren().remove(node);
						        	grid.add(node,columnIndex, rowIndex - 2);
						        }
						        stage.setScene(new Scene(grid, 900, 450));
						        stage.show();
							}
			        		
			        	});
			        	grid.add(comboBox1, 0, i);
			        	grid.add(textField1, 4, i);
			        	grid.add(comboBox2, 8, i);
			        	grid.add(textField2, 12, i);
			        	grid.add(deleteButton, 16, i);
			        	i += 2;
			        }
			        Button btnAdd = new Button();
			        btnAdd.setText("Add");
			        Button btnSave = new Button();
			        btnSave.setText("Save");
			        
			        grid.add(btnAdd, 0, i);
			        grid.add(btnSave, 16, i);
			        stage.setScene(new Scene(grid, 900, 450));
			        stage.show();
			        
			        btnAdd.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							ObservableList<String> options1 = 
				        		    FXCollections.observableArrayList(
				        		        "Starts With",
				        		    	"Find"
				        		    );
				        	ComboBox comboBox1 = new ComboBox(options1);
				        	TextField textField1 = new TextField();
				        	ObservableList<String> options2 = 
				        		    FXCollections.observableArrayList(
				        		        "Replace",
				        		    	"Delete",
				        		    	"Delete Cell"
				        		    );
				        	ComboBox comboBox2 = new ComboBox(options2);
				        	TextField textField2 = new TextField();
				        	
				        	int i = grid.getRowIndex(btnAdd);
				        	grid.add(comboBox1, 0, i);
				        	grid.add(textField1, 4, i);
				        	grid.add(comboBox2, 8, i);
				        	grid.add(textField2, 12, i);
				        	
				        	Button deleteButton = new Button();
				        	deleteButton.setText("X");
				        	
				        	deleteButton.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									final int i = grid.getRowIndex(deleteButton);
									ObservableList<Node> childrens = grid.getChildren();
									ArrayList<Node> deleteList = new ArrayList<>();
									ArrayList<Node> moveList = new ArrayList<>();
							        for(Node node : childrens) {
							        	if(grid.getRowIndex(node) == i) {						       
							        		deleteList.add(node);
							        	}
							        	if(grid.getRowIndex(node) > i) {
							        		moveList.add(node);
							        	}
							        }
							        for(Node node : deleteList) {
							        	grid.getChildren().remove(node);
							        }
							        for(Node node : moveList) {
							        	int rowIndex = grid.getRowIndex(node);
							        	int columnIndex = grid.getColumnIndex(node);
							        	grid.getChildren().remove(node);
							        	grid.add(node,columnIndex, rowIndex - 2);
							        }
							        stage.setScene(new Scene(grid, 900, 450));
							        stage.show();
								}
				        		
				        	});
				        	
				        	grid.add(deleteButton, 16, i);
				        	grid.setRowIndex(btnAdd, i+2);
				        	grid.setRowIndex(btnSave, i+2);
				        	stage.setScene(new Scene(grid, 900, 450));
					        stage.show();
						}
			        	
			        });
			        btnSave.setOnAction(new EventHandler<ActionEvent>() {
			 
						@Override
						public void handle(ActionEvent arg0) {
							String[] setting = new String[4];
							int k = grid.getRowIndex(btnSave);
							int counter = 0;
							cleaner.clearSettings();
							ObservableList<Node> childrens = grid.getChildren();
					        for(Node node : childrens) {
					        	if(grid.getRowIndex(node) < k) {
					        		counter+=1;
					        		System.out.println("ENTERING LOOP");
					        		switch(grid.getColumnIndex(node)) {
					        			case 0:
					        				System.out.println("Saving Setting 0");
					        				switch((String)((ComboBox) node).getValue()) {			
					        					case "Starts With":
					        						setting[0] = "start";
					        						break;
					        					case "Find":
					        						setting[0] = "find";
					        						break;
					        				}
					        				break;
					        			case 4:
					        				System.out.println("Saving Setting 1");
					        				setting[1] = ((TextField) node).getText();
					        				break;
					        			case 8:
					        				System.out.println("Saving Setting 2");
					        				switch((String)((ComboBox) node).getValue()) {
				        						case "Replace":
				        							setting[2] = "replaceW";
				        							break;
				        						case "Delete":
				        							setting[2] = "deleteW";
				        							break;
				        						case "Delete Cell":
				        							setting[2] = "deleteC";
				        							break;
					        				}
					        				break;
					        			case 12:
					        				System.out.println("Saving Setting 3");
					        				setting[3] = ((TextField) node).getText();
					        				break;
					        		}
					        	}
					        	if(counter == 5) {
					        System.out.println("Settings: " + setting[0] + setting[1] + setting[2] + setting[3]);
					        if(setting[3] == null) setting[3] = "";
					        cleaner.addList(setting[0], setting[1], setting[2], setting[3]);
					        setting = new String[4];
					        counter = 0;
					        	}
					        }
						} 
			        }); 
				}
	        });
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));
	        
	        grid.add(btn, 0, 0);
	        grid.add(btn2, 4, 0);
	        grid.add(btnSettings, 4, 4);
	        primaryStage.setScene(new Scene(grid, 300, 250));
	        primaryStage.show();
	    }
	
	public void showAddresses(ArrayList<String> addresses) {
		Stage stage = new Stage();
		stage.setTitle("Addresses");
		
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        String addressString = formatList(addresses);
        
        TextArea addressesText = new TextArea(addressString);
        addressesText.setEditable(false);
		grid.add(addressesText, 0, 0);
		
		final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(addressString);
        clipboard.setContent(content);
		
		stage.setScene(new Scene(grid, 450, 450));
		
		
		stage.show();
	}
	
	public String formatList(ArrayList<String> list) {
		StringBuilder sb = new StringBuilder();
		for(String str : list) {
			sb.append(str + "\n");
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
