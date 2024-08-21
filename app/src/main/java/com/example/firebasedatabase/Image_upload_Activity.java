package com.example.firebasedatabase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Image_upload_Activity extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 101;
    Uri image_uri;

    ImageView imageView;
    EditText image_name;
    TextView upload_persent;
    ProgressBar progressBar;
    Button btn_upload_image;

    DatabaseReference df;
    StorageReference sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_upload);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.image_icon);
        image_name = findViewById(R.id.image_name);
        upload_persent = findViewById(R.id.upload_persent);
        progressBar = findViewById(R.id.progress_bar);
        btn_upload_image = findViewById(R.id.button_upload_image);

        upload_persent.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        df = FirebaseDatabase.getInstance().getReference().child("image_path_db");
        sf = FirebaseStorage.getInstance().getReference().child("image_db");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);

            }
        });

        btn_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String img_name;
                img_name = image_name.getText().toString();
                if(img_name!=null)
                {
                    upload_image_method(img_name);
                }
            }
        });
    }

    private void upload_image_method(String name) {
        upload_persent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        String key = df.push().getKey();
        sf.child(key+"jpg").putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sf.child(key+"jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("name",name);
                        hashMap.put("image_url",image_uri.toString());
                        df.child(name).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(Image_upload_Activity.this,MainActivity.class));
                                Toast.makeText(Image_upload_Activity.this,"Update image successful",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double transfer_percent = (double) ((snapshot.getBytesTransferred()/snapshot.getTotalByteCount())*100);
                progressBar.setProgress((int)transfer_percent);
                upload_persent.setText(transfer_percent+"%");

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_IMAGE && data!=null)
        {
            image_uri = data.getData();
            imageView.setImageURI(image_uri);
        }
    }
}