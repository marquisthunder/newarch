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
	} else {
		try {
			String[] ids = idstr.split(",");
			System.out.println("ids[0] = " + ids[0]);
			System.out.println("ids.length = " + ids.length);
			id = Integer.parseInt(ids[0]);
		} catch (Exception e) {
			System.out.println("ID input error");
			id = 1;
		}
	}
	System.out.println("id = " + id);
	Good good = GoodDao.getGood(id);
%>
<body>
	<script language="javascript">
		var xmlHttp;
		var isResult = false;
		var ResultNmb;
		
		function createXmlHttpObject() {
			if (window.XMLHttpRequest) {
				xmlHttp = new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			return xmlHttp;
		}

		function send() {
			xmlHttp = createXmlHttpObject();
			var url = "AEwebAjax.jsp?time=" + Math.random() + "&id=1";
			if (xmlHttp) {
				xmlHttp.onreadystatechange = callback;
				xmlHttp.open("GET", url, true);
				xmlHttp.send(null);
			} else {
				alert("your browser does not support ajax");
				return;
			}
		}

		function callback() {
			if (xmlHttp.readyState == 4) {
				if (xmlHttp.status == 200) {
					var xmlDoc = xmlHttp.responseXML;
					alert("goods---"+xmlDoc.getElementsByTagName("goods_name")[1].childNodes[0].nodeValue);
				} else {
					alert(xmlHttp.statusText);
				}
			}
		}

		function rules(idstr) {
			send();
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
			<td >
				<div id="thisGood">
					商品名称：<%=good.getGoods_name()%><br> 
					商品编号：<%=good.getGoods_number()%><br>
					商品重量：<%=good.getGoods_weight()%><br> 
					商品原价：<%=good.getMarket_price()%><br>
					商品一般价格：<%=good.getShop_price()%><br>
					商品会员价格：<%=good.getPromote_price()%><br>
				</div>
			</td>
		</tr>
	</table>

	<div id="result"></div>
</body>
<script type="text/javascript">
	rules(<%=idstr%>);
</script>
</html>
