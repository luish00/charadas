package com.cafeinelabs.charadas;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cafeinelabs.charadas.utils.AppConstants;

public class ResultsActivity extends Activity {
	
	private ArrayList<String> correctWords;
	private ArrayList<String> wrongWords;
	private ArrayList<String> allWords;
	
	private TextView correctWordsTextView;
	private TextView wrongWordsTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_results);
		
		correctWordsTextView = (TextView) findViewById(R.id.correctWordsTextView);
		ListView correctsWordsListView = (ListView) findViewById(R.id.correctWordsListView);
		correctWords = getIntent().getStringArrayListExtra(AppConstants.KEY_CORRECT_WORDS);
		
		wrongWordsTextView = (TextView) findViewById(R.id.wrongWordsTextView);
		ListView wrongWordsListView = (ListView) findViewById(R.id.wrongWordsListView);
		wrongWords = getIntent().getStringArrayListExtra(AppConstants.KEY_WRONG_WORDS);

		displayResults(correctsWordsListView, correctWords, correctWordsTextView,
				wrongWordsListView, wrongWords, wrongWordsTextView);
		
		allWords = getIntent().getStringArrayListExtra(AppConstants.KEY_CUSTOM_THEME);
		
		Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
		playAgainButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				playSound();
				if (allWords.size() == 0 || allWords == null) {
					Toast.makeText(ResultsActivity.this, R.string.no_hay_palabras_para_un_nuevo_juego, Toast.LENGTH_LONG).show();
				} else {
					Intent intent = new Intent(ResultsActivity.this, TimerActivity.class);
					intent.putStringArrayListExtra(AppConstants.KEY_CUSTOM_THEME, allWords);
					startActivity(intent);
					ResultsActivity.this.finish();
				}
			}
		});
		
		Button backToMenuButton = (Button) findViewById(R.id.backButtonInResults);
		backToMenuButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				playSound();
				ResultsActivity.this.finish();
			}
		});
	}
	
	private void displayResults(ListView list1, ArrayList<String> array1, TextView textView1, 
			 ListView list2, ArrayList<String> array2, TextView textView2){
		
		if (array1 == null) {
			array1 = new ArrayList<String>();
		}
		
		if (array2 == null) {
			array2 = new ArrayList<String>();
		}
		
		if (array1.size() == 0 && array2.size() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
			.setMessage(R.string._que_paso_fue_mucho_para_ti_)
			.setPositiveButton(android.R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
		} else if (array1.size() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
			.setMessage(R.string._que_paso_ni_siquiera_una_palabra_)
			.setPositiveButton(android.R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
		} else if (array2.size() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
			.setMessage(R.string.increible_acertaste_a_todas_las_palabras_)
			.setPositiveButton(android.R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		
		ArrayAdapter<String> adapter1 = 
				new ArrayAdapter<String>(this, R.layout.list_item_correct_word, array1);
		list1.setAdapter(adapter1);
		ArrayAdapter<String> adapter2 = 
				new ArrayAdapter<String>(this, R.layout.list_item_wrong_word, array2);
		list2.setAdapter(adapter2);
		
		textView1.setText(getString(R.string.palabras_acertadas_)+array1.size());
		textView2.setText(getString(R.string.palabras_que_pasaste_)+array2.size());
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
}
