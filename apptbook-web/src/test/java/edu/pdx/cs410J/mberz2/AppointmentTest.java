package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
* Unit tests for the {@link Appointment} and {@link AppointmentBook} classes.
*/
public class AppointmentTest {

	//Appointment Tests
	@Test
	void nullBeginTimeCannotBeParsed() {
		assertThrows(NullPointerException.class, () ->
						new Appointment("Desc",
						null,
						"10/10/2020 10:10 AM"));
	}

	@Test
	void malFormedBeginTimeCannotBeParsed() {

		assertThrows(
				ParserException.class, () ->
						new Appointment("Desc",
						"10/10/2020",
						"10/10/2020 10:10 AM"));
	}


	@Test
	void nullEndTimeCannotBeParsed() {

		assertThrows(
				NullPointerException.class, () ->
						new Appointment("Desc",
						"10/10/2020 10:10 AM",
						null));
	}

	@Test
	void malFormedEndTimeCannotBeParsed() {

		assertThrows(
				ParserException.class, () ->
						new Appointment("Desc",
						"10/10/2020 10:10 AM",
						"10/10/2020"));
	}

	@Test
	void cannotEndBeforeBeginTime() {

		assertThrows(
				UnsupportedOperationException.class, () ->
						new Appointment("Desc",
						"10/10/2020 10:10 AM",
						"10/09/2020 10:10 AM"));
	}

	@Test
	void getBeginTimeStringReturnsFormattedString() throws ParserException {

		String e = "10/10/20 10:10 AM";
		Appointment app = new Appointment("Desc",
				"10/10/2020 10:10 AM",
				"10/11/2020 10:10 AM");

		assertEquals(e, app.getBeginTimeString());
	}

	@Test
	void getEndTimeStringReturnsFormattedString() throws ParserException {

		String e = "10/11/20 10:10 AM";
		Appointment app = new Appointment("Desc",
				"10/10/2020 10:10 AM",
				"10/11/2020 10:10 AM");

		assertEquals(e, app.getEndTimeString());
	}

	@Test
	void compareLessThan () throws ParserException {
		Appointment app = new Appointment("Desc",
				"10/09/2020 10:10 AM",
				"10/11/2020 10:10 AM");


		Appointment app2 = new Appointment("Desc",
				"10/10/2021 10:10 AM",
				"10/11/2021 10:10 AM");

		int e = app.compareTo(app2);

		assertEquals(e, -1);
	}

	@Test
	void compareEqual () throws ParserException {
		Appointment app = new Appointment("Desc",
				"10/10/2020 10:10 AM",
				"10/11/2020 10:10 AM");


		Appointment app2 = new Appointment("Desc",
				"10/10/2020 10:10 AM",
				"10/11/2020 10:10 AM");

		int e = app.compareTo(app2);
		System.out.println(e);

		assertEquals(e, 0);
	}

	@Test
	void compareGreater () throws ParserException {
		Appointment app = new Appointment("Desc",
				"10/15/2020 10:10 AM",
				"10/16/2020 10:10 AM");


		Appointment app2 = new Appointment("Desc",
				"10/12/2020 10:10 AM",
				"10/12/2020 10:10 AM");

		int e = app.compareTo(app2);
		System.out.println(e);

		assertEquals(e, 1);
	}

	@Test
	void getDescriptionReturnsString () throws ParserException {
		Appointment app = new Appointment("Desc",
				"10/15/2020 10:10 AM",
				"10/16/2020 10:10 AM");
		assertEquals(app.getDescription(), "Desc");
	}

	@Test
	void nullGetDescriptionThrowsException () {
		assertThrows(UnsupportedOperationException.class, () ->{
			Appointment app = new Appointment(null,
					"10/15/2020 10:10 AM",
					"10/16/2020 10:10 AM");
			app.getDescription();
		});
	}
}
