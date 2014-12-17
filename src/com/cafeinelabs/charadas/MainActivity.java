package com.cafeinelabs.charadas;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cafeinelabs.charadas.db.Category;
import com.cafeinelabs.charadas.db.CategoryDataSource;
import com.cafeinelabs.charadas.tutorial.TutorialActivity;
import com.cafeinelabs.charadas.utils.AppConstants;
import com.cafeinelabs.charadas.utils.ShakeDetector;
import com.cafeinelabs.charadas.utils.SharedPreferencesManager;

@SuppressLint({ "NewApi", "ResourceAsColor" })
public class MainActivity extends ActionBarActivity {

	private Context context;
	private SharedPreferencesManager mSharedPreferencesManager;
	private boolean theUserIsNew;

	private SensorManager mSensorManager;
	private ShakeDetector mShakeDetector;
	private Sensor mAccelerometer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_main);

		//Layout dinamico 1.0
		CategoryDataSource catDB = new CategoryDataSource(this);
		List<Category> cateries;
		catDB.open();
		cateries = catDB.findAll();
		catDB.close();
		//Dos renglones extras a las categorias para configuración, juego rapido, crear/borrar/ tutorial
		int contRow = ((int)(cateries.size()/2)); //Un renglon extra para NullPounterException
//		Log.e("contRow",contRow+"");   
		
		ScrollView scView = new ScrollView(this);
		LinearLayout lyContainer = new LinearLayout(this);
		GridLayout grLayout = new GridLayout(this);
		LinearLayout lyLogo = new LinearLayout(this);
		LinearLayout lyRowFirth = new LinearLayout(this);
		LinearLayout lyRowSeconds = new LinearLayout(this);
		TextView txtLogo = new TextView(this);
		TextView txtSlogan = new TextView(this);
		Button btnQuikPlay = new Button(this);
		Button btnCreate = new Button(this);
		Button btnHowUse = new Button(this);
		Button btnSetting = new Button(this);
		LayoutParams paramsLayout = new LayoutParams(
				LayoutParams.MATCH_PARENT, 
				getResources().getDimensionPixelSize(R.dimen.height_layout_btn),
				1f);
		LayoutParams paramsBtn = new LayoutParams(LayoutParams.MATCH_PARENT, 	LayoutParams.MATCH_PARENT, 1f);
		
		scView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		scView.setBackgroundColor(getResources().getColor(R.color.background_blue));
		lyContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)); lyContainer.setOrientation(LinearLayout.VERTICAL);
		grLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//       grLayout.setRowCount(contRow);
        grLayout.setOrientation(LinearLayout.VERTICAL);
		
        //btn/layout default
        int whiteColor = getResources().getColor(R.color.white);
        lyLogo.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)); 
        lyLogo.setOrientation(LinearLayout.VERTICAL);
        txtLogo.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)); 
        txtSlogan.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//		//Set Typeface to the logo and slogan
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/junegull.ttf");
		txtLogo.setTypeface(typeface);
		txtLogo.setText(R.string.logo);
		txtLogo.setGravity(Gravity.CENTER);
		txtLogo.setTextColor(whiteColor);
		txtLogo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 120);
		txtSlogan.setTypeface(typeface);
		txtSlogan.setText(R.string.slogan);
		txtSlogan.setGravity(Gravity.CENTER);
		txtSlogan.setTextColor(whiteColor);
		txtSlogan.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        lyLogo.addView(txtLogo);
        lyLogo.addView(txtSlogan);
        lyContainer.addView(lyLogo);
        btnQuikPlay.setLayoutParams(paramsBtn);
        btnQuikPlay.setText(getString(R.string.juego_r_pido));
        btnQuikPlay.setTextColor(whiteColor);
        btnQuikPlay.setBackgroundDrawable( (Drawable) getResources().getDrawable(R.drawable.btn_quickplay)); 
        btnCreate.setLayoutParams(paramsBtn);
        btnCreate.setText(getString(R.string.crear_borrar));
        btnCreate.setTextColor(whiteColor);
        btnCreate.setBackgroundDrawable( (Drawable) getResources().getDrawable(R.drawable.btn_create));
        
        btnCreate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent makeCardintent = new Intent(MainActivity.this, MakeCardListActivity.class);
				startActivity(makeCardintent);
			}
		});
        
        btnHowUse.setLayoutParams(paramsBtn);
        btnHowUse.setText(getString(R.string.como_jugar));
        btnHowUse.setTextColor(whiteColor);
        btnHowUse.setBackgroundDrawable( (Drawable) getResources().getDrawable(R.drawable.btn_artists));
        
        btnHowUse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent makeCardintent = new Intent(MainActivity.this, TutorialActivity.class);
				startActivity(makeCardintent);
			}
		});
        
        btnSetting.setLayoutParams(paramsBtn);
        btnSetting.setText(getString(R.string.configuraci_n));
        btnSetting.setTextColor(whiteColor);
        btnSetting.setBackgroundDrawable( (Drawable) getResources().getDrawable(R.drawable.btn_configuration));
        
        btnSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent makeCardintent = new Intent(MainActivity.this, ConfigurationActivity.class);
				startActivity(makeCardintent);
			}
		});
        
        lyRowFirth.setLayoutParams(paramsLayout);
        lyRowSeconds.setLayoutParams(paramsLayout);
        lyRowFirth.addView(btnQuikPlay);
        lyRowFirth.addView(btnCreate);
        lyRowSeconds.addView(btnHowUse);
        lyRowSeconds.addView(btnSetting);
        grLayout.addView(lyRowFirth, 0);
        grLayout.addView(lyRowSeconds, 1);
        

        int cont=2;
