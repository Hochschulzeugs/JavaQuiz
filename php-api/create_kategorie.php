<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
 

include_once 'config/database.php';
 
include_once 'objekte/kategorien.php';
 
$database = new Database();
$db = $database->getConnection();
 
$kategorien = new Kategorien($db);
 
$data = json_decode(file_get_contents("php://input"));
 
if(
    !empty($data->Kategorie)
){
 
    $kategorien->Kategorie = $data->Kategorie;
 
    if($kategorien->create()){
 
        http_response_code(201);
 
        echo json_encode(array("message" => "Kategorie erfolgreich erstellt"));
    }
 
    else{
 
        http_response_code(503);
 
        echo json_encode(array("message" => "Kategorie konnte nicht erstellt werden."));
    }
}
 
else{

    http_response_code(400);
 
    echo json_encode(array("message" => "Kategorie konnte nicht erstellt werden. Daten sind nicht vollstaendig."));
}
?>