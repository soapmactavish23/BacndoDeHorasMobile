package com.example.bancodehoras.framents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bancodehoras.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BancoHorasFragment extends Fragment {

    public BancoHorasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_banco_horas, container, false);
    }
}
