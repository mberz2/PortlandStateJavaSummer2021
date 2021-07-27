package edu.pdx.cs410J.mberz2;

import org.junit.jupiter.api.Test;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AppointmentBookServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AppointmentBookServletTest {

	@Test
	void initiallyServletContainsNoAppointments () throws IOException {
		AppointmentBookServlet servlet = new AppointmentBookServlet();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		PrintWriter pw = mock(PrintWriter.class);

		when(response.getWriter()).thenReturn(pw);

		servlet.doGet(request, response);

		int expectedAppts = 0;
		verify(pw).println(Messages.getMappingCount(expectedAppts));
		verify(response).setStatus(HttpServletResponse.SC_OK);
	}

	private interface ServletMethodInvoker {
		void invoke(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	}

	private StringWriter invokeServletMethod(Map<String, String> params, ServletMethodInvoker invoker) throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		params.forEach((key, value) -> when(request.getParameter(key)).thenReturn(value));

		HttpServletResponse response = mock(HttpServletResponse.class);

		StringWriter sw = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(sw));

		invoker.invoke(request, response);

		verify(response).setStatus(HttpServletResponse.SC_OK);
		return sw;
	}

	@Test
	void addAppointment() throws ServletException, IOException {
		AppointmentBookServlet servlet = new AppointmentBookServlet();

		String owner = "Matt";
		String description = "Test Desc";
		String beginTime = "7/1/2021 10:00 AM";
		String endTime = "7/1/2021 10:30 AM";

		invokeServletMethod(Map.of("owner", owner,
				"description", description,
				"beginTime", beginTime,
				"endTime", endTime), servlet::doPost);

		AppointmentBook book = servlet.getAppointmentBook(owner);
		assertThat(book, notNullValue());
		assertThat(book.getOwnerName(), equalTo(owner));

		Collection<Appointment> appointments = book.getAppointments();
		assertThat(appointments, hasSize(1));

		Appointment appointment = appointments.iterator().next();
		assertThat(appointment.getDescription(), equalTo(description));
	}

	@Test
	void noApptBookIsNull() throws ServletException, IOException {
		AppointmentBookServlet servlet = new AppointmentBookServlet();

		String owner = "Matt";

		invokeServletMethod(Map.of("owner", owner), servlet::doGet);

		AppointmentBook book = servlet.getAppointmentBook(owner);
		assertNull(book);
	}

	@Test
	void validApptIsNotNull() throws ServletException, IOException {
		AppointmentBookServlet servlet = new AppointmentBookServlet();

		String owner = "Matt";
		String description = "Test Desc";
		String beginTime = "7/1/2021 10:00 AM";
		String endTime = "7/1/2021 10:30 AM";

		invokeServletMethod(Map.of("owner", owner,
				"description", description,
				"beginTime", beginTime,
				"endTime", endTime), servlet::doPost);


		invokeServletMethod(Map.of("owner", owner), servlet::doGet);

		AppointmentBook book = servlet.getAppointmentBook(owner);
		assertNotNull(book);
	}

	@Test
	void noApptSearchIsNull() throws ServletException, IOException {
		AppointmentBookServlet servlet = new AppointmentBookServlet();

		String owner = "Matt";
		String beginTime = "7/1/2021 10:00 AM";
		String endTime = "7/1/2021 10:30 AM";

		invokeServletMethod(Map.of("owner", owner,
				"beginTime", beginTime,
				"endTime", endTime), servlet::doGet);

		AppointmentBook book = servlet.getAppointmentBook(owner);
		assertNull(book);
	}

	@Test
	void validApptSearchIsNotNull() throws ServletException, IOException {
		AppointmentBookServlet servlet = new AppointmentBookServlet();

		String owner = "Matt";
		String description = "Test Desc";
		String beginTime = "7/1/2021 10:00 AM";
		String endTime = "7/1/2021 10:30 AM";

		invokeServletMethod(Map.of("owner", owner,
				"description", description,
				"beginTime", beginTime,
				"endTime", endTime), servlet::doPost);

		invokeServletMethod(Map.of("owner", owner,
				"beginTime", beginTime,
				"endTime", endTime), servlet::doGet);

		AppointmentBook book = servlet.getAppointmentBook(owner);
		assertNotNull(book);
	}

	@Test
	void deleteIsNotNull() throws ServletException, IOException {
		AppointmentBookServlet servlet = new AppointmentBookServlet();

		String owner = "Matt";
		String description = "Test Desc";
		String beginTime = "7/1/2021 10:00 AM";
		String endTime = "7/1/2021 10:30 AM";

		invokeServletMethod(Map.of("owner", owner,
				"description", description,
				"beginTime", beginTime,
				"endTime", endTime), servlet::doPost);


		invokeServletMethod(Map.of(), servlet::doDelete);

		AppointmentBook book = servlet.getAppointmentBook(owner);
		assertNull(book);
	}
}
