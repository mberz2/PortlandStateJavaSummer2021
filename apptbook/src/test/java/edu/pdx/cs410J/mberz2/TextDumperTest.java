package edu.pdx.cs410J.mberz2;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TextDumperTest {

	@Test
	void dumperDumpsAppointmentBookOwner() throws IOException {
		String owner = "Owner";
		Appointment app = new Appointment("Description",
				"1/1/2020 10:00", "1/1/2020 10:30");

		AppointmentBook book = new AppointmentBook(owner, app);
		StringWriter sw = new StringWriter();
		Collection<Appointment> apps = book.getAppointments();

		for (Appointment a: apps) {
			String[] btSplit = a.getBeginTimeString().split("\\s");
			String[] etSplit = a.getBeginTimeString().split("\\s");

			String toFile = book.getOwnerName()
					+ "|" + a.getDescription()
					+ "|" + btSplit[0] + "|" + btSplit[1]
					+ "|" + etSplit[0] + "|" + etSplit[1]
					+ "\n";

			sw.write(toFile);
		}

		TextDumper dumper = new TextDumper(sw);
		dumper.setFileName("file");
		dumper.dump(book);

		sw.flush();

		String dumpedText = sw.toString();
		assertThat(dumpedText, containsString(owner));

	}
}