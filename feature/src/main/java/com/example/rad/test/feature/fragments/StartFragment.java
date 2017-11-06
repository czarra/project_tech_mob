package com.example.rad.test.feature.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rad.test.feature.R;

/**
 * Created by Rad on 2017-11-06.
 */

public class StartFragment extends Fragment {


    public static StartFragment newInstance() {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_start, container, false);
        Context context = getContext();

        return fragmentView;
    }


}
