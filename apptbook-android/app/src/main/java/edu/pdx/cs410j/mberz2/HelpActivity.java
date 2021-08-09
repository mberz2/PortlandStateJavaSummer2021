package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import edu.pdx.cs410J.ParserException;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HelpActivity";
    protected TextView textView;
    protected Button btnReadme;
    protected Button btnExit;
    protected Button btnReset;
    protected String htmlAsString;
    protected Spanned htmlAsSpanned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "Creating help activity.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        btnReadme = findViewById(R.id.btnReadme);
        btnExit = findViewById(R.id.btnExit);
        btnReset = findViewById(R.id.btnReset);

        btnReadme.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        htmlAsString = getString(R.string.title_help);
        htmlAsSpanned = Html.fromHtml(htmlAsString, Html.FROM_HTML_MODE_COMPACT);
        textView = findViewById(R.id.titleHelp);
        textView.setText(htmlAsSpanned);

        htmlAsString = getString(R.string.version);
        htmlAsSpanned = Html.fromHtml(htmlAsString, Html.FROM_HTML_MODE_COMPACT);
        textView = findViewById(R.id.titleVersion);
        textView.setText(htmlAsSpanned);

        htmlAsString = getString(R.string.created_for);
        htmlAsSpanned = Html.fromHtml(htmlAsString, Html.FROM_HTML_MODE_COMPACT);
        textView = findViewById(R.id.titleFor);
        textView.setText(htmlAsSpanned);

        htmlAsString = getString(R.string.created_by);
        htmlAsSpanned = Html.fromHtml(htmlAsString, Html.FROM_HTML_MODE_COMPACT);
        textView = findViewById(R.id.titleAuthor);
        textView.setText(htmlAsSpanned);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnReadme)
            displayReadme(v);
        else if (v.getId() == R.id.btnExit)
            finish();
        else if (v.getId() == R.id.btnReset)
            reset();
    }

    private void reset() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("RESETING APPLICATION")
                .setMessage("This will reset the app, deleting all files.\n" +
                        "Are you SURE you want to proceed?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteAll();
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteAll(){
        Log.e(TAG, "Deleting all");
        File dir = new File(String.valueOf(HelpActivity.this.getFilesDir()));
        File[] foundFiles = dir.listFiles((dir1, name) -> name.contains(".csv"));

        assert foundFiles != null;

        int count = 0;

        for (File f : foundFiles) {
            if(!f.delete()){
                Log.e(TAG, "Error deleting file.");
            } else {
                count++;
            }
        }

        Toast.makeText(getApplicationContext(),
                count + " file(s) deleted.", Toast.LENGTH_SHORT).show();
    }

    public void displayReadme(View v) {
        startActivity(
                new Intent(this, ReadmeActivity.class));
    }
}