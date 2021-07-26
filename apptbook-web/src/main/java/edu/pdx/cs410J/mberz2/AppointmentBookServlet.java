package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
	protected void doGet( HttpServletRequest request, HttpServletResponse response )
			throws IOException {

		response.setContentType( "text/plain" );
		PrintWriter pw = response.getWriter();

		String owner = getParameter("owner", request);
		String beginTime = getParameter("beginTime", request);
		String endTime = getParameter("endTime", request);

		if (data.isEmpty()) {
			pw.println(Messages.getMappingCount(0));
		} else if(!data.containsKey(owner)) {
				pw.println("Error: No appointment book for this owner.");
			} else if(data.containsKey(owner) && beginTime != null && endTime != null ) {
				try {
					searchPrint(owner, beginTime, endTime, response);
				} catch (ParseException e) {
					System.out.println("Issue while searching.");
				}
			} else if(owner != null && beginTime == null && endTime == null) {

				ArrayList<Appointment> appList = data.get(owner).getAppointments();
				AppointmentBook temp = new AppointmentBook(owner, appList.get(0));
				for(int i = 1; i < appList.size(); ++i)
					temp.addAppointment(appList.get(i));

				PrettyPrinter printer = new PrettyPrinter(new PrintWriter(pw));
				printer.dump(temp);
				pw.flush();
			}

		pw.flush();
		response.setStatus( HttpServletResponse.SC_OK);
	}

	private void searchPrint(String owner, String beginTime, String endTime,
	                         HttpServletResponse response)
			throws ParseException, IOException {

		PrintWriter pw = response.getWriter();

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Date min = format.parse(beginTime);
		Date max = format.parse(endTime);
		boolean found = false;

		AppointmentBook temp = new AppointmentBook();
		ArrayList<Appointment> appList = data.get(owner).getAppointments();
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
			PrettyPrinter printer = new PrettyPrinter(new PrintWriter(pw));
			printer.dump(temp);
		} else {
			pw.println("Error: No appointments found between those dates.");
		}

		pw.flush();
		response.setStatus( HttpServletResponse.SC_OK);
	}

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

	@Override
	protected void doDelete(HttpServletRequest request,
	                        HttpServletResponse response) throws IOException {
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
