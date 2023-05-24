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

public class LoginController {
	private Stage primaryStage = de.uni_hannover.hci.group_3122.application.Main.primaryStage;
	private String userName;
	
	private void setUserName(String userName){
		this.userName = userName;
	}
	
	private String getUserName(){
		return this.userName;
	}
	
    @FXML
    private Label labelHeader;

    @FXML
    private Label labelPassword;

    @FXML
    private Label labelUsername;

    @FXML
    private TextField userTextField;

    @FXML
    private Button buttonLogin;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button buttonBack;
	
	@FXML
	private void buttonHandleBack(ActionEvent event){
		try{			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/startScreen.fxml"));
			Parent rootParent = fxmlLoader.load();
			
			primaryStage.setScene(new Scene(rootParent, 800, 600));
			primaryStage.setTitle("Einloggen oder registrieren");
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@FXML
	private void buttonHandleLogin(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        try{
            String userName = userTextField.getText();
			setUserName(userName);
            String password = passwordField.getText();
            if (!userTextField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty() && Database.checkUser(userName, password)) {
                alert.setContentText("Login erfolgreich, " + userName + " ist jetzt eingeloggt!");
                alert.showAndWait();
                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainScreen.fxml"));
                MainscreenController mainController = new MainscreenController(userName);
				fxmlLoader.setController(mainController);
				Parent rootParent = fxmlLoader.load();
			
			
                primaryStage.setScene(new Scene(rootParent, 800, 600));
                primaryStage.setTitle("Hauptmen\u00FC");
            } else {
                alert.setContentText("Login nicht erfolgreich. Benutzername und Passwort d\u00FCrfen nicht leer sein.");
                alert.showAndWait();
            }
            
        } catch(IOException e){
            alert.setContentText("Login fehlgeschlagen: Der Benutzer " + getUserName() + " existiert nicht oder es besteht keine Internetverbindung!");
            alert.setTitle("Fehler");
			alert.showAndWait();
        }
	}

}