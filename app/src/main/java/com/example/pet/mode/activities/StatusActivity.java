package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;

import com.example.pet.R;
import com.example.pet.databinding.ActivityStatusBinding;
import com.example.pet.mode.adapters.ImageAdapter;
import com.example.pet.mode.models.Image;
import com.example.pet.mode.models.New;
import com.example.pet.mode.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import static android.provider.CalendarContract.CalendarCache.URI;

public class StatusActivity extends AppCompatActivity {

    private ActivityStatusBinding statusBinding;
    private ImageAdapter imageAdapter;
    private ArrayList<Image> listImageResourse, listImage;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private New aNew;
    private String TAG = " log";
    private SharedPreferences preferences;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBinding = DataBindingUtil.setContentView(this, R.layout.activity_status);

        preferences = getSharedPreferences("login", MODE_PRIVATE);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("News");

        listImageResourse = getFilePaths();
        listImage = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, listImageResourse, 1);

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
                for (Image image : listImageResourse) {
                    if (image.isChosen() && !listImage.contains(image)) {
                        listImage.add(image);
                    }

                }
                imageAdapter = new ImageAdapter(StatusActivity.this, listImage, 2);

                statusBinding.rvImage.setAdapter(imageAdapter);
                statusBinding.rvImage.setLayoutManager(new LinearLayoutManager(StatusActivity.this, LinearLayoutManager.HORIZONTAL, false));

                statusBinding.chooseImage.setVisibility(View.GONE);
            }
        });

        statusBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusBinding.chooseImage.setVisibility(View.GONE);
            }
        });
        statusBinding.btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postNew();
                finish();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resultIAV;
    }

    private void postNew() {
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("News");

        HashMap<String, String> temp = new HashMap<>();

        long miliseconds = Calendar.getInstance().getTimeInMillis();
        String uid = preferences.getString("user_id", "");
        String id_new = uid + miliseconds;
        String content = statusBinding.edStatus.getText().toString();
        aNew = new New(id_new, content, 100, uid,
                listImage, "#heee", String.valueOf(miliseconds));
        temp.put("id_new", id_new);
        temp.put("content", aNew.getContent());
        temp.put("like", aNew.getLikes() + "");
        temp.put("user_id", aNew.getUser_id());
        temp.put("tag", aNew.getTag());
        temp.put("time", aNew.getTime());


        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sf.format(Calendar.getInstance().getTime());
        Date date_post_new = null;
        try {
            date_post_new = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long key_post = date_post_new.getTime();
        databaseReference.child(String.valueOf(key_post)).child(id_new).setValue(temp);

        //upload image
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("UploadImage");

        for (Image image : listImage) {
            Uri file = Uri.fromFile(new File(image.getImage()));
            storageReference.child(id_new).child(file.getLastPathSegment()).
                    putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            databaseReference.child(key_post +"/"+id_new).child("list_image").push().setValue(uri.toString());
                        }
                    });

                }
            });
        }



    }


}