package org.wyc.wechat.pojo;

import java.util.List;

public class WeatherResult {
	//定位城市
	private String currentCity;
	//PM2.5的值
	private String pm25;
	//天气状况列表
	private List<WeatherData> weather_data;
	
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public List<WeatherData> getWeather_data() {
		return weather_data;
	}
	public void setWeather_data(List<WeatherData> weather_data) {
		this.weather_data = weather_data;
	}
}
