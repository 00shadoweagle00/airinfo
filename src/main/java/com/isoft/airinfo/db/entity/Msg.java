package com.isoft.airinfo.db.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * ͨ�õķ�����
 * @author ws
 *
 */
public class Msg {

	
	/**
	 * ״̬��
	 * 100:�ɹ�
	 * 200:ʧ��
	 */
	private int code;
	/**
	 * ��ʾ��Ϣ
	 */
	private String msg;
	/**
	 * �û�Ҫ���ظ������������
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
	 * ����ɹ�
	 * @return
	 */
	public static Msg success(){
		Msg msg = new Msg();
		msg.setCode(100);
		msg.setMsg("����ɹ�");
		return msg;
	}
	
	/**
	 * ����ʧ��
	 * @return
	 */
	public static Msg fail(){
		Msg msg = new Msg();
		msg.setCode(200);
		msg.setMsg("����ʧ��");
		return msg;
	}
	
	/**
	 * �Լ�ֵ���������
	 * @param key
	 * @param value
	 * @return
	 */
	public Msg add(String key, Object value) {
		this.getData().put(key, value);
		return this;
	} 
}
