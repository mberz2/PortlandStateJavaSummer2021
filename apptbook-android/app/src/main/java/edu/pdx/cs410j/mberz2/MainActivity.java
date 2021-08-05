package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;

import edu.pdx.cs410J.ParserException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "MainActivity";
    private TextView txtCountBox;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCreate = findViewById(R.id.btnCreate);
        Button btnSearch = findViewById(R.id.btnSearch);
        Button btnView = findViewById(R.id.btnView);
        Button btnHelp = findViewById(R.id.btnHelp);
        Button btnExit = findViewById(R.id.btnExit);

        TextView txtWelcome = findViewById(R.id.titleMain);
        TextView txtGettingStarted = findViewById(R.id.gettingStarted);
        txtCountBox = findViewById(R.id.mainCount);

        btnCreate.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        btnExit.setOnClickListener(this);

        fill(txtWelcome, getString(R.string.welcome));
        fill(txtGettingStarted, getString(R.string.gettingStarted));

        total(txtCountBox);
    }

    @Override
    public void onResume(){
        super.onResume();
        total(txtCountBox);
    }

    public void fill (TextView v, String id) {
        Spanned htmlAsSpanned = Html.fromHtml(id, Html.FROM_HTML_MODE_COMPACT);
        v.setText(htmlAsSpanned);
    }

    public void total (TextView v) {

        File file = new File(String.valueOf(MainActivity.this.getFilesDir()));
        File [] list = file.listFiles();
        count = 0;
        for (File f: list){
            String name = f.getName();
            if (name.endsWith(".csv"))
                count++;
            Log.e(TAG, String.valueOf(count));
            System.out.println("COUNT: " + count);
        }

        String txt = "Total Bookings, by individuals: " + count;

        v.setText(txt);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnCreate:
                intent = new Intent(MainActivity.this, ApptActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSearch:
                if (count == 0){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setMessage("There are currently no appointments booked.")
                            .setNegativeButton("Ok", null)
                            .show();
                } else {
                    intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btnView:
                if (count == 0){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setMessage("There are currently no appointments booked.")
                            .setNegativeButton("Ok", null)
                            .show();
                } else {
                    intent = new Intent(MainActivity.this, ViewActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btnHelp:
                intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.btnExit:
                onBackPressed();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Shutting down")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}