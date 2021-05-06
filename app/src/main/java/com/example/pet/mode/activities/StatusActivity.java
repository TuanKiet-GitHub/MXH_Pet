package com.example.pet.mode.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pet.R;
import com.example.pet.databinding.ActivityStatusBinding;
import com.example.pet.mode.adapters.ImageAdapter;
import com.example.pet.mode.models.Image;
import com.example.pet.mode.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class StatusActivity extends AppCompatActivity {

    ActivityStatusBinding statusBinding;
    ImageAdapter imageAdapter;
    ArrayList<Image> listImageResourse, listImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBinding = DataBindingUtil.setContentView(this, R.layout.activity_status);
        getSupportActionBar().hide();

        listImageResourse = getFilePaths();
        listImage = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, listImageResourse);

        statusBinding.rvListImage.setAdapter(imageAdapter);
        statusBinding.rvListImage.setLayoutManager(new GridLayoutManager(this, 3));

        statusBinding.edStatus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                statusBinding.edStatus.setText("");
                statusBinding.btnDangBai.setEnabled(true);
                statusBinding.btnDangBai.setBackgroundColor(getApplication().getResources().getColor(R.color.yellowmain2));
            }
        });

        statusBinding.imgBackStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        statusBinding.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftKeyboard(StatusActivity.this);
            }
        });

        statusBinding.btnLoadAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFilePaths();
                statusBinding.chooseImage.setVisibility(View.VISIBLE);
            }
        });

        statusBinding.btChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Image image: listImageResourse) {
                    if(image.isChosen()){
                        listImage.add(image);
                        Log.e("TAG", "onClick: " + image.getImage() );
                    }

                }

                statusBinding.chooseImage.setVisibility(View.GONE);
            }
        });

        statusBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusBinding.chooseImage.setVisibility(View.GONE);
            }
        });
    }


    public ArrayList<Image> getFilePaths() {

        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<String>();
        ArrayList<Image> resultIAV = new ArrayList<Image>();

        String[] directories = null;
        if (u != null) {
            c = managedQuery(u, projection, null, null, null);
        }

        if ((c != null) && (c.moveToFirst())) {
            do {
                String tempDir = c.getString(0);

                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));

                try {
                    dirList.add(tempDir);
                } catch (Exception e) {

                }
            }
            while (c.moveToNext());
            directories = new String[dirList.size()];
            dirList.toArray(directories);

        }

        for (int i = 0; i < dirList.size(); i++) {
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if (imageList == null)
                continue;
            for (File imagePath : imageList) {
                try {

                    if (imagePath.isDirectory()) {
                        imageList = imagePath.listFiles();

                    }
                    if (imagePath.getName().contains(".jpg") || imagePath.getName().contains(".JPG")
                            || imagePath.getName().contains(".jpeg") || imagePath.getName().contains(".JPEG")
                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                    ) {


                        String path = imagePath.getAbsolutePath();
                        resultIAV.add(new Image(path));

                    }
                }
                //  }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resultIAV;


    }



}