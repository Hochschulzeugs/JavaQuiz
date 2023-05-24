package de.uni_hannover.hci.group_3122.gui;
import javafx.fxml.*;
import de.uni_hannover.hci.group_3122.database.*;
import javafx.scene.control.Button;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.stage.*;
import javafx.event.*;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

public class MainscreenController{
    public Stage primaryStage = de.uni_hannover.hci.group_3122.application.Main.primaryStage;
	private String userName;
	
	public MainscreenController(String userName){
		this.userName = userName;
	}
	
	private String getUsername(){
		return this.userName;
	}
	
	@FXML
    private ButtonBar buttonBar;	
		
	@FXML
    private Button buttonCreateCategory;

    @FXML
    private Button buttonCreateQnA;
		
	@FXML
    private Button buttonHighscores;
    
	/**
		Methode zum Anzeigen der Highscores aller Benutzer.
		Nach Größe geordnet durch SQL, wodurch eine Bestenliste entsteht.
		
		@param event Event, welches die Methode auslöst.
	*/
	@FXML
	private void buttonHandleHighscores(ActionEvent event){
		try{
			Database dataBase = new Database();
			HighSc[] allHighScores = dataBase.getHighscoresFromDatabase();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Highscores");
			alert.setHeaderText(null);
			String contentText = "";
			for(int i = 0; i < allHighScores.length; i++){
				contentText += "Platz " + (i+1) + ": " + allHighScores[i].getBenID() + " mit " + allHighScores[i].getPunkte() + " Punkten\n";
			}
			alert.setContentText(contentText);
			alert.showAndWait();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
		Methode zum Aufrufen des Formulars zum Erstellen von Fragen und Antworten zu bestehenden Kategorien.
				
		@param event Event, welches die Methode auslöst.
	*/
	@FXML
	private void buttonHandleCreateQnA(ActionEvent event){
		try{
			Button button = (Button) event.getSource();
			String title = button.getText();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/createQuestionsAndAnswersScreen.fxml"));
			CreateQuestionsAndAnswersController qnaController = new CreateQuestionsAndAnswersController(getUsername());
			
			fxmlLoader.setController(qnaController);
			Parent rootParent = fxmlLoader.load();
			
			primaryStage.setScene(new Scene(rootParent, 800, 600));
			primaryStage.setTitle(title);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
		Methode zum Aufrufen des Formulars zum Erstellen einer einzigartigen Kategorie.
				
		@param event Event, welches die Methode auslöst.
	*/	
	@FXML
	private void buttonHandleCreateCategory(ActionEvent event){
		try{
			Button button = (Button) event.getSource();
			String categoryTitle = button.getText();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/createCategoryScreen.fxml"));
			CreateCategoryController categoryController = new CreateCategoryController(getUsername());
			
			fxmlLoader.setController(categoryController);
			Parent rootParent = fxmlLoader.load();
			
			primaryStage.setScene(new Scene(rootParent, 800, 600));
			primaryStage.setTitle(categoryTitle);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
		Methode zum Aufrufen der ausgewählten Kategorie als Spielfenster.
				
		@param event Event, welches die Methode auslöst.
	*/	
	@FXML
	private void buttonActionHandle(ActionEvent event){
		try{
			Database dataBase = new Database();
			Button button = (Button) event.getSource();
			String categoryID = button.getId();
			String categoryTitle = button.getText();
			Fragen[] questionsArray = dataBase.getQuestionsFromDatabase(Integer.parseInt(categoryID));
			int questionID = questionsArray[0].getFrageID();
			Antworten[] answerArray = dataBase.getAnswersFromDatabase(questionID);
						
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/playWindow.fxml"));
			WindowController windowController = new WindowController(getUsername(), categoryTitle, categoryID, questionsArray, answerArray);
			
			fxmlLoader.setController(windowController);
			Parent rootParent = fxmlLoader.load();
			
			primaryStage.setScene(new Scene(rootParent, 800, 600));
			primaryStage.setTitle(categoryTitle);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
		Initalisiert die aktuelle Scene, besonders die ButtonBar mit den allen Kategorien.
		Die Buttons zu Kategorien ohne Fragen/Antworten sind deaktiviert.
	*/	
    public void initialize() {
		try{
			Database dataBase = new Database();
			Kategorien[] kat = dataBase.getCategoriesFromDatabase();
			for(int i = 0; i < kat.length; i++) {
				Button button = new Button(kat[i].getKategorie());
				String catID = Integer.toString(kat[i].getKatID());
				try{
					Fragen[] questions = dataBase.getQuestionsFromDatabase(Integer.parseInt(catID));
				}
				catch(IOException e){
					button.setDisable(true);
				}
				button.setId(catID);
				button.setPrefWidth(200);
				button.setPrefHeight(100);
				button.setStyle("-fx-background-radius: 0; -fx-font-size: 14;");
				button.setOnAction(event -> buttonActionHandle(event));
				buttonBar.getButtons().addAll(button);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
    }
}