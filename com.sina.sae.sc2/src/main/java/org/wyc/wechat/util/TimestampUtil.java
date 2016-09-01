package org.wyc.wechat.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimestampUtil {
	public static void main(String[]args){
		System.out.println(getMondayOfThisWeek());
	}
	
	/**
	 * 返回这个星期一的日期
	 * 
	 * @return 星期一日期 (yyyy--MM--dd 00:00:00)
	 */
	public static String getMondayOfThisWeek(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar calendar = Calendar.getInstance();
		//得到今天的星期数
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
		//判断是否是星期天
		if(dayOfWeek == 0){
			dayOfWeek = 7;
		}
		calendar.add(Calendar.DATE, 1-dayOfWeek);
		return dateFormat.format(calendar.getTime());
	}
}
