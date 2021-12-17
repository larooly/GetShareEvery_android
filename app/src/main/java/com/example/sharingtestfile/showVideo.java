package com.example.sharingtestfile;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;


public class showVideo extends Fragment {

    public showVideo() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_show_video, container, false);
        VideoView videoView = (VideoView) v.findViewById(R.id.vV);
        String videoPath ="android.resource://"+getActivity().getPackageName()+"/"+R.raw.dog;
        Uri uri= Uri.parse(videoPath);

        MediaController mediaController = new MediaController(v.getContext());
        videoView.setMediaController(mediaController);


        if (getArguments()==null){
            videoView.setVideoPath(videoPath);//비디오 설정

        }
        else if(getArguments().getString("videoURI")!=null){
            Log.d("Hello",getArguments().getString("videoURI"));

            Uri video= Uri.parse(getArguments().getString("videoURI"));
            Log.d("Hello",video.getPath());
            videoView.setVideoURI(video);//비디오 설정
        }




        //videoView.setVideoPath(videoPath);//비디오 설정

        videoView.seekTo(1);//ㅁㅣ리보기설정
        videoView.setZOrderOnTop(true);//검은화면 나오는 거 방지


        mediaController.setAnchorView(videoView);//플레이어랑 영상 연결

        return v;
    }
}
