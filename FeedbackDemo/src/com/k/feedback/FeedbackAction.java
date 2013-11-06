package com.k.feedback;

import android.content.Context;

public class FeedbackAction {
	
	private Context mContext = null;
	private static final int SUCCESS = 0;
	private static final int FAILURE = 1;
	
	public FeedbackAction(Context context) {
		mContext = context;
	}

	/**
	 * 此处用于将反馈信息发送给服务器
	 * @param mContent
	 * @param contact
	 * @return
	 */
	public int sendFeedbackMessage(String[] mContent) {
		return SUCCESS;
	}

}
