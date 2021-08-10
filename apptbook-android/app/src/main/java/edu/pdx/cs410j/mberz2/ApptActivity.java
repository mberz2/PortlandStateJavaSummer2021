package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import edu.pdx.cs410J.ParserException;

public class ApptActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    // Widget variables.
    private static final String TAG = "ApptActivity";
    private TextView txtStartDate;
    private TextView txtEndDate;
    private TextView txtStartTime;
    private TextView txtEndTime;
    protected Button btnReset;
    protected Button btnConfirm;
    protected Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e(TAG, "Creating appointment activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appt);

        // Set widget variables.
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        txtStartTime = findViewById(R.id.txtStartTime);
        txtEndTime = findViewById(R.id.txtEndTime);
        btnReset = findViewById(R.id.btnReset);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnExit = findViewById(R.id.btnExit);

        // Check current context on click.
        txtStartDate.setOnClickListener(this);
        txtEndDate.setOnClickListener(this);
        txtStartTime.setOnClickListener(this);
        txtEndTime.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Checks the current view and performs the appropriate action.

        if (v.getId() == R.id.txtStartDate) {
            createDialogFragment("StartDatePicker");
        } else if (v.getId() == R.id.txtEndDate) {
            createDialogFragment("EndDatePicker");
        } else if (v.getId() == R.id.txtStartTime) {
            createDialogFragment("StartTimePicker");
        } else if (v.getId() == R.id.txtEndTime) {
            createDialogFragment("EndTimePicker");
        } else if (v.getId() == R.id.btnConfirm) {
            try {
                confirmInput(v);
            } catch (ParseException e) {
                printError(e.getMessage());
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.btnReset) {
            startActivity(new Intent(this, ApptActivity.class));
            finish();
        } else if (v.getId() == R.id.btnExit) {
            onBackPressed();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());

        // Set the date into the widget.
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
        String currentTimeString =
                DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag("StartTimePicker") != null)
            txtStartTime.setText(currentTimeString);
        else
            txtEndTime.setText(currentTimeString);
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

    private boolean checkDates(TextView d, TextView t) throws ParseException {
        // Check if the date is before TODAY. Error.
        SimpleDateFormat format =
                new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.ENGLISH);

        Date date = format.parse(d.getText() + " " + t.getText());
        Date today = new Date();

        return (Objects.requireNonNull(date).before(today));
    }

    private boolean checkDatesEnd(TextView bd, TextView bt, TextView ed, TextView et) throws ParseException {
        // Check if the date is before TODAY. Error.
        SimpleDateFormat format =
                new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.ENGLISH);

        Date beginDate = format.parse(bd.getText() + " " + bt.getText());
        Date endDate = format.parse(ed.getText() + " " + et.getText());

        return (Objects.requireNonNull(endDate).before(beginDate))
                || (Objects.requireNonNull(endDate).equals(beginDate));
    }

    private void confirmInput(View view) throws ParseException {
        Log.e(TAG, "Confirming appointment.");

        //Check if the confirmation widget is checked.
        boolean isChecked = ((CheckBox) findViewById(R.id.chboxConfirm)).isChecked();

        //Check dates to ensure they are not BEFORE today.
        if (checkDates(txtStartDate, txtStartTime)) {
            printError("Start date/time cannot be the same/earlier than current time.");
            txtStartDate.setText(R.string.startDate);
            txtStartTime.setText(R.string.startTime);
            return;
        } else if (checkDates(txtEndDate, txtEndTime)) {
            printError("End date/time cannot be earlier than the current time.");
            txtEndDate.setText(R.string.endDate);
            txtEndTime.setText(R.string.endTime);
            return;
        } else if (checkDatesEnd(txtStartDate, txtStartTime, txtEndDate, txtEndTime)){
            printError("End date/time cannot be earlier than the start date/time.");
            txtEndDate.setText(R.string.endDate);
            txtEndTime.setText(R.string.endTime);
            return;
        }

        try {
            //Get the name and description fields from their widgets.
            EditText editTextName = findViewById(R.id.appt_name);
            String name = editTextName.getText().toString();
            EditText editTextDesc = findViewById(R.id.appt_desc);
            String desc = editTextDesc.getText().toString();

            //If name or description are empty, notify the user.
            if (TextUtils.isEmpty(name)) {
                printError("Missing Fields. Name cannot be blank.");
                return;
            } else if (TextUtils.isEmpty(desc)) {
                printError("Missing Fields. Description cannot be blank.");
                return;
            }

            try {
                Appointment app = new Appointment(desc,
                        txtStartDate.getText().toString() + " " + txtStartTime.getText().toString(),
                        txtEndDate.getText().toString() + " " + txtEndTime.getText().toString());

                AppointmentBook tempBook = loadFromInternalStorage(app, name);

                if (tempBook == null)
                    tempBook = new AppointmentBook(name, app);

                if (isChecked)
                    printAppointment(app, tempBook);
                else
                    writeToInternalStorage(tempBook);

            } catch (ParseException | ParserException e) {
                printError("Unable to parse date/time.\n" + e.getMessage());
            }

        } catch (NullPointerException | UnsupportedOperationException | IOException e) {
            printError(e.getMessage());
        }
    }

    private AppointmentBook loadFromInternalStorage(Appointment app, String owner)
            throws ParserException {
        try {
            File file = new File(ApptActivity.this.getFilesDir()
                    + "/apptBook_" + owner + ".csv");
            TextParser textParser = new TextParser(new FileReader(file));

            //Check if anything is in the file.
            AppointmentBook tempBook = textParser.parse();

            tempBook.setOwnerName(owner);
            // Combining the appointments.
            tempBook.addAppointment(app);

            return tempBook;

        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private void writeToInternalStorage(AppointmentBook appBook) throws IOException {
        Log.e(TAG, "Writing appointment to file");
        FileOutputStream fileout = openFileOutput("apptBook_" + appBook.getOwnerName() + ".csv", MODE_PRIVATE);
        TextDumper textDumper = new TextDumper(new OutputStreamWriter(fileout));
        textDumper.dump(appBook);

        Toast.makeText(getApplicationContext(),
                "Appointment saved successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void printAppointment(Appointment app, AppointmentBook tempBook) {
        Log.e(TAG, "Printing appointment.");
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Appointment Confirmation")
                .setMessage("Description: " + app.getDescription()
                        + "\nFrom: " + app.getBeginTimeString()
                        + "\nTo: " + app.getEndTimeString())
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
                .setTitle("Returning to Main Menu")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }
}