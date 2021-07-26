package edu.pdx.cs410J.mberz2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AppointmentBookRestClient extends HttpRequestHelper
{
	private static final String WEB_APP = "apptbook";
	private static final String SERVLET = "appointments";

	private final String url;


	/**
	 * Creates a client to the appointment book REST service running on the given host and port
	 * @param hostName The name of the host
	 * @param port The port
	 */
	public AppointmentBookRestClient( String hostName, int port )
	{
		this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
	}

	/**
	 * Returns all keys and values from the server
	 */
	/**
	 *
	 * @return
	 * @throws IOException
	 */
	public Response getAllKeysAndValues() throws IOException
	{
		return get(this.url,  Map.of());
	}


	/**
	 *
	 * @param description
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws IOException
	 */
	public Response addAppointment( String owner, String description, String beginTime, String endTime) throws IOException {
		System.out.println("Hello from addAppointment\n");
		return post(this.url,  Map.of("owner", owner, "description", description, "beginTime", beginTime, "endTime", endTime));
	}

	/**
	 * Search for appointment with time
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws IOException
	 */
	public Response searchTime(String owner, String beginTime, String endTime) throws IOException {
		return get(this.url, Map.of("owner", owner, "beginTime", beginTime, "endTime", endTime));	}


	public Response removeAllMappings() throws IOException {
		return delete(this.url,  Map.of());
	}


}
