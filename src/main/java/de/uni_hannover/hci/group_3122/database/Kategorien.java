package de.uni_hannover.hci.group_3122.database;

/**

 * Kategorien Objekt.

 * Das Java-Objekt zur MySQL-Tabelle "Kategorien".

 */
public class Kategorien {

    private int KatID;
    private String Kategorie;
    
    /**
     * Konstruktor für ein Kategorien Objekt.
     *
     * @param KatID die ID der Kategorie.
     *
     * @param Kategorie der Name der Kategorie.
     */
    public Kategorien (int KatID, String Kategorie) {
        this.KatID = KatID;
        this.Kategorie = Kategorie;
    }
    
    public int getKatID() {
        return KatID;
    }
    
    public String getKategorie() {
        return Kategorie;
    }

    
}