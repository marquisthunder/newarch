<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="js/service.js" type="text/javascript"></script>
<title>Call webservice with javascript and xmlhttp.</title>
<body>
	<script language="javascript">
		//Test function with get method.
	rules();
	function rules() {
		var result = new Recommend();
		
		var state = result.jurisdiction();
		if(state!=2){
			return false;
		}
		alert(state==2);
		
		var endUser = 'liming';
		var product = '2';
		var scheme = 'scheme1';
		var outputItemsNum = '2';
		var outputQuantitye = '2';
		result.information(endUser,product,scheme,outputItemsNum,outputQuantitye);
		
		var str = result.getRecommend();
			//alert(str);
		document.getElementById("result").innerHTML = '';
		for(var i=0;i<str.length; i++){
			if(i!=0)
				document.getElementById("result").innerHTML += '-------';
			document.getElementById("result").innerHTML += str[i];
		}
	}
	</Script>
	<input type="button" value="CallWebserviceByPost"
		onClick="rules()">
	<div id="result"></div>
</body>

</html>
