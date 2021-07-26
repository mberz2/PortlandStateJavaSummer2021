package edu.pdx.cs410J.mberz2;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
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

}
