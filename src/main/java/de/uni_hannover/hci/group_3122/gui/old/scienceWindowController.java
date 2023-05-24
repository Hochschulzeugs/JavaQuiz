package de.uni_hannover.hci.group_3122.gui;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.*;
import java.io.*;
import de.uni_hannover.hci.group_3122.database.Database;
import java.util.ResourceBundle;
import java.net.URL;
import com.google.gson.*;
import javafx.event.*;

public class scienceWindowController{	
    public Stage primaryStage = de.uni_hannover.hci.group_3122.application.Main.primaryStage;
	@FXML
    private Label labelQuestion;
	
	@FXML
    private Button buttonFirstAnswer;

    @FXML
    private Button buttonFourthAnswer;

    @FXML
    private Button buttonThirdAnswer;

    @FXML
    private Button buttonSecondAnswer;
	
	@FXML
    private Button buttonMainScreen;
	
	@FXML
	private void backToMainMenu(ActionEvent event){
		try{
			Button button = (Button) event.getSource();
			String source = button.getText();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainScreen.fxml"));
			Parent rootParent = fxmlLoader.load();
			
			primaryStage.setScene(new Scene(rootParent, 800, 600));
			primaryStage.setTitle(source);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
