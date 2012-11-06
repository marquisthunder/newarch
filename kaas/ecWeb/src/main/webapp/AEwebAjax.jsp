<%@page import="com.ecweb.dao.GoodDao"%>
<%@page import="com.ecweb.model.Good"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
    response.setContentType("text/xml;charset=gb2312");      
	String idstr = request.getParameter("id");
	System.out.println(idstr);
	int id = 0;
	if(idstr==null||idstr.equals("")){
		id=1;
	}else{
		try {
			String[] ids=idstr.split(",");
		System.out.println("ids[0] = "+ids[0]);
		System.out.println("ids.length = "+ids.length);
			id = Integer.parseInt(ids[0]);
		} catch (Exception e) {
		System.out.println("ID input error");
			id=1;
		}
	}
	System.out.println("id = " + id);
	Good good = GoodDao.getGood(id);

	
    StringBuffer str=new StringBuffer();
    str.append("<information>");  
    str.append("<goods_name>");str.append(good.getGoods_name());str.append("</goods_name>");  
    str.append("<goods_number>");str.append(good.getGoods_number());str.append("</goods_number>");
    str.append("<goods_weight>");str.append(good.getGoods_weight());str.append("</goods_weight>");  
    str.append("<market_price>");str.append(good.getMarket_price());str.append("</market_price>");  
    str.append("<shop_price>");str.append(good.getShop_price());str.append("</shop_price>");  
    str.append("<promote_price>");str.append(good.getPromote_price());str.append("</promote_price>");  
    str.append("</information>");  
    System.out.println(str.toString());
    out.print(str.toString());
%>  
