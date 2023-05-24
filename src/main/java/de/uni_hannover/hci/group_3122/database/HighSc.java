package de.uni_hannover.hci.group_3122.database;

/**

 * Highscore Objekt.

 * Das Java-Objekt zur MySQL-Tabelle "HighSc".

 */
public class HighSc {

    private String BenID;
    private int Punkte;
    
    /**
     * Konstruktor für ein HighSC (Highscore) Objekt.
     *
     * @param BenID die BenutzerID.
     *
     * @param Punkte der Punktestand.
     */
    public HighSc (String BenID, int Punkte) {
        this.BenID = BenID;
        this.Punkte = Punkte;
    }

    public String getBenID() {
        return BenID;
    }
    
    public int getPunkte() {
        return Punkte;
    }

    
}