package de.uni_hannover.hci.group_3122.database;
import com.google.gson.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import com.google.gson.stream.*;
import java.net.HttpURLConnection;


/**
 * Database Klasse.
 *
 * Dient dem Zugriff auf die PHP-API um JSON Elemente zu erhalten oder zu verschicken.
 */
public class Database{
	public static Fragen[] getQuestionsFromDatabase(int categoryID) throws IOException{
		URL baseURL = new URL("https://blackdan.de/uni/prog2/quizapi/read_questions_by_cat.php?id=");
		URL finalURL = new URL(""+baseURL+""+categoryID);
		try(InputStream in = finalURL.openStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"))){
			Gson gson = new Gson();
            Fragen frage[] = gson.fromJson(reader, Fragen[].class);
            
			return frage;
		}
	}
    
    /**
     * Antworten-Methode zum erhalten der Antworten einer Frage von der PHP-API
     *
     * @param frageID Die FrageID, zu der die Antworten herausgeholt werden sollen.
     *
     * @return Ein Array aus Antworten.
     */
    public static Antworten[] getAnswersFromDatabase(int frageID) throws IOException{
		URL baseURL = new URL("https://blackdan.de/uni/prog2/quizapi/read_answers_by_id.php?id=");
		URL finalURL = new URL(""+baseURL+""+frageID);
		try(InputStream in = finalURL.openStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"))){
			Gson gson = new Gson();
            Antworten antwort[] = gson.fromJson(reader, Antworten[].class);
            
			return antwort;
		}
	}
    
    /**
     * Kategorien-Methode zum erhalten aller Kategorien aus der PHP-API.
     *
     * @return Ein Array aus Kategorien.
     */
    public static Kategorien[] getCategoriesFromDatabase() throws IOException{
		URL finalURL = new URL("https://blackdan.de/uni/prog2/quizapi/read_all_categories.php");
		try(InputStream in = finalURL.openStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"))){
			Gson gson = new Gson();
            Kategorien kategorie[] = gson.fromJson(reader, Kategorien[].class);
            
			return kategorie;
		}
	}
	
    /**
     * Methode zum Erstellen neuer Benutzer mit Hilfe der PHP-API.
     *
     * @param name Benutzername
     *
     * @param pw Klartext-Passwort
     *
     * Das Passwort wird vor der Übergabe an die API mit BCrypt gehashed.
     *
     * @return True oder False, je nachdem, ob die Erstellung erfolgreich war.
     */
	public static boolean createUser(String name, String pw) throws IOException{
		URL finalURL = new URL("https://blackdan.de/uni/prog2/quizapi/create_user.php");
        Benutzer benutzer = new Benutzer(name, pw, "0123456789");
        benutzer.clearToHash();
        Gson gson = new Gson();
        String benutzerJson = gson.toJson(benutzer);
        HttpURLConnection con = (HttpURLConnection)finalURL.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
		try(OutputStream os = con.getOutputStream()){
			byte[] input = benutzerJson.getBytes("utf-8");
            os.write(input, 0, input.length);
		}
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            Gson gsonresp = new Gson();
            Message message = gsonresp.fromJson(reader, Message.class);
            return message.getMessage().equals("Benutzer wurde erstellt");
        }
	}
    
    /**
     * Methode für den Benutzer Login mit Hilfe der PHP-API.
     *
     * @param name Benutzername
     *
     * @param pw Klartext-Passwort
     *
     * @return True oder False, je nachdem, ob die Überprüfung erfolgreich war.
     */
    public static boolean checkUser(String name, String pw) throws IOException{
        URL finalURL = new URL("https://blackdan.de/uni/prog2/quizapi/check_user.php");
        Benutzer benutzer = new Benutzer(name, pw, "0123456789");
        Gson gson = new Gson();
        String benutzerJson = gson.toJson(benutzer);
        HttpURLConnection con = (HttpURLConnection)finalURL.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        try(OutputStream os = con.getOutputStream()){
            byte[] input = benutzerJson.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            Gson gsonresp = new Gson();
            Message message = gsonresp.fromJson(reader, Message.class);
            return message.getMessage().equals("Benutzer check OK");
        }
	}
    
    /**
     * Methode zum Auslesen der Highscore mit Hilfe der PHP-API.
     *
     * @return Ein Array aus HighSc (Highscores), sortiert nach Punktestand.
     */
    public static HighSc[] getHighscoresFromDatabase() throws IOException{
		URL finalURL = new URL("https://blackdan.de/uni/prog2/quizapi/read_highscore.php");
		try(InputStream in = finalURL.openStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"))){
			Gson gson = new Gson();
            HighSc highscore[] = gson.fromJson(reader, HighSc[].class);
            
			return highscore;
		}
	}
    
    /**
     * Methode zum Addieren des Punktestands mit Hilfe der PHP-API.
     *
     * @param benID Die eindeutige Benutzer ID
     *
     * @param punkte Die zu addierenden Punkte
     *
     * @return True oder False, je nachdem, ob die Addition erfolgreich war.
     */
    public static boolean setPoints(String benID, int punkte) throws IOException{
		URL finalURL = new URL("https://blackdan.de/uni/prog2/quizapi/update_highscore.php");
        HighSc highscore = new HighSc(benID, punkte);
        Gson gson = new Gson();
        String highscoreJson = gson.toJson(highscore);
        HttpURLConnection con = (HttpURLConnection)finalURL.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
		try(OutputStream os = con.getOutputStream()){
			byte[] input = highscoreJson.getBytes("utf-8");
            os.write(input, 0, input.length);
		}
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            Gson gsonresp = new Gson();
            Message message = gsonresp.fromJson(reader, Message.class);
            return message.getMessage().equals("Highscore erfolgreich addiert");
        }
	}
    
    
    /**
     * Methode zum Erstellen einer Kategorie mit Hilfe der PHP-API.
     *
     * @param catname Der Name der neuen Kategorie.
     *
     * @return True oder False, je nachdem, ob die Erstellung erfolgreich war.
     */
    public static boolean createCategorie(String catname) throws IOException{
		URL finalURL = new URL("https://blackdan.de/uni/prog2/quizapi/create_kategorie.php");
        Kategorien kategorie = new Kategorien(0, catname);
        Gson gson = new Gson();
        String kategorieJson = gson.toJson(kategorie);
        HttpURLConnection con = (HttpURLConnection)finalURL.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
		try(OutputStream os = con.getOutputStream()){
			byte[] input = kategorieJson.getBytes("utf-8");
            os.write(input, 0, input.length);
		}
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            Gson gsonresp = new Gson();
            Message message = gsonresp.fromJson(reader, Message.class);
            return message.getMessage().equals("Kategorie erfolgreich erstellt");
        }
	}
    
    /**
     * Methode zum Erstellen einer Frage mit Hilfe der PHP-API.
     *
     * @param KatID die ID der Kategorie, zu der die Frage gehören soll.
     *
     * @param question Der Fragetext.
     *
     * @return True oder False, je nachdem, ob die Erstellung erfolgreich war.
     */
    public static boolean createQuestion(int KatID, String question) throws IOException{
		URL finalURL = new URL("https://blackdan.de/uni/prog2/quizapi/create_frage.php");
        Fragen frage = new Fragen(0, KatID, question);
        Gson gson = new Gson();
        String frageJson = gson.toJson(frage);
        HttpURLConnection con = (HttpURLConnection)finalURL.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
		try(OutputStream os = con.getOutputStream()){
			byte[] input = frageJson.getBytes("utf-8");
            os.write(input, 0, input.length);
		}
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            Gson gsonresp = new Gson();
            Message message = gsonresp.fromJson(reader, Message.class);
            return message.getMessage().equals("Frage erfolgreich erstellt");
        }
	}
    
    
    /**
     * Methode zum Erstellen der Antworten einer Frage mit Hilfe der PHP-API.
     *
     * @param FrageID die ID der Frage, zu der die Antworten gehören sollen.
     *
     * @param antworten Ein String Array der Größe 4 mit den Antwort-Texten.
     *
     * @param richtig Der Index des antworten-String-Arrays mit der richtigen Antwort.
     *
     * @return True oder False, je nachdem, ob die Erstellung erfolgreich war.
     */
    public static boolean createAnswers(int FrageID, String[] antworten, int richtig) throws IOException{
		URL finalURL = new URL("https://blackdan.de/uni/prog2/quizapi/create_answers.php");
        Antworten antwort[] = new Antworten[4];
        for(int i = 0; i < 4; i++) {
            if (i == richtig) antwort[i] = new Antworten(0, FrageID, antworten[i], 1);
            else antwort[i] = new Antworten(0, FrageID, antworten[i], 0);
        }
        Gson gson = new Gson();
        String antwortJson = gson.toJson(antwort);
        HttpURLConnection con = (HttpURLConnection)finalURL.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
		try(OutputStream os = con.getOutputStream()){
			byte[] input = antwortJson.getBytes("utf-8");
            os.write(input, 0, input.length);
		}
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            Gson gsonresp = new Gson();
            Message message = gsonresp.fromJson(reader, Message.class);
            return message.getMessage().equals("Antworten erfolgreich erstellt");
        }
	}

    
}