package com.cafeinelabs.charadas.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
	
	private static final String PREF_NAME = "sharedPreferencesManager";
	private static final String WELCOMESCREEN = "welcomeScreen";
	private static final String TIME = "TIME";
	private SharedPreferences mSharedPreferences;
	
	public SharedPreferencesManager(Context context){
		mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}
	
	public void saveWelcomeScreenState(boolean isNewUser){
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putBoolean(WELCOMESCREEN, isNewUser);
		editor.commit();
	}
	
	public boolean getWelcomeScreenState(){
		boolean welcomeScreenState = mSharedPreferences.getBoolean(WELCOMESCREEN, true);
		return welcomeScreenState;
	}
	
	public void saveGameTime(int time){
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(TIME, time);
		editor.commit();
	}
	public int getGameTime(){
		int Time = mSharedPreferences.getInt(TIME, 60);
		return Time;
	}
}
