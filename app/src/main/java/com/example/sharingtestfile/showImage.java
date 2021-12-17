package com.example.sharingtestfile;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link showImage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class showImage extends Fragment {
    ImageView img;
    public showImage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_show_image, container, false);
        // Inflate the layout for this fragment
        if (getArguments().getString("imageURI").equals("N")){
            img= (ImageView)view.findViewById(R.id.Iv);
            img.setImageResource(R.drawable.ic_cloud_queue_black_24dp);
        }
        else if(getArguments().getString("imageURI")!=null){
            Uri image= Uri.parse(getArguments().getString("imageURI"));
            img= (ImageView)view.findViewById(R.id.Iv);
            img.setImageURI(image);
        }
        return view;
    }
}
