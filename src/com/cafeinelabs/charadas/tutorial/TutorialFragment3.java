package com.cafeinelabs.charadas.tutorial;

import com.cafeinelabs.charadas.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TutorialFragment3 extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,  Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.tutorial_fragment_3, container, false);
		
		return rootView;
	}

	public static TutorialFragment3 newInstance(String text) {
 
		TutorialFragment3 f = new TutorialFragment3();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

}
