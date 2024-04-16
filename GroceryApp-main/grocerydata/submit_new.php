<?php
include "connect.php";
if(isset($_POST['submit_password']) && $_POST['email'])
{
  $email=$_POST['email'];
  $pass=$_POST['password'];
  $query = "UPDATE tbl_user set Password='$pass' WHERE email='$email'";
  $data = mysqli_query($conn,$query);
  if($data == true) {
    echo "Thanh cong";
  }
}
?>