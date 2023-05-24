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

public class WindowController{
	private Stage primaryStage = de.uni_hannover.hci.group_3122.application.Main.primaryStage;
    private Fragen[] questionsArray;
	private Antworten[] answerArray;
	private String userName;
	private int categoryID;
	private int scoreCounter = 0;
	private int answeredQuestionsCounter = 0;
	private int currentIndexOfArray = 0;
	private int questionID;
	private int endOfQuiz;
	private String categoryString;
	
	public WindowController(String userName, String categoryString, String categoryID, Fragen[] questionsArray, Antworten[] answerArray){
		this.userName = userName;
		this.categoryString = categoryString;
		this.categoryID = Integer.parseInt(categoryID);
		this.questionsArray = questionsArray;
		this.answerArray = answerArray;
	}
	
	private String getUsername(){
		return this.userName;
	}
	
	private String getCategoryString(){
		return this.categoryString;
	}
	
	private void setEndOfQuiz(int endOfQuiz){
		this.endOfQuiz = endOfQuiz;
	}
	
	private int getEndOfQuiz(){
		return this.endOfQuiz-1;
	}
	
	private Fragen[] getQuestionsArray(){
		return this.questionsArray;
	}
	
	private Antworten[] getAnswerArray(){
		return this.answerArray;
	}
	
	private void setAnswerArray(Antworten[] answerArray){
		this.answerArray = answerArray;
	}
	
	private int getQuestionID(){
		return this.questionID;
	}
	
	private void setQuestionID(int currentQuestionID){
		this.questionID = currentQuestionID;
	}
	
	private int getCurrentScore(){
		return this.scoreCounter;
	}
	
	private void incrementScoreCounter(){
		this.scoreCounter++;
	}
	
	private int getCurrentIndexOfArray(){
		return this.currentIndexOfArray;
	}
	
	private void incrementCurrentIndexOfArray(){
		this.currentIndexOfArray++;
	}
	
	private int getAnsweredQuestionsCounter(){
		return this.answeredQuestionsCounter;
	}
	
	private void incrementAnsweredQuestionsCounter(){
		this.answeredQuestionsCounter++;
	}
	
	@FXML
    private Label labelScore;
	
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
	private void backToMainMenu(ActionEvent event){
		if(getAnsweredQuestionsCounter() >= 1){
			Alert alert = new Alert(AlertType.CONFIRMATION, "Wirklich zur\u00FCck zum Hauptmen\u00FC?  Der Fortschritt geht verloren!", ButtonType.YES, ButtonType.NO);
			alert.setTitle("Zur\u00FCck zum Hauptmen\u00FC?");
			alert.setHeaderText(null);
			alert.showAndWait();
			if(alert.getResult() == ButtonType.YES){
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
		}
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
	
	@FXML
	private void buttonHandlePlay(ActionEvent event){
		try{
			if(getAnsweredQuestionsCounter() == getEndOfQuiz()){
				Button button = (Button) event.getSource();
				int buttonID = Integer.parseInt(button.getId());
				if(getAnswerArray()[buttonID].getRichtig() == 1){
					incrementAnsweredQuestionsCounter();
					incrementScoreCounter();
					updateScore();
				}
				String category = getCategoryString();
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/highScoreScreen.fxml"));
				HighscoreController highScoreController = new HighscoreController(getUsername(), category, getCurrentScore(), 0);
				fxmlLoader.setController(highScoreController);
				Parent rootParent = fxmlLoader.load();
				primaryStage.setScene(new Scene(rootParent, 800, 600));
				primaryStage.setTitle("Quiz beendet!");
			}
			else{
				Button button = (Button) event.getSource();
				int buttonID = Integer.parseInt(button.getId());
				
				if(getAnswerArray()[buttonID].getRichtig() == 1){
					incrementAnsweredQuestionsCounter();
					incrementScoreCounter();
					updateScore();
					incrementCurrentIndexOfArray();
					labelQuestion.setText(getQuestionsArray()[getCurrentIndexOfArray()].getFrage());
					setQuestionsAndAnswers();
				}
				else {
					incrementAnsweredQuestionsCounter();
					incrementCurrentIndexOfArray();
					setQuestionsAndAnswers();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void updateScore(){
		try{
			labelScore.setText("Score: " + getCurrentScore());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void updateArrays(){
		try{
			setQuestionID(getQuestionsArray()[getCurrentIndexOfArray()].getFrageID());
			setAnswerArray(Database.getAnswersFromDatabase(getQuestionID()));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void setIds(){
		try{
			ArrayList<Integer> listOfIdArray = new ArrayList<>(5);
			for(int i = 0; i < 4; i++){
				listOfIdArray.add(i);
			}
			int[] idArray = new int[4];
			for(int i = 0; i < 4; i++){
				idArray[i] = listOfIdArray.remove((int) (Math.random() * listOfIdArray.size()));
			}
			buttonFirstAnswer.setId(Integer.toString(idArray[0]));
			buttonSecondAnswer.setId(Integer.toString(idArray[1]));
			buttonThirdAnswer.setId(Integer.toString(idArray[2]));
			buttonFourthAnswer.setId(Integer.toString(idArray[3]));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void setQuestionsAndAnswers(){
		try{
			setIds();
			updateArrays();
			labelQuestion.setText(getQuestionsArray()[getAnsweredQuestionsCounter()].getFrage());
			buttonFirstAnswer.setText(getAnswerArray()[Integer.parseInt(buttonFirstAnswer.getId())].getAntwort());
			buttonSecondAnswer.setText(getAnswerArray()[Integer.parseInt(buttonSecondAnswer.getId())].getAntwort());
			buttonThirdAnswer.setText(getAnswerArray()[Integer.parseInt(buttonThirdAnswer.getId())].getAntwort());
			buttonFourthAnswer.setText(getAnswerArray()[Integer.parseInt(buttonFourthAnswer.getId())].getAntwort());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
    public void initialize() {
		try{
			updateScore();
			setQuestionsAndAnswers();
			setEndOfQuiz(getQuestionsArray().length);
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }
}
