package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import edu.pdx.cs410J.ParserException;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "SearchActivity";
    private Spinner dropdown;
    private TextView txtStartDate;
    private TextView txtEndDate;
    private TextView txtStartTime;
    private TextView txtEndTime;
    protected Button btnExit;
    protected Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Widget setup.
        TextView txtCountBox = findViewById(R.id.mainCount);
        TextView txtSearchWelcome = findViewById(R.id.titleSearch);
        TextView txtGettingStarted = findViewById(R.id.gettingStartedSearch);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        txtStartTime = findViewById(R.id.txtStartTime);
        txtEndTime = findViewById(R.id.txtEndTime);
        dropdown = findViewById(R.id.searchBooks);
        btnExit = findViewById(R.id.btnExit);
        btnSearch = findViewById(R.id.btnSearch);

        txtStartDate.setOnClickListener(this);
        txtEndDate.setOnClickListener(this);
        txtStartTime.setOnClickListener(this);
        txtEndTime.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        dropdown.setOnItemSelectedListener(this);

        fill(txtSearchWelcome, getString(R.string.welcomeSearch));
        fill(txtGettingStarted, getString(R.string.gettingStartedSearch));
        total(txtCountBox);

        File[] files = getFiles(String.valueOf(SearchActivity.this.getFilesDir()));
        ArrayList<String> names = getFileNames(files);
        Collection<AppointmentBook> books = new ArrayList<>();

        for (String n : names) {
            try {
                books.add(loadFromInternalStorage("/" + n));
            } catch (ParserException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Select an option:");
        for (AppointmentBook book : books) {
            adapter.addAll(book.getOwnerName());
        }
        dropdown.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txtStartDate) {
            createDialogFragment("StartDatePicker");
        } else if (v.getId() == R.id.txtEndDate) {
            createDialogFragment("EndDatePicker");
        } else if (v.getId() == R.id.txtStartTime) {
            createDialogFragment("StartTimePicker");
        } else if (v.getId() == R.id.txtEndTime) {
            createDialogFragment("EndTimePicker");
        } else if (v.getId() == R.id.btnSearch) {
            search();
        } else if (v.getId() == R.id.btnExit) {
            onBackPressed();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        TextView textView = (TextView) dropdown.getSelectedView();
        String result = textView.getText().toString();

        if (pos > 0) {
            Intent intent = new Intent(SearchActivity.this, DisplayActivity.class);
            intent.putExtra("fileName", "/apptBook_" + result + ".csv");
            intent.putExtra("type", 1);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Do nothing.
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag("StartDatePicker") != null)
            txtStartDate.setText(currentDateString);
        else
            txtEndDate.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        String currentTimeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag("StartTimePicker") != null)
            txtStartTime.setText(currentTimeString);
        else
            txtEndTime.setText(currentTimeString);
    }

    public File[] getFiles(String DirectoryPath) {
        File f = new File(DirectoryPath);
        if (!f.mkdirs())
            printError("Error in directory creation.");
        return f.listFiles();
    }

    public ArrayList<String> getFileNames(File[] file) {
        ArrayList<String> arrayFiles = new ArrayList<>();
        if (file.length == 0)
            return null;
        else {
            for (File value : file) arrayFiles.add(value.getName());
        }

        return arrayFiles;
    }

    public AppointmentBook loadFromInternalStorage(String fileName)
            throws ParserException, FileNotFoundException {

        File file = new File(SearchActivity.this.getFilesDir() + fileName);
        TextParser textParser = new TextParser(new FileReader(file));
        return textParser.parse();
    }


    private void createDialogFragment(String tag) {
        if (tag.equals("StartDatePicker") || tag.equals("EndDatePicker")) {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), tag);
        } else {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), tag);
        }
    }

    public void search() {
        String msg = (String) txtStartDate.getText();
        if (msg.equals("Start Date")
                || msg.equals("End Date")
                || msg.equals("Start Time")
                || msg.equals("End Time")) {
            printError("Missing dates or times.");
            return;
        }

        Intent intent = new Intent(SearchActivity.this, DisplayActivity.class);
        intent.putExtra("start", txtStartDate.getText() + " " + txtStartTime.getText());
        intent.putExtra("end", txtEndDate.getText() + " " + txtEndTime.getText());
        startActivity(intent);
        finish();
    }

    public void fill(TextView v, String id) {
        Spanned htmlAsSpanned = Html.fromHtml(id, Html.FROM_HTML_MODE_COMPACT);
        v.setText(htmlAsSpanned);
    }

    public void total(TextView v) {

        File file = new File(String.valueOf(SearchActivity.this.getFilesDir()));
        File[] list = file.listFiles();
        int count = 0;
        if (list != null) {
            for (File f : list) {
                String name = f.getName();
                if (name.endsWith(".csv"))
                    count++;
                Log.e(TAG, String.valueOf(count));
                System.out.println("COUNT: " + count);
            }
        }

        String txt = "Total individuals with appointments: " + count;
        v.setText(txt);
    }

    private void printError(String s) {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Error")
                .setMessage(s)
                .setNegativeButton("Ok", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Returning to main menu")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }
}