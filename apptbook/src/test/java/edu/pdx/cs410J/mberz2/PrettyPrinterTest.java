package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class PrettyPrinterTest {

	@Test
	void dumperDumpsAppointmentBookOwner() throws ParserException {
		String owner = "Owner";

		Appointment app = new Appointment("Description",
				"1/1/1000 10:00 AM", "1/1/1000 10:30 PM");

		AppointmentBook book = new AppointmentBook(owner, app);

		StringWriter sw = new StringWriter();
		TextDumper dumper = new TextDumper(sw);
		dumper.dump(book);

		sw.flush();

		String dumpedText = sw.toString();
		assertThat(dumpedText, containsString(owner));

	}

	@Test
	void dumperDumpsAppointmentBookDescription() throws ParserException {
		String desc = "Description";

		Appointment app = new Appointment("Description",
				"1/1/1000 10:00 AM", "1/1/1000 10:30 PM");

		AppointmentBook book = new AppointmentBook(desc, app);

		StringWriter sw = new StringWriter();
		TextDumper dumper = new TextDumper(sw);
		dumper.dump(book);

		sw.flush();

		String dumpedText = sw.toString();
		assertThat(dumpedText, containsString(desc));

	}

	@Test
	void dumperDumpsAppointmentBookDates() throws ParserException {
		String date = "1/1/1000 10:00";

		Appointment app = new Appointment("Description",
				"1/1/1000 10:00 AM", "1/1/1000 10:30 PM");

		AppointmentBook book = new AppointmentBook(date, app);

		StringWriter sw = new StringWriter();
		TextDumper dumper = new TextDumper(sw);
		dumper.dump(book);

		sw.flush();

		String dumpedText = sw.toString();
		assertThat(dumpedText, containsString(date));

	}
}