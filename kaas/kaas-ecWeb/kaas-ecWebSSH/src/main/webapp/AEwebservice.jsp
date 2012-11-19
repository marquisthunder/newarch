<%@page import="com.ecweb.dao.GoodDao"%>
<%@page import="com.ecweb.model.Good"%>
<%@page import="java.util.List"%>
<%@page import="com.ecweb.kaasservice.manage.KaasserviceManage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="js/service.js" type="text/javascript"></script>
<title>Call webservice with javascript and xmlhttp.</title>

<%
	String idstr = request.getParameter("id");
	//System.out.println(idstr);
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
	
	String ecommerceName = "jingdong";
	String apiKey = "an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ";
	String endUser = "liming";
	String scheme = "scheme1";
	int outputItemsNum = 2;
	int outputQuantitye = 3;
	
	System.out.println("idstr = " + idstr);
	List<Good> goods = KaasserviceManage.getRecommends(ecommerceName,apiKey,scheme,endUser,idstr,outputItemsNum,outputQuantitye);
%>
<body>
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
					<a href="AEwebservice.jsp?id=<%=g.getGoods_id()%>">
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
