package de.uni_hannover.hci.group_3122.gui;
import javafx.fxml.*;
import de.uni_hannover.hci.group_3122.application.Main;
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

public class CreateCategoryController{
	private Stage primaryStage = de.uni_hannover.hci.group_3122.application.Main.primaryStage;
	private String userName;
	
	public CreateCategoryController(String userName){
		this.userName = userName;
	}
	
	private String getUsername(){
		return this.userName;
	}
	
	private String getCategoryName(){
		return this.textfieldCategoryName.getText();
	}
	
	@FXML
    private TextField textfieldCategoryName;
	
	@FXML
    private Button buttonCreateCategory;
	
    @FXML
    private Button buttonMainScreen;

    @FXML
    private void backToMainMenu(ActionEvent event) {
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
	private boolean uniqueCategory(){
		try{
			Database dataBase = new Database();
			Kategorien[] kat = dataBase.getCategoriesFromDatabase();
			for(int i = 0; i < kat.length; i++) {
				if(getCategoryName().equals(kat[i].getKategorie())) return false;
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return true;
	}
	
	@FXML
    private void buttonHandleCreate(ActionEvent event) {
		try{
			if(!getCategoryName().equals("") && uniqueCategory()){
				Alert alert = new Alert(AlertType.CONFIRMATION, "Wirklich die Kategorie "+ getCategoryName() +" erstellen?", ButtonType.YES, ButtonType.NO);
				alert.setTitle("Kategorie erstellen?");
				alert.setHeaderText(null);
				alert.showAndWait();
				if(alert.getResult() == ButtonType.YES){
					Database dataBase = new Database();
					dataBase.createCategorie(getCategoryName());
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainScreen.fxml"));
					MainscreenController mainController = new MainscreenController(getUsername());
					
					Alert confirm = new Alert(AlertType.CONFIRMATION, "Kategorie "+ getCategoryName() + " erfolgreich erstellt!", ButtonType.OK);
					confirm.setTitle("Erfolgreich erstellt");
					confirm.setHeaderText(null);
					confirm.showAndWait();
					
					fxmlLoader.setController(mainController);
					Parent rootParent = fxmlLoader.load();
					
					
					primaryStage.setScene(new Scene(rootParent, 800, 600));
					primaryStage.setTitle("Hauptmen\u00FC");
				}
			}
			else{
				Alert alert = new Alert(AlertType.WARNING, "Gehen Sie sicher, dass das Feld nicht leer ist und Sie eine einzigartige Kategorie erstellen!", ButtonType.OK);
				alert.setTitle("Fehler");
				alert.setHeaderText(null);
				alert.showAndWait();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }

}