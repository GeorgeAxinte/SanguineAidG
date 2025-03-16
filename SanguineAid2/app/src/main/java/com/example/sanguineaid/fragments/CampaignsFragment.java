package com.example.sanguineaid.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.example.sanguineaid.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class CampaignsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.campaigns_fragment, container, false);
    }
}