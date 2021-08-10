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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

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
    protected TextView txtCountBox;
    protected TextView txtSearchWelcome;
    protected TextView txtGettingStarted;
    protected Button btnExit;
    protected Button btnSearch;
    protected String selected;
    protected TextView chboxPrintAll;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "Creating search activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Widget setup.
        txtCountBox = findViewById(R.id.mainCount);
        txtSearchWelcome = findViewById(R.id.titleSearch);
        txtGettingStarted = findViewById(R.id.gettingStartedSearch);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        txtStartTime = findViewById(R.id.txtStartTime);
        txtEndTime = findViewById(R.id.txtEndTime);
        chboxPrintAll = findViewById(R.id.chboxPrintAll);
        dropdown = findViewById(R.id.searchBooks);
        btnExit = findViewById(R.id.btnExit);
        btnSearch = findViewById(R.id.btnSearch);

        txtStartDate.setOnClickListener(this);
        txtEndDate.setOnClickListener(this);
        txtStartTime.setOnClickListener(this);
        txtEndTime.setOnClickListener(this);
        chboxPrintAll.setOnClickListener(this);
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
            try {
                search();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.btnExit) {
            onBackPressed();
        } else if (v.getId() == R.id.chboxPrintAll){
            if (((CheckBox) findViewById(R.id.chboxPrintAll)).isChecked()){
                findViewById(R.id.searchByDates).setVisibility(View.INVISIBLE);
                txtStartDate.setClickable(false);
                txtStartDate.setVisibility(View.INVISIBLE);
                msg = "Start Date";
                txtStartDate.setText(msg);
                txtStartTime.setClickable(false);
                txtStartTime.setVisibility(View.INVISIBLE);
                msg = "Start Time";
                txtStartTime.setText(msg);
                txtEndDate.setClickable(false);
                txtEndDate.setVisibility(View.INVISIBLE);
                msg = "End Date";
                txtEndDate.setText(msg);
                txtEndTime.setClickable(false);
                txtEndTime.setVisibility(View.INVISIBLE);
                msg = "End Time";
                txtEndTime.setText(msg);
            }
            else {
                findViewById(R.id.searchByDates).setVisibility(View.VISIBLE);
                txtStartDate.setClickable(true);
                msg = "Start Date";
                txtStartDate.setText(msg);
                txtStartDate.setVisibility(View.VISIBLE);
                txtStartTime.setClickable(true);
                msg = "Start Time";
                txtStartTime.setText(msg);
                txtStartTime.setVisibility(View.VISIBLE);
                txtEndDate.setClickable(true);
                msg = "End Date";
                txtEndDate.setText(msg);
                txtEndDate.setVisibility(View.VISIBLE);
                txtEndTime.setClickable(true);
                msg = "End Time";
                txtEndTime.setText(msg);
                txtEndTime.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        TextView textView = (TextView) dropdown.getSelectedView();
        selected = textView.getText().toString();
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

    public void search() throws ParseException {

        //Check if the confirmation widget is checked.
        boolean isChecked = ((CheckBox) findViewById(R.id.chboxPrintAll)).isChecked();

        if (isChecked) {
            if (selected.equals("Select an option:")) {
                printError("Missing an owner.");
                return;
            }

            Intent intent = new Intent(SearchActivity.this, DisplayActivity.class);
            intent.putExtra("fileName", "/apptBook_" + selected + ".csv");
            intent.putExtra("type", 1);
            startActivity(intent);
            finish();
            return;
        }

        if (txtStartDate.getText().equals("Start Date")
                || txtEndDate.getText().equals("End Date")
                || txtStartTime.getText().equals("Start Time")
                || txtEndTime.getText().equals("End Time")) {
            printError("Missing dates or times.");
            return;
        } else if (checkDatesEnd(txtStartDate, txtStartTime, txtEndDate, txtEndTime)) {
            Log.e(TAG, "Checking dates");
            printError("End date/time must be after start time.");
            txtEndDate.setText(R.string.endDate);
            txtEndTime.setText(R.string.endTime);
            return;
        } else if (selected.equals("Select an option:")) {
            printError("Missing an owner.");
            return;
        }

        Log.e(TAG, selected);
        Intent intent = new Intent(SearchActivity.this, DisplayActivity.class);
        intent.putExtra("owner", selected);
        intent.putExtra("start", txtStartDate.getText() + " " + txtStartTime.getText());
        intent.putExtra("end", txtEndDate.getText() + " " + txtEndTime.getText());
        startActivity(intent);
        finish();
    }

    public boolean checkDatesEnd(TextView bd, TextView bt, TextView ed, TextView et) throws ParseException {
        // Check if the date is before TODAY. Error.
        SimpleDateFormat format =
                new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.ENGLISH);

        Date beginDate = format.parse(bd.getText() + " " + bt.getText());
        Date endDate = format.parse(ed.getText() + " " + et.getText());

        return (Objects.requireNonNull(endDate).before(beginDate))
                || (Objects.requireNonNull(endDate).equals(beginDate));
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