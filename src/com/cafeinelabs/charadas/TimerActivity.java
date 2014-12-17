package com.cafeinelabs.charadas;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.TextView;

import com.cafeinelabs.charadas.camera.ninja.CameraPreview;
import com.cafeinelabs.charadas.utils.AppConstants;

public class TimerActivity extends Activity {
	
	private Intent masterIntent;
	private CountDownTimer countDownTimer;
	private TextView time;
	private Context context;
	
	private CameraPreview camPreview;
	private int PreviewSizeWidth = 640;
 	private int PreviewSizeHeight= 480;
 	
 	private Handler mHandler = new Handler(Looper.getMainLooper());
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_timer);
		
		context = this;
		
		masterIntent = getIntent();
		
		time = (TextView) findViewById(R.id.time);
		
		SurfaceView camView = new SurfaceView(context);
        SurfaceHolder camHolder = camView.getHolder();
        camPreview = new CameraPreview(PreviewSizeWidth, PreviewSizeHeight);
        
        camHolder.addCallback(camPreview);
        camHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		mHandler.postDelayed(TakePicture, 300);
		
		countDownTimer = new CountDownTimer(6000, 1) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				long timeleft = millisUntilFinished/1000;
				time.setText(""+timeleft);
			}
			
			@Override
			public void onFinish() {
				playSound();
				Intent intent =  new Intent(context, PlayingActivity.class);
				String value = masterIntent.getStringExtra(AppConstants.UNIVERSAL_KEY);
				ArrayList<String> customTheme = masterIntent.getStringArrayListExtra(AppConstants.KEY_CUSTOM_THEME);
				if (customTheme != null) {
					intent.putStringArrayListExtra(AppConstants.KEY_CUSTOM_THEME, customTheme);
				} else {
					intent.putExtra(AppConstants.UNIVERSAL_KEY, value);
				}
				
				startActivity(intent);
				TimerActivity.this.finish();
			}
		}.start(); 
	}
	
	private void playSound(){
		MediaPlayer sound = MediaPlayer.create(this, R.raw.count_down_sound);
		sound.start();
		sound.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			};
		});
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (countDownTimer != null) {
			countDownTimer.cancel();
			countDownTimer = null;
		}
	}
	
	private Runnable TakePicture = new Runnable() {
    	String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
    	String charadasFolder = File.separator + "¡Charadas!"; 
		public void run() {
	        String myDirectory_path = extStorageDirectory+charadasFolder;
	        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss",Locale.US).format(new Date());
			
			File file = new File(myDirectory_path);
			if (!file.exists()) 
				file.mkdirs();
			String PictureFileName = myDirectory_path + File.separator +"IMG_"+timeStamp+".jpg";
			Log.e("PictureFileName",PictureFileName);

			camPreview.CameraTakePicture(PictureFileName);
		}
    };    
}
