package com.cafeinelabs.charadas.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cafeinelabs.charadas.R;

public class TutorialFragment5 extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,  Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.tutorial_fragment_5, container, false);
		
		return rootView;
	}

	public static TutorialFragment5 newInstance(String text) {

		TutorialFragment5 f = new TutorialFragment5();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

}
