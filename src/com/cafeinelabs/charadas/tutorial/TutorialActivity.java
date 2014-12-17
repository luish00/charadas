package com.cafeinelabs.charadas.tutorial;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.cafeinelabs.charadas.R;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class TutorialActivity extends FragmentActivity {

	private ViewPager viewPager;
	private PageIndicator indicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tutorial);

		viewPager = (ViewPager) findViewById(R.id.tutorialViewPager);
		viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		indicator = (CirclePageIndicator) findViewById(R.id.tutorialIndicator);
		indicator.setViewPager(viewPager);
		
		Button backToMenuButton = (Button) findViewById(R.id.backToMenuTutorialButton);
		backToMenuButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				playSound();
				TutorialActivity.this.finish();
			}
		});
	}
	
	private void playSound(){
		MediaPlayer click = MediaPlayer.create(this, R.raw.button_click);
		click.start();
		click.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			switch(pos) {

			case 0: return TutorialFragment1.newInstance("");
			case 1: return TutorialFragment2.newInstance("");
			case 2: return TutorialFragment3.newInstance("");
			case 3: return TutorialFragment4.newInstance("");
			case 4: return TutorialFragment5.newInstance("");
			default: return TutorialFragment1.newInstance("");
			}
		}

		@Override
		public int getCount() {
			return 5;
		}       
	}
}
