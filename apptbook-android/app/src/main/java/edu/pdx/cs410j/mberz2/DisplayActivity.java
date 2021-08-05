package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.pdx.cs410J.ParserException;

public class DisplayActivity extends AppCompatActivity {

    private final String TAG = "DisplayActivity";
    private TextView txtOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "In display activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        savedInstanceState = getIntent().getExtras();
        String fileName = savedInstanceState.getString("fileName");

        BufferedReader reader = null;
        StringBuilder text = new StringBuilder();

        AppointmentBook appBook = null;
        
        try {
            Log.e(TAG, "Loading appointment book.");
            appBook = loadFromInternalStorage(fileName);
            
            Toast toast = Toast.makeText(this, String.valueOf(appBook), Toast.LENGTH_LONG);
            toast.show();

        } catch (ParserException | FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        
        String msg = ("All bookings for: " +appBook.getOwnerName());

        txtOwner = findViewById(R.id.owner);
        txtOwner.setText(msg);

        ArrayList<Appointment> app = appBook.getAppointments();

        final ArrayList<String> list = new ArrayList<>();

        for(Appointment a : app){
            String desc = "\nDescription: " + a.getDescription();
            String bt = "\nFrom: " + a.getBeginTimeString();
            String et = "\nUntil: " + a.getEndTimeString();
            String duration = "\nDuration: "+TimeUnit.MILLISECONDS.toMinutes(a.getEndTime().getTime()
                    - a.getBeginTime().getTime()) + " mins\n";

            list.add(desc+bt+et+duration);
        }

        final ListView listview = findViewById(R.id.listview);
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
    }

    private static class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
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