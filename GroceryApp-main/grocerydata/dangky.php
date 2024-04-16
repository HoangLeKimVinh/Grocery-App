<?php 
include "connect.php";
$email = $_POST['Email'];
$pass = $_POST['Password'];
$username = $_POST['UserName'];
$mobile = $_POST['Mobile'];

// check data
$query = 'SELECT * FROM `tbl_user` WHERE `Email` = "' . $email.'"';
$data = mysqli_query($conn,$query);
$numrow = mysqli_num_rows($data);
if($numrow > 0){
    $arr = [
        'success' => false,
        'message' => "Email đã tồn tại",
        
    ];
}else {
    $query = 'INSERT INTO `tbl_user`(`UserName`, `Password`, `Email`, `Mobile`) VALUES ("'.$username.'","'.$pass.'","'.$email.'","'.$mobile.'")';
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
}

print_r(json_encode($arr));
?>