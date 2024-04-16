<?php 
include "connect.php";
$page = $_POST['page'];
$total = 5;
$pos = ($page-1)*$total;
$category_id = $_POST['category_id'];


$query = "SELECT * FROM tbl_product WHERE category_id =".$category_id." LIMIT ".$pos.",".$total."";
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