package com.cafeinelabs.charadas.tutorial;

import com.cafeinelabs.charadas.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TutorialFragment1 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,  Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.tutorial_fragment_1, container, false);
		
		return rootView;
	}

	public static TutorialFragment1 newInstance(String text) {

		TutorialFragment1 f = new TutorialFragment1();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

}
