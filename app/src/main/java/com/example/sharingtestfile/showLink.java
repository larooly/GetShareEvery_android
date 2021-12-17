package com.example.sharingtestfile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class showLink extends Fragment {


    public showLink() {
        // Required empty public constructor
    }

    private TextView showget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_show_link, container, false);
        showget = (TextView)v.findViewById(R.id.getText);
        if (getArguments()==null){
            showget.setText("이야 아무것도 없다+");
        }
        else if (getArguments().getString("getText").equals("N")){
            showget.setText("이야 아무것도 없다");
        }
        else if(getArguments().getString("getText")!=null){
           showget.setText(getArguments().getString("getText"));
        }
        return v;
    }
}
