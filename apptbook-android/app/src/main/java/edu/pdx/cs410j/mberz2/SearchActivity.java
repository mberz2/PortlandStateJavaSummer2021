package edu.pdx.cs410j.mberz2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import edu.pdx.cs410J.ParserException;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    static final String TAG = "SearchActivity";
    private Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button btnExit = findViewById(R.id.btnExit);

        TextView txtCountBox = findViewById(R.id.mainCount);
        TextView txtSearchWelcome = findViewById(R.id.titleSearch);
        TextView txtGettingStarted = findViewById(R.id.gettingStartedSearch);

        dropdown = findViewById(R.id.searchBooks);

        btnExit.setOnClickListener(this);
        dropdown.setOnItemSelectedListener(this);

        fill(txtSearchWelcome, getString(R.string.welcomeSearch));
        fill(txtGettingStarted, getString(R.string.gettingStartedSearch));

        total(txtCountBox);

        File [] files = getFiles(String.valueOf(SearchActivity.this.getFilesDir()));
        ArrayList<String> names = getFileNames(files);
        Collection<AppointmentBook> books = new ArrayList<>();

        for (String n : names) {
            try {
                books.add(loadFromInternalStorage("/"+n));
            } catch (ParserException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Select an option:");
        for (AppointmentBook book : books){
            adapter.addAll(book.getOwnerName());
        }
        dropdown.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        TextView textView = (TextView)dropdown.getSelectedView();
        String result = textView.getText().toString();

        try {
            Log.e(TAG, "Loading appointment book.");
            AppointmentBook appBook = loadFromInternalStorage("/apptBook_"+result+".csv");
            Intent intent = new Intent(SearchActivity.this, DisplayActivity.class);
            intent.putExtra("displayBook", appBook);
            startActivity(intent);
            finish();

        } catch (ParserException | FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        //Toast toast = Toast.makeText(this, result, Toast.LENGTH_LONG);
        //toast.show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Another interface callback
    }

    public AppointmentBook loadFromInternalStorage(String fileName)
            throws ParserException, FileNotFoundException {

        File file = new File(SearchActivity.this.getFilesDir() +fileName);
        TextParser textParser = new TextParser(new FileReader(file));
        return textParser.parse();
    }

    public File[] getFiles(String DirectoryPath) {
        File f = new File(DirectoryPath);
        f.mkdirs();
        File[] file = f.listFiles();
        return file;
    }

    public ArrayList<String> getFileNames(File[] file){
        ArrayList<String> arrayFiles = new ArrayList<String>();
        if (file.length == 0)
            return null;
        else {
            for (int i=0; i<file.length; i++)
                arrayFiles.add(file[i].getName());
        }

        return arrayFiles;
    }

    public void fill (TextView v, String id) {
        Spanned htmlAsSpanned = Html.fromHtml(id, Html.FROM_HTML_MODE_COMPACT);
        v.setText(htmlAsSpanned);
    }

    public void total (TextView v) {

        File file = new File(String.valueOf(SearchActivity.this.getFilesDir()));
        File [] list = file.listFiles();
        int count = 0;
        for (File f: list){
            String name = f.getName();
            if (name.endsWith(".csv"))
                count++;
            Log.e(TAG, String.valueOf(count));
            System.out.println("COUNT: " + count);
        }

        String txt = "Total Bookings, by Name: " + count;

        v.setText(txt);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.btnReadme:
                displayReadme(v);
                break;*/
            case R.id.btnExit:
                onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Returning to main menu")
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