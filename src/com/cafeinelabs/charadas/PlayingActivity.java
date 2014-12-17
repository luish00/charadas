package com.cafeinelabs.charadas;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cafeinelabs.charadas.db.Card;
import com.cafeinelabs.charadas.db.CardsDataSource;
import com.cafeinelabs.charadas.db.DbOpenHelper;
import com.cafeinelabs.charadas.utils.Accelerometer;
import com.cafeinelabs.charadas.utils.AppConstants;
import com.cafeinelabs.charadas.utils.SharedPreferencesManager;

public class PlayingActivity extends Activity {

	private LinearLayout rootLayout;
	private TextView tvRandomWord;
	private TextView tvTime;
	private ArrayList<String> words;

	private CardsDataSource dataSource;

	private CountDownTimer countDownTimer;
	private Accelerometer accelerometer;
	private Vibrator vibrator;

	boolean checkAnsware = false;

	private String masterKey;

	private String currentWord;

	private ArrayList<String> correctWords;
	private ArrayList<String> wrongWords;
	
	private SharedPreferencesManager mSharedPreferencesManager;
	
	private int gameTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_playing);
		
		mSharedPreferencesManager = new SharedPreferencesManager(this);

		gameTime = mSharedPreferencesManager.getGameTime();
		
		rootLayout = (LinearLayout) findViewById(R.id.rootViewPlaying);

		Button cancel = (Button) findViewById(R.id.cancelGame);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
				PlayingActivity.this.finish();
			}
		});

		tvRandomWord = (TextView) findViewById(R.id.randomWord);
		tvTime = (TextView) findViewById(R.id.textTime);

		dataSource = new CardsDataSource(this);
		dataSource.open();

		masterKey = getIntent().getStringExtra(AppConstants.UNIVERSAL_KEY);

		if (masterKey == null) {
			words = getIntent().getStringArrayListExtra(AppConstants.KEY_CUSTOM_THEME);
		} else {
			words = getArray(masterKey);
		}

		correctWords = new ArrayList<String>();
		wrongWords = new ArrayList<String>();

		setbackgroundColor(masterKey);

		accelerometer = new Accelerometer(this);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		setRandomWord(words);

		countDownTimer = new CountDownTimer(gameTime, 1) {

			@Override
			public void onTick(long millisUntilFinished) {
				tvTime.setText(""+millisUntilFinished/1000);
				answare();
			}

			@Override
			public void onFinish() {
				Toast.makeText(PlayingActivity.this, R.string._se_agoto_el_tiempo_, Toast.LENGTH_LONG).show();
				sendResults();
			}
		}.start();

	}

	private void sendResults(){
		Intent intent = new Intent(PlayingActivity.this, ResultsActivity.class);
		intent.putExtra(AppConstants.KEY_CORRECT_WORDS, correctWords);
		intent.putExtra(AppConstants.KEY_WRONG_WORDS, wrongWords);
		intent.putStringArrayListExtra(AppConstants.KEY_CUSTOM_THEME, words);
		startActivity(intent);
		PlayingActivity.this.finish();
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

	public ArrayList<String> getArray(String key){

		List<Card> cards = new ArrayList<Card>();
		ArrayList<String> words = new ArrayList<String>();

		switch (key) {
		case AppConstants.VAlUE_MOVIES:
			cards = dataSource.findAll(DbOpenHelper.TABLE_MOVIES);
			for (Card card : cards) {
				words.add(card.getTitle());
			}
			break;
		case AppConstants.VAlUE_COUNTRIES:
			cards = dataSource.findAll(DbOpenHelper.TABLE_COUNTRIES);
			for (Card card : cards) {
				words.add(card.getTitle());
			}
			break;
		case AppConstants.VAlUE_ANIMALS:
			cards = dataSource.findAll(DbOpenHelper.TABLE_ANIMALS);
			for (Card card : cards) {
				words.add(card.getTitle());
			}
			break;
		case AppConstants.VAlUE_CHARACTERS:
			cards = dataSource.findAll(DbOpenHelper.TABLE_CHARACTERS);
			for (Card card : cards) {
				words.add(card.getTitle());
			}
			break;
		case AppConstants.VAlUE_VIDEO_GAMES:
			cards = dataSource.findAll(DbOpenHelper.TABLE_VIDEO_GAMES);
			for (Card card : cards) {
				words.add(card.getTitle());
			}
			break;
		case AppConstants.VAlUE_ARTISTS:
			cards = dataSource.findAll(DbOpenHelper.TABLE_ARTISTS);
			for (Card card : cards) {
				words.add(card.getTitle());
			}
			break;
		case AppConstants.VAlUE_QUICK_PLAY:
			cards = dataSource.findAll(DbOpenHelper.TABLE_QUICK_PLAY);
			for (Card card : cards) {
				words.add(card.getTitle());
			}
			break;
		case AppConstants.VAlUE_ACT_IT_OUT:
			cards = dataSource.findAll(DbOpenHelper.TABLE_ACT_IT_OUT);
			for (Card card : cards) {
				words.add(card.getTitle());
			}
			break;
		case AppConstants.VAlUE_SUPER_HEROES:
			cards = dataSource.findAll(DbOpenHelper.TABLE_SUPER_HEROES);
			for (Card card : cards) {
				words.add(card.getTitle());
			}
			break;
		case AppConstants.VAlUE_TV_SHOWS:
			cards = dataSource.findAll(DbOpenHelper.TABLE_TV_SHOWS);
			for (Card card : cards) {
				words.add(card.getTitle());
			}
			break;
		case AppConstants.VAlUE_TV_SHOWS_FOR_KIDS:
			cards = dataSource.findAll(DbOpenHelper.TABLE_TV_SHOWS_FOR_KIDS);
			for (Card card : cards) {
				words.add(card.getTitle());
			}
			break;

		default:
			break;
		}
		return words;
	}

	private void setRandomWord(ArrayList<String> array){
		if (array.isEmpty()) {
			Toast.makeText(this, R.string._increible_nos_has_dejado_sin_palabras_, Toast.LENGTH_SHORT).show();
			sendResults();
		} else {
			int index = (int) (Math.random() * array.size());
			currentWord = array.get(index);
			tvRandomWord.setText(currentWord);
			array.remove(index);
		}
	}

	private void answare(){
		int curZ = (int) accelerometer.getCurZ();
		if (curZ > 7 && !checkAnsware) {
			playCorrectAnswareSound();
			vibrate();
			tvRandomWord.setText(R.string._correcto_);
			setBackgroundColorCorrect();
			checkAnsware = true;
			correctWords.add(currentWord);

		} else if (curZ < -8 && !checkAnsware) {
			playWrongAnswareSound();
			vibrate();
			tvRandomWord.setText(R.string._paso_);
			setBackgroundColorPass();
			checkAnsware = true;
			wrongWords.add(currentWord);
		} else if ((curZ < 3 && curZ > -3) && checkAnsware) {
			setRandomWord(words);
			setbackgroundColor(masterKey);
			checkAnsware = false;
		}
	}

	private void vibrate(){
		long[] pattern = {0, 100, 100, 100, 100};
		vibrator.vibrate(pattern, -1);
	}

	private void playCorrectAnswareSound(){
		MediaPlayer sound = MediaPlayer.create(this, R.raw.correct_answare_sound);
		sound.start();
		sound.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			};
		});
	}

	private void playWrongAnswareSound(){
		MediaPlayer sound = MediaPlayer.create(this, R.raw.wrong_answare_sound);
		sound.start();
		sound.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			};
		});
	}

	public void setBackgroundColorCorrect() {
		rootLayout.setBackgroundResource(R.color.correct);
	}

	public void setBackgroundColorPass() {
		rootLayout.setBackgroundResource(R.color.pass);
	}

	public void setbackgroundColor(String key) {
		if (key == null) {
			rootLayout.setBackgroundResource(R.color.background_blue);
		} else {
			switch (key) {
			case AppConstants.VAlUE_MOVIES:
				rootLayout.setBackgroundResource(R.color.turquoise);
				break;
			case AppConstants.VAlUE_COUNTRIES:
				rootLayout.setBackgroundResource(R.color.carrot);
				break;
			case AppConstants.VAlUE_ANIMALS:
				rootLayout.setBackgroundResource(R.color.alizarin);
				break;
			case AppConstants.VAlUE_CHARACTERS:
				rootLayout.setBackgroundResource(R.color.emerald);
				break;
			case AppConstants.VAlUE_VIDEO_GAMES:
				rootLayout.setBackgroundResource(R.color.peter_river);
				break;
			case AppConstants.VAlUE_ARTISTS:
				rootLayout.setBackgroundResource(R.color.peter_river);
				break;
			case AppConstants.VAlUE_QUICK_PLAY:
				rootLayout.setBackgroundResource(R.color.amethyst);
				break;
			case AppConstants.VAlUE_ACT_IT_OUT:
				rootLayout.setBackgroundResource(R.color.wet_asphalt);
				break;
			case AppConstants.VAlUE_SUPER_HEROES:
				rootLayout.setBackgroundResource(R.color.chambray);
				break;
			case AppConstants.VAlUE_TV_SHOWS:
				rootLayout.setBackgroundResource(R.color.mediumpurple);
				break;
			case AppConstants.VAlUE_TV_SHOWS_FOR_KIDS:
				rootLayout.setBackgroundResource(R.color.junglegreen);
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (countDownTimer != null) {
			countDownTimer.cancel();
			countDownTimer = null;
		}
	}
}
