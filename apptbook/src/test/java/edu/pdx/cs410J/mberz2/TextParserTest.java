package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class TextParserTest {

	@Test
	void emptyFileCannotBeParsed() {
		InputStream resource =
				getClass().getResourceAsStream("emptyFile.txt");
		assertNotNull(resource);

		TextParser parser = new TextParser(new InputStreamReader(resource));
		assertThrows(ParserException.class, parser::parse);
	}

	@Test
	void parsingTest() throws ParserException {
		InputStream resource = getClass().getResourceAsStream("example.txt");

		TextParser parser = new TextParser(new InputStreamReader(resource));
		AppointmentBook first = parser.parse();

		Appointment app3 = new Appointment("early", "1/11/2021 10:10 am", "1/13/2021 10:15 am");
		Appointment app4 = new Appointment("earlier", "12/10/2021 10:10 am", "12/10/2021 10:15 am");
		Appointment app5 = new Appointment("earliest", "7/7/2021 10:10 am", "7/7/2021 10:15 am");
		AppointmentBook second = new AppointmentBook("Owner", app3);
		second.addAppointment(app4);
		second.addAppointment(app5);

		ArrayList<Appointment> appBook1 = second.getAppointments();

		for (Appointment a: appBook1) {
			first.addAppointment(a);
		}

		Project3.printAppt(first);

	}

	/*

	@Test
	void appointmentBookOwnerCanBeDumpedAndParsed() throws ParserException {
		String owner = "Owner";
		Appointment app = new Appointment("Description",
				"1/1/1000 10:00", "1/1/1000 10:30");

		AppointmentBook book = new AppointmentBook(owner, app);

		StringWriter sw = new StringWriter();
		TextDumper dumper = new TextDumper(sw);
		dumper.dump(book);

		TextParser parser = new TextParser(new StringReader(sw.toString()));
		book = parser.parse();

		assertThat(book.getOwnerName(), equalTo(owner));
	}

	 */

}