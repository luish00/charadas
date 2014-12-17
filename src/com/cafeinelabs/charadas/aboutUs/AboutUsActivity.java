package com.cafeinelabs.charadas.aboutUs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.cafeinelabs.charadas.R;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class AboutUsActivity extends FragmentActivity {

	private ViewPager viewPager;
	private PageIndicator indicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about_us);

		viewPager = (ViewPager) findViewById(R.id.aboutUsViewPager);
		viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		indicator = (CirclePageIndicator) findViewById(R.id.aboutUsindicator);
		indicator.setViewPager(viewPager);
	}
	
	private class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			switch(pos) {

			case 0: return AboutUsFragment1.newInstance("");
			case 1: return AboutUsFragment2.newInstance("");
			default: return AboutUsFragment1.newInstance("");
			}
		}

		@Override
		public int getCount() {
			return 2;
		}       
	}
}

