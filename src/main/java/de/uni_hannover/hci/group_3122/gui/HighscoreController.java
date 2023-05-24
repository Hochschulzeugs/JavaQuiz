package de.uni_hannover.hci.group_3122.gui;

import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.stage.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import de.uni_hannover.hci.group_3122.database.*;
import com.google.gson.*;
import javafx.event.*;
import java.util.concurrent.*;

public class HighscoreController{
	private Stage primaryStage = de.uni_hannover.hci.group_3122.application.Main.primaryStage;
	private int scoreThisRound;
	private int personalBest;
	private String userName;
	private String category;
	private int highScoreOfUser;
	
	public HighscoreController(String userName, String category, int scoreThisRound, int personalBest){
		this.userName = userName;
		this.category = category;
		this.scoreThisRound = scoreThisRound;
		this.personalBest = personalBest;
	}
	
	private void setHighScoreOfUser(int highScoreOfUser){
		this.highScoreOfUser = highScoreOfUser;
	}
	
	private int getHighScoreOfUser(){
		return this.highScoreOfUser;
	}
	
	private int getHighScoreOfUserAfter(){
		return (this.highScoreOfUser + getScoreOfUserThisRound());
	}
	
	private int getScoreOfUserThisRound(){
		return this.scoreThisRound;
	}
	
	private String getCategory(){
		return this.category;
	}
	
	private int getPersonalBest(){
		return this.personalBest;
	}
	
	private String getUsername(){
		return this.userName;
	}
	
	@FXML
    private Button buttonMainScreen;

    @FXML
    private Label labelUsername;

    @FXML
    private Label labelCategory;

    @FXML
    private Label labelScore;

    @FXML
    private Label labelPersonalBestBefore;

    @FXML
    private Label labelPersonalBestAfter;

    @FXML
    void backToMainMenu(ActionEvent event) {
	try{
		Button button = (Button) event.getSource();
		String source = button.getText();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainScreen.fxml"));
		MainscreenController mainController = new MainscreenController(getUsername());
			
		fxmlLoader.setController(mainController);
		Parent rootParent = fxmlLoader.load();
		
		primaryStage.setScene(new Scene(rootParent, 800, 600));
		primaryStage.setTitle(source);
	}
	catch(IOException e){
			e.printStackTrace();
		}
    }
	
	public void initialize() {
		try{
			Database dataBase = new Database();
			HighSc[] allHighscores = dataBase.getHighscoresFromDatabase();
			for(int i = 0; i < allHighscores.length; i++){
				if(allHighscores[i].getBenID().equals(getUsername())){
					setHighScoreOfUser(allHighscores[i].getPunkte());
					break;
				}
			}
			labelUsername.setText(getUsername());
			labelCategory.setText("Kategorie: " + getCategory());
			labelScore.setText("Punktzahl diese Runde: " + Integer.toString(getScoreOfUserThisRound()));
			labelPersonalBestBefore.setText("Pers\u00f6nlicher Highscore bevor dieser Runde: " + getHighScoreOfUser());
			labelPersonalBestAfter.setText("Pers\u00f6nlicher Highscore nach dieser Runde: " + getHighScoreOfUserAfter());
			dataBase.setPoints(getUsername(), getScoreOfUserThisRound());
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
    }
	
}