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
		//System.out.println("DONE WITH FILE:\n"+first.getAppointments());

		Appointment app3 = new Appointment("early", "7/11/1900 10:10 am", "7/13/1900 10:15 am");
		Appointment app4 = new Appointment("earlier", "7/10/1900 10:10 am", "7/10/1900 10:15 am");
		Appointment app5 = new Appointment("earliest", "7/7/1900 10:10 am", "7/7/1900 10:15 am");
		AppointmentBook second = new AppointmentBook("Owner", app3);
		second.addAppointment(app4);
		second.addAppointment(app5);

		AppointmentBook newBook = new AppointmentBook();
		newBook.setOwnerName(first.getOwnerName());

		ArrayList<Appointment> appBook1 = second.getAppointments();
		ArrayList<Appointment> appBook2 = first.getAppointments();

		for (Appointment a: appBook1) {
			newBook.addAppointment(a);
		}

		for (Appointment a: appBook2){
			newBook.addAppointment(a);
		}

		System.out.println(newBook.getAppointments());

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