<?php
class Fragen{
 
    private $conn;
    private $table_name = "Fragen";
 
    public $FrageID;
    public $KatID;
    public $Frage;
 
    public function __construct($db){
        $this->conn = $db;
    }
    function readbycatid(){
        $query = "SELECT
                f.FrageID, f.KatID, f.Frage
            FROM
                " . $this->table_name . " f
            WHERE
                f.KatID = ?";
 
        $stmt = $this->conn->prepare($query);
        
        $stmt->bindParam(1, $this->KatID);
 
        $stmt->execute();
 
        return $stmt;
    }
    
    function readbyid(){
 
        $query = "SELECT
                f.FrageID, f.KatID, f.Frage
            FROM
                " . $this->table_name . " f
            WHERE
                f.FrageID = ?";
 
        $stmt = $this->conn->prepare( $query );

        $stmt->bindParam(1, $this->FrageID);
 
        $stmt->execute();
 
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
 
        $this->FrageID = $row['FrageID'];
        $this->KatID = $row['KatID'];
        $this->Frage = $row['Frage'];
    }
    
    function create(){

        $query = "INSERT INTO
                " . $this->table_name . "
            SET
                KatID=:KatID, Frage=:Frage";
 
        $stmt = $this->conn->prepare($query);
 
        $this->KatID=htmlspecialchars(strip_tags($this->KatID));
        $this->Frage=htmlspecialchars(strip_tags($this->Frage));
 
        $stmt->bindParam(":KatID", $this->KatID);
        $stmt->bindParam(":Frage", $this->Frage);
 
        if($stmt->execute()){
            return true;
        }
 
        return false;
     
    }
}