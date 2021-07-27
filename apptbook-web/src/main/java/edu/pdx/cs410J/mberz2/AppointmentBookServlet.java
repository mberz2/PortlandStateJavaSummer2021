package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This is the main class method for the CS410J Appointment Book Project.
 * <p>The program simulates the receiving an appointment via command line
 * arguments and proceeds through operations to validate the input, process
 * a limited number of options, and then creates an {@code Appointment}. This
 * appointment is added to a server that handles appointment book objects.
 *
 * @author Matthew Berzinskas
 * @since 2020-6-23
 * @see Appointment
 * @see AppointmentBook
 */
public class AppointmentBookServlet extends HttpServlet
{
	/* Map containing the key,values for appointment books */
	private final Map<String, AppointmentBook> data = new HashMap<>();

	/**
	 * Handles an HTTP GET request from a client by writing the value of the key
	 * specified in the "key" HTTP parameter to the HTTP response. Depending on
	 * how many parameters are specified, the GET will perform either: getting
	 * all appointments belonging to a user, or performing a search between two
	 * dates specified.
	 *
	 * @param request Request containing parameters.
	 * @param response HTTP response to return.
	 * @throws IOException For handing failures in input/output.
	 */
	@Override
	protected void doGet( HttpServletRequest request,
	                      HttpServletResponse response ) throws IOException {

		response.setContentType( "text/plain" );
		PrintWriter pw = response.getWriter();

		String owner = getParameter("owner", request);
		String beginTime = getParameter("beginTime", request);
		String endTime = getParameter("endTime", request);

		if (data.isEmpty()) {
			pw.println(Messages.getAppointmentCount(data.size()));
		} else if(!data.containsKey(owner)) {
				pw.println(Messages.printMissingOwner());
			} else if(data.containsKey(owner) &&
				beginTime != null && endTime != null ) {

					pw.println(Messages.findAppointments(data,
							owner, beginTime, endTime));

					/*
									try {
					SimpleDateFormat format =
							new SimpleDateFormat("MM/dd/yyyy hh:mm a",
											Locale.ENGLISH);
					Date min = format.parse(beginTime);
					Date max = format.parse(endTime);
					boolean found = false;

					AppointmentBook temp = new AppointmentBook();
					ArrayList<Appointment> appList
							= data.get(owner).getAppointments();
					for(Appointment app : appList) {

						Date d = app.getBeginTime();

						boolean include = d.after(min) && d.before(max);
						System.out.println(include);

						if (include){
							temp.setOwnerName(owner);
							temp.addAppointment(app);
							found = true;
						}
					}

					if (found){
						PrettyPrinter printer
								= new PrettyPrinter(new PrintWriter(pw));
						printer.dump(temp);
					} else {
						pw.println(Messages.printNoAppointmentsFound());
					}



				} catch (ParseException e) {

				}
				*/
			} else if(owner != null && beginTime == null && endTime == null) {

				AppointmentBook temp = getAppointmentBook(owner);
				PrettyPrinter printer = new PrettyPrinter(new PrintWriter(pw));
				printer.dump(temp);
				pw.flush();
			}

		pw.flush();
		response.setStatus( HttpServletResponse.SC_OK);
	}

	/**
	 * Helper method for doGet. Searches the map for a matching owner, and if
	 * it exists, returns an appointment book to the calling routine.
	 *
	 * @param o String containing the owner to find in the map.
	 * @return Null if no existing owner, or an appointment book.
	 */
	public AppointmentBook getAppointmentBook(String o){
		if(data.containsKey(o))
			return data.get(o);

		return null;
	}

	/**
	 * Handles an HTTP POST request from a client by reading the values of the
	 * keys specified in the "key" HTTP parameter to the HTTP response. If all
	 * keys/parameters are present, the function will post a new appointment to
	 * the server. It checks to see if the owner exists in the data map already.
	 *
	 * @param request Request containing parameters.
	 * @param response HTTP response to return.
	 * @throws IOException For handing failures in input/output.
	 */
	@Override
	protected void doPost( HttpServletRequest request,
	                       HttpServletResponse response ) throws IOException
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
		Appointment app = null;
		
		try {
			app = new Appointment(description, beginTime, endTime);
			AppointmentBook temp;

			if (data.get(owner) == null) {
				temp = new AppointmentBook(owner, app);
			} else {
				temp = data.get(owner);
				temp.addAppointment(app);
			}
			data.put(owner, temp);

		} catch (ParserException e) {
			System.err.println("Error: Parsing when creating appointment.");
			e.printStackTrace();
			System.exit(1);
		}

		pw.print("\nAdded the follow appointment to " +
				owner +"'s appointment book:");
		pw.println(Messages.printAppointment(app));
		pw.println();

		pw.flush();
		response.setStatus( HttpServletResponse.SC_OK);
	}

	/**
	 * Handles an HTTP DELETE request from a client. Clears the data map.
	 *
	 * @param request Request containing no parameters.
	 * @param response HTTP response to return.
	 * @throws IOException For handing failures in input/output.
	 */
	@Override
	protected void doDelete(HttpServletRequest request,
	                        HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");

		this.data.clear();

		PrintWriter pw = response.getWriter();
		pw.println(Messages.deleteAllAppointments());
		pw.flush();

		response.setStatus(HttpServletResponse.SC_OK);

	}

	/**
	 * Writes an error message about a missing parameter to the HTTP response.
	 *
	 * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
	 */
	private void missingRequiredParameter( HttpServletResponse response,
	                                       String parameterName )
			throws IOException {
		String message = Messages.missingRequiredParameter(parameterName);
		response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
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
