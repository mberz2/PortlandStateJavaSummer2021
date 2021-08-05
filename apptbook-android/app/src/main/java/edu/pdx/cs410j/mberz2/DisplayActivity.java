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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;

import edu.pdx.cs410J.ParserException;

public class DisplayActivity extends AppCompatActivity {

    private final String TAG = "DisplayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "In display activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        savedInstanceState = getIntent().getExtras();
        String filename = savedInstanceState.getString("fileName");

        try {
            Log.e(TAG, "Loading appointment book.");
            AppointmentBook appBook = loadFromInternalStorage(filename);

            Toast toast = Toast.makeText(this, String.valueOf(appBook), Toast.LENGTH_LONG);
            toast.show();

        } catch (ParserException | FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }


    public AppointmentBook loadFromInternalStorage(String fileName)
            throws ParserException, FileNotFoundException {

        File file = new File(DisplayActivity.this.getFilesDir() + fileName);
        TextParser textParser = new TextParser(new FileReader(file));
        return textParser.parse();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DisplayActivity.this, SearchActivity.class);
        startActivity(intent);
        finish();
    }
}