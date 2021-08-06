package edu.pdx.cs410j.mberz2;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReadmeActivity extends AppCompatActivity {

    private static final String TAG = "ReadmeActivity";
    protected TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "Creating readme activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);

        t = findViewById(R.id.readme);
        t.setText(Html.fromHtml("<html lang=\"en\">\t<head>\t\t<meta charset=\"UTF-8\" />" +
                "\t\t<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />" +
                "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
                "\t\t<title>Help</title>\t</head>\t<body>\t\t<main>" +
                "<div class=\"container\">" +
                "<div id=\"about\"><h1>BookMe Appointment Book App</h1>" +
                "<p>BookMe is a self-contained demo application for " +
                "creating, storing, viewing, and deleting applications based on a series of simple " +
                "inputs.<br>appointmentBook-android is not available for public release.</p>" +
                "<br><h2>About</h2><p>From the create menu, provide a name, " +
                "description, beginning and ending times, and the application will store the " +
                "appointment for later viewing.</p>" +
                "<br><p>The application will also allow the searching for appointments in " +
                "a variety of formats, including by a username, or by appointments that take place " +
                "between two dates. </p>" +
                "<br><h2>Features</h2><ul><li>Create and store appointments</li>" +
                "<li>Search for appointments by user or dates</li>" +
                "<li>Delete stored appointments</li></ul>" +
                "<br><h2>Permissions</h2><p>This app requires no device permissions.</p>" +
                "<br><h2>License</h2><p>This app requires no device permissions.</p>" +
                "<br><p>Copyright 2021 Matthew Berzinskas</p><p>" +
                "<br>Permission is hereby granted, free of charge, to any person obtaining " +
                "a copy of this software and associated documentation files (the \"Software\"), to " +
                "deal in the Software without restriction, including without limitation the rights " +
                "to use, copy, modify, merge, publish, distribute, sublicense, and/ or sell copies of " +
                "the Software, and to permit persons to whom the Software is furnished to do so, " +
                "subject to the following conditions:</p><br>The above copyright " +
                "notice and this permission notice shall be included in all copies or " +
                "substantial portions of the Software." +
                        "<p><br>THE SOFTWARE IS PROVIDED \"AS IS\", " +
                "WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE " +
                        "WARRANTIES OF MERCHANTABILITY, FITNESS " +
                "FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL " +
                "THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR " +
                "OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, " +
                "ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR " +
                "OTHER DEALINGS IN THE SOFTWARE." +
                "\t\t\t\t\t</p>\t\t\t\t</div>\t\t\t</div>\t\t</main>\t</body></html>",
                Html.FROM_HTML_MODE_COMPACT));
    }


}
