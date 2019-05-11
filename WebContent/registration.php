<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Registration</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<?php
require('db.php');
// If form submitted, insert values into the database.
//×÷Õß£ºwww.manongjc.com
if (isset($_REQUEST['username'])){
        // removes backslashes
	$username = stripslashes($_REQUEST['username']);
        //escapes special characters in a string
	$username = mysqli_real_escape_string($con,$username); 
	$companyName="Micro";
	$email = stripslashes($_REQUEST['email']);
	$email = mysqli_real_escape_string($con,$email);
	$phoneNumber = stripslashes($_REQUEST['phoneNumber']);
	$phoneNumber = mysqli_real_escape_string($con,$phoneNumber);
	$password = stripslashes($_REQUEST['password']);
	$password = mysqli_real_escape_string($con,$password);
	$address = stripslashes($_REQUEST['address']);
	$address = mysqli_real_escape_string($con,$password);
	$creat_date = date("Y-m-d H:i:s");
	
	$query1 = "SELECT * FROM EbizUser WHERE UserName='$username'";
	
	$result = mysqli_query($con,$query1) or die(mysqli_error($con));
	$rows = mysqli_num_rows($result);

	if($rows>=1){

	    echo "<script> {window.alert('User name already exist, please chose a different one');location.href='registration.php'} </script>";
	    exit();

    
	}
	
        $query = "INSERT into `EbizUser` (UserName, CompanyName,PassWord, Email,PhoneNumber,Address,CreateTime)
VALUES ('$username','$companyName', '$password', '$email','$phoneNumber','$address', '$creat_date')";
//VALUES ('$username', '".md5($password)."', '$email','$phoneNumber', '$creat_date')";
        $result = mysqli_query($con,$query);

        
        if($result){
            echo "<div class='form'>
<h3>You are registered successfully.Please contact the administrator to active your account before you login</h3>
<br/>Click here to <a href='login.php'>Login</a></div>";
        }
    }else{
?>
<div class="form">
<h1>Registration</h1>
<form name="registration" action="" method="post">
<input type="text" name="username" placeholder="Username" required />
<input type="email" name="email" placeholder="Email" required />
<input type="number" name="phoneNumber" placeholder="PhoneNumber" required />
<input type="password" name="password" placeholder="Password" required />
<input type="text" name="address" placeholder="Shipping Address" required style="width:400px"/>
<div style="width:400px">
<br>
<p>This address will be used for shipping label </p>
<br>
</div>
<input type="submit" name="submit" value="Register" />
</form>
<p>Already registered? <a href='login.php'>Login</a></p>
</div>
<?php } ?>
</body>
</html>