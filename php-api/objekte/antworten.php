<?php
class Antworten{
 
    private $conn;
    private $table_name = "Antworten";
 
    public $AntwortID;
    public $FrageID;
    public $Antwort;
    public $Richtig;
 
    public function __construct($db){
        $this->conn = $db;
    }
    
    function readbyfid(){
        $query = "SELECT
                a.AntwortID, a.FrageID, a.Antwort, a.Richtig
            FROM
                " . $this->table_name . " a
            WHERE
                a.FrageID = ?";
 
        $stmt = $this->conn->prepare($query);
        
        $stmt->bindParam(1, $this->FrageID);
 
        $stmt->execute();
 
        return $stmt;
    }
    
    function create(){

        $query = "INSERT INTO
                " . $this->table_name . "
            SET
                FrageID=:FrageID, Antwort=:Antwort, Richtig=:Richtig";
 
        $stmt = $this->conn->prepare($query);
 
        $this->FrageID=htmlspecialchars(strip_tags($this->FrageID));
        $this->Antwort=htmlspecialchars(strip_tags($this->Antwort));
        $this->Richtig=htmlspecialchars(strip_tags($this->Richtig));
 
        $stmt->bindParam(":FrageID", $this->FrageID);
        $stmt->bindParam(":Antwort", $this->Antwort);
        $stmt->bindParam(":Richtig", $this->Richtig);
 
        if($stmt->execute()){
            return true;
        }
 
        return false;
     
    }
}