package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import edu.pdx.cs410J.ParserException;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String TAG = "DisplayActivity";
        Log.e(TAG, "In display activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        savedInstanceState = getIntent().getExtras();
        String fileName = savedInstanceState.getString("fileName");

        TextView txtOwner = findViewById(R.id.owner);

        if (savedInstanceState.getInt("type") == 1) {
            AppointmentBook appBook = null;
            try {
                Log.e(TAG, "Loading appointment book.");
                appBook = loadFromInternalStorage(fileName);
            } catch (ParserException | FileNotFoundException e) {
                printError(e.getMessage());
                e.printStackTrace();
            }

            assert appBook != null;
            String msg = ("All bookings for: " + appBook.getOwnerName());
            txtOwner.setText(msg);
            loadAppointments(appBook);

        } else {
            Log.e(TAG, "Loading all appointments between dates.");

            String header = "Displaying all bookings between dates:";
            txtOwner.setText(header);

            String start = savedInstanceState.getString("start");
            String end = savedInstanceState.getString("end");

            File dir = new File(String.valueOf(DisplayActivity.this.getFilesDir()));
            File[] foundFiles = dir.listFiles((dir1, name) -> name.startsWith("apptBook_"));

            assert foundFiles != null;

            ArrayList<AppointmentBook> books = new ArrayList<>();

            for (File f : foundFiles) {
                try {
                    TextParser textParser = new TextParser(new FileReader(f));
                    books.add(textParser.parse());
                } catch (FileNotFoundException | ParserException e) {
                    printError(e.getMessage());
                    e.printStackTrace();
                }
            }

            try {
                SimpleDateFormat format =
                        new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.ENGLISH);

                AppointmentBook temp = new AppointmentBook();
                for (AppointmentBook book : books) {

                    ArrayList<Appointment> appList = book.getAppointments();
                    for (Appointment app : appList) {

                        if (checkDates(Objects.requireNonNull(format.parse(app.getBeginTimeString())),
                                format.parse(start), format.parse(end))) {
                            app.setOwnerName(book.getOwnerName());
                            temp.setOwnerName(book.getOwnerName());
                            temp.addAppointment(app);
                            loadAppointmentsWithOwner(temp);
                        }
                    }
                }

            } catch (ParseException e) {
                printError("Error in parsing.");
            }
        }
    }

    private boolean checkDates(Date d, Date min, Date max) {
        return d.after(min) && d.before(max);
    }

    private void loadAppointments(AppointmentBook appBook) {
        ArrayList<Appointment> app = appBook.getAppointments();

        final ArrayList<String> list = new ArrayList<>();

        for (Appointment a : app) {
            String desc = "\nDescription: " + a.getDescription();
            String bt = "\nFrom: " + a.getBeginTimeString();
            String et = "\nUntil: " + a.getEndTimeString();
            String duration = "\nDuration: " + TimeUnit.MILLISECONDS.toMinutes(a.getEndTime().getTime()
                    - a.getBeginTime().getTime()) + " mins\n";

            list.add(desc + bt + et + duration);
        }

        final ListView listview = findViewById(R.id.listview);
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
    }

    private void loadAppointmentsWithOwner(AppointmentBook appBook) {
        ArrayList<Appointment> app = appBook.getAppointments();

        final ArrayList<String> list = new ArrayList<>();

        for (Appointment a : app) {
            String o = "\nBooking for: " + a.getOwnerName();
            String desc = "\nDescription: " + a.getDescription();
            String bt = "\nFrom: " + a.getBeginTimeString();
            String et = "\nUntil: " + a.getEndTimeString();
            String duration = "\nDuration: " + TimeUnit.MILLISECONDS.toMinutes(a.getEndTime().getTime()
                    - a.getBeginTime().getTime()) + " mins\n";

            list.add(o + desc + bt + et + duration);
        }

        final ListView listview = findViewById(R.id.listview);
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
    }

    public AppointmentBook loadFromInternalStorage(String fileName)
            throws ParserException, FileNotFoundException {

        File file = new File(DisplayActivity.this.getFilesDir() + fileName);
        TextParser textParser = new TextParser(new FileReader(file));
        return textParser.parse();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DisplayActivity.this, SearchActivity.class);
        startActivity(intent);
        finish();
    }

    private void printError(String s) {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Error")
                .setMessage(s)
                .setNegativeButton("Ok", null)
                .show();
    }
}