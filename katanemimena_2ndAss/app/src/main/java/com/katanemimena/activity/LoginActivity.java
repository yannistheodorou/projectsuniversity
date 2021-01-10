package com.katanemimena.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.katanemimena.R;

public class LoginActivity extends AppCompatActivity {

    /**
     * View objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView fbLoginBtn = findViewById(R.id.fb_login_btn);
        fbLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                finish();
            }
        });

    }



}
