<?php 
include "connect.php";
$product_name = $_POST['product_name'];
$product_img = $_POST['product_img'];
$product_price = $_POST['product_price'];
$product_desc = $_POST['product_desc'];
$category_id = $_POST['category_id'];

// check data
$query = 'INSERT INTO `tbl_product`(`product_name`, `product_img`, `product_price`, `product_des`, `category_id`) VALUES ("'.$product_name.'","'.$product_img.'","'.$product_price.'","'.$product_desc.'",'.$category_id.')';
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