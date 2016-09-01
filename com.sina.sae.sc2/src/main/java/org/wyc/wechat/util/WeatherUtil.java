package org.wyc.wechat.util;

import java.util.List;

import org.wyc.wechat.pojo.WeatherData;
import org.wyc.wechat.pojo.WeatherResp;
import org.wyc.wechat.pojo.WeatherResult;

import com.google.gson.Gson;

/**
 * 天气查询接口调用类(车联网API提供支持)
 * 
 * @author wyc
 */
public class WeatherUtil {
	/**
	 * 车联网天气查询调用接口URL地址
	 */
	private static final String WEATHER_URL = "http://api.map.baidu.com/telematics/v3/weather?location=LOCATION&output=json&ak=AK";

	/**
	 * 根据定位城市查询天气情况
	 * 
	 * @param location 定位城市
	 * @return 天气情况
	 */
	public static String getWeather(String location) {
		// 拼装请求地址
		String requestUrl = WEATHER_URL;
		requestUrl = requestUrl.replace("LOCATION", location);
		requestUrl = requestUrl.replace("AK", "nDV4eYup9GorvUZKhpaGsbn3KsGlorYF");

		// 发起请求调用接口
		String respJSON = HttpConnectionUtil.httpRequest(requestUrl, "GET", null);

		// 将JSON字符串转为WeatherResp对象
		Gson gson = new Gson();
		WeatherResp weatherResp = gson.fromJson(respJSON, WeatherResp.class);

		// 解析返回的结果,拼装天气数据报
		StringBuffer buffer = new StringBuffer();
		for (WeatherResult weatherResult : weatherResp.getResults()) {
			//定位城市
			String currentCity = weatherResult.getCurrentCity();
			buffer.append(currentCity).append("天气预报:").append("\n");
			//未来几天天气预报数据
			List<WeatherData> weatherDataList = weatherResult.getWeather_data();
			for(WeatherData weatherData: weatherDataList){
				buffer.append(weatherData.getDate()).append(": ");
				buffer.append(weatherData.getTemperature()).append(", ");
				buffer.append(weatherData.getWeather()).append(", ");
				buffer.append(weatherData.getWind()).append("\n\n");
			}
		}
		return buffer.toString();
	}

//	 public static void main(String[]args){
//	 System.out.println(getWeather("南通"));
//	 }
}
