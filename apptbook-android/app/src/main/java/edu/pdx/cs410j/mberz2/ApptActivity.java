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
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

public class ApptActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private static final String TAG = "ApptActivity";
    private TextView txtStartDate;
    private TextView txtEndDate;
    private TextView txtStartTime;
    private TextView txtEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    } //end onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtStartDate:
                /* You need to define a unique tag name for each fragment */
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
        /* getSupportFragmentManager() will return a FragmentManager
         * that is used to manage the fragments */
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

        boolean isChecked = ((CheckBox) findViewById(R.id.chboxConfirm)).isChecked();

        try {

            EditText editTextName = findViewById(R.id.appt_name);
            String name = editTextName.getText().toString();
            EditText editTextDesc = findViewById(R.id.appt_desc);
            String desc = editTextDesc.getText().toString();

            if(TextUtils.isEmpty(name)) {
                Snackbar.make(view, "Name is blank.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                printError(view, "Name cannot be blank.");
                return;
            } else if (TextUtils.isEmpty(desc)) {
                Snackbar.make(view,"Desc: "+desc, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if(name.equals(""))
                    printError(view, "Description cannot be blank.");
                return;
            }

            Log.e(TAG, "Creating appointment.");
            Appointment appointment = new Appointment(
                    desc,
                    txtStartDate.getText().toString() + " " + txtStartTime.getText().toString(),
                    txtEndDate.getText().toString() + " " + txtEndTime.getText().toString());

            if (isChecked){
                Snackbar.make(view, appointment.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(view, "Appointment confirmed!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        } catch (ParseException | NullPointerException | UnsupportedOperationException e) {
            String error = e.getMessage();
            assert error != null;
            Snackbar.make(view, error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void printError(View view, String s){
        String msg = "Error: "+s;
        Log.d(TAG, msg);
        Snackbar.make(view,msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Returning to Main Menu")
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