package com.zeddysoft.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zeddysoft.popularmovies.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void clickHandler(View view) {
        for (int i = 0; i < 10; i++) {
            Log.d("Checking i", i+"");
        }
    }
}
