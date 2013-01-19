package com.baidu.locSDK.test;

//import com.baidu.locTest.Location;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.*;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Process;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class mainActivity extends Activity {

	private TextView mTv = null;
	private EditText mSpanEdit;
	private EditText mAddrEdit;
	private EditText mCoorEdit;
	private CheckBox mGpsCheck;
	private Button   mStartBtn;
	private Button	 mSetBtn;
	private Button 	 mLocBtn;
	private Button 	 mPoiBtn;
	private Button 	 mOffLineBtn;
	private boolean  mIsStart;
	private static int count = 1;
	private Vibrator mVibrator01 =null;
	private LocationClient mLocClient;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		mTv = (TextView)findViewById(R.id.textview);
		mSpanEdit = (EditText)findViewById(R.id.edit);
		mCoorEdit = (EditText)findViewById(R.id.coorEdit);
		mAddrEdit = (EditText)findViewById(R.id.addrEdit);
		mGpsCheck = (CheckBox)findViewById(R.id.gpsCheck);
		mStartBtn = (Button)findViewById(R.id.StartBtn);
		mLocBtn = (Button)findViewById(R.id.locBtn);
		mSetBtn = (Button)findViewById(R.id.setBtn);       
		mPoiBtn = (Button)findViewById(R.id.PoiReq);
		mOffLineBtn = (Button)findViewById(R.id.offLineLocation);
		mIsStart = false;
	
		mLocClient = ((Location)getApplication()).mLocationClient;
		((Location)getApplication()).mTv = mTv;
		mVibrator01 =(Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
		((Location)getApplication()).mVibrator01 = mVibrator01;
		//��ʼ/ֹͣ��ť
		mStartBtn.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mIsStart) {
					setLocationOption();
					mLocClient.start();
					mStartBtn.setText("ֹͣ");
					mIsStart = true;
					
				} else {
					mLocClient.stop();
					mIsStart = false;
					mStartBtn.setText("��ʼ");
				} 
				Log.d("locSDK_Demo1", "... mStartBtn onClick... pid="+Process.myPid()+" count="+count++);
			}
		});

		//��λ��ť
		mLocBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mLocClient != null && mLocClient.isStarted()){
					setLocationOption();
					mLocClient.requestLocation();	
				}				
				else 
					Log.d("boot", "locClient is null or not started");
				Log.d("locSDK_Demo1", "... mlocBtn onClick... pid="+Process.myPid()+" count="+count++);
			}
		});

		//���ð�ť
		mSetBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLocationOption();
			}
		});

		mPoiBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mLocClient.requestPoi();
			}
		});    

		mOffLineBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mLocClient.requestOfflineLocation();
			}
		});    
	}   

	@Override
	public void onDestroy() {
		mLocClient.stop();
		((Location)getApplication()).mTv = null;
		super.onDestroy();
	}

	//������ز���
	private void setLocationOption(){
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(mGpsCheck.isChecked());				//��gps
		option.setCoorType(mCoorEdit.getText().toString());		//������������
		option.setAddrType(mAddrEdit.getText().toString());		//���õ�ַ��Ϣ��������Ϊ��all��ʱ�е�ַ��Ϣ��Ĭ���޵�ַ��Ϣ
		option.setScanSpan(Integer.parseInt(mSpanEdit.getText().toString()));	//���ö�λģʽ��С��1����һ�ζ�λ;���ڵ���1����ʱ��λ
		mLocClient.setLocOption(option);
	}

}