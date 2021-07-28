package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MessagesTest {

	@Test
	void printOwnerReturnsString() {
		assertThat(Messages.printOwner("Owner"),
		    equalTo("Owner"));
	}

  @Test
  void findAppointmentsInvalid() throws ParserException {

		Map <String, AppointmentBook> data = new HashMap<>();

		Appointment app = new Appointment("description",
				"1/1/2020 09:00 AM",
				"1/1/2020 09:30 AM");

		AppointmentBook appBook = new AppointmentBook("owner1", app);
		data.put("owner1", appBook);

		String result = Messages.findAppointments(data, "owner1",
				"1/1/2020 10:00 AM",
				"1/1/2020 11:00 AM");

	  assertThat(result, equalTo("** Error: No appointments found between those dates."));
  }

	@Test
	void findAppointmentsValid() throws ParserException {

		Map <String, AppointmentBook> data = new HashMap<>();

		Appointment app = new Appointment("description",
				"1/1/2020 09:00 AM",
				"1/1/2020 09:30 AM");

		AppointmentBook appBook = new AppointmentBook("owner1", app);
		data.put("owner1", appBook);

		String result = Messages.findAppointments(data, "owner1",
				"1/1/2020 08:50 AM",
				"1/1/2020 09:30 AM");

		assertThat(result, containsString("Appointment Book for: owner1"));
	}


	@Test
	void printAppointment() throws ParserException {
		Appointment app = new Appointment("description",
				"1/1/2020 09:00 AM",
				"1/1/2020 09:30 AM");
		String result = Messages.printAppointment(app);
		assertThat(result, containsString("From"));
	}
}
