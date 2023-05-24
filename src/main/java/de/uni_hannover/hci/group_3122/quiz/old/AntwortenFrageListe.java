package de.uni_hannover.hci.group_3122.quiz;
import de.uni_hannover.hci.group_3122.database.*;
import java.io.*;

public class AntwortenFrageListe {
    
    private Fragen frage;
    private Antworten[] antworten;
    private AntwortenFrageListe next;
    
       
    public AntwortenFrageListe (int FrageID) {
       try{
            Fragen[] fragen = Database.getQuestionsFromDatabase(FrageID);
            for(int i = 0; i < fragen.length; i++) {
                frage = fragen[i];
                antworten = Database.getAnswersFromDatabase(FrageID);
            
            }   
       } catch(IOException e){
           e.printStackTrace();
		}
        
    }
}