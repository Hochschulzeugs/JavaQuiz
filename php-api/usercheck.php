<?php
class UserCheck{
    include_once 'config/database.php';
    include_once 'objekte/benutzer.php';
    $database = new Database();
    $db = $database->getConnection();

    function check_hash($BenID, $BenPass) {
        $benutzer = new Benutzer($db);
        $benutzer->BenID = $BenID;
        $benutzer->readbyName();
 
        return $benutzer->BenPass != null && $BenPass == $benutzer->BenPass;
    }
    
    function check_nohash($BenID, $BenPass) {
        $benutzer = new Benutzer($db);
        $benutzer->BenID = $BenID;
        $benutzer->readbyName();
 
        return $benutzer->BenPass != null && password_verify($BenPass, $benutzer->$BenPass);
    }



}
?>
