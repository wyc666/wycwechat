package org.wyc.wechat.pojo;

public class Face {
	//被检验出来的人脸在Face++系统中唯一的标识符
	private String faceId;
	//年龄估计值
	private int age;
	//年龄估计区间
	private int ageRange;
	//性别分析
	private String gender;
	//性别分析准确度
	private double genderConfidence;
	//白/黄/黑种人分析
	private String race;
	//白/黄/黑种人分析准确度
	private double raceConfidence;
	//微笑程度分析值
	private double smilingValue;
	//人脸的中心点X坐标
	private double centerX;
	//人脸的中心点Y坐标
	private double centerY;
	
	public String getFaceId() {
		return faceId;
	}
	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(int ageRange) {
		this.ageRange = ageRange;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public double getGenderConfidence() {
		return genderConfidence;
	}
	public void setGenderConfidence(double genderConfidence) {
		this.genderConfidence = genderConfidence;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public double getRaceConfidence() {
		return raceConfidence;
	}
	public void setRaceConfidence(double raceConfidence) {
		this.raceConfidence = raceConfidence;
	}
	public double getSmilingValue() {
		return smilingValue;
	}
	public void setSmilingValue(double smilingValue) {
		this.smilingValue = smilingValue;
	}
	public double getCenterX() {
		return centerX;
	}
	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}
	public double getCenterY() {
		return centerY;
	}
	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}
}
