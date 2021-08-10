package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private int count;
    private TextView txtCountBox;
    protected TextView txtWelcome;
    protected TextView txtGettingStarted;
    protected Button btnCreate;
    protected Button btnSearch;
    protected Button btnView;
    protected Button btnHelp;
    protected Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "Creating main activity.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Widget setup.
        btnCreate = findViewById(R.id.btnCreate);
        btnSearch = findViewById(R.id.btnSearch);
        btnView = findViewById(R.id.btnView);
        btnHelp = findViewById(R.id.btnHelp);
        btnExit = findViewById(R.id.btnExit);
        txtWelcome = findViewById(R.id.titleMain);
        txtGettingStarted = findViewById(R.id.gettingStarted);
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
    public void onClick(View v) {
        Intent intent;

        if (v.getId() == R.id.btnCreate) {
            intent = new Intent(MainActivity.this, ApptActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btnSearch) {
            if (count == 0) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setMessage("There are currently no appointments booked.")
                        .setNegativeButton("Ok", null)
                        .show();
            } else {
                intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        } else if (v.getId() == R.id.btnView) {
            if (count == 0) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setMessage("There are currently no appointments booked.")
                        .setNegativeButton("Ok", null)
                        .show();
            } else {
                intent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(intent);
            }
        } else if (v.getId() == R.id.btnHelp) {
            intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btnExit) {
            onBackPressed();
        }
    }

    public void fill(TextView v, String id) {
        Spanned htmlAsSpanned = Html.fromHtml(id, Html.FROM_HTML_MODE_COMPACT);
        v.setText(htmlAsSpanned);
    }

    public void total(TextView v) {

        File file = new File(String.valueOf(MainActivity.this.getFilesDir()));
        File[] list = file.listFiles();
        count = 0;

        Log.e(TAG, "No appointments, disable buttons.");
        btnView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.gray));
        btnSearch.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.gray));

        if (list != null && list.length > 0) {
            Log.e(TAG, "Appointments, enable buttons.");
            btnView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.purple_500));
            btnSearch.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.purple_500));

            for (File f : list) {
                String name = f.getName();
                if (name.endsWith(".csv"))
                    count++;
            }
        }

        String txt = "Total individuals with appointments: " + count;
        v.setText(txt);
    }

    @Override
    public void onResume() {
        super.onResume();
        total(txtCountBox);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Shutting down")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }
}