package com.example.firebasedatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Upload_Multipol_data_Activity extends AppCompatActivity {

    EditText user_name,user_address,user_phone,get_user_name,delete_user;
    TextView get_data_text_view;
    Button submit_data,get_data,edit_data,btn_delete_user;
    DatabaseReference df;
    StorageReference sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_multipol_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        df = FirebaseDatabase.getInstance().getReference().child("User_information");

        user_name = findViewById(R.id.user_name);
        user_address = findViewById(R.id.user_address);
        user_phone = findViewById(R.id.user_phone_number);
        submit_data = findViewById(R.id.submit_data);
        get_user_name = findViewById(R.id.get_data);
        get_data = findViewById(R.id.btn_get_data);
        get_data_text_view = findViewById(R.id.get_data_text_view);
        edit_data = findViewById(R.id.edit_data);
        delete_user = findViewById(R.id.delete_user);
        btn_delete_user = findViewById(R.id.btn_delete_user);


        submit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap hashMap = new HashMap();
                hashMap.put("name",user_name.getText().toString());
                hashMap.put("address",user_address.getText().toString());
                hashMap.put("phone",user_phone.getText().toString());

                df.child(user_name.getText().toString()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Upload_Multipol_data_Activity.this,"upload successfully",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Upload_Multipol_data_Activity.this,"Data can't upload",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df.child(get_user_name.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            Map<String,String> map = (Map<String, String>) snapshot.getValue();
                            get_data_text_view.setText("Name = "+map.get("name")+",Address = "+ map.get("address")+",Phone = "+map.get("phone"));
                        }
                        else Toast.makeText(Upload_Multipol_data_Activity.this,"This user can't exist",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        edit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap hashMap = new HashMap();
                hashMap.put("name",user_name.getText().toString());
                //hashMap.put("address",user_address.getText().toString());
                hashMap.put("phone",user_phone.getText().toString());

                df.child(user_name.getText().toString()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Upload_Multipol_data_Activity.this,"Edit successfully",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Upload_Multipol_data_Activity.this,"Data can't Edit",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btn_delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df.child(delete_user.getText().toString()).removeValue();
            }
        });
    }
}