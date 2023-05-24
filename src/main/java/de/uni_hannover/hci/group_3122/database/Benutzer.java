package de.uni_hannover.hci.group_3122.database;
import org.mindrot.jbcrypt.BCrypt;

/**

 * Benutzer Objekt.

 * Das Java-Objekt zur MySQL-Tabelle "Benutzer".

 */
public class Benutzer {
	private String BenID;
	private String BenPass;
    private String API_KEY;
    
   /**
     * Konstruktor für ein Benutzer Objekt.
     *
     * @param BenID ID (=Name) des Benutzers.
     *
     * @param BenPass Passwort des Benutzers (in der Datenbank gehashed).
     *
     * @param API_KEY wird nur für die API-Anfrage benötigt, ist nicht in der DB vorhanden.
     */
	public Benutzer (String BenID, String BenPass, String API_KEY) {
		this.BenID = BenID;
		this.BenPass = BenPass;
        this.API_KEY = API_KEY;
	}
	
	public String getBenID(){
		return BenID;
	}
		
	
	public String getBenPass() {
        return BenPass;
    }
	
/**
     * Methode um das aktuelle BenPass mit BCrypt zu hashen.
     *
     */
	public void clearToHash() {
		String hash = BCrypt.hashpw(BenPass, BCrypt.gensalt());
		BenPass = hash;
	}
}