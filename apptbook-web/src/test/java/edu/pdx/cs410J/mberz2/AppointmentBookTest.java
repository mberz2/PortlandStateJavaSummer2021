package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
* Unit tests for the {@link Appointment} and {@link AppointmentBook} classes.
*/
public class AppointmentBookTest {

	@Test
	void getEndTimeStringNeedsToBeImplemented() throws ParserException {
		assertThrows(NullPointerException.class, () ->{
			Appointment appt =
					new Appointment(null, null, null);
		});
	}

	@Test
	void getEndTimeStringImplemented() throws ParserException {
		Appointment appt =
				new Appointment("test", "1/1/2000 10:10 AM",
						"1/1/2000 10:15 AM");
		assertNotNull(appt.getEndTimeString());
		assertThat(appt.getEndTimeString(),
				containsString("10:15"));
	}

	@Test
	void initiallyAllAppointmentsHaveTheSameDescription() throws ParserException {
		assertThrows(NullPointerException.class, () -> {
			Appointment appt = new Appointment(null, null, null);
		});
	}

	@Test
	void getDescriptionStringImplemented() throws ParserException {
		Appointment appt =
				new Appointment("test", "1/1/2000 10:10 AM",
						"1/1/2000 10:15 AM");
		assertNotNull(appt.getDescription());
		assertEquals("test",appt.getDescription());
	}

	@Test
	void getApptBookOwnerImplemented() throws ParserException {
		Appointment appt =
				new Appointment("test", "1/1/2000 10:10 AM",
						"1/1/2000 10:15 AM");
		AppointmentBook appBook =
				new AppointmentBook("test", appt);
		assertEquals("test", appBook.getOwnerName());
	}

	@Test
	void NewAppointmentEmptyAppointment(){
		assertThrows(UnsupportedOperationException.class, () -> {
					AppointmentBook appBook =
							new AppointmentBook("test", null);
					appBook.getAppointments();
		});
	}

	@Test
	void NewAppointmentIsNotEmpty() throws ParserException {
		Appointment appt =
				new Appointment("test", "1/1/2000 10:10 AM",
						"1/1/2000 10:15 AM");
		AppointmentBook appBook =
				new AppointmentBook("test", appt);
		appBook.getAppointments();
	}

	@Test
	void AddAppointmentIsNotEmpty(){
		assertThrows(NullPointerException.class, () ->{
			Appointment appt = new Appointment(null, null, null);
			AppointmentBook appBook =
					new AppointmentBook("test", appt);
			Appointment appt2 = new Appointment(null, null, null);
			appBook.addAppointment(appt2);
		});
	}

	@Test
	void getAppointmentEmptyAppointment(){
		assertThrows(ParserException.class, () -> {
			Appointment appt =
					new Appointment("Test", "12:00", "13:00");
			AppointmentBook appBook =
					new AppointmentBook("test", appt);

			appBook.getAppointments().clear();
			appBook.getAppointments();
		});
	}

	@Test
	void getAppointmentIsNotEmpty() throws ParserException {
		Appointment appt =
				new Appointment("test", "1/1/2000 10:10 AM",
						"1/1/2000 10:15 AM");
		AppointmentBook appBook =
				new AppointmentBook("test", appt);

		Collection<Appointment> output = appBook.getAppointments();
		assertFalse(output.isEmpty());
	}
}
