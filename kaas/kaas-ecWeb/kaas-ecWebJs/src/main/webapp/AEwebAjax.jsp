<%@page import="com.thinkingtop.kaas.ecweb.dao.GoodDao"%>
<%@page import="com.thinkingtop.kaas.ecweb.model.Good"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
    response.setContentType("text/xml;charset=gb2312");      
	String idstr = request.getParameter("ids");
	System.out.println(idstr);
	int[] ids;
	if(idstr==null||idstr.equals("")){
		ids = new int[]{1};
	}else{
		try {
			String[] idstrs=idstr.split(",");
		System.out.println("ids[0] = "+idstrs[0]);
		System.out.println("ids.length = "+idstrs.length);
			ids = new int[idstrs.length];
			for(int i=0; i<idstrs.length; i++){
				ids[i] = Integer.parseInt(idstrs[i]);
			}
		} catch (Exception e) {
		System.out.println("ID input error");
			ids = new int[]{1};
		}
	}
	System.out.println("id = " + ids[0]);
    StringBuffer str=new StringBuffer();
    str.append("<information>");
    for(int i=0;i<ids.length;i++){
		Good good = GoodDao.getGood(ids[i]);
		str.append("<goods_id>");str.append(good.getGoods_id());str.append("</goods_id>");
	    str.append("<goods_name>");str.append(good.getGoods_name());str.append("</goods_name>");
	    str.append("<goods_number>");str.append(good.getGoods_number());str.append("</goods_number>");
	    str.append("<goods_weight>");str.append(good.getGoods_weight());str.append("</goods_weight>");  
	    str.append("<market_price>");str.append(good.getMarket_price());str.append("</market_price>");  
	    str.append("<shop_price>");str.append(good.getShop_price());str.append("</shop_price>");  
	    str.append("<promote_price>");str.append(good.getPromote_price());str.append("</promote_price>");
    }
    str.append("</information>");
    /* str.append("<information>"); 
    str.append("<goods_name>");str.append("<table><tr><td>sdffsdf</td></tr></table>");str.append("</goods_name>");
    str.append("</information>");  */
    System.out.println(str.toString());
    out.print(str.toString());
%>  
