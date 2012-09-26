<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<html>
<head>
<title>desplay all the jar packages in the directory "dist"</title>
</head>
<body>
<h1>desplay all the jar packages in the directory "dist"</h1>

<%
String webapp = request.getRealPath("/d");
File[] fs;

fs = new File(webapp).getParentFile().listFiles();
for(int i=0;i<fs.length;i++) {
%>
<%=fs[i].getName() %><br>
<%} %>
</body>
</html>
