package com.example.firebasedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Show_image_Activity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    EditText search_image_name;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<image_path_db> options;
    FirebaseRecyclerAdapter<image_path_db,My_view_holder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("image_path_db");

        // take id
        floatingActionButton = findViewById(R.id.floating_button);
        search_image_name = findViewById(R.id.search_image_name);
        recyclerView =  findViewById(R.id.recycle_view);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Show_image_Activity.this,Image_upload_Activity.class));
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(Show_image_Activity.this));
        recyclerView.setHasFixedSize(true);
        Load_data();
    }

    private void Load_data() {
        options = new FirebaseRecyclerOptions.Builder<image_path_db>().setQuery(databaseReference,image_path_db.class).build();
        adapter = new FirebaseRecyclerAdapter<image_path_db, My_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull My_view_holder myViewHolder, int i, @NonNull image_path_db imagePathDb) {
                myViewHolder.textView.setText(imagePathDb.getName());
                Picasso.get().load(imagePathDb.getImage_url()).into(myViewHolder.imageView);
            }

            @NonNull
            @Override
            public My_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_view,parent,false);
                return new My_view_holder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}