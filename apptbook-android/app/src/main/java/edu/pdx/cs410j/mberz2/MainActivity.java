package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import edu.pdx.cs410J.ParserException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button_create);
        btn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ApptActivity.class);
            startActivity(intent);
        });
    }



}