<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: access");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Credentials: true");
header('Content-Type: application/json');
 
include_once 'config/database.php';
include_once 'objekte/kategorien.php';
 
$database = new Database();
$db = $database->getConnection();
 
$kategorien = new Kategorien($db);
 
$stmt = $kategorien->read();
$num = $stmt->rowCount();
 
if($num>0){
 
    $kategorien_arr=array();
 

    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
 
        $kategorien_item=array(
            "KatID" => $KatID,
            "Kategorie" => html_entity_decode($Kategorie)
        );
 
        array_push($kategorien_arr, $kategorien_item);
    }
 
    http_response_code(200);
 
    echo json_encode($kategorien_arr);
}
 
else{

    http_response_code(404);
 
    echo json_encode(array("message" => "Fehler beim Auslesen der Kategorien."));
}
?>