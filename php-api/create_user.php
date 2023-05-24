<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
 

include_once 'config/database.php';
 
include_once 'objekte/benutzer.php';
include_once 'objekte/highsc.php';
 
$database = new Database();
$db = $database->getConnection();
 
$benutzer = new Benutzer($db);
$highsc = new HighSc($db);
 
$data = json_decode(file_get_contents("php://input"));
 
if(
    !empty($data->BenID) &&
    !empty($data->BenPass) &&
    $data->API_KEY == "0123456789"
){
    $highsc->BenID = $data->BenID;
    $highsc->Punkte = 0;
    $benutzer->BenID = $data->BenID;
    $benutzer->BenPass = $data->BenPass;
 
    if($benutzer->addUser() && $highsc->create()){
 
        http_response_code(201);
 
        echo json_encode(array("message" => "Benutzer wurde erstellt"));
    }
 
    else{
 
        http_response_code(503);
 
        echo json_encode(array("message" => "Benutzer konnte nicht erstellt werden."));
    }
}
 
else{

    http_response_code(400);
 
    echo json_encode(array("message" => "Benutzer konnte nicht erstellt werden. Daten sind nicht vollstaendig."));
}
?>