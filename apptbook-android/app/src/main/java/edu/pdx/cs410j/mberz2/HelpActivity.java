package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Button btnReadme = findViewById(R.id.btnReadme);
        Button btnExit = findViewById(R.id.btnExit);

        btnReadme.setOnClickListener(this);
        btnExit.setOnClickListener(this);

        String htmlAsString = getString(R.string.title_help);
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString, Html.FROM_HTML_MODE_COMPACT);
        TextView textView = (TextView) findViewById(R.id.titleHelp);
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
        switch (v.getId()) {
            case R.id.btnReadme:
                displayReadme(v);
                break;
            case R.id.btnExit:
                finish();
        }
    }

    public void displayReadme(View view) {
        startActivity(
                new Intent(this, ReadmeActivity.class));
    }
}