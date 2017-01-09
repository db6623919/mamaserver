<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<title>login</title>

	<script type="text/javascript" src="RSA.js"></script>
	<script type="text/javascript" src="BigInt.js"></script>
	<script type="text/javascript" src="Barrett.js"></script>
	
	<script type="text/javascript">
function rsalogin(){
	var thisPwd = document.getElementById("password").value;
	setMaxDigits(130);
  	var key = new RSAKeyPair("10001","","e3ed55c7803ada77d8c12791c4bffbdf96dbb8894bf9ab24966bf4d492d7026f6541f899eaef0ed2d83feb1f5c31815451fb3cefed17007f51c3c1c51dcbdd1774a2580741d416c21f0e4f73b4329358df1d9e9a2d7378d69680bcc8855a5aa566f9b811f85640b5a65669a50050fd452adcbbaeaa30c16ccd7590714d412989"); 

	var result = encryptedString(key, encodeURIComponent(thisPwd));
	alert("加密后："+result);
	document.getElementById("passworded").value=result;
}
</script>
</head>

<body>
	<form method="post" name="loginForm" target=_blank>
		<table border="0">
			<tr>
				<td>
					加密前:
				</td>
				<td>
					<input type='text' name="password" id=password style='width:400px' value="my passwd"/><br>
				</td>
			</tr>
			
			<tr>
				<td>
					加密后:
				</td>
				<td>
					<input type='text' name="passworded" id="passworded" style='width:1200px'/><br>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="SUBMIT" onclick="rsalogin();" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>
