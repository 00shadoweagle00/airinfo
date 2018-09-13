package com.isoft.airinfo.db.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回类
 * @author ws
 *
 */
public class Msg {

	
	/**
	 * 状态码
	 * 100:成功
	 * 200:失败
	 */
	private int code;
	/**
	 * 提示信息
	 */
	private String msg;
	/**
	 * 用户要返回给浏览器的数据
	 */
	private Map<String, Object> data = new HashMap<String,Object>();
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	/**
	 * 处理成功
	 * @return
	 */
	public static Msg success(){
		Msg msg = new Msg();
		msg.setCode(100);
		msg.setMsg("处理成功");
		return msg;
	}
	
	/**
	 * 处理失败
	 * @return
	 */
	public static Msg fail(){
		Msg msg = new Msg();
		msg.setCode(200);
		msg.setMsg("处理失败");
		return msg;
	}
	
	/**
	 * 以键值对添加数据
	 * @param key
	 * @param value
	 * @return
	 */
	public Msg add(String key, Object value) {
		this.getData().put(key, value);
		return this;
	} 
}
