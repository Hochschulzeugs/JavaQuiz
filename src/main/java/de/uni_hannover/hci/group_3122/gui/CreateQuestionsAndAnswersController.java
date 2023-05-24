package de.uni_hannover.hci.group_3122.gui;

import javafx.fxml.*;
import java.util.*;
import de.uni_hannover.hci.group_3122.database.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class CreateQuestionsAndAnswersController extends Observable{
	public Stage primaryStage = de.uni_hannover.hci.group_3122.application.Main.primaryStage;
	private String userName;
	private String question;
	private int categoryID;
	private String[] answers;
	private Kategorien[] categoryArray;
	private Fragen[] questionsArray;
	
	public CreateQuestionsAndAnswersController(String userName){
		this.userName = userName;
	}
	
	private String[] getAnswerArray(){
		return this.answers;
	}
	
	private String getUsername(){
		return this.userName;
	}
	
	private String getQuestion(){
		return this.question;
	}
	
	private void setQuestion(String question){
		this.question = question;
	}
	
	private Fragen[] getQuestionsArray(){
		return this.questionsArray;
	}
	
	private void setQuestionsArray(Fragen[] questionsArray){
		this.questionsArray = questionsArray;
	}
	
	private Kategorien[] getCategoryArray(){
		return this.categoryArray;
	}
	
	private void setCategoryArray(Kategorien[] categoryArray){
		this.categoryArray = categoryArray;
	}
	
	private void setCategoryID(int categoryID){
		this.categoryID = categoryID;
	}
	
    @FXML
    private Button buttonMainScreen;

    @FXML
    private ListView<String> listviewCategories;
	
	@FXML
    private TextField textfieldQuestion;

    @FXML
    private TextField textfieldFirstAnswer;

    @FXML
    private TextField textfieldThirdAnswer;

    @FXML
    private TextField textfieldSecondAnswer;

    @FXML
    private TextField textfieldFourthAnswer;

    @FXML
    private Button buttonCreateQnA;

    @FXML
    private Label labelTrueOrFalse;

    @FXML
    private CheckBox checkboxFirstAnswer;

    @FXML
    private CheckBox checkboxFourthAnswer;

    @FXML
    private CheckBox checkboxThirdAnswer;

    @FXML
    private CheckBox checkboxSecondAnswer;
	
	/**
		Methode zum Erstellen von Fragen und Antworten zu bestehenden Kategorien.
		Dann und nur dann, wenn alle Felder ausgewählt beziehungsweise ausgefüllt sind, wird die Frage mit ihren Antworten erstellt.
				
		@param event Event, welches die Methode auslöst.
	*/	
	@FXML
	private void buttonHandleCreateQnA(ActionEvent event){
        Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Erfolg");
        alert.setHeaderText(null);
		try{
			if(listviewCategories.getSelectionModel().getSelectedItem() != null){
				Database dataBase = new Database();
				String category = listviewCategories.getSelectionModel().getSelectedItem();
				setAnswerArray();
				
				int right = getRight();
					
				int categoryID = getCategoryID(category);
				setCategoryID(categoryID);
				
				setQuestion(textfieldQuestion.getText());
				
				int questionID = -1;
							
				if(!getQuestion().isEmpty() && categoryID != -1 && right != -1 && checkAnswerArrayForAllEntries()){
					dataBase.createQuestion(categoryID, question);
					questionID = getQuestionID(getQuestion());
						if(questionID != -1 && dataBase.createAnswers(questionID, getAnswerArray(), right)){
						alert.setContentText("Erfolgreich erstellt!");
						alert.showAndWait(); 
						}
                }
				else{
					alert.setTitle("Fehler");
                    alert.setContentText("Felder d\u00FCrfen nicht leer sein.");
					alert.showAndWait();
				}
			}
			else{
				alert.setTitle("Fehler");
                alert.setContentText("W\u00e4hle eine Kategorie aus!");
				alert.showAndWait();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			alert.setTitle("Fehler");
            alert.setContentText("Fehler: \n" +e);
            alert.showAndWait();
		}
	}
	
	/**
		Methode um die Szene erneut aufs Hauptmenü zu ändern.
				
		@param event Event, welches die Methode auslöst.
	*/
    @FXML
    private void backToMainMenu(ActionEvent event) {
		try{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainScreen.fxml"));
			MainscreenController mainController = new MainscreenController(getUsername());
				
			fxmlLoader.setController(mainController);
			Parent rootParent = fxmlLoader.load();
			
			primaryStage.setScene(new Scene(rootParent, 800, 600));
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }
	
	/**
		Methode zum Auswerten eines Wahrheitswerts.
		Dann und nur dann, wenn alle Einträge des Arrays bestehen (das heißt, wenn alle vier Antworten gegeben wurden), wird true zurückgegeben, sonst false.
	*/
	private boolean checkAnswerArrayForAllEntries(){
		try{
			for(int i = 0; i < getAnswerArray().length; i++){
				if(getAnswerArray()[i].equals("")){
					return false;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	/**
		Methode um die einzigartige ID der Frage auszulesen.
		Die ID ist notwendig um die Antworten an die Frage zu koppeln.
		
		@param question Die Frage für welche die ID ausgelesen werden soll.
	*/
	private int getQuestionID(String question){
		int questionID = -1;
		try{
			Database dataBase = new Database();
			setQuestionsArray(dataBase.getQuestionsFromDatabase(categoryID));
			for(int i = 0; i < getQuestionsArray().length; i++){
				if(question.equals(getQuestionsArray()[i].getFrage())) questionID = getQuestionsArray()[i].getFrageID();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return questionID;
	}
	
	/**
		Methode um auszulesen welche der vier Antworten die richtige ist.
		Wenn die CheckBox der jeweiligen Antwort angekreuzt ist, ist diese als "richtig" markiert.
	*/
	private int getRight(){
		int right = -1;
		try{
			if(checkboxFirstAnswer.isSelected()) return Integer.parseInt(checkboxFirstAnswer.getId());
			else if(checkboxSecondAnswer.isSelected()) return Integer.parseInt(checkboxSecondAnswer.getId());
			else if(checkboxThirdAnswer.isSelected()) return Integer.parseInt(checkboxThirdAnswer.getId());
			else if(checkboxFourthAnswer.isSelected()) return Integer.parseInt(checkboxFourthAnswer.getId());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return right;
	}
	
	/**
		Methode um die einzigartige ID der Kategorie auszulesen.
		Die ID ist notwendig um die Fragen und Antworten and die Kategorie zu koppeln.
		
		@param category Die Kategorie für welche die ID ausgelesen werden soll.
	*/
	private int getCategoryID(String category){
		int categoryID = -1;
		try{
			for(int i = 0; i < getCategoryArray().length; i++){
				if(category == getCategoryArray()[i].getKategorie()) return (categoryID = getCategoryArray()[i].getKatID());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return categoryID;
	}
	
	private void setAnswerArray(){
		try{
			this.answers = new String[4];
			answers[0] = textfieldFirstAnswer.getText();
			answers[1] = textfieldSecondAnswer.getText();
			answers[2] = textfieldThirdAnswer.getText();
			answers[3] = textfieldFourthAnswer.getText();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
		Methode um Listener an die ListView und die CheckBoxen zu binden.
		Dann und nur dann, wenn eine Kategorie aus der ListView gewählt wurde, werden die CheckBoxen sichtbar und alle TextFields aktiviert.
		Sobald eine CheckBox gewählt wurde, werden alle anderen deaktiviert damit nicht mehrere Antworten als "richtig" markiert werden können.
	*/
	private void initListViewAndCheckBoxes(){
		listviewCategories.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if(listviewCategories.getSelectionModel().selectedItemProperty().isNull().get() == false){
						textfieldQuestion.setDisable(false);
						textfieldFirstAnswer.setDisable(false);
						textfieldSecondAnswer.setDisable(false);
						textfieldThirdAnswer.setDisable(false);
						textfieldFourthAnswer.setDisable(false);
						labelTrueOrFalse.setVisible(true);
						checkboxFirstAnswer.setVisible(true);
						checkboxSecondAnswer.setVisible(true);
						checkboxThirdAnswer.setVisible(true);
						checkboxFourthAnswer.setVisible(true);
					}
				}
			});
			checkboxFirstAnswer.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(newValue){
						checkboxSecondAnswer.setDisable(true);
						checkboxThirdAnswer.setDisable(true);
						checkboxFourthAnswer.setDisable(true);
					}else{
						checkboxSecondAnswer.setDisable(false);
						checkboxThirdAnswer.setDisable(false);
						checkboxFourthAnswer.setDisable(false);
					}
				}
			});
			
			checkboxSecondAnswer.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(newValue){
						checkboxFirstAnswer.setDisable(true);
						checkboxThirdAnswer.setDisable(true);
						checkboxFourthAnswer.setDisable(true);
					}else{
						checkboxFirstAnswer.setDisable(false);
						checkboxThirdAnswer.setDisable(false);
						checkboxFourthAnswer.setDisable(false);
					}
				}
			});
			
			checkboxThirdAnswer.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(newValue){
						checkboxFirstAnswer.setDisable(true);
						checkboxSecondAnswer.setDisable(true);
						checkboxFourthAnswer.setDisable(true);
					}else{
						checkboxFirstAnswer.setDisable(false);
						checkboxSecondAnswer.setDisable(false);
						checkboxFourthAnswer.setDisable(false);
					}
				}
			});
			
			checkboxFourthAnswer.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(newValue){
						checkboxFirstAnswer.setDisable(true);
						checkboxSecondAnswer.setDisable(true);
						checkboxThirdAnswer.setDisable(true);
					}else{
						checkboxFirstAnswer.setDisable(false);
						checkboxSecondAnswer.setDisable(false);
						checkboxThirdAnswer.setDisable(false);
					}
				}
			});
	}
	
	/**
		Initalisiert die ListView mit allen bestehenden Kategorien und ruft die Methode auf, welche die ChangeListener an die jeweiligen Elemente bindet.
	*/
	public void initialize(){
		try{
			Database dataBase = new Database();
			ObservableList<String> categories = FXCollections.observableArrayList();
			setCategoryArray(dataBase.getCategoriesFromDatabase());
			for(int i = 0; i < categoryArray.length; i++){
				categories.add(categoryArray[i].getKategorie());
			}
			listviewCategories.setItems(categories);
			initListViewAndCheckBoxes();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}
	
