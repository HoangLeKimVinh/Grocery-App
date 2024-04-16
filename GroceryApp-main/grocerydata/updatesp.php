<?php 
include "connect.php";
$product_name = $_POST['product_name'];
$product_img = $_POST['product_img'];
$product_price = $_POST['product_price'];
$product_desc = $_POST['product_desc'];
$category_id = $_POST['category_id'];
$id = $_POST['id'];

// check data
$query = 'UPDATE `tbl_product` SET `product_name`="'.$product_name.'",`product_img`="'.$product_img.'",`product_price`="'.$product_price.'",`product_des`="'.$product_desc.'",`category_id`='.$category_id.' WHERE `id`='.$id;
$data = mysqli_query($conn,$query);
    if($data == true) {
        $arr = [
            'success' => true,
            'message' => "thanh cong",
        
        ];
    }else {
        $arr = [
            'success' => false,
            'message' => "ko thanh cong",
        
        ];
    }

print_r(json_encode($arr));
?>