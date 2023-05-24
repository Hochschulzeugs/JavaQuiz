<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
 

include_once 'config/database.php';
 
include_once 'objekte/highsc.php';
 
$database = new Database();
$db = $database->getConnection();
 
$highsc = new HighSc($db);
 
$data = json_decode(file_get_contents("php://input"));
 
if(
    !empty($data->BenID) &&
    !empty($data->Punkte)
){
 
    $highsc->BenID = $data->BenID;
    $highsc->Punkte = $data->Punkte;
 
    if($highsc->addpoints()){
 
        http_response_code(201);
 
        echo json_encode(array("message" => "Highscore erfolgreich addiert"));
    }
 
    else{
 
        http_response_code(503);
 
        echo json_encode(array("message" => "Highscore konnte nicht addiert werden."));
    }
}
 
else{

    http_response_code(400);
 
    echo json_encode(array("message" => "Highscore konnte nicht addiert werden. Daten sind nicht vollstaendig."));
}
?>