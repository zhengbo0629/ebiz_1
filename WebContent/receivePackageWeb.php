

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Receive a Package</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>

	<div style="text-align: center; font-size: 18px; color: #c90506;">
		<p>Receive a Package</p>
	</div>



<?php
// include auth.php file on all secure pages
include ("head.php");
include ("body.php");
include ("auth.php");
require ('db.php');

// $currentUser = $_SESSION['currentUser'];

if (isset ( $_REQUEST ['UPC1'] )) {
	
	$username = $_SESSION ['username'];
	
	$currentUser = $_SESSION ['currentUser'];
	$address = $currentUser ['Address'];
	$trackingNumber = stripslashes ( $_REQUEST ['trackingNumber'] );
	$receiver = stripslashes ( $_REQUEST ['receiver'] );
	$CompanyName = stripslashes ( $_REQUEST ['CompanyName'] );
	$UPC1 = $_REQUEST ['UPC1'];
	$quantity1 = $_REQUEST ['quantity1'];
	$productName1 = stripslashes ( $_REQUEST ['productname1'] );
	$UPC2 = $_REQUEST ['UPC2'];
	$quantity2 = $_REQUEST ['quantity2'];
	$productName2 = stripslashes ( $_REQUEST ['productname2'] );
	$UPC3 = $_REQUEST ['UPC3'];
	$quantity3 = $_REQUEST ['quantity3'];
	$productName3 = stripslashes ( $_REQUEST ['productname3'] );
	
	$creat_date = date ( "Y-m-d H:i:s" );
	
	// check if the tracking number is already exist
	$trackingNumber = $_REQUEST ['trackingNumber'];
	if (strlen ( $trackingNumber ) >= 5) {
		$query2 = "SELECT * FROM ReceivedPackageList WHERE '$trackingNumber' LIKE CONCAT('%',TrackingNumber,'%') AND Length(TrackingNumber)>4 OR TrackingNumber LIKE '%$trackingNumber%'";
		// echo $query2;
		// exit();
		$result = mysqli_query ( $con, $query2 ) or die ( mysqli_error ( $con ) );
		$rows = mysqli_num_rows ( $result );
		if ($rows >= 1) {
			echo "<script> {alert('TrackingNumber already exist, please input a different one')'} </script>";
			exit ();
		}
	} else {
		echo "<script> {alert('Please input a good trackingNumber')} </script>";
		exit ();
	}
	function addProduct($trackingNumber, $CompanyName, $productName, $UPC, $quantity, $username, $address, $receiver, $creat_date) {
		if (strlen ( $UPC ) != 12) {
			return;
		}
		require ('db.php');
		$productName = str_replace ( '!', '', $productName );
		$query = "insert into ReceivedPackageList (CompanyName,TrackingNumber,ShipID,ModelNumber,ProductName,
       ProductCondition,UPC,ASIN,SKU,Brand,Price,BasePrice,PromPrice,quantity,Promquantity,StoreName,UserName,ShippingAddress,Email,
       PhoneNumber,Receiver,Note,ReportTime,UpdateTime,CreditCardNumber,Status)
       values('$CompanyName','$trackingNumber','','','$productName','','$UPC',
       '','','','','','','$quantity','','','$username','$address','',
       '','$receiver','','$creat_date','$creat_date','','')";
		$result = mysqli_query ( $con, $query );
		
		if ($result) {
			echo "<script> {alert('Product $productName was added successfully')} </script>";
		} else {
			echo "<script> {alert('Please check your connection and try again')} </script>";
			exit ();
		}
	}
	function processUPC($UPC, $productName) {
		if (strlen ( $UPC ) != 12) {
			return;
		}
		require ('db.php');
		$productName = str_replace ( '!', '', $productName );
		$allProducts = $_SESSION ['allProducts'];
		$upcexist = false;
		foreach ( $allProducts as $item ) {
			if ($item ['UPC'] === $UPC && $item ['ProductName'] != $productName) {
				$upcexist = true;
				$packageid = $item ['id'];
				$query = "UPDATE WarehouseProductList SET ProductName='$productName', UpdateTime='$creat_date' WHERE id='$packageid'";
				$result = mysqli_query ( $con, $query );
				
				if ($result) {
					echo "<script> {alert('UPC $UPC was Updated successfully')} </script>";
				} else {
					echo "<script> {alert('Please check your connection and try again')} </script>";
					exit ();
				}
			}
		}
		if (! $upcexist) {
			$query = "insert into WarehouseProductList (UPC,ProductName) values('$UPC','$productName')";
			$result = mysqli_query ( $con, $query );
			if ($result) {
				echo "<script> {alert('UPC one was added successfully')} </script>";
			} else {
				echo "<script> {alert('Please check your connection and try again')} </script>";
				exit ();
			}
		}
	}
	
	processUPC ( $UPC1, $productName1 );
	processUPC ( $UPC2, $productName2 );
	processUPC ( $UPC3, $productName3 );
	addProduct ( $trackingNumber, $CompanyName, $productName1, $UPC1, $quantity1, $username, $address, $receiver, $creat_date );
	$trackingNumber2 = $trackingNumber . "002";
	addProduct ( $trackingNumber2, $CompanyName, $productName2, $UPC2, $quantity2, $username, $address, $receiver, $creat_date );
	$trackingNumber3 = $trackingNumber . "003";
	addProduct ( $trackingNumber3, $CompanyName, $productName3, $UPC3, $quantity3, $username, $address, $receiver, $creat_date );
} else {
	
	$query = "SELECT * FROM `WarehouseProductList` order by id desc";
	
	$result = mysqli_query ( $con, $query ) or die ( mysqli_error ( $con ) );
	$allProducts = array ();
	$productsUPCName = "";
	
	while ( $row = mysqli_fetch_array ( $result ) ) {
		$allProducts [] = $row;
		$productsUPCName = $productsUPCName . $row ['UPC'] . '_' . $row ['ProductName'] . '!';
	}
	$productsUPCName = substr ( $productsUPCName, 0, strlen ( $productsUPCName ) - 1 );
	
	$_SESSION ['allProducts'] = $allProducts;
	
	?>

<div
		style="text-align: left; margin-left: 20%; width: 80%; line-height: 15px">
		<div align="left" style="margin-left: 80px">

			<br>

			<form name="packageinfor" id="packageinfor" action="" method="post">
				<input type="text" name="trackingNumber" id="trackingNumber"
					onchange="trackingchange()" placeholder="Tracking Number"
					style="width: 300px;" /> <input type="text" name="receiver"
					id="receiver" onchange="receiverchange()"
					placeholder="Receiver Name" style="width: 150px;" required /> <input
					type="text" name="CompanyName" id="CompanyName"
					onchange="CompanyNameChange()" placeholder="Company Name"
					style="width: 150px;" /> <br> <br> <input type="number" name="UPC1"
					id="UPC1" placeholder="UPC1" onchange="upc1change()"
					style="width: 300px;" /> <input type="number" name="quantity1"
					id="quantity1" onchange="quantity1change()" placeholder="quantity1"
					style="width: 300px;" /> <br> <input type="text"
					name="productname1" id="productname1"
					onchange="productName1change()" placeholder="productname1"
					style="width: 600px;" /> <br> <br> <input type="number" name="UPC2"
					id="UPC2" placeholder="UPC2" onchange="upc2change()"
					style="width: 300px;" /> <input type="number" name="quantity2"
					id="quantity2" onchange="quantity2change()" placeholder="quantity2"
					style="width: 300px;" /> <br> <input type="text"
					name="productname2" id="productname2"
					onchange="productName2change()" placeholder="productname2"
					style="width: 600px;" /> <br> <br> <input type="number" name="UPC3"
					id="UPC3" onchange="upc2change()" placeholder="UPC3"
					style="width: 300px;" /> <input type="number" name="quantity3"
					id="quantity3" onchange="quantity3change()" placeholder="quantity3"
					style="width: 300px;" /> <br> <input type="text"
					name="productname3" id="productname3"
					onchange="productName3change()" placeholder="productname3"
					style="width: 600px;" /> <br> <br>
			</form>
		</div>
	</div>
	<div align="center" style="text-align: center;">
		<input type="button" name="submit" value="submit"
			onClick="checkprocess()" />
	</div>


	<script type="text/javascript">
function trackingchange(){
	document.getElementById("receiver").focus();
}
function receiverchange(){
	document.getElementById("CompanyName").focus();
}
function CompanyNameChange(){
	document.getElementById("UPC1").focus();
}
function productName1change(){
	document.getElementById("UPC2").focus();
}
function productName2change(){
	document.getElementById("UPC3").focus();
}
function productName3change(){
	//document.getElementById("UPC4").focus();
}

function quantity1change(){
	if (document.getElementById("productname1").value.length==0){
		document.getElementById("productname1").focus();
		alert('please input a product name for upc1');
	}else{
		document.getElementById("UPC2").focus();
	}
	
}
	function quantity2change(){
		if (document.getElementById("productname2").value.length==0){
			document.getElementById("productname2").focus();
			alert('please input a product name for upc2');
		}else{
			document.getElementById("UPC3").focus();
		}
		
	}
		function quantity3change(){
			if (document.getElementById("productname3").value.length==0){
				document.getElementById("productname3").focus();
				alert('please input a product name for upc3');
			}else{
				//document.getElementById("UPC4").focus();
			}
			
		}

function upc1change(){
	var upc=document.getElementById("UPC1").value;

	if(upc.length>0 && upc.length!=12){
		alert('please input a good upc1');
		return;
	}
	
	var text = <?php echo json_encode($productsUPCName); ?>;
	var array=text.split('!');
	var productName="";
	for (i=0;i<array.length;i++){
		var infor= array[i].split('_');
		if (infor[0]==upc){
			document.getElementById("productname1").value=infor[1];
			document.getElementById("quantity1").focus();
			changeTrackingtext();
			productName=document.getElementById("productname1").value;
				
			return;
		}
	}
	document.getElementById("quantity1").focus();	
}
	function upc2change(){
		var upc=document.getElementById("UPC2").value;
		if(upc.length>0 && upc.length!=12){
			alert('please input a good upc2');
			return;
		}
		
		var text = <?php echo json_encode($productsUPCName); ?>;
		var array=text.split('!');
		var productName="";
		for (i=0;i<array.length;i++){
			var infor= array[i].split('_');
			if (infor[0]==upc){
				document.getElementById("productname2").value=infor[1];
				document.getElementById("quantity2").focus();	
				changeTrackingtext();
				productName=document.getElementById("productname2").value;
				
				return;
			}
		}
		document.getElementById("quantity2").focus();
	}
	function upc3change(){
		var upc=document.getElementById("UPC3").value;

		if(upc.length>0 && upc.length!=12){
			alert('please input a good upc3');
			return;
		}
		var text = <?php echo json_encode($productsUPCName); ?>;
		var array=text.split('!');
		var productName="";
		for (i=0;i<array.length;i++){
			var infor= array[i].split('_');
			if (infor[0]==upc){
				document.getElementById("productname3").value=infor[1];
				document.getElementById("quantity3").focus();	
				changeTrackingtext();
				productName=document.getElementById("productname3").value;
				
				return;
			}
		}
		document.getElementById("quantity3").focus();	
	}

function checkprocess(){

	var trackingnumber=document.getElementById("trackingNumber").value;
	if(trackingnumber.length<=5){
		alert('please input a good tracking number');
		return;
	}
	var receiver=document.getElementById("receiver").value;
	if(receiver.length<2){
		alert('please input receiver name');
		return;
	}
	var CompanyName=document.getElementById("CompanyName").value;
	if(CompanyName.length==0){
		alert('please input Identification code');
		return;
	}
	
	var upc1=document.getElementById("UPC1").value;
	//var isNumber = /^\d+\.\d+$/.test(upc1);
	//alert(upc1);
	//alert(upc1.length);
	if(upc1.length!=12){
		alert('please input a good upc1');
		return;
	}
	var quantity1=document.getElementById("quantity1").value;

	if(quantity1.length==0){
		alert('please input a good quantity1');
		return;
	}
	if(document.getElementById("productname1").value.length==0){
		alert('please input a product name for upc1');
	}	
	
	var upc2=document.getElementById("UPC2").value;
	var quantity2=document.getElementById("quantity2").value;


	if(upc2.length>0 && upc2.length!=12){
		alert('please input a good upc2');
		return;
	}else if (upc2.length==12 && quantity2.length==0){
		
		alert('please input a good quantity2');
		return;
	}else if (upc2.length==12){
		if (upc1===upc2){
			alert('upc1 equals upc2, please check');
			return;
		}
		if(document.getElementById("productname2").value.length==0){
			alert('please input a product name for upc2');
		}
	}

	
	var upc3=document.getElementById("UPC3").value;
	var quantity3=document.getElementById("quantity3").value;

	if(upc3.length>0 && upc3.length!=12){
		alert('please input a good upc3');
		return;
	}else if(upc3.length==12 && quantity3.length==0){

		alert('please input a good quantity3');
		return;
	}else if (upc3.length==12){
		if (upc1===upc3){
			alert('upc1 equals upc3, please check');
			return;
		}
		if (upc2===upc3){
			alert('upc2 equals upc3, please check');
			return;
		}
		if(document.getElementById("productname3").value.length==0){
			alert('please input a product name for upc3');
		}
	}


	document.getElementById("packageinfor").submit();

	
}
</script>

</body>

</html>

<?php 
}
?>