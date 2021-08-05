package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class DisplayActivity extends AppCompatActivity {

    private final String TAG = "DisplayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "In display activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        /*
        savedInstanceState = getIntent().getExtras();
        Serializable display = savedInstanceState.getSerializable("displayBook");
        AppointmentBook newBook = (AppointmentBook) display;
        newBook.setOwnerName("NEW OWNER");
        savedInstanceState.putSerializable(newBook);

        Toast toast = Toast.makeText(this, String.valueOf(display), Toast.LENGTH_LONG);
        toast.show();

         */
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DisplayActivity.this, SearchActivity.class);
        startActivity(intent);
        finish();
    }
}