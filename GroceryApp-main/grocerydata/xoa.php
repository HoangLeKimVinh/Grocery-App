<?php 
include "connect.php";
$id = $_POST['id'];

// check data
$query = 'DELETE FROM `tbl_product` WHERE `id` ='.$id;
$data = mysqli_query($conn,$query);
    if($data == true) {
        $arr = [
            'success' => true,
            'message' => "thanh cong",
        
        ];
    }else {
        $arr = [
            'success' => false,
            'message' => "xoa ko thanh cong",
        
        ];
    }

print_r(json_encode($arr));
?>