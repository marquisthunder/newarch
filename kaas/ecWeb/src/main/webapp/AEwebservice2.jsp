<%@page import="com.ecweb.dao.GoodDao"%>
<%@page import="com.ecweb.model.Good"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="js/service.js" type="text/javascript"></script>
<title>Call webservice with javascript and xmlhttp.</title>

<%
	String idstr = request.getParameter("id");
	System.out.println(idstr);
	int id = 0;
	if (idstr == null || idstr.equals("")) {
		id = 1;
		idstr = "1";
	} else {
		try {
			String[] ids = idstr.split(",");
			System.out.println("ids[0] = " + ids[0]);
			System.out.println("ids.length = " + ids.length);
			id = Integer.parseInt(ids[0]);
		} catch (Exception e) {
			System.out.println("ID input error");
			id = 1;
			idstr = "1";
		}
	}
	System.out.println("id = " + id);
	Good good = GoodDao.getGood(id);
%>
<body>
	<script language="javascript">
		var xmlHttp;
		var isResult = false;
		
		function createXmlHttpObject() {
			if (window.XMLHttpRequest) {
				xmlHttp = new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			return xmlHttp;
		}

		function send(id) {
			xmlHttp = createXmlHttpObject();
			var url = "AEwebAjax.jsp?time=" + Math.random() + "&ids=" + id;
			if (xmlHttp) {
				xmlHttp.onreadystatechange = callback;
				xmlHttp.open("GET", url, true);
				xmlHttp.send(null);
				//callback();
			//alert(url);
			} else {
				alert("your browser does not support ajax");
				return;
			}
		}

		function callback() {
			//alert("isResult:"+isResult);
			if (xmlHttp.readyState == 4) {
				if (xmlHttp.status == 200) {
					var xmlDoc = xmlHttp.responseXML;
					var goods_names = xmlDoc.getElementsByTagName("goods_name");
					var str = "";
					for(var i=0; i<goods_names.length;i++){
						str += "<td><div onClick='sendandr("+xmlDoc.getElementsByTagName("goods_id")[i].childNodes[0].nodeValue+")'>";
						str +="商品ID："+ xmlDoc.getElementsByTagName("goods_id")[i].childNodes[0].nodeValue + "<br>";
						str +="商品名称："+ xmlDoc.getElementsByTagName("goods_name")[i].childNodes[0].nodeValue + "<br>";
						str +="商品编号："+xmlDoc.getElementsByTagName("goods_number")[i].childNodes[0].nodeValue+"<br>";
						str +="商品重量："+xmlDoc.getElementsByTagName("goods_weight")[i].childNodes[0].nodeValue+"<br>";
						str +="商品原价："+xmlDoc.getElementsByTagName("market_price")[i].childNodes[0].nodeValue+"<br>";
						str +="商品一般价格："+xmlDoc.getElementsByTagName("shop_price")[i].childNodes[0].nodeValue+"<br>";
						str +="商品会员价格："+xmlDoc.getElementsByTagName("promote_price")[i].childNodes[0].nodeValue+"</div><br><br></td>";
					}
					if(isResult){
						document.getElementById("result").innerHTML = str;
					}else{
						document.getElementById("thisGood").innerHTML = str;
						isResult = true;
					}
				} else {
					alert(xmlHttp.statusText);
				}
			}
		}

		function rules(idstr) {
			var result = new Recommend();

			var state = result.jurisdiction();
			if (state != 2) {
				return false;
			}
			//alert(state == 2);

			var endUser = 'liming';
			var product = idstr;
			var scheme = 'scheme1';
			var outputItemsNum = '1';
			var outputQuantitye = '2';
			result.information(endUser, product, scheme, outputItemsNum,
					outputQuantitye);
			isResult = true;
			var str = result.getRecommend();
			send(str);
			return str;
		}
		
		function sendandr(id){
			isResult = false;
			send(id);
			var str = rules(id);
		}
	</Script>
	<input type="button" value="CallWebserviceByPost" onClick="rules()">
	<table align="center">
		<tr>
			<div id="thisGood"  align="center">
			</div>
		</tr>
	</table>

	<table align="right">
		<tr>
			<div id="result" align="center">
			</div>
		</tr>
	</table>
</body>
<script type="text/javascript">
	sendandr(<%=idstr%>);
</script>
</html>