//        grLayout.addView(titleView);
        final Intent intent = new Intent(MainActivity.this, TimerActivity.class);
        
	   for(int i=0; i<cateries.size(); i+=2){
		   LinearLayout lyRow = new LinearLayout(this);
		   lyRow.setLayoutParams(paramsLayout);
		   
		   Button btn1 = new Button(this);
		   btn1.setLayoutParams(paramsBtn);
		   btn1.setText(cateries.get(i).getName());
		   btn1.setTextColor(whiteColor);
		   
		   if(cateries.get(i).getName().equals("xxx")){
			   btn1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					 intent.putExtra(AppConstants.UNIVERSAL_KEY, AppConstants.VAlUE_TV_SHOWS_FOR_KIDS);
					 startActivity(intent);
					
				}
			});
			   
		   }

		   lyRow.addView(btn1);
		   
		   if(i!=cateries.size()-1){
			   Button btn2 = new Button(this);
			   btn2.setLayoutParams(paramsBtn);
			   btn2.setText(cateries.get(i+1).getName());
			   btn2.setTextColor(whiteColor);
			   lyRow.addView(btn2);
			   
		   }else{
			   Space space = new Space(this);
			   space.setLayoutParams(paramsBtn);
			   lyRow.addView(space);
		   }
		   
		   grLayout.addView(lyRow, cont);
		   cont++;
	   }
	   
	   //grLayout.addView(lyRowEnds, cont+1);//Agregar un renglon más
	   lyContainer.addView(grLayout);
	   scView.addView(lyContainer);
	   setContentView(scView);
		//en layout dinamico 1.0
	   
	   context = this;

		mSharedPreferencesManager = new SharedPreferencesManager(context);
		theUserIsNew = mSharedPreferencesManager.getWelcomeScreenState();

//		//Check if the user is new and show the welcome screen

		if (theUserIsNew) {
			View welcomScreen = getLayoutInflater().inflate(R.layout.welcome_screen_dialog, null);
			final AlertDialog welcomScreenDialog = new AlertDialog.Builder(context)
			.setView(welcomScreen)
			.create();

			Button accept = (Button) welcomScreen.findViewById(R.id.welcomeDialogYesButton);
			accept.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					playSound();
					mSharedPreferencesManager.saveWelcomeScreenState(false);
					welcomScreenDialog.dismiss();
				}
			});

			Button cancel = (Button) welcomScreen.findViewById(R.id.welcomeDialogCancelButton);
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					playSound();
					mSharedPreferencesManager.saveWelcomeScreenState(false);
					welcomScreenDialog.dismiss();
					Intent intent = new Intent(context, TutorialActivity.class);
					startActivity(intent);
				}
			});

			welcomScreenDialog.show();
		}
