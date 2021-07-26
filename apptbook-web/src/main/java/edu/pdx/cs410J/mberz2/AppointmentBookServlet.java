package edu.pdx.cs410J.mberz2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>AppointmentBook</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AppointmentBookServlet extends HttpServlet
{
	private final Map<String, AppointmentBook> data = new HashMap<>();

	/**
	 * Handles an HTTP GET request from a client by writing the value of the key
	 * specified in the "key" HTTP parameter to the HTTP response.  If the "key"
	 * parameter is not specified, all of the key/value pairs are written to the
	 * HTTP response.
	 */
	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		response.setContentType( "text/plain" );

		String owner = getParameter("owner", request);
		String beginTime = getParameter("beginTime", request);
		String endTime = getParameter("endTime", request);

		if(owner != null && beginTime != null && endTime != null) {
			try {
				searchPrint(owner, beginTime, endTime, response);
			} catch (ParseException e) {
				System.out.println("Issue while searching.");
			}
		} else if(owner != null && beginTime == null && endTime == null) {
			AppointmentBook aAppointmentBook = data.get(owner);
			appointmentBookPrint(owner, aAppointmentBook, response);
		} else {
			writeAllMappings(response);
		}
	}

	/**
	 * Print the result of the search
	 * @param owner
	 * @param beginTime
	 * @param endTime
	 * @param response
	 * @throws ParseException
	 * @throws IOException
	 */
	private void searchPrint(String owner, String beginTime, String endTime, HttpServletResponse response) throws ParseException, IOException {

		PrintWriter pw = response.getWriter();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Date beginDateTime = format.parse(beginTime);
		Date endDateTime = format.parse(endTime);

		if(data.get(owner) != null) {
			Collection<Appointment> appointmentList = data.get(owner).getAppointments();
			boolean foundSomthing = false;
			boolean printStatment = false;

			for(Appointment app : appointmentList) {
				Date comparebegin = format.parse(app.getBeginTimeString());
				Date compareend = format.parse(app.getEndTimeString());
				if(comparebegin.compareTo(beginDateTime) >= 0 && compareend.compareTo(endDateTime) <= 0) {
					if(!printStatment){
						pw.println("\n- Appointment's Found -");
						pw.println("--------------------------------------------");
						printStatment = true;
					}
					foundSomthing = true;
					pw.println(Messages.printAppointment(app));
				}
			}
			if (!foundSomthing) {
				pw.println("No Appointments found within " + owner + " AppointmentBook.");
			}
		} else {
			pw.println("owner AppointmentBook not found.");
		}

		pw.flush();

		response.setStatus( HttpServletResponse.SC_OK);
	}


	/**
	 * Print the appointment Book
	 * @param owner
	 * @param aAppintmentBook
	 * @param response
	 * @throws IOException
	 */
	private void appointmentBookPrint (String owner, AppointmentBook aAppintmentBook, HttpServletResponse response) throws IOException {

		PrintWriter pw = response.getWriter();

		if(data.get(owner) != null) {
			pw.println();
			pw.println(Messages.printOwner(owner));
			Collection<Appointment> appointmentList = data.get(owner).getAppointments();
			boolean foundSomthing = false;
			for(Appointment app : appointmentList) {
				foundSomthing = true;
				pw.println(Messages.printAppointment(app));
			}
			if (!foundSomthing) {
				pw.println("No Appointments found within " + owner + " AppointmentBook." );
			}
		} else {
			pw.println("This owner doesn't have a AppointmentBook currently.");
		}

		pw.flush();

		response.setStatus( HttpServletResponse.SC_OK);
	}


	/**
	 * Handles an HTTP POST request by storing the key/value pair specified by the
	 * "key" and "value" request parameters.  It writes the key/value pair to the
	 * HTTP response.
	 */
	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		response.setContentType("text/plain");

		String owner = getParameter("owner", request);
		if (owner == null) {
			missingRequiredParameter(response, "owner");
			return;
		}

		String description = getParameter("description", request);
		if (description == null) {
			missingRequiredParameter(response, "description");
			return;
		}

		String beginTime = getParameter("beginTime", request);
		if (beginTime == null) {
			missingRequiredParameter(response, "beginTime");
			return;
		}

		String endTime = getParameter("endTime", request);
		if (endTime == null) {
			missingRequiredParameter(response, "endTime");
			return;
		}

		PrintWriter pw = response.getWriter();

		try {
			Appointment app = new Appointment(description, beginTime, endTime);
			AppointmentBook temp;
			if (data.isEmpty())
				temp = new AppointmentBook(owner, app);
				else {
					temp = data.get(owner);
					temp.addAppointment(app);
				}
			data.put(owner, temp);
		} catch (ParserException e) {
			System.err.println("Error: Parsing when creating appointment.");
			e.printStackTrace();
			System.exit(1);
		}

		pw.print("\nAdded the follow appointment to " + owner +"'s appointment book:");
		pw.println(Messages.printAppointment(description, beginTime, endTime));
		pw.println();

		pw.flush();

		response.setStatus( HttpServletResponse.SC_OK);
	}

	/**
	 * Handles an HTTP DELETE request by removing all key/value pairs.  This
	 * behavior is exposed for testing purposes only.  It's probably not
	 * something that you'd want a real application to expose.
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");

		this.data.clear();

		PrintWriter pw = response.getWriter();
		pw.println(Messages.allMappingsDeleted());
		pw.flush();

		response.setStatus(HttpServletResponse.SC_OK);

	}

	/**
	 * Writes an error message about a missing parameter to the HTTP response.
	 *
	 * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
	 */
	private void missingRequiredParameter( HttpServletResponse response, String parameterName )
			throws IOException
	{
		String message = Messages.missingRequiredParameter(parameterName);
		response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
	}

	/**
	 * Writes all of the key/value pairs to the HTTP response.
	 *
	 * The text of the message is formatted with
	 * {@link Messages#formatKeyValuePair(String, String)}
	 */
	private void writeAllMappings( HttpServletResponse response ) throws IOException
	{
		PrintWriter pw = response.getWriter();
		pw.println(Messages.getMappingCount(data.size()));

		for (Map.Entry<String, AppointmentBook> entry : this.data.entrySet()) {
			pw.println(Messages.formatKeyValuePair(entry.getKey(), entry.getValue().toString()));
		}

		pw.flush();

		response.setStatus( HttpServletResponse.SC_OK );
	}

	/**
	 * Returns the value of the HTTP request parameter with the given name.
	 *
	 * @return <code>null</code> if the value of the parameter is
	 *         <code>null</code> or is the empty string
	 */
	private String getParameter(String name, HttpServletRequest request) {
		String value = request.getParameter(name);
		if (value == null || "".equals(value)) {
			return null;

		} else {
			return value;
		}
	}

}
