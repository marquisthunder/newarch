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
	try {
		id = Integer.parseInt(idstr);
	} catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println(id);
	Good good = GoodDao.getGood(id);
%>
<body>
	<script language="javascript">
		//Test function with get method.
		function rules() {
			var result = new Recommend();

			var state = result.jurisdiction();
			if (state != 2) {
				return false;
			}
			alert(state == 2);

			var endUser = 'liming';
			var product = "<%=idstr%>";
			var scheme = 'scheme1';
			var outputItemsNum = '2';
			var outputQuantitye = '10';
			result.information(endUser, product, scheme, outputItemsNum,
					outputQuantitye);

			var str = result.getRecommend();
			//alert(str);
			document.getElementById("result").innerHTML = '';
			for ( var i = 0; i < str.length; i++) {
				if (i != 0)
					document.getElementById("result").innerHTML += '-------';
				document.getElementById("result").innerHTML += str[i];
			}
		}
	</Script>
	<input type="button" value="CallWebserviceByPost" onClick="rules()">
	<table align="center">
		<tr>
			<td>商品名称：<%=good.getGoods_name() %><br>
				商品编号：<%=good.getGoods_number() %><br>
				商品重量：<%=good.getGoods_weight() %><br>
				商品原价：<%=good.getMarket_price() %><br>
				商品一般价格：<%=good.getShop_price() %><br>
				商品会员价格：<%=good.getPromote_price() %><br>
			</td>
		</tr>
	</table>

	<div id="result"></div>
</body>
<script type="text/javascript">
	rules();
</script>
</html>
