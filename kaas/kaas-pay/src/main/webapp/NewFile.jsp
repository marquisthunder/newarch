<%@ page import="com.alipay.config.*"%>
<%@ page import="java.sql.*, com.liang.forums.*, java.util.*"%> 
<html>
<%
Connection conn = DB.getConn();
boolean autoCommit = conn.getAutoCommit();
conn.setAutoCommit(false);//very inportant, make sure all the data changed in the same time 


String sql = "insert into post values (null, ?, ?, ?, ?, now(), ?)";
PreparedStatement pstmt = DB.prepareStmt(conn, sql);
pstmt.setInt(1, 5);
pstmt.setInt(2, 1);
pstmt.setString(3, "22");
pstmt.setString(4, "22222");
pstmt.setInt(5, 1);
pstmt.executeUpdate();

//Statement stmt = DB.createStmt(conn);
//stmt.executeUpdate("update post set isleaf = 0 where id = 5");

conn.commit(); //put all the operating into one session

conn.setAutoCommit(autoCommit);
DB.close(pstmt);
DB.close(conn);
%>
<h1>good</h1>
</html>

