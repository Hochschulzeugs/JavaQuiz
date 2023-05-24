package de.uni_hannover.hci.group_3122.database;

/**

 * Fragen Objekt.

 * Das Java-Objekt zur MySQL-Tabelle "Fragen".

 */
public class Fragen {

    private int FrageID;
    private int KatID;
    private String Frage;
    
    /**
     * Konstruktor für ein Fragen Objekt.
     *
     * @param FrageID die ID der Frage.
     *
     * @param KatID die ID der zur Frage gehörenden Kategorie.
     *
     * @param Frage der Fragetext.
     */
    public Fragen (int FrageID, int KatID, String Frage) {
        this.FrageID = FrageID;
        this.KatID = KatID;
        this.Frage = Frage;
    }
    
 
    public int getFrageID() {
        return FrageID;
    }
    
    public int getKatID() {
        return KatID;
    }
    
    public String getFrage() {
        return Frage;
    }

    
}