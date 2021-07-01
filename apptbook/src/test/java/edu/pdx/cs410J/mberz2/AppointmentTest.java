package edu.pdx.cs410J.mberz2;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
* Unit tests for the @see:edu.pdx.cs410J.mberz2.Appointment class.
*/
public class AppointmentTest {

	//Appointment Tests
	@Test
	public void testPrivateApptConstructor() throws Exception {
		Constructor<Appointment> constructor =
				Appointment.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()),
				"Constructor is not private");

		constructor.setAccessible(true);
		constructor.newInstance();
	}

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
	public void testPrivateApptBookConstructor() throws Exception {
		Constructor<AppointmentBook> constructor =
				AppointmentBook.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()),
				"Constructor is not private");

		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	void getApptBookOwnerNeedsToBeImplemented() {
		Appointment appt = new Appointment(null, null, null);
		AppointmentBook<Appointment> appBook =
				new AppointmentBook<>(null, appt);
		assertThrows(UnsupportedOperationException.class,
				appBook::getOwnerName);
	}

	@Test
	void getApptBookOwnerImplemented() {
		Appointment appt = new Appointment(null, null, null);
		AppointmentBook<Appointment> appBook =
				new AppointmentBook<>("test", appt);
		assertEquals("test", appBook.getOwnerName());
	}

	@Test
	void NewAppointmentEmptyAppointment(){
		assertThrows(UnsupportedOperationException.class, () -> {
					AppointmentBook<Appointment> appBook =
							new AppointmentBook<>("test", null);
		});
	}

	@Test
	void NewAppointmentIsNotEmpty(){
		Appointment appt = new Appointment(null, null, null);
		AppointmentBook<Appointment> appBook =
				new AppointmentBook<>("test", appt);
	}

	@Test
	void AddAppointmentEmptyAppointment(){
		Appointment appt = new Appointment(null, null, null);
		AppointmentBook<Appointment> appBook =
				new AppointmentBook<>("test", appt);

		assertThrows(UnsupportedOperationException.class, ()
				-> appBook.addAppointment(null));
	}

	@Test
	void AddAppointmentIsNotEmpty(){
		assertDoesNotThrow(()->{
			Appointment appt = new Appointment(null, null, null);
			AppointmentBook<Appointment> appBook =
					new AppointmentBook<>("test", appt);
			Appointment appt2 = new Appointment(null, null, null);
			appBook.addAppointment(appt2);
		});
	}

	@Test
	void getAppointmentEmptyAppointment(){
		assertThrows(UnsupportedOperationException.class, () -> {
			Appointment appt =
					new Appointment("Test", "12:00", "13:00");
			AppointmentBook<Appointment> appBook =
					new AppointmentBook<>("test", appt);

			appBook.getAppointments().clear();
			appBook.getAppointments();
		});
	}

	@Test
	void getAppointmentIsNotEmpty(){
		Appointment appt =
				new Appointment("Test", "12:00", "13:00");
		AppointmentBook<Appointment> appBook =
				new AppointmentBook<>("test", appt);

		Collection<Appointment> output = appBook.getAppointments();
		assertFalse(output.isEmpty());
	}

}
