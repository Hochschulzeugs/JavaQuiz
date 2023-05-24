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
 
$fragen->KatID = isset($_GET['id']) ? $_GET['id'] : die();
 
 
$stmt = $fragen->readbycatid();
$num = $stmt->rowCount();
 
if($num>0){
 
    $fragen_arr=array();

    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
 
        $fragen_item=array(
            "FrageID" => $FrageID,
            "KatID" => $KatID,
            "Frage" => html_entity_decode($Frage)
        );
 
        array_push($fragen_arr, $fragen_item);
    }
 
    http_response_code(200);
 
    echo json_encode($fragen_arr);
}
 
else{

    http_response_code(404);
 
    echo json_encode(array("message" => "Keine Fragen zu dieser Kategorie gefunden."));
}
?>