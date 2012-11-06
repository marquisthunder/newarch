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
		var ResultString;
		
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
						str += "<td><a href='AEwebservice3.jsp?id="+ xmlDoc.getElementsByTagName("goods_id")[i].childNodes[0].nodeValue+"'>";
						str +="商品ID："+ xmlDoc.getElementsByTagName("goods_id")[i].childNodes[0].nodeValue + "<br>";
						str +="商品名称："+ xmlDoc.getElementsByTagName("goods_name")[i].childNodes[0].nodeValue + "<br>";
						str +="商品编号："+xmlDoc.getElementsByTagName("goods_number")[i].childNodes[0].nodeValue+"<br>";
						str +="商品重量："+xmlDoc.getElementsByTagName("goods_weight")[i].childNodes[0].nodeValue+"<br>";
						str +="商品原价："+xmlDoc.getElementsByTagName("market_price")[i].childNodes[0].nodeValue+"<br>";
						str +="商品一般价格："+xmlDoc.getElementsByTagName("shop_price")[i].childNodes[0].nodeValue+"<br>";
						str +="商品会员价格："+xmlDoc.getElementsByTagName("promote_price")[i].childNodes[0].nodeValue+"</a><br><br></td>";
					}
					if(isResult){
						document.getElementById("result").innerHTML = str;
					}else{
						document.getElementById("thisGood").innerHTML = str;
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

			var str = result.getRecommend();
			 isResult = true;
			send(str);
			return str;
		}
	</Script>
	<input type="button" value="CallWebserviceByPost" onClick="rules()">
	<table align="center">
		<tr>
			<div id="thisGood">
				<td><a href="AEwebservice3.jsp?id=<%=good.getGoods_id()%>">
					商品ID：<%=good.getGoods_id()%><br>
					商品名称：<%=good.getGoods_name()%><br> 
					商品编号：<%=good.getGoods_number()%><br>
					商品重量：<%=good.getGoods_weight()%><br> 
					商品原价：<%=good.getMarket_price()%><br>
					商品一般价格：<%=good.getShop_price()%><br>
					商品会员价格：<%=good.getPromote_price()%><br><br><br><br><br>
					</a>
				</td>
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
	rules(<%=idstr%>);
</script>
</html>