//
//		//Set Typeface to the logo and slogan
//		TextView logo = (TextView) findViewById(R.id.logo);
//		TextView slogan = (TextView) findViewById(R.id.slogan);
//		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/junegull.ttf");
//		logo.setTypeface(typeface);
//		slogan.setTypeface(typeface);
//
//		//The click listener
//		View.OnClickListener clickListener = new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				playSound();
//
//				String tag = (String) v.getTag();
//
//				Intent intent = new Intent(context, TimerActivity.class);
//
//				switch (tag) {
//
//				case AppConstants.VAlUE_QUICK_PLAY:
//					intent.putExtra(AppConstants.UNIVERSAL_KEY, tag);
//					startActivity(intent);
//					break;
//
//				case AppConstants.VALUE_CREATE_DELETE:
//					Intent makeCardintent = new Intent(context, MakeCardListActivity.class);
//					startActivity(makeCardintent);
//					break;
//
//				case AppConstants.VAlUE_MOVIES:
//					intent.putExtra(AppConstants.UNIVERSAL_KEY, tag);
//					startActivity(intent);
//					break;
//
//				case AppConstants.VAlUE_CHARACTERS:
//					intent.putExtra(AppConstants.UNIVERSAL_KEY, tag);
//					startActivity(intent);
//					break;
//
//				case AppConstants.VAlUE_VIDEO_GAMES:
//					intent.putExtra(AppConstants.UNIVERSAL_KEY, tag);
//					startActivity(intent);
//					break;
//
//				case AppConstants.VAlUE_COUNTRIES:
//					intent.putExtra(AppConstants.UNIVERSAL_KEY, tag);
//					startActivity(intent);
//					break;
//
//				case AppConstants.VAlUE_ANIMALS:
//					intent.putExtra(AppConstants.UNIVERSAL_KEY, tag);
//					startActivity(intent);
//					break;
//
//				case AppConstants.VAlUE_ARTISTS:
//					intent.putExtra(AppConstants.UNIVERSAL_KEY, tag);
//					startActivity(intent);
//					break;
//
//				case AppConstants.VAlUE_ACT_IT_OUT:
//					intent.putExtra(AppConstants.UNIVERSAL_KEY, tag);
//					startActivity(intent);
//					break;
//
//				case AppConstants.VAlUE_SUPER_HEROES:
//					intent.putExtra(AppConstants.UNIVERSAL_KEY, tag);
//					startActivity(intent);
//					break;
//
//				case AppConstants.VAlUE_TV_SHOWS:
//					intent.putExtra(AppConstants.UNIVERSAL_KEY, tag);
//					startActivity(intent);
//					break;
//
//				case AppConstants.VAlUE_TV_SHOWS_FOR_KIDS:
//					intent.putExtra(AppConstants.UNIVERSAL_KEY, tag);
//					startActivity(intent);
//					break;
//
//				case AppConstants.VALUE_HOW_TO_PLAY:
//					Intent tutorialIntent = new Intent(context, TutorialActivity.class);
//					startActivity(tutorialIntent);
//					break;
//
//				case AppConstants.VALUE_CONFIGURATION:
//					Intent configurationIntent = new Intent(context, ConfigurationActivity.class);
//					startActivity(configurationIntent);
//					break;
//
//				default:
//					break;
//				}
//			}
//		};
//
////		//Buttons
//
//		Button btnQuickPlay = (Button) findViewById(R.id.btnQuickPlay);
//		btnQuickPlay.setTypeface(typeface);
//		btnQuickPlay.setTag(AppConstants.VAlUE_QUICK_PLAY);
//		btnQuickPlay.setOnClickListener(clickListener);
//
//		Button btnCreate_delete = (Button) findViewById(R.id.btnCreate_delete);
//		btnCreate_delete.setTypeface(typeface);
//		btnCreate_delete.setTag(AppConstants.VALUE_CREATE_DELETE);
//		btnCreate_delete.setOnClickListener(clickListener);
//
//		Button btnMovies = (Button) findViewById(R.id.btnMovies);
//		btnMovies.setTypeface(typeface);
//		btnMovies.setTag(AppConstants.VAlUE_MOVIES);
//		btnMovies.setOnClickListener(clickListener);
//
//		Button btnCharacters = (Button) findViewById(R.id.btnCharacters);
//		btnCharacters.setTypeface(typeface);
//		btnCharacters.setTag(AppConstants.VAlUE_CHARACTERS);
//		btnCharacters.setOnClickListener(clickListener);
//
//		Button btnVideoGames = (Button) findViewById(R.id.btnVideoGames);
//		btnVideoGames.setTypeface(typeface);
//		btnVideoGames.setTag(AppConstants.VAlUE_VIDEO_GAMES);
//		btnVideoGames.setOnClickListener(clickListener);
//
//		Button btnCountries = (Button) findViewById(R.id.btnCountries);
//		btnCountries.setTypeface(typeface);
//		btnCountries.setTag(AppConstants.VAlUE_COUNTRIES);
//		btnCountries.setOnClickListener(clickListener);
//
//		Button btnAnimals = (Button) findViewById(R.id.btnAnimals);
//		btnAnimals.setTypeface(typeface);
//		btnAnimals.setTag(AppConstants.VAlUE_ANIMALS);
//		btnAnimals.setOnClickListener(clickListener);
//
//		Button btnArtists = (Button) findViewById(R.id.btnArtists);
//		btnArtists.setTypeface(typeface);
//		btnArtists.setTag(AppConstants.VAlUE_ARTISTS);
//		btnArtists.setOnClickListener(clickListener);
//
//		Button btnActItOut = (Button) findViewById(R.id.btnAct);
//		btnActItOut.setTypeface(typeface);
//		btnActItOut.setTag(AppConstants.VAlUE_ACT_IT_OUT);
//		btnActItOut.setOnClickListener(clickListener);
//
//		Button btnSuperHeroes = (Button) findViewById(R.id.btnSuperHeroes);
//		btnSuperHeroes.setTypeface(typeface);
//		btnSuperHeroes.setTag(AppConstants.VAlUE_SUPER_HEROES);
//		btnSuperHeroes.setOnClickListener(clickListener);
//
//		Button btnTvShows = (Button) findViewById(R.id.btnTvShows);
//		btnTvShows.setTypeface(typeface);
//		btnTvShows.setTag(AppConstants.VAlUE_TV_SHOWS);
//		btnTvShows.setOnClickListener(clickListener);
//
//		Button btnTvShowsForKids = (Button) findViewById(R.id.btnTvShowsForKids);
//		btnTvShowsForKids.setTypeface(typeface);
//		btnTvShowsForKids.setTag(AppConstants.VAlUE_TV_SHOWS_FOR_KIDS);
//		btnTvShowsForKids.setOnClickListener(clickListener);
//
//		Button btnHowToPlay = (Button) findViewById(R.id.btnHowToPlay);
//		btnHowToPlay.setTypeface(typeface);
//		btnHowToPlay.setTag(AppConstants.VALUE_HOW_TO_PLAY);
//		btnHowToPlay.setOnClickListener(clickListener);
//
//		Button btnConfiguration = (Button) findViewById(R.id.btnConfiguration);
//		btnConfiguration.setTypeface(typeface);
//		btnConfiguration.setTag(AppConstants.VALUE_CONFIGURATION);
//		btnConfiguration.setOnClickListener(clickListener);

//		//Acelerometer, ShakeDetector and SensorManager
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

//		mShakeDetector = new ShakeDetector(new OnShakeListener() {
//
//			@Override
//			public void onShake() {
//				Intent intent = new Intent(MainActivity.this, TimerActivity.class);
//				intent.putExtra(AppConstants.UNIVERSAL_KEY, AppConstants.VAlUE_QUICK_PLAY);
//				startActivity(intent);
//			}
//		});

	}

//	//The click sound
	private void playSound(){
		MediaPlayer click = MediaPlayer.create(context, R.raw.button_click);
		click.start();
		click.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}
}
