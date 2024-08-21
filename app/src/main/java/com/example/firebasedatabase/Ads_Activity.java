package com.example.firebasedatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Ads_Activity extends AppCompatActivity {
    Button ads_banner;

    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ads);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ads_banner = findViewById(R.id.ads_banner);
        adView = findViewById(R.id.ads_view);

        ads_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //step 1
                MobileAds.initialize(Ads_Activity.this);
                //step 2
                AdRequest adRequest = new AdRequest.Builder().build();
                //step 3
                adView.loadAd(adRequest);
            }
        });
    }
}