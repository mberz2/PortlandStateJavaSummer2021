package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
* Unit tests for the {@link Appointment} and {@link AppointmentBook} classes.
*/
public class AppointmentTest {

	//Appointment Tests
	@Test
	void nullBeginTimeCannotBeParsed() {

		assertThrows(
				NullPointerException.class, () ->{
					new Appointment("Desc",
							null,
							"10/10/2020 10:10 AM");
				});
	}

	@Test
	void malFormedBeginTimeCannotBeParsed() {

		assertThrows(
				ParserException.class, () ->{
					new Appointment("Desc",
							"10/10/2020",
							"10/10/2020 10:10 AM");
				});
	}


	@Test
	void nullEndTimeCannotBeParsed() {

		assertThrows(
				NullPointerException.class, () ->{
					new Appointment("Desc",
							"10/10/2020 10:10 AM",
							null);
				});
	}

	@Test
	void malFormedEndTimeCannotBeParsed() {

		assertThrows(
				ParserException.class, () ->{
					new Appointment("Desc",
							"10/10/2020 10:10 AM",
							"10/10/2020");
				});
	}

	@Test
	void cannotEndBeforeBeginTime() {

		assertThrows(
				UnsupportedOperationException.class, () ->{
					new Appointment("Desc",
							"10/10/2020 10:10 AM",
							"10/09/2020 10:10 AM");
				});
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

}
