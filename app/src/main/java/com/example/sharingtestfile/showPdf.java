package com.example.sharingtestfile;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;


public class showPdf extends Fragment {


    public showPdf() {
        // Required empty public constructor
    }


    PDFView pdfView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_show_pdf, container, false);
        // Inflate the layout for this fragment
        pdfView = (PDFView)v.findViewById(R.id.pdf_viewer);
        //pdfView.fromAsset("copy.pdf");

        if (getArguments()==null){
            pdfView.fromAsset("copy.pdf")
                    .defaultPage(0)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .onTap(new OnTapListener() {
                        @Override
                        public boolean onTap(MotionEvent e) {
                            return true;
                        }
                    }).onRender(new OnRenderListener() {
                @Override
                public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                    pdfView.fitToWidth();
                }
            })
                    .enableAnnotationRendering(true)
                    .invalidPageColor(Color.WHITE)
                    .load();


        }
        else if(getArguments().getString("pdfURI")!=null){
            Uri video= Uri.parse(getArguments().getString("pdfURI"));
            pdfView.fromUri(video)
                    .defaultPage(0)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .onTap(new OnTapListener() {
                        @Override
                        public boolean onTap(MotionEvent e) {
                            return true;
                        }
                    }).onRender(new OnRenderListener() {
                @Override
                public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                    pdfView.fitToWidth();
                }
            })
                    .enableAnnotationRendering(true)
                    .invalidPageColor(Color.WHITE)
                    .load();

        }




        return v;
    }
}
