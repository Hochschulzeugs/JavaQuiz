package de.uni_hannover.hci.group_3122.gui;

import javafx.fxml.*;
import de.uni_hannover.hci.group_3122.database.*;
import javafx.scene.control.Button;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.event.*;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

public class StartscreenController {
	private Stage primaryStage = de.uni_hannover.hci.group_3122.application.Main.primaryStage;
	
    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonRegister;
	
	@FXML
	private void buttonHandleLogin(ActionEvent event){
		try{
			Button button = (Button) event.getSource();
			String source = button.getText();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginWindow.fxml"));
			Parent rootParent = fxmlLoader.load();
			
			primaryStage.setScene(new Scene(rootParent, 800, 600));
			primaryStage.setTitle(source);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@FXML
	private void buttonHandleRegister(ActionEvent event){
		try{
			Button button = (Button) event.getSource();
			String source = button.getText();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/registerWindow.fxml"));
			Parent rootParent = fxmlLoader.load();
			
			primaryStage.setScene(new Scene(rootParent, 800, 600));
			primaryStage.setTitle(source);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}
