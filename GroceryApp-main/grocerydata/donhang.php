<?php 
include "connect.php";
$sdt = $_POST['sdt'];
$email = $_POST['email'];
$tongtien = $_POST['tongtien'];
$iduser = $_POST['iduser'];
$diachi = $_POST['diachi'];
$soluong = $_POST['soluong'];
$chitiet = $_POST['chitiet'];


$query = 'INSERT INTO `donhang`(`iduser`, `diachi`, `soluong`, `tongtien`, `sodienthoai`, `email`) VALUES ('.$iduser.',"'.$diachi.'","'.$soluong.'","'.$tongtien.'","'.$sdt.'","'.$email.'")';
$data = mysqli_query($conn,$query);
if($data == true) {
    $query = "SELECT `id` AS iddonhang  FROM `donhang` WHERE `iduser` = $iduser ORDER BY `id` DESC LIMIT 1";
    $data = mysqli_query($conn,$query);
    while($row = mysqli_fetch_assoc($data)) 
    {
        $iddonhang = ($row);
    }
    if(!empty($iddonhang)) {
        $chitiet = json_decode($chitiet, true);
        foreach($chitiet as $key => $value) {
            $truyvan = 'INSERT INTO `chitietdonhang`(`iddonhang`, `idsp`, `soluong`, `gia`) VALUES ('.$iddonhang["iddonhang"].','.$value["idsp"].','.$value["soluong"].','.$value["giasp"].')';
            $data = mysqli_query($conn,$truyvan);
        }
    
        if($data == true){
            $arr = [
                'success' => true,
                'message' => "thanh cong",
                
            ];
        }else {
            $arr = [
                'success' => false,
                'message' => "ko thanh cong"
            ];
        }
        print_r(json_encode($arr));
    }

}else {
    $arr = [
        'success' => false,
        'message' => "ko thanh cong",
    ];
    
    print_r(json_encode($arr));
}

?>