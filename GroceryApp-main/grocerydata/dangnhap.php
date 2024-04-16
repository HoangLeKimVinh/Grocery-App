<?php 
include "connect.php";
$email = $_POST['Email'];
$pass = $_POST['Password'];

// check data
$query = 'SELECT * FROM `tbl_user` WHERE `Email` = "'.$email.'"AND `Password` ="'.$pass.'"';
$data = mysqli_query($conn,$query);
$result = array();
while($row = mysqli_fetch_assoc($data)){
    $result[] = ($row);
}
if(!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "thanh cong",
        'result' => $result
    ];
}else {
    $arr = [
        'success' => false,
        'message' => "ko thanh cong",
        'result' => $result
    ];
}
print_r(json_encode($arr));
?>