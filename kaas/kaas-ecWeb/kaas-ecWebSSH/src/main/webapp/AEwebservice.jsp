<%@page import="java.util.List"%>
<%@page import="com.thinkingtop.kaas.ecweb.model.Good"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="good" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="js/service.js" type="text/javascript"></script>
<title>Call webservice with javascript and xmlhttp.</title>

<body>
	<input type="button" value="CallWebserviceByPost" onClick="rules()">
	<table align="center">
		<tr>
			<td>
				<div id="thisGood">
					<a href="good.action?id=${good.goods_id }">
					商品ID：${good.goods_id }<br>
					商品名称：${good.goods_name }<br>
					商品编号：${good.goods_number }<br>
					商品重量：${good.goods_weight }<br> 
					商品原价：${good.market_price }<br>
					商品一般价格：${good.shop_price }<br>
					商品会员价格：${good.promote_price }<br><br><br><br><br>
					</a>
				</div>
			</td>
		</tr>
	</table>
	<table  align='center'>
		<tr>
		<good:iterator value="goods">
			<c:if test="${goods_name==null}">
				</tr></table>
				<table  align='center'><tr>
			</c:if>
			<c:if test="${goods_name!=null}">
				<td>
					<div id="thisGood">
						<a href="good.action?id=${goods_id }">
						商品ID：${goods_id }<br>
						商品名称：${goods_name }<br>
						商品编号：${goods_number }<br>
						商品重量：${goods_weight }<br> 
						商品原价：${market_price }<br>
						商品一般价格：${shop_price }<br>
						商品会员价格：${promote_price }<br>
						</a>
					</div>
				</td>
			</c:if>
		</good:iterator>
		</tr>
	</table>
</body>
</html>
