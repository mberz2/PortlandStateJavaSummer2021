package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HelpActivity";
    protected TextView textView;
    protected Button btnReadme;
    protected Button btnExit;
    protected String htmlAsString;
    protected Spanned htmlAsSpanned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "Creating help activity.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        btnReadme = findViewById(R.id.btnReadme);
        btnExit = findViewById(R.id.btnExit);

        btnReadme.setOnClickListener(this);
        btnExit.setOnClickListener(this);

        htmlAsString = getString(R.string.title_help);
        htmlAsSpanned = Html.fromHtml(htmlAsString, Html.FROM_HTML_MODE_COMPACT);
        textView = findViewById(R.id.titleHelp);
        textView.setText(htmlAsSpanned);

        htmlAsString = getString(R.string.version);
        htmlAsSpanned = Html.fromHtml(htmlAsString, Html.FROM_HTML_MODE_COMPACT);
        textView = (TextView) findViewById(R.id.titleVersion);
        textView.setText(htmlAsSpanned);

        htmlAsString = getString(R.string.created_for);
        htmlAsSpanned = Html.fromHtml(htmlAsString, Html.FROM_HTML_MODE_COMPACT);
        textView = (TextView) findViewById(R.id.titleFor);
        textView.setText(htmlAsSpanned);

        htmlAsString = getString(R.string.created_by);
        htmlAsSpanned = Html.fromHtml(htmlAsString, Html.FROM_HTML_MODE_COMPACT);
        textView = (TextView) findViewById(R.id.titleAuthor);
        textView.setText(htmlAsSpanned);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnReadme)
            displayReadme(v);
        else if (v.getId() == R.id.btnExit)
            finish();
    }

    public void displayReadme(View v) {
        startActivity(
                new Intent(this, ReadmeActivity.class));
    }
}