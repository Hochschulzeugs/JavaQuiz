<?php
class HighSc{
 
    private $conn;
    private $table_name = "HighSc";
 
    public $BenID;
    public $Punkte;
 
    public function __construct($db){
        $this->conn = $db;
    }
    
    function read(){
        $query = "SELECT
                h.BenID, h.Punkte
            FROM
                " . $this->table_name . " h
            ORDER BY
                h.Punkte DESC";
 
        $stmt = $this->conn->prepare($query);
        
        $stmt->execute();
 
        return $stmt;
    }
    
    function create(){

        $query = "INSERT INTO
                " . $this->table_name . "
            SET
                BenID=:BenID, Punkte=:Punkte";
 
        $stmt = $this->conn->prepare($query);
 
        $this->BenID=htmlspecialchars(strip_tags($this->BenID));
        $this->Punkte=htmlspecialchars(strip_tags($this->Punkte));
 
        $stmt->bindParam(":BenID", $this->BenID);
        $stmt->bindParam(":Punkte", $this->Punkte);
 
        if($stmt->execute()){
            return true;
        }
 
        return false;
     
    }
    
    function addpoints(){
        $query = "UPDATE
                " . $this->table_name . "
            SET
                Punkte = Punkte + :Punkte
            WHERE
                BenID = :BenID";
 
        $stmt = $this->conn->prepare($query);
        
        $this->Punkte=htmlspecialchars(strip_tags($this->Punkte));
        $this->BenID=htmlspecialchars(strip_tags($this->BenID));
        
        $stmt->bindParam(':Punkte', $this->Punkte);
        $stmt->bindParam(':BenID', $this->BenID);
 
        if($stmt->execute()){
            return true;
        }
 
        return false;
    }
}