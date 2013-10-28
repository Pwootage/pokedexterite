package com.pwootage.pokedex;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pwootage.pokedex.utils.QueryHelper;
import com.pwootage.pokedex.utils.VeekunDBHelper;

/**
 * Created by pwootage on 10/24/13.
 */
public class SuperSearchFragment extends Fragment {
    private static final String t = VeekunDBHelper.class.getName();
    private TextWatcher tw;

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
        tw = new SuperSearchListener();
        EditText edit = (EditText) rootView.findViewById(R.id.super_search_box);
        edit.addTextChangedListener(tw);
//		TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//		textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainPage) activity).onSectionAttached(1);
    }

    private class SuperSearchListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            SQLiteDatabase db = VeekunDBHelper.getDB(getActivity().getApplicationContext());
            Cursor c = null;
            try {
                c = QueryHelper.buildExecutePokeNameQuery(db, s.toString());
                while (c.moveToNext()) {
                    Log.d(t, "Found poke: " + c.getString(c.getColumnIndex("name")));
                }
            } catch (Exception e) {
                Log.d(t, "Error searching for pokes", e);
            } finally {
                if (c != null) {
                    c.close();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
