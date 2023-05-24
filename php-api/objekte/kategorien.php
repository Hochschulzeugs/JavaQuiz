<?php
class Kategorien{
 
    private $conn;
    private $table_name = "Kategorien";
 
    public $KatID;
    public $Kategorie;
 
    public function __construct($db){
        $this->conn = $db;
    }
    
    function read(){
        $query = "SELECT
                k.KatID, k.Kategorie
            FROM
                " . $this->table_name . " k
            ORDER BY
                k.KatID DESC";
 
        $stmt = $this->conn->prepare($query);
        
        $stmt->execute();
 
        return $stmt;
    }
    
    function create(){

        $query = "INSERT INTO
                " . $this->table_name . "
            SET
                Kategorie=:Kategorie";
 
        $stmt = $this->conn->prepare($query);
 
        $this->Kategorie=htmlspecialchars(strip_tags($this->Kategorie));
 
        $stmt->bindParam(":Kategorie", $this->Kategorie);
 
        if($stmt->execute()){
            return true;
        }
 
        return false;
     
    }
}