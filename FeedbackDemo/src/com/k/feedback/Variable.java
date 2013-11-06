package com.k.feedback;

public class Variable {
	/**
	 * 发送用户反馈      反馈需要用post方式发送
	 */
	public static final String urlFeedback = "http://192.168.1.15:8080/tvportal/feedback/add";
	/**
	 * 获取当天的反馈列表
	 */
	public static final String urlFeedbackList="http://192.168.1.15:8080/tvportal/feedback/today.json";
	/**
	 * 获取频道列表    后面数字1/20代表第一页，每页20条
	 */
	public static final String url="http://192.168.1.15:8080/tvportal/mobile/program/list/1/20";
}
