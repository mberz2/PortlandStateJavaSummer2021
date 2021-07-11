package edu.pdx.cs410J.mberz2;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TextDumperTest {

	@Test
	void dumperDumpsAppointmentBookOwner() throws IOException {
		String owner = "Owner";
		Appointment app =
				new Appointment("test", "1/1/1000 10:00", "1/1/1000 10:30");
		AppointmentBook book = new AppointmentBook(owner, app);

		StringWriter sw = new StringWriter();
		TextDumper dumper = new TextDumper(sw);
		dumper.dump(book);



		sw.flush();

		String dumpedText = sw.toString();
		System.out.println(dumpedText);
		assertThat(dumpedText, containsString(owner));


	}
}