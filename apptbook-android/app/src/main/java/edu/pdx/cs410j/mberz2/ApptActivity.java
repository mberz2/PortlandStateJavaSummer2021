package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.material.snackbar.Snackbar;

import java.io.*;
import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;

import edu.pdx.cs410J.ParserException;

public class ApptActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private static final String TAG = "ApptActivity";
    private TextView txtStartDate;
    private TextView txtEndDate;
    private TextView txtStartTime;
    private TextView txtEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e(TAG, "Activity Switch");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appt);

        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        txtStartTime = findViewById(R.id.txtStartTime);
        txtEndTime = findViewById(R.id.txtEndTime);
        Button btnReset = findViewById(R.id.btnReset);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        txtStartDate.setOnClickListener(this);
        txtEndDate.setOnClickListener(this);
        txtStartTime.setOnClickListener(this);
        txtEndTime.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtStartDate:
                createDialogFragment("StartDatePicker");
                break;
            case R.id.txtEndDate:
                createDialogFragment("EndDatePicker");
                break;
            case R.id.txtStartTime:
                createDialogFragment("StartTimePicker");
                break;
            case R.id.txtEndTime:
                createDialogFragment("EndTimePicker");
                break;
            case R.id.btnConfirm:
                confirmInput(v);
                break;
            case R.id.btnReset:
                startActivity(new Intent(this, ApptActivity.class));
                finish();
                break;
        }
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag("StartDatePicker") != null) {
            txtStartDate.setText(currentDateString);
        } else {
            txtEndDate.setText(currentDateString);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        String currentTimeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag("StartTimePicker") != null) {
            txtStartTime.setText(currentTimeString);
        } else {
            txtEndTime.setText(currentTimeString);
        }
    }

    public void confirmInput(View view) {
        Log.e(TAG,"Confirming appointment.");

        //Check if the confirmation widget is checked.
        boolean isChecked = ((CheckBox) findViewById(R.id.chboxConfirm)).isChecked();

        try {

            //Get the name and description fields from their widgets.
            EditText editTextName = findViewById(R.id.appt_name);
            String name = editTextName.getText().toString();
            EditText editTextDesc = findViewById(R.id.appt_desc);
            String desc = editTextDesc.getText().toString();

            //If name or description are empty, notify the user.
            if(TextUtils.isEmpty(name)) {
                printError("Missing Fields. Name cannot be blank.");
                return;
            } else if (TextUtils.isEmpty(desc)) {
                printError("Missing Fields. Description cannot be blank.");
                return;
            }

            try{
                Appointment app = new Appointment(
                        desc,
                        txtStartDate.getText().toString() + " " + txtStartTime.getText().toString(),
                        txtEndDate.getText().toString() + " " + txtEndTime.getText().toString());

                AppointmentBook book = new AppointmentBook(name, app);
                AppointmentBook tempBook = loadFromInternalStorage(book);

                if (isChecked){
                    displayAppointment(app, tempBook);

                } else {
                    writeToInternalStorage(tempBook);
                }

            } catch (ParseException | ParserException e) {
                printError("Unable to parse date/time.\n"+e.getMessage());
            }

        } catch (NullPointerException | UnsupportedOperationException | IOException e) {
            printError(e.getMessage());
        }
    }

    public AppointmentBook loadFromInternalStorage(AppointmentBook appBook)
            throws ParserException {

        try {
            File file = new File(ApptActivity.this.getFilesDir()
                    +"apptBook_" + appBook.getOwnerName()
                    +".csv");
            TextParser textParser = new TextParser(new FileReader(file));

            //Check if anything is in the file.
            AppointmentBook tempBook = textParser.parse();

            // Combining the appointments.
            ArrayList<Appointment> app = appBook.getAppointments();

            for (Appointment a: app)
                tempBook.addAppointment(a);

            return tempBook;

        } catch (FileNotFoundException e){
            //Nothing to merge, return original book.
            return appBook;
        }
    }

    public void writeToInternalStorage(AppointmentBook appBook) throws IOException {
        Log.e(TAG, "Writing book");
        FileOutputStream fileout = openFileOutput("apptBook_" + appBook.getOwnerName() + ".csv", MODE_PRIVATE);
        TextDumper textDumper = new TextDumper(new OutputStreamWriter(fileout));
        textDumper.dump(appBook);

        //display file saved message
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage("Appointment saved successfully!")
                .setPositiveButton("Ok", (dialog, which) -> finish())
                .show();
    }

    private void printError(String s){

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Error")
                .setMessage(s)
                .setNegativeButton("Ok", null)
                .show();
    }

    public void displayAppointment(Appointment app, AppointmentBook tempBook){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Appointment Confirmation")
                .setMessage("Description: "+app.getDescription()
                +"\nFrom: "+app.getBeginTimeString()
                +"\nTo: "+app.getEndTimeString())
                .setPositiveButton("Ok", (dialog, which) -> {
                    try {
                        writeToInternalStorage(tempBook);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Returning to Main Menu")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }
}