package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

/**
* Unit tests for the {@link Appointment} and {@link AppointmentBook} classes.
*/
public class AppointmentBookTest {

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
