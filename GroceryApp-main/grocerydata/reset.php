<?php
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
require 'PHPMailer/src/SMTP.php';
include "connect.php";
  $email = $_POST['Email'];
  $query = 'SELECT * FROM `tbl_user` WHERE `Email` = "'.$email.'"';
  $data = mysqli_query($conn,$query);
  $result = array();
  while($row = mysqli_fetch_assoc($data)){
      $result[] = ($row);
  }

  if(empty($result)){
    $arr = [
        'success' => false,
        'message' => "Mail khong chinh xac",
        'result' => $result
        
    ];
  }else{
    
    // send mail
    $email=($result[0]['Email']);
    $pass=($result[0]['Password']);

    $link="<a href='http://192.168.1.14/grocerydata/reset_pass.php?key=".$email."&reset=".$pass."'>Click To Reset password</a>";
    $mail = new PHPMailer();
    $mail->CharSet =  "utf-8";
    $mail->IsSMTP();
    // enable SMTP authentication
    $mail->SMTPAuth = true;                  
    // GMAIL username
    $mail->Username = "vinhhlk.21ad@vku.udn.vn";
    // GMAIL password
    $mail->Password = "cong0902396136";
    $mail->SMTPSecure = "ssl";  
    // sets GMAIL as the SMTP server
    $mail->Host = "smtp.gmail.com";
    // set the SMTP port for the GMAIL server
    $mail->Port = "465";
    $mail->From= "kimbii.2003@gmail.com";
    $mail->FromName='Grocery App';
    $mail->AddAddress($email, 'reciever_name');
    $mail->Subject  =  'Reset Password';
    $mail->IsHTML(true);
    $mail->Body    = $link;
    if($mail->Send())
    {
        $arr = [
            'success' => true,
            'message' => "Vui long kiem tra email cua ban",
            'result' => $result
        ];
    }
    else
    {
        $arr = [
            'success' => true,
            'message' => "Mail Error - >".$mail->ErrorInfo,
            'result' => $result
        ];
    }
  }
  print_r(json_encode($arr));



  
    
    
?>