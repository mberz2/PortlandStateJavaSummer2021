package edu.pdx.cs410j.mberz2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import edu.pdx.cs410J.ParserException;

public class ViewActivity extends AppCompatActivity {

    private final String TAG = "ViewActivity";
    protected TextView txtViewWelcome;
    protected TextView txtHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e(TAG, "Creating view activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        txtViewWelcome = findViewById(R.id.titleView);
        txtHeader = findViewById(R.id.viewHeader);

        String header = "Displaying all appointment bookings.\nFrom most recent to latest:";
        txtHeader.setText(header);

        Log.e(TAG, "Loading all appointments.");
        File dir = new File(String.valueOf(ViewActivity.this.getFilesDir()));
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

        AppointmentBook temp = new AppointmentBook();
        for (AppointmentBook book : books) {

            ArrayList<Appointment> appList = book.getAppointments();
            for (Appointment app : appList) {

                    app.setOwnerName(book.getOwnerName());
                    temp.setOwnerName(book.getOwnerName());
                    temp.addAppointment(app);
                    loadAppointmentsWithOwner(temp);
                }
            }

        fill(txtViewWelcome, getString(R.string.welcomeView));
    }

    private void loadAppointmentsWithOwner(AppointmentBook appBook) {
        Log.e(TAG, "Loading appointments");
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

    public void fill(TextView v, String id) {
        Spanned htmlAsSpanned = Html.fromHtml(id, Html.FROM_HTML_MODE_COMPACT);
        v.setText(htmlAsSpanned);
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
        finish();
    }

}