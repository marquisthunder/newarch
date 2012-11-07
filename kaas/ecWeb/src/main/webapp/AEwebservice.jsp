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
	function rules(id){
		function outRFunction(xmlDoc){
			var goods_names = xmlDoc.getElementsByTagName('goods_name');
			var str = '';
			for(var i=0; i<goods_names.length;i++){
				str += '<a href="AEwebservice.jsp?id='+ xmlDoc.getElementsByTagName('goods_id')[i].childNodes[0].nodeValue+'">';
				str +='商品ID：'+ xmlDoc.getElementsByTagName('goods_id')[i].childNodes[0].nodeValue + '<br>';
				str +='商品名称：'+ xmlDoc.getElementsByTagName('goods_name')[i].childNodes[0].nodeValue + '<br>';
				str +='商品编号：'+xmlDoc.getElementsByTagName('goods_number')[i].childNodes[0].nodeValue+'<br>';
				str +='商品重量：'+xmlDoc.getElementsByTagName('goods_weight')[i].childNodes[0].nodeValue+'<br>';
				str +='商品原价：'+xmlDoc.getElementsByTagName('market_price')[i].childNodes[0].nodeValue+'<br>';
				str +='商品一般价格：'+xmlDoc.getElementsByTagName('shop_price')[i].childNodes[0].nodeValue+'<br>';
				str +='商品会员价格：'+xmlDoc.getElementsByTagName('promote_price')[i].childNodes[0].nodeValue+'</a><br><br>';
			}
			//alert(str);
			document.getElementById('result').innerHTML = str;
		}
		
		var info = Recommend();
		var endUser = 'liming';
		var product = id;
		var scheme = 'scheme1';
		var outputItemsNum = '1';
		var outputQuantitye = '2';
		var s = info(endUser,product,scheme,outputItemsNum,outputQuantitye,outRFunction);
		var str = s();
		//alert(str);
	}
	</Script>
	<input type="button" value="CallWebserviceByPost" onClick="rules()">
	<table align="center">
		<tr>
				<td>
					<div id="thisGood">
						<a href="AEwebservice.jsp?id=<%=good.getGoods_id()%>">
						商品ID：<%=good.getGoods_id()%><br>
						商品名称：<%=good.getGoods_name()%><br>
						商品编号：<%=good.getGoods_number()%><br>
						商品重量：<%=good.getGoods_weight()%><br> 
						商品原价：<%=good.getMarket_price()%><br>
						商品一般价格：<%=good.getShop_price()%><br>
						商品会员价格：<%=good.getPromote_price()%><br><br><br><br><br>
						</a>
					</div>
				</td>
		</tr>
	</table>

	<table align="center">
		<tr>
			<td>
				<div id="result" align="center">
				</div>
			</td>
		</tr>
	</table>
</body>
<script type="text/javascript">
	(function(){
		rules(<%=good.getGoods_id()%>);
	})();
</script>
</html>
