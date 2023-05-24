package de.uni_hannover.hci.group_3122.database;

/**

 * Message Objekt.

 * Wird zum En- und Dekodieren der JSON-Mitteilungen genutzt.

 */
public class Message {

    private String message;
    
    public Message (String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    
}