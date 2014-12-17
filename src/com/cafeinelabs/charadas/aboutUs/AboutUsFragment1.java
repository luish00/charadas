package com.cafeinelabs.charadas.aboutUs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cafeinelabs.charadas.R;

public class AboutUsFragment1 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,  Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.about_us_fragment_1, container, false);
		ImageView cafeineLogo = (ImageView) rootView.findViewById(R.id.cafeinelabsLogo);
		cafeineLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cafeinelabs.com"));
				startActivity(browserIntent);
			}
		});
		return rootView;
	}

	public static AboutUsFragment1 newInstance(String text) {

		AboutUsFragment1 f = new AboutUsFragment1();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}
}
