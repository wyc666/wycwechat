package org.wyc.wechat.pojo;

import java.util.List;

public class WeatherResp {
	//错误类型
	private int error;
	//查询结果
	private String status;
	//当前日期
	private String date;
	//城市天气列表
	private List<WeatherResult> results;
	
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<WeatherResult> getResults() {
		return results;
	}
	public void setResults(List<WeatherResult> results) {
		this.results = results;
	}
}
