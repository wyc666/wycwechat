<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<html>
<body>
<h2>Hello World!</h2>

<%
	String driver = "com.mysql.jdbc.Driver";
	String username = System.getenv("ACCESSKEY");
	String password = System.getenv("SECRETKEY");
	//System.getenv("MYSQL_HOST_S"); 为从库，只读
	String sql_url = "jdbc:mysql://"+System.getenv("MYSQL_HOST")+":"+System.getenv("MYSQL_PORT")+"/"+System.getenv("MYSQL_DB");
	String sql = "show status;";
	Connection con = null;
	PreparedStatement ps =null;
	try {
		Class.forName(driver).newInstance();
		con = DriverManager.getConnection(sql_url,username,password);
		ps = con.prepareStatement(sql);
		con.setAutoCommit(false);
		int count = ps.executeUpdate(sql);
		out.print("<br>");
		out.print("execute sql : show status ");
		out.print(" = "+count);
		out.print("<br>");
		con.commit();
		ps.close();
		con.close();
	} catch (Exception e){
		out.print("cause ===> "+e.getCause());
		out.print("<br>");
		out.print("message  ====> "+e.getMessage());
	}
%>
</body>
</html>
