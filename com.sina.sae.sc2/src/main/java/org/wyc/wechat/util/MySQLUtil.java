package org.wyc.wechat.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.wyc.wechat.message.TextMessage;

public class MySQLUtil {	
//	public static void main(String[]args) throws ParseException{
//	TextMessage a = new TextMessage();
//	a.setContent("123421");
//	a.setFromUserName("wzx");
//	a.setCreateTime(19912312);
//	MySQLUtil mysql = new MySQLUtil();
//	Connection conn = mysql.getConnection();
//	String sql = "select create_time from message_text";
//	PreparedStatement s = null;
//	ResultSet set = null;
//	try {
//		s = conn.prepareStatement(sql);
//		set = s.executeQuery();
//		while(set.next()){
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			java.util.Date date = dateFormat.parse(set.getString(1));
//			System.out.println(date.toString());
//			Date date1 = new Date(date.getTime());
//			String aa = dateFormat.format(date1);
//			System.out.println(aa);
//		}
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}finally{
//		mysql.releaseResultSet(set);
//		mysql.releaseStatement(s);
//		mysql.releaseConnection(conn);		
//	}
//		String s = "wyc";
//		MySQLUtil.saveWechatUser(s);
//		MySQLUtil.saveWechatSign(s, 2);
//		MySQLUtil.updateUserPoints(s, 2);
//		if(!MySQLUtil.isAllSignedWeekly(s, TimestampUtil.getMondayOfThisWeek())){
//			MySQLUtil.saveWechatSign(s, 12);
//			MySQLUtil.updateUserPoints(s, 12);
//		}
//		else{
//			System.out.println("未签到!");
//		}
//}

///**
// * 获取MySQL连接
// * @return 一个MySQL连接
// */
//public Connection getConnection(){
//	Connection connection = null;
//	String url = "jdbc:mysql://localhost:3306/wechat";
//	String username = "root";
//	String password = "";
//	try {
//		Class.forName("com.mysql.jdbc.Driver");
//		connection = DriverManager.getConnection(url,username,password);
//	} catch (ClassNotFoundException | SQLException e) {
//		e.printStackTrace();
//	} 
//	return connection;
//}
	
	/**
	 * 获取MySQL连接
	 * 
	 * @return 一个MySQL连接
	 */
	public Connection getConnection(){
		Connection connection = null;
		String url = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_wycwechat";
		String username = "jwz12213wk";
		String password = "jj1h13z0z312j03wh3lki1iz52k4i3wj1zym5h5w";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return connection;
	}
	
	/**
	 * 释放MySQL连接
	 * 
	 * @param connection 一个MySQL连接
	 */
	public void releaseConnection(Connection connection){
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 释放MySQL搜索结果
	 * 
	 * @param connection 一个MySQL搜索结果
	 */
	public void releaseResultSet(ResultSet resultSet){
		if(resultSet != null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 释放MySQL连接
	 * 
	 * @param statement 一个MySQL语句
	 */
	public void releaseStatement(Statement statement){
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveTextMessage(TextMessage textMessage){
		PreparedStatement ps = null;
		
		MySQLUtil mysql = new MySQLUtil();
		Connection connection = mysql.getConnection();
		
		String open_id = textMessage.getFromUserName();
		String content = textMessage.getContent();
		Timestamp timestamp = new Timestamp(textMessage.getCreateTime());
		
		String sql = "insert into message_text(open_id,content,create_time)values(?,?,?)";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, open_id);
			ps.setString(2, content);
			ps.setTimestamp(3, timestamp);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			mysql.releaseStatement(ps);
			mysql.releaseConnection(connection);
		}	
	}
	
	/**
	 * 保存用户信息,关注时间，关注状态进入数据库
	 * 
	 * @param openId 用户的openID
	 */
	public static void saveWechatUser(String openId){
		PreparedStatement ps = null;
		
		MySQLUtil mysql = new MySQLUtil();
		Connection connection = mysql.getConnection();
		
		String sql = "insert into wechat_user(open_id,subscribe_time,subscribe_status)values(?,now(),1)";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, openId);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			mysql.releaseStatement(ps);
			mysql.releaseConnection(connection);
		}
	}
	
	/**
	 * 保存签到信息,签到时间，签到积分进入数据库
	 * 
	 * @param openId 用户的openID
	 */
	public static void saveWechatSign(String openId, int signPoints){
		PreparedStatement ps = null;
		
		MySQLUtil mysql = new MySQLUtil();
		Connection connection = mysql.getConnection();
		
		String sql = "insert into wechat_sign(open_id,sign_time,sign_points)values(?,now(),?)";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, openId);
			ps.setInt(2, signPoints);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			mysql.releaseStatement(ps);
			mysql.releaseConnection(connection);
		}
	}
	
	/**
	 * 更新用户的总积分
	 * 
	 * @param openId 用户的openID
	 * @param signPoints 需要增加的积分
	 */
	public static void updateUserPoints(String openId, int signPoints){
		PreparedStatement ps = null;
		
		MySQLUtil mysql = new MySQLUtil();
		Connection connection = mysql.getConnection();
		
		String sql = "update wechat_user set points=points+? where open_id=?";
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, signPoints);
			ps.setString(2, openId);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			mysql.releaseStatement(ps);
			mysql.releaseConnection(connection);
		}
	}
	
	/**
	 * 判断今天是否已经签到过
	 * 
	 * @param openId 用户的openID
	 * @return 是否签到过,签到过返回true
	 */
	public static boolean isSignedToday(String openId){
		boolean isSignedToday = false;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		MySQLUtil mysql = new MySQLUtil();
		Connection connection = mysql.getConnection();
		
		String sql = "select count(*) as sign_count from wechat_sign where open_id=? and DATE_FORMAT(now(),'%Y-%m-%d')=DATE_FORMAT(sign_time,'%Y-%m-%d')";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, openId);
			rs = ps.executeQuery();
			
			while(rs.next()){
				int sign_count = rs.getInt("sign_count");
				if(sign_count == 1)
					isSignedToday = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			mysql.releaseResultSet(rs);
			mysql.releaseStatement(ps);
			mysql.releaseConnection(connection);
		}
		return isSignedToday;
	}
	
	/**
	 * 判断是否是一星期内第7次签到
	 * 
	 * @param openId 用户的openID
	 * @param monday 本周一的日期
	 * @return 是否是一星期内第7次签到，是返回true
	 */
	public static boolean isAllSignedWeekly(String openId, String monday){
		boolean isSignedToday = false;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		MySQLUtil mysql = new MySQLUtil();
		Connection connection = mysql.getConnection();
		
		String sql = "select count(*) as sign_count from wechat_sign where open_id=? and sign_time between str_to_date(?,'%Y-%d-%d %H:%i:%s') and now()";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, openId);
			ps.setString(2, monday);
			rs = ps.executeQuery();
			
			while(rs.next()){
				int sign_count = rs.getInt("sign_count");
				if(sign_count == 6)
					isSignedToday = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			mysql.releaseResultSet(rs);
			mysql.releaseStatement(ps);
			mysql.releaseConnection(connection);
		}
		return isSignedToday;
	}
}
