<?php 
include "connect.php";
$search = $_POST['search'];
if(empty($search)){
    $arr = [
        'success' => false,
        'message' => "ko thanh cong",
    ];
}else{
    $query = "SELECT * FROM `tbl_product` WHERE `product_name` LIKE '%$search%'";
    $data = mysqli_query($conn, $query);
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
}

print_r(json_encode($arr));
?>