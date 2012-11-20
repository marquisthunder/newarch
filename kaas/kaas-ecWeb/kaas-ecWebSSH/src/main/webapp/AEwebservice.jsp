<%@page import="java.util.List"%>
<%@page import="com.thinkingtop.kaas.ecweb.model.Good"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="js/service.js" type="text/javascript"></script>
<title>Call webservice with javascript and xmlhttp.</title>

<%
	Good good = (Good)request.getAttribute("good");
	List<Good> goods = (List<Good>)request.getAttribute("goods");
%>
<body>
	<input type="button" value="CallWebserviceByPost" onClick="rules()">
	<%if(good ==null)return; %>
	<table align="center">
		<tr>
			<td>
				<div id="thisGood">
					<a href="good.action?id=<%=good.getGoods_id()%>">
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
	
	<%if(goods==null)return; %>
	<table  align='center'>
		<tr>
	<%for(Good g : goods){
		if(g.getGoods_name()==null){
			out.println("</tr></table>\n    <table  align='center'><tr>");
			continue;
		}
	%>
			<td>
				<div>
					<a href="good.action?id=<%=g.getGoods_id()%>">
					商品ID：<%=g.getGoods_id()%><br>
					商品名称：<%=g.getGoods_name()%><br>
					商品编号：<%=g.getGoods_number()%><br>
					商品重量：<%=g.getGoods_weight()%><br> 
					商品原价：<%=g.getMarket_price()%><br>
					商品一般价格：<%=g.getShop_price()%><br>
					商品会员价格：<%=g.getPromote_price()%><br>
					</a>
				</div>
			</td>
	<%} %>
		</tr>
	</table>
</body>
</html>
