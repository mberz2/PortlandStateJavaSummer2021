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
public class AppointmentBookRestClient extends HttpRequestHelper {

	/* String containing the WEB_APP path */
	private static final String WEB_APP = "apptbook";

	/* String containing the SERVLET path */
	private static final String SERVLET = "appointments";

	/* String containing the url for requests */
	private final String url;

	/**
	 * Method sets up the servlet to the specified static strings containing
	 * the web_app path, and servlet path and the passed in host/port.
	 *
	 * @param hostName String containing the desired hostName.
	 * @param port String containing the desired port.
	 */
	@SuppressWarnings("DefaultLocale")
	public AppointmentBookRestClient(String hostName, int port ) {
		this.url = String.format( "http://%s:%d/%s/%s", hostName,
				port, WEB_APP, SERVLET );
	}

	/**
	 * Helper method for GET on one parameter. Attempts to get all appointment
	 * matching the passed in owner and return to the response.
	 *
	 * @param owner Owner to find all appointments for.
	 * @return Response containing HTTP response.
	 * @throws IOException Exception handling for Input/Output.
	 */
	public Response getAllAppointments(String owner) throws IOException {
		return get(this.url, Map.of("owner", owner));
	}

	/**
	 * Helper method for GET on three parameters. Attempts to get all appts.
	 * between a beginning and ending date.
	 *
	 * @param owner Owner to find all appointments for.
	 * @param beginTime Beginning time to search for.
	 * @param endTime Ending time to search for.
	 * @return Response containing HTTP response.
	 * @throws IOException Exception handling for Input/Output.
	 */
	public Response searchTime(String owner,
	                           String beginTime,
	                           String endTime)
			throws IOException {
		return get(this.url, Map.of("owner", owner,
				"beginTime", beginTime,
				"endTime", endTime));	}

	/**
	 * Helper method for POST of an appointment to the server. Attempts to post
	 * a given appointment based on four parameters.
	 *
	 * @param owner Owner of the appointment.
	 * @param description Appointment description.
	 * @param beginTime Begintime of the appointment.
	 * @param endTime Endtime of the appointment.
	 * @return HTTP response.
	 * @throws IOException Exception handling for Input/Output.
	 */
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

	/**
	 * Helper method for DELETE. Attempts to delete all appointments/objects on
	 * the servlet.
	 *
	 * @throws IOException Exception handling for input/output.
	 */
	public void deleteAllAppointments() throws IOException {
		delete(this.url, Map.of());
	}
}
