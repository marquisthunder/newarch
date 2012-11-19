<%@page import="com.thinkingtop.kaas.ecweb.dao.GoodDao"%>
<%@page import="com.thinkingtop.kaas.ecweb.model.Good"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
	var xmlHttp;

	//创建xmlHttpRequest对象  
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
		var url = "refreshAjax.jsp?time=" + Math.random(); //加一个随机数，解决浏览器缓存问题      
		if (xmlHttp) {
			xmlHttp.onreadystatechange = callback; //注册回调函数名,只需要函数名，不要加括号  
			//设置连接信息：  
			//第一个参数：表示http的请求方式，主要使用get和post  
			//第二个参数：表示请求的URL地址，get方式的请求参数也在URL中  
			//第三个参数：表示采用同步还是异步方式进行交互，true表示异步交互  
			xmlHttp.open("GET", url, true);

			//发送数据，开始和服务器端进行交互  
			//同步方式下，send语句会在服务器端返回数据后才执行  
			//异步方式下，send语句会立即执行  
			xmlHttp.send(null);
		} else {
			alert("your browser does not support ajax");
			return;
		}
	}

	//回调函数  
	function callback() {
		//判断对象的状态是交互完成  
		if (xmlHttp.readyState == 4) {
			//判断http的交互是否成功  
			if (xmlHttp.status == 200) {
				//获取服务器端返回的数据  
				var xmlDoc = xmlHttp.responseXML;

				document.getElementById("name").innerHTML = xmlDoc
						.getElementsByTagName("name")[0].childNodes[0].nodeValue;
				document.getElementById("tel").innerHTML = xmlDoc
						.getElementsByTagName("tel")[0].childNodes[0].nodeValue;
				document.getElementById("city").innerHTML = xmlDoc
						.getElementsByTagName("city")[0].childNodes[0].nodeValue;
			} else {
				alert(xmlHttp.statusText);
			}
		}
	}

	function refresh() {
		window.setInterval("send()", 1000); //定时刷新  
	}
</script>
</head>
<body>
	<!-- <form action="">
		选择用户： <select name="employees" onchange="send(this.value);">
			<option value="Tom">Tom</option>
			<option value="Jom">Jom</option>
			<option value="Sun">Sun</option>
		</select>
	</form> -->

	用户名称：
	<span id="name"></span>
	<br> 电话：
	<span id="tel"></span>
	<br> 城市：
	<span id="city"></span>
	<br>
	<input type="button" value="CallWebserviceByPost" onClick="send()">
</body>
</html>
