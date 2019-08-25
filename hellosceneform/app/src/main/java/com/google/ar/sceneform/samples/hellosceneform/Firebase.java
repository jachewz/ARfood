package com.google.ar.sceneform.samples.hellosceneform;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Firebase extends AppCompatActivity {
    private StorageReference mStorageRef;
    private ProgressBar progressBar, progressBar2;
    double progress,progress2;
    boolean yes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        mStorageRef = FirebaseStorage.getInstance("gs://bite-by-bite.appspot.com").getReference();


        StorageReference jpegRef = mStorageRef.child("jpeg/61242B6C-C91C-4D72-821E-2AA310B84950.jpeg");
        StorageReference plyRef = mStorageRef.child("models/61242B6C-C91C-4D72-821E-2AA310B84950.ply");

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File localFile2 = null;
        try {
            localFile2 = File.createTempFile("model", "ply");
        } catch (IOException e) {
            e.printStackTrace();
        }

        jpegRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                     }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                //calculating progress percentage
                progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                //displaying percentage in progress dialog
                progressBar = (ProgressBar) findViewById(R.id.progressBarFirebase);
                progressBar.setProgress((int)progress);
            }
        });

        plyRef.getFile(localFile2)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                        yes = true;
                        openActivity2();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                //calculating progress percentage
                progress2 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                //displaying percentage in progress dialog
                progressBar2 = (ProgressBar) findViewById(R.id.progressBarFirebase);
                progressBar2.setProgress((int)progress2);
            }
        });


    }

    public void openActivity2() {
        Intent intent = new Intent(this, HelloSceneformActivity.class);
        intent.putExtra("model","random");
        startActivity(intent);
    }
}
