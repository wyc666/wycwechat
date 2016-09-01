package org.wyc.wechat.util;

import java.util.ArrayList;
import java.util.List;

import org.wyc.wechat.pojo.Face;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Face++接口工具类
 * 
 * @author wyc
 */
public class FacePlusPlusUtil {
	public static void main(String[] args){
		System.out.println(FacePlusPlusUtil.detectFace("http://c.hiphotos.baidu.com/image/h%3D200/sign=89c6743711ce36d3bd0484300af23a24/ae51f3deb48f8c54469d4dc23e292df5e1fe7f95.jpg"));
	}
	/**
	 * API接口URL地址
	 */
	private static final String FACE_DETECT_URL = "http://apicn.faceplusplus.com/v2/detection/detect?api_key=YOUR_API_KEY&api_secret=YOUR_API_SECRET&url=URL&attribute=glass,pose,gender,age,race,smiling";
	
	/**
	 * 识别人脸(提供给外部调用)
	 * @param imageUrl 待识别的图片地址
	 * @return 年龄|性别|种族|微笑程度
	 */
	public static String detectFace(String imageUrl){
		//拼装请求地址
		String requestUrl = FACE_DETECT_URL;
		requestUrl = requestUrl.replace("YOUR_API_KEY", "7d3916f70c80e4aa2ed3bec37b13d677");
		requestUrl = requestUrl.replace("YOUR_API_SECRET", "mEJqaKftusjFIk6JVioxrlIHTC1oBCwC");
		requestUrl = requestUrl.replace("URL", CommonUtil.urlEncodeUTF8(imageUrl));
		
		//发起请求
		String respJSON = HttpConnectionUtil.httpRequest(requestUrl, "GET", null);
		System.out.println(respJSON);
		
		//解析对象
		JSONArray faceArray = (JSONArray) JSONObject.fromObject(respJSON).get("face");
		List<Face> faceList = new ArrayList<Face>();
		for(int i=0;i<faceArray.size();i++){
			JSONObject faceObject = faceArray.getJSONObject(i);
			JSONObject attrObject = faceObject.getJSONObject("attribute");
			JSONObject posObject = faceObject.getJSONObject("position");
			
			Face face = new Face();
			face.setFaceId(faceObject.getString("face_id"));
			face.setAge(attrObject.getJSONObject("age").getInt("value"));
			face.setAgeRange(attrObject.getJSONObject("age").getInt("range"));
			face.setGender(attrObject.getJSONObject("gender").getString("value"));
			face.setGenderConfidence(attrObject.getJSONObject("gender").getDouble("confidence"));
			face.setRace(attrObject.getJSONObject("race").getString("value"));
			face.setRaceConfidence(attrObject.getJSONObject("race").getDouble("confidence"));
			face.setSmilingValue(attrObject.getJSONObject("smiling").getDouble("value"));
			face.setCenterX(posObject.getJSONObject("center").getDouble("x"));
			face.setCenterY(posObject.getJSONObject("center").getDouble("y"));
			
			faceList.add(face);
		}
		
		StringBuffer buffer = new StringBuffer();
		//检测到一张人脸
		if(faceList.size() == 1){
			buffer.append("共检测到").append(faceList.size()).append("张人脸").append("\n");
		}
		//检测到多张人脸
		else if(faceList.size() >= 1){
			buffer.append("共检测到").append(faceList.size()).append("张人脸").append("\n");
			buffer.append("从左到右依次为:").append("\n");
		}
		//遍历检测到的所有人脸
		for(Face face: faceList){
			buffer.append("肤色为: ").append(face.getRace());
			buffer.append(",性别为: ").append(face.getGender());
			buffer.append(",年龄约为: ").append(face.getAge());
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
