package com.cafeinelabs.charadas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cafeinelabs.charadas.aboutUs.AboutUsActivity;
import com.cafeinelabs.charadas.utils.SharedPreferencesManager;

public class ConfigurationActivity extends Activity {
	
	private int gameTime = 0;
	private SharedPreferencesManager mSharedPreferencesManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_configuration);
		
		mSharedPreferencesManager = new SharedPreferencesManager(this);

		Button gameTimeButton = (Button) findViewById(R.id.configurationTimeButton);
		gameTimeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
				showGameTimeDialog();
			}
		});

		Button aboutUsButton = (Button) findViewById(R.id.aboutUsButton);
		aboutUsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
				Intent intent = new Intent(ConfigurationActivity.this, AboutUsActivity.class);
				startActivity(intent);
			}
		});
		
		Button backToMenuButton = (Button) findViewById(R.id.backMenuButton);
		backToMenuButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				playSound();
				ConfigurationActivity.this.finish();
			}
		});
	}

	private void playSound(){
		MediaPlayer click = MediaPlayer.create(this, R.raw.button_click);
		click.start();
		click.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			};
		});
	}
	
	private void showGameTimeDialog(){
		LayoutInflater inflater = LayoutInflater.from(this);
		View gameTimeDialog = inflater.inflate(R.layout.set_game_time_dialog, null);
		
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setView(gameTimeDialog);

		Button acceptButton = (Button) gameTimeDialog.findViewById(R.id.timeDialogAceptButton);
		acceptButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
				mSharedPreferencesManager.saveGameTime(gameTime);
				dialog.dismiss();
				Toast.makeText(ConfigurationActivity.this, ""+gameTime, Toast.LENGTH_LONG).show();
			}
		});

		Button cancelButton = (Button) gameTimeDialog.findViewById(R.id.timeDialogCancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
				dialog.dismiss();
			}
		});

		dialog.show();
	}
	
	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch(view.getId()) {
		case R.id.radio_10:
			if (checked){
				gameTime = 10000;
			}
			break;
		case R.id.radio_20:
			if (checked){
				gameTime = 20000;
			}
			break;
		case R.id.radio_30:
			if (checked){
				gameTime = 30000;
			}
			break;
		case R.id.radio_40:
			if (checked){
				gameTime = 40000;
			}
			break;
		case R.id.radio_50:
			if (checked){
				gameTime = 50000;
			}
			break;
		case R.id.radio_60:
			if (checked){
				gameTime = 60000;
			}
		case R.id.radio_80:
			if (checked) {
				gameTime = 80000;
			}
		case R.id.radio_100:
			if (checked) {
				gameTime = 100000;
			}
		case R.id.radio_120:
			if (checked) {
				gameTime = 120000;
			}
		default:
			gameTime = 60000;
			break;
		}
	}
}
