<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: access");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Credentials: true");
header('Content-Type: application/json');
 
include_once 'config/database.php';
include_once 'objekte/fragen.php';
 
$database = new Database();
$db = $database->getConnection();
 
$fragen = new Fragen($db);
 
$fragen->FrageID = isset($_GET['id']) ? $_GET['id'] : die();
 
$fragen->readbyid();
 
if($fragen->Frage!=null){
    
    $fragen_arr = array(
        "FrageID" =>  $fragen->FrageID,
        "KatID" => $fragen->KatID,
        "Frage" => $fragen->Frage
    );
 
    http_response_code(200);
 
    echo json_encode($fragen_arr);
}
 
else{

    http_response_code(404);
 
    echo json_encode(array("message" => "Frage existiert nicht."));
}
?>