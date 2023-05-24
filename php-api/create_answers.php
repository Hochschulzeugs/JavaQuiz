<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
 

include_once 'config/database.php';
 
include_once 'objekte/antworten.php';
 
$database = new Database();
$db = $database->getConnection();

$antworten = new Antworten($db);
 
$data = json_decode(file_get_contents("php://input"));



if(
    !empty($data[0]->FrageID) &&
    !empty($data[0]->Antwort)
){

    for($i = 0; $i < count($data); $i++) {
        
        $antworten->FrageID = $data[$i]->FrageID;
        $antworten->Antwort = $data[$i]->Antwort;
        $antworten->Richtig = $data[$i]->Richtig;
        if(!$antworten->create()) {
            http_response_code(402);
            exit("Datenbankfehler");
        }
    }
    http_response_code(201);
    echo json_encode(array("message" => "Antworten erfolgreich erstellt"));

}
 
else{

    http_response_code(400);
 
    echo json_encode(array("message" => "Antworten konnten nicht erstellt werden. Daten sind nicht vollstaendig."));
}

?>