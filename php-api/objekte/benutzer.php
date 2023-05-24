<?php
class Benutzer{
 
    private $conn;
    private $table_name = "Benutzer";
 
    public $BenID;
    public $BenPass;
 
    public function __construct($db){
        $this->conn = $db;
    }
    
    function readbyName(){
 
        $query = "SELECT
                b.BenID, b.BenPass
            FROM
                " . $this->table_name . " b
            WHERE
                b.BenID = ?";
 
        $stmt = $this->conn->prepare( $query );

        $stmt->bindParam(1, $this->BenID);
 
        $stmt->execute();
 
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
 
        $this->BenID = $row['BenID'];
        $this->BenPass = $row['BenPass'];
    }
    
    function addUser(){
        $query = "INSERT INTO
                " . $this->table_name . "
            SET
               BenID=:BenID, BenPass=:BenPass";
 
        $stmt = $this->conn->prepare($query);
 
        $this->BenID=htmlspecialchars(strip_tags($this->BenID));
        $this->BenPass=htmlspecialchars(strip_tags($this->BenPass));
 
        $stmt->bindParam(":BenID", $this->BenID);
        $stmt->bindParam(":BenPass", $this->BenPass);
 
        if($stmt->execute()){
            return true;
        }
 
        return false;
    }
    
    function check(){
 
        $query = "SELECT
                b.BenID, b.BenPass
            FROM
                " . $this->table_name . " b
            WHERE
                b.BenID = ?";
 
        $stmt = $this->conn->prepare( $query );

        $stmt->bindParam(1, $this->BenID);
 
        $stmt->execute();
 
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
 
        return password_verify($this->BenPass, $row['BenPass']);
    }
}