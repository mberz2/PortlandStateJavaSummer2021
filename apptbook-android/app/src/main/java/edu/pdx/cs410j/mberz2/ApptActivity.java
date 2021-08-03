package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class ApptActivity extends AppCompatActivity {

    private static final String TAG = "ApptActivity";
    private TextView mDisplayDateBegin;
    private TextView mDisplayDateEnd;
    private DatePickerDialog.OnDateSetListener mDateSetListenerBegin;
    private DatePickerDialog.OnDateSetListener mDateSetListenerEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appt);

        mDisplayDateBegin = (TextView) findViewById(R.id.beginTime);
        mDisplayDateBegin.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    ApptActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListenerBegin,
                    year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListenerBegin = (datePicker, year, month, day) -> {
            month = month +1;
            Log.d(TAG, "onDateSet: mm/dd/yyyy" + month +"/"+ day +"/"+ year);
            String date = month + "/" + day + "/" + year;
            mDisplayDateBegin.setText(date);
        };

        mDisplayDateEnd = (TextView) findViewById(R.id.endTime);
        mDisplayDateEnd.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int etYear = cal.get(Calendar.YEAR);
            int etMonth = cal.get(Calendar.MONTH);
            int etDay = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    ApptActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListenerEnd,
                    etYear, etMonth, etDay);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListenerEnd = (datePicker, year, month, day) -> {
            month = month +1;
            Log.d(TAG, "onDateSet: mm/dd/yyyy" + month +"/"+ day +"/"+ year);
            String date = month + "/" + day + "/" + year;
            mDisplayDateEnd.setText(date);
        };


    } //end onCreate
}

/*
                Appointment appointment = null;
                try {
                    appointment = new Appointment("Teach Java", "07/28/2021 5:30 PM", "07/28/2021 9:00 PM");
                    Snackbar.make(view, appointment.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (ParserException e) {
                    e.printStackTrace();
                }
 */