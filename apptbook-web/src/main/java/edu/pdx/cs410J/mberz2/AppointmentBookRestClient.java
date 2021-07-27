package edu.pdx.cs410J.mberz2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Map;

/**
 * Helper class for a the servlet. Serves as a REST client for processing the
 * different requests from the main method. Handles GET, POST, DELETE requests
 * through {@see HttpRequestHelper}, eventually going to
 * {@see AppointmentBookServlet}.
 *
 * @author Matthew Berzinskas
 * @since 2020-6-23
 * @see Appointment
 * @see AppointmentBook
 */
public class AppointmentBookRestClient extends HttpRequestHelper
{
	private static final String WEB_APP = "apptbook";
	private static final String SERVLET = "appointments";

	private final String url;

	@SuppressWarnings("DefaultLocale")
	public AppointmentBookRestClient(String hostName, int port ) {
		this.url = String.format( "http://%s:%d/%s/%s", hostName,
				port, WEB_APP, SERVLET );
	}

	public Response getAllAppointments(String owner) throws IOException {
		return get(this.url, Map.of("owner", owner));
	}

	public Response addAppointment( String owner,
	                                String description,
	                                String beginTime,
	                                String endTime)
			throws IOException {
		return post(this.url, Map.of("owner", owner,
				"description", description,
				"beginTime", beginTime,
				"endTime", endTime));
	}

	public Response searchTime(String owner,
	                           String beginTime,
	                           String endTime)
			throws IOException {
		return get(this.url, Map.of("owner", owner,
				"beginTime", beginTime,
				"endTime", endTime));	}

	public void deleteAllAppointments() throws IOException {
		delete(this.url, Map.of());
	}

	@VisibleForTesting
	Response postToMyURL(Map<String, String> entries) throws IOException {
		return post(this.url, entries);
	}
}
