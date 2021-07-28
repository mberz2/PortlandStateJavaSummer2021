package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.web.HttpRequestHelper;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Helper class for a the servlet. Serves as a REST client for processing the
 * different requests from the main method. Handles GET, POST, DELETE requests
 * through {@link HttpRequestHelper}, eventually going to {@link AppointmentBookServlet}.
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
	public AppointmentBookRestClient(String hostName, int port) {
		this.url = String.format( "http://%s:%d/%s/%s", hostName,
				port, WEB_APP, SERVLET );
	}

	/**
	 * Helper method for GET on one parameter. Depending on the parameters in
	 * a given map, will attempt to either get all appointments of a given
	 * owner, or search for matching appointments within two dates.
	 *
	 * @param map Containing the parameters to get from URL.
	 * @return Response containing HTTP response.
	 * @throws IOException Exception handling for Input/Output.
	 */
	public Response getURL(Map<String, String> map)
			throws IOException {
		return get(this.url, map);
	}

	/**
	 * Helper method for POST of an appointment to the server. Attempts to post
	 * a given appointment, based on four parameters.
	 *
	 * @param map Map containing the appointment parameters.
	 * @return HTTP response.
	 * @throws IOException Exception handling for Input/Output.
	 */
	public Response postURL(Map <String, String> map)
			throws IOException {
		return post(this.url, map);
	}

	/**
	 * Helper method for DELETE. Attempts to delete all appointments/objects on
	 * the servlet.
	 *
	 * @throws IOException Exception handling for input/output.
	 */
	public void deleteURL() throws IOException {
		delete(this.url, Map.of());
	}
}
