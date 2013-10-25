package com.pwootage.pokedex;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by pwootage on 10/24/13.
 */
public class SuperSearchFragment extends Fragment {

	public static SuperSearchFragment newInstance() {
		SuperSearchFragment fragment = new SuperSearchFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public SuperSearchFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pokedex, container, false);
//		TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//		textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainPage) activity).onSectionAttached(1);
	}
}
