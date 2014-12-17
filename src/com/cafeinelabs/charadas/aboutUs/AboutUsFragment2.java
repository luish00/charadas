package com.cafeinelabs.charadas.aboutUs;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cafeinelabs.charadas.R;

public class AboutUsFragment2 extends Fragment {
	
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.about_us_fragment_2, container, false);
		context = rootView.getContext();
		
		View.OnClickListener clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.cafeinelabs.fl4ppymath"));
				startActivity(browserIntent);
			}
		};
		
		ImageView fl4ppyPromo = (ImageView) rootView.findViewById(R.id.fl4ppyLogo);
		fl4ppyPromo.setOnClickListener(clickListener);
		
		Button fl4ppyPromoButton = (Button) rootView.findViewById(R.id.fl4ppyMathPromoButton);
		fl4ppyPromoButton.setOnClickListener(clickListener);
		
		return rootView;
	}
	
	private void playSound(){
		MediaPlayer click = MediaPlayer.create(context, R.raw.button_click);
		click.start();
		click.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			};
		});
	}

	public static AboutUsFragment2 newInstance(String text) {

		AboutUsFragment2 f = new AboutUsFragment2();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}
}
