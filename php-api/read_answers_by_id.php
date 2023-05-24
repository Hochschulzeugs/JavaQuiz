<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: access");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Credentials: true");
header('Content-Type: application/json');
 
include_once 'config/database.php';
include_once 'objekte/antworten.php';
 
$database = new Database();
$db = $database->getConnection();
 
$antworten = new Antworten($db);
 
$antworten->FrageID = isset($_GET['id']) ? $_GET['id'] : die();
 
$stmt = $antworten->readbyfid();
$num = $stmt->rowCount();
 
if($num>0){
 
    $antworten_arr=array();
 

    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
 
        $antworten_item=array(
            "AntwortID" => $AntwortID,
            "FrageID" => $FrageID,
            "Antwort" => html_entity_decode($Antwort),
            "Richtig" => $Richtig
        );
 
        array_push($antworten_arr, $antworten_item);
    }
 
    http_response_code(200);
 
    echo json_encode($antworten_arr);
}
 
else{
 
    http_response_code(404);
 
    echo json_encode(
        array("message" => "Keine Antworten zu der Frage gefunden.")
    );
}
?>