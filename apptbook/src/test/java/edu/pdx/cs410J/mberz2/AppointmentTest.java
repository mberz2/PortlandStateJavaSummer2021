package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
* Unit tests for the {@link Appointment} and {@link AppointmentBook} classes.
*/
public class AppointmentTest {

	@Test
	void compareDateAndTimes () throws ParserException {
		Appointment app = new Appointment("test", "7/13/2021 2:00 pm",
				"7/13/2021 2:05 pm");
		Appointment sameBegin = new Appointment("test2", "7/13/2021 2:00 pm",
				"7/13/2021 2:15 pm");
		Appointment sameBeginEnd = new Appointment("testttt", "7/13/2021 2:00 pm",
				"7/13/2021 2:05 pm");
		Appointment sameAll = new Appointment("a", "7/13/2021 2:00 pm",
				"7/13/2021 2:05 pm");

		String [] first = {"-textfile", "TEST", "Owner",
				"Description", "7/13/2021", "10:00", "am", "7/13/2021", "10:05", "am"};

		String [] mid = {"-textfile", "TEST", "Owner",
				"Description", "7/13/2021", "10:10", "am", "7/13/2021", "11:00", "am"};

		String [] last = {"-textfile", "TEST", "Owner",
				"Description", "7/13/2021", "11:00", "am", "7/13/2021", "11:05", "am"};


		/* 0 if the argument Date is equal to this Date; a value less than 0 if
		this Date is before the Date argument; and a value greater than 0 if
		this Date is after the Date argument. */

		Appointment app1 = new Appointment("EARLIEST", "7/13/2021 10:00 am", "7/13/2021 10:05 am");
		Appointment app2 = new Appointment("MIDDLEST", "7/13/2021 10:05 am", "7/13/2021 10:10 am");
		Appointment app3 = new Appointment("LATEST", "7/13/2021 10:10 am", "7/13/2021 10:15 am");

		System.out.println(app1.compareTo(app2));
		System.out.println(app1.compareTo(app3));

		AppointmentBook appBook = new AppointmentBook("Owner", app3);
		System.out.println(appBook.getAppointments());
		appBook.addAppointment(app2);
		System.out.println(appBook.getAppointments());
		appBook.addAppointment(app1);
		System.out.println(appBook.getAppointments());

	}


	/*
	//Appointment Tests
	@Test
	void getBeginTimeStringNeedsToBeImplemented() {
		Appointment appt = new Appointment(null, null, null);
		assertThrows(UnsupportedOperationException.class,
				appt::getBeginTimeString);
	}

	@Test
	void getBeginTimeStringImplemented() {
		Appointment appt =
				new Appointment(null, "11:30", null);
		assertNotNull(appt.getBeginTimeString());
		assertEquals("11:30",appt.getBeginTimeString());
	}

	@Test
	void getEndTimeStringNeedsToBeImplemented() {
		Appointment appt =
				new Appointment(null, null, null);
		assertThrows(UnsupportedOperationException.class,
				appt::getEndTimeString);
	}

	@Test
	void getEndTimeStringImplemented() {
		Appointment appt =
		new Appointment(null, null, "11:30");
		assertNotNull(appt.getEndTimeString());
		assertEquals("11:30",appt.getEndTimeString());
	}

	@Test
	void initiallyAllAppointmentsHaveTheSameDescription() {
		Appointment appt = new Appointment(null, null, null);
		assertThrows(UnsupportedOperationException.class, appt::getDescription);
	}

	@Test
	void getDescriptionStringImplemented() {
		Appointment appt =
				new Appointment("test", null, null);
		assertNotNull(appt.getDescription());
		assertEquals("test",appt.getDescription());
	}


	//AppointmentBook Tests
	@Test
	void getApptBookOwnerNeedsToBeImplemented() {
		Appointment appt = new Appointment(null, null, null);
		AppointmentBook appBook =
				new AppointmentBook(null, appt);
		assertThrows(UnsupportedOperationException.class,
				appBook::getOwnerName);
	}

	@Test
	void getApptBookOwnerImplemented() {
		Appointment appt = new Appointment(null, null, null);
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
	void NewAppointmentIsNotEmpty(){
		Appointment appt = new Appointment(null, null, null);
		AppointmentBook appBook =
				new AppointmentBook("test", appt);
		appBook.getAppointments();
	}

	@Test
	void AddAppointmentEmptyAppointment(){
		Appointment appt = new Appointment(null, null, null);
		AppointmentBook appBook =
				new AppointmentBook("test", appt);

		assertThrows(UnsupportedOperationException.class, ()
				-> appBook.addAppointment(null));
	}

	@Test
	void AddAppointmentIsNotEmpty(){
		assertDoesNotThrow(()->{
			Appointment appt = new Appointment(null, null, null);
			AppointmentBook appBook =
					new AppointmentBook("test", appt);
			Appointment appt2 = new Appointment(null, null, null);
			appBook.addAppointment(appt2);
		});
	}

	@Test
	void getAppointmentEmptyAppointment(){
		assertThrows(UnsupportedOperationException.class, () -> {
			Appointment appt =
					new Appointment("Test", "12:00", "13:00");
			AppointmentBook appBook =
					new AppointmentBook("test", appt);

			appBook.getAppointments().clear();
			appBook.getAppointments();
		});
	}

	@Test
	void getAppointmentIsNotEmpty(){
		Appointment appt =
				new Appointment("Test", "12:00", "13:00");
		AppointmentBook appBook =
				new AppointmentBook("test", appt);

		Collection<Appointment> output = appBook.getAppointments();
		assertFalse(output.isEmpty());
	}

	*/
}
