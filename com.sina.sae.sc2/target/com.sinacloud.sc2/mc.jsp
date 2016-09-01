<%@page import="java.lang.reflect.Constructor"%>
<%@page import="java.net.InetSocketAddress"%>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="net.spy.memcached.AddrUtil" %>
<%@ page import="net.spy.memcached.ConnectionFactoryBuilder" %>
<%@ page import="net.spy.memcached.ConnectionFactoryBuilder.Protocol" %>
<%@ page import="net.spy.memcached.MemcachedClient" %>
<%@ page import="net.spy.memcached.auth.AuthDescriptor" %>
<%@ page import="net.spy.memcached.auth.PlainCallbackHandler" %>
<%@ page import="net.spy.memcached.internal.OperationFuture" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MC测试</title>
</head>
<body>
<%
	AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" }, new PlainCallbackHandler(System.getenv("ACCESSKEY"),System.getenv("SECRETKEY")));
	MemcachedClient mc = null;
	String host = System.getenv("MEMCACHE_SERVERS");
	if (mc == null) {
		mc = new MemcachedClient(
				new ConnectionFactoryBuilder().setProtocol(Protocol.BINARY).setAuthDescriptor(ad).build(),
				AddrUtil.getAddresses(host));
	}
	OperationFuture<Boolean> of = mc.set("key1", 0, "value1");
	out.print(of.get());
	out.print("<br>");
	out.print(mc.get("key1"));
	out.print("<br>");
	
	Map<String,String> map =  System.getenv();
	for(String s : map.keySet()){
		out.print(s+ " : " +map.get(s));
		out.print("<br>");
	}
	
%>
</body>
</html>