<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: access");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Credentials: true");
header('Content-Type: application/json');
 
include_once 'config/database.php';
include_once 'objekte/highsc.php';
 
$database = new Database();
$db = $database->getConnection();
 
$highsc = new HighSc($db);
 
$stmt = $highsc->read();
$num = $stmt->rowCount();
 
if($num>0){
 
    $highsc_arr=array();
 

    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
 
        $highsc_item=array(
            "BenID" => $BenID,
            "Punkte" => html_entity_decode($Punkte)
        );
 
        array_push($highsc_arr, $highsc_item);
    }
 
    http_response_code(200);
 
    echo json_encode($highsc_arr);
}
 
else{

    http_response_code(404);
 
    echo json_encode(array("message" => "Fehler beim Auslesen der Highscore."));
}
?>