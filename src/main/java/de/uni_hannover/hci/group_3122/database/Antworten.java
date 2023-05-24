package de.uni_hannover.hci.group_3122.database;

/**
 * Antworten Objekt.
 *
 * Das Java-Objekt zur MySQL-Tabelle "Antworten".
 */
public class Antworten {

    private int AntwortID;
    private int FrageID;
    private String Antwort;
    private int Richtig;
    
    
    /**
     * Konstruktor für ein Atworten Objekt.
     *
     * @param AntwortID Die ID der Antwort.
     *
     * @param FrageID die ID der zu der Antwort gehörenden Frage.
     *
     * @param Antwort der Antwort Text.
     *
     * @param Richtig 0: falsch, 1: richtig.
     */
    public Antworten (int AntwortID, int FrageID, String Antwort, int Richtig) {
        this.AntwortID = AntwortID;
        this.FrageID = FrageID;
        this.Antwort = Antwort;
        this.Richtig = Richtig;
    }
    
    public int getAntwortID() {
        return AntwortID;
    }
    
    public int getFrageID() {
        return FrageID;
    }
    
    public String getAntwort() {
        return Antwort;
    }
    
    public int getRichtig() {
        return Richtig;
    }

    
}