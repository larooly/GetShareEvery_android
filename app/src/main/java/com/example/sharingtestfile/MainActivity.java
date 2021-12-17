package com.example.sharingtestfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
   // ImageView b;
    TextView a;

    showImage imageF;
    showVideo videoF;
    FragmentManager fm;
    FragmentTransaction trans;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a= (TextView)findViewById(R.id.tete);

        File test = new File("dk");

        try {
            initIntent();//이거 받으려고 적어둔거
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    public void selfclick(View v){
        Fragment ff= null;
        if(v ==findViewById( R.id.buttonImage)){
            //이미지
            ff= new showImage();
            Bundle bb = new Bundle(1);
            bb.putString("imageURI","N");
            ff.setArguments(bb);
        }
        else if(v ==findViewById( R.id.buttonVideo)){
            //영상
            ff=new showVideo();
        }
        else if(v ==findViewById( R.id.buttonText)){
            //텍스트

            ff=new showLink();
            Bundle bb = new Bundle(1);
            bb.putString("getText","N");
            ff.setArguments(bb);
        }
        else if(v ==findViewById( R.id.buttonPDF)){
            //피디에프
            ff = new showPdf();
        }
        if(ff!=null) {
            setFragement(ff);
        }


    }

    private void initIntent() throws FileNotFoundException {
        Intent intent = getIntent();
        String type= intent.getType();
        String i = intent.getAction();
        a.setText(type);
        //Log.d("AAAAAA",type);

        if(Intent.ACTION_SEND.equals(i)&& type !=null){
            Fragment ff = null;
            Log.d("@@@",type);
            String filename = "null.txt";
            Uri Sample = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            Log.d("@@@",Sample.getPath());
            File SamF= new File(""+Sample);
            try {
                Cursor cursor = getContentResolver().query(Sample, null, null, null, null);
                cursor.moveToNext();
                String path = cursor.getString(cursor.getColumnIndex("_data"));
                cursor.close();
                if (path != null) {
                    Log.d("@@@", path.substring(path.lastIndexOf("/") + 1));//가끔 null이 나옴
                    filename = path.substring(path.lastIndexOf("/") + 1);
                } else {
                    Log.d("@@@", SamF.getName());//가끔 null이 나옴
                    filename = SamF.getName();
                }
            }catch (Exception e){
                Log.d("@@@", "망함");
                filename = SamF.getName();
            }
            savefile(Sample,filename);//ㅍㅏ일 저장용


            if("image/jpg".equals(type)){
                a.setText("jpg");
                Uri image = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if(image == null){
                    a.setText("Uri없음");
                }
                ff = new showImage();
                Bundle bb = new Bundle(1);
                bb.putString("imageURI",image.toString());
                ff.setArguments(bb);
               // b.setImageURI(image);
            }
            else if("image/*".equals(type)||type.startsWith("image/")){//다른 앱에서 받기 가능
                a.setText("another image");
                Uri image = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if(image == null){
                    a.setText("Uri없음");
                }
               // b.setImageURI(image);
                ff = new showImage();
                Bundle bb = new Bundle(1);
                bb.putString("imageURI",image.toString());
                ff.setArguments(bb);
            }

            /////============이 위까지가 이미지======//////
            else if("text/plain".equals(type)){
                String k =intent.getStringExtra(Intent.EXTRA_TEXT);
                a.setText( k);
                ff=new showLink();
                Bundle bb = new Bundle(1);
                bb.putString("getText",k);
                ff.setArguments(bb);
            }
            //텍스트//이건 나중에 편집각//////////////////
            //여기는 영상//////////////

            else if("video/*".equals(type)){
                String k = intent.getStringExtra(Intent.EXTRA_TEXT);
                a.setText(type);
                ff = new showVideo();
                Uri video = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                //Log.d("AAAAAA",video.getPath());
                Bundle bb = new Bundle(1);
                bb.putString("videoURI",video.toString());
                ff.setArguments(bb);

            }
            else if(type.startsWith("application/")|| "application/pdf".equals(type)){
                a.setText("피디에프");
                ff= new showPdf();
                Uri ppdf = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                Bundle bb = new Bundle(1);
                bb.putString("pdfURI",ppdf.toString());
                ff.setArguments(bb);

            }
            else{
                String k = intent.getStringExtra(Intent.EXTRA_TEXT);
                Uri file = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                String finame[] = file.getPath().split("/");
                a.setText(finame[finame.length-1]);
            }

            //ㄷㅏ나오고나서
            if(ff!=null) {
                a.setText("피디에프");

                setFragement(ff);
            }

        }
    }
    void savefile(Uri sourceuri, String filename)
    {
       // String sourceFilename= sourceuri.getPath();
        String destinationFilename = android.os.Environment.getExternalStorageDirectory().getPath()+"/"+filename;
        Log.d("@@@",destinationFilename);
       // BufferedInputStream bis = null;
        InputStream bis=null;
        BufferedOutputStream bos = null;
        //OutputStream bos = null;
        try {
         //   bis = new BufferedInputStream(new FileInputStream(sourceFilename));
            bis = this.getContentResolver().openInputStream(sourceuri);
            bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
            byte[] buf = new byte[1024];
            int lenn=0;
            while ((lenn=bis.read(buf))>0){
                bos.write(buf,0,lenn);
            }
        } catch (NullPointerException | IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Log.d("@@@","있었는데");
        //File del = new File(destinationFilename);
        //if(del != null){
           // del.delete();
        //}

    }
    void setFragement(Fragment ff){
        androidx.fragment.app.FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, ff);
        transaction.commit();
    }

//    void savefile(Uri sourceuri)
//    {
//        String sourceFilename= sourceuri.getPath();
//        String destinationFilename = android.os.Environment.getExternalStorageDirectory().getPath()+File.separatorChar+"abc.pdf";
//
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
//
//        try {
//            bis = new BufferedInputStream(new FileInputStream(sourceFilename));
//            bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
//            byte[] buf = new byte[1024];
//            bis.read(buf);
//            do {
//                bos.write(buf);
//            } while(bis.read(buf) != -1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (bis != null) bis.close();
//                if (bos != null) bos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
//            if ("text/plain".equals(type)) {
//                // 가져온 인텐트의 텍스트 정보
//                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
//                // TODO : 위에 받아온 텍스트로 원하는 기능 구현
//            }


//                Log.d("AAAAAA",ppdf.getPath());
//                ////////////////
//                Cursor cursor = getContentResolver().query(ppdf, null, null, null, null );
//
//                cursor.moveToNext();
//
//                String path = cursor.getString( cursor.getColumnIndex( "_data" ) );
//
//                cursor.close();
//
//                Log.d("AAAAAAAAAA  : ",path);
//
//                File RrealFile = new File(path);
//                OutputStream output = null;
//                File file = new File(Environment.getExternalStorageDirectory().toString()+"/UNITALK");
//                if(!file.exists()){
//                    file.mkdir();
//                }
//                File realfile = new File(file,"dfdfdf.pdf");
//                Log.d("AAAAAAAAAA  : ",realfile.getPath());
////                InputStream inStream = null;
//                OutputStream outStream = null;
//                try {
//                    inStream = new FileInputStream(new File(ppdf.getPath()));
//                    outStream = new FileOutputStream(realfile);
//                    byte[] buffer = new byte[1024];
//                    int length;
//                    while ((length = inStream.read(buffer))>0){
//                        outStream.write(buffer,0,length);
//                    }
//                    inStream.close();
//                    outStream.close();
//
//
//                }catch (Exception e){
//                    Log.d("ddd","!!!!!!!!!!ㅁㅏㅇ함");
//                }finally {
//
//                }


//                File realfile = new File(file,"dfdfdf.pdf");
//                try {
//                    FileOutputStream out= new FileOutputStream(realfile);
//                    new BufferedWriter(new FileWriter(path));
//                    out.flush();
//                    out.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
////////////////////////////