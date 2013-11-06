package com.k.feedback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.djun100.DataStructure.UtilTransList;
import com.djun100.System.UtilSystemInfo;
import com.hust.iprai.feedback.R;
import com.k.Http.UtilHttpPost;
import com.lurencun.cfuture09.androidkit.http.BaseParams;
import com.lurencun.cfuture09.androidkit.http.Http;
import com.ta.TASyncHttpClient;
import com.ta.util.TALogger;
import com.ta.util.http.AsyncHttpResponseHandler;
import com.ta.util.http.RequestParams;

public class FeedbackActivity extends Activity {
	Activity context=this;
	private EditText mContactEdit = null;
	private EditText mContentEdit = null;
	private ImageView mLeftBtn = null;
	private ImageView mRightBtn = null;
	private Button mSubmitBtn = null;
	String appVersionName;
	String postInfo[];
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_main);
		initData();
		initView();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN,
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private void initData() {
		// TODO Auto-generated method stub
		postInfo=new String[7];
		postInfo[0]=UtilSystemInfo.getAppVersionName(context);//appVersion
		postInfo[1]=UtilSystemInfo.VERSION_RELEASE;//appOs
		postInfo[2]=UtilSystemInfo.getScreenSize(context).x+"x"+UtilSystemInfo.getScreenSize(context).y;//deviceScreenSize
		postInfo[3]="";//moreInfo
		postInfo[4]="";//userId
		postInfo[5]="";//userComment
		postInfo[6]="5";//userRating
	}

	private void initView() {
    	mContactEdit = (EditText) findViewById(R.id.feedback_contact_edit);
    	mContentEdit = (EditText) findViewById(R.id.feedback_content_edit);
    	mLeftBtn = (ImageView) findViewById(R.id.left_btn);
    	mRightBtn = (ImageView) findViewById(R.id.right_btn);
    	mContentEdit.requestFocus();
    	
    	mLeftBtn.setVisibility(View.GONE);
    	mRightBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setClass(FeedbackActivity.this, FeedbackRecordActivity.class);
				startActivity(intent);
			}
		});
    	
    	mSubmitBtn = (Button) findViewById(R.id.submit_button);
    	mSubmitBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String content = mContentEdit.getText().toString().trim();

				String contact = mContactEdit.getText().toString().trim();
				String clientInfo="软件版本号："+UtilSystemInfo.getAppVersionName(context)+
						"  手机型号："+UtilSystemInfo.PRODUCT+
						"  系统版本："+UtilSystemInfo.VERSION_RELEASE;
				if (content.equals("")) {
					Toast.makeText(FeedbackActivity.this, R.string.request_content, Toast.LENGTH_SHORT).show();
					return;
				}				
				postInfo[5]=content;
				SendFeedbackTask task = new SendFeedbackTask(FeedbackActivity.this, postInfo);
				task.execute("");
				
			}
		});
    }

	private class SendFeedbackTask extends AsyncTask<Object, Object, Object> {

		private Context mContext = null;
		private String[] mContent ;
		private ProgressDialog mProgDialog = null;

		public SendFeedbackTask(Context context, String content[]) {
			mContext = context;
			mContent = content;
		}

		@Override
		protected Object doInBackground(Object... arg0) {
/*			BaseParams baseParams=new BaseParams();
			baseParams.put("appVersion", postInfo[0]);
			baseParams.put("appOs", postInfo[1]);
			baseParams.put("deviceScreenSize", postInfo[2]);
			baseParams.put("moreInfo", "HTTP");//上传乱码
			baseParams.put("userId", postInfo[4]);
			baseParams.put("userComment", postInfo[5]);
			baseParams.put("userRating", postInfo[6]);
			System.out.println( UtilTransList.transArrayToArrayList(postInfo).toString());
//			Log.i(tag, msg);
			try {
				Http.post(Variable.urlFeedback, baseParams);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestParams params = new RequestParams();//Ta库
			params.put("appVersion", postInfo[0]);
			params.put("appOs", postInfo[1]);
			params.put("deviceScreenSize", postInfo[2]);
			params.put("moreInfo", "TAsync");//传不上去
			params.put("userId", postInfo[4]);
			params.put("userComment", postInfo[5]);
			params.put("userRating", postInfo[6]);
			TASyncHttpClient taSyncHttpClient=new TASyncHttpClient();
			
			taSyncHttpClient.post(Variable.urlFeedback, new AsyncHttpResponseHandler()
		        {
		            @Override
		            public void onSuccess(String content)
		            {
		                // TODO Auto-generated method stub
		                super.onSuccess(content);
		                TALogger.d(context, content);
		            }

		            @Override
		            public void onStart()
		            {
		                // TODO Auto-generated method stub
		                super.onStart();
		            }

		            @Override
		            public void onFailure(Throwable error)
		            {
		                // TODO Auto-generated method stub
		                super.onFailure(error);
		            }

		            @Override
		            public void onFinish()
		            {
		                // TODO Auto-generated method stub
		                super.onFinish();
		            }

		        });*/
			
			Map<String,String> map=new HashMap<String,String>();
			map.put("appVersion", postInfo[0]);
			map.put("appOs", postInfo[1]);
			map.put("deviceScreenSize", postInfo[2]);
			map.put("moreInfo", "UtilHttpPost");
			map.put("userId", postInfo[4]);
			map.put("userComment", postInfo[5]);
			map.put("userRating", postInfo[6]);
			new UtilHttpPost(Variable.urlFeedback,map).post();
			
			return Integer.valueOf(new FeedbackAction(mContext)
					.sendFeedbackMessage(mContent));
			
		}

		@Override
		protected void onPostExecute(Object result) {
			if (mProgDialog != null) {
				mProgDialog.dismiss();
				
			}
			int resultCode = ((Integer) result).intValue();
			if (resultCode == 0) {
				Toast.makeText(FeedbackActivity.this,
						R.string.feedback_success, Toast.LENGTH_SHORT).show();
				FeedbackActivity.this.finish();
			} else {
				Toast.makeText(FeedbackActivity.this, R.string.feedback_failed,
						Toast.LENGTH_SHORT).show();
			}
			return;
		}

		@Override
		protected void onPreExecute() {
			mProgDialog = new ProgressDialog(FeedbackActivity.this);
			mProgDialog.setMessage(FeedbackActivity.this
					.getString(R.string.waiting));
			mProgDialog.setCancelable(false);
			mProgDialog.show();
		}

	}
}
