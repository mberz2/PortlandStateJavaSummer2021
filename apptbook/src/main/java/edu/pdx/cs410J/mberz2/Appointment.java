package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.ParserException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class extends the read-only {@link AbstractAppointment} class.
 * The class implements methods for an "appointment" object.
 *
 * <p> An object is created via a parameterized constructor and private data
 * members are accessed/retrieved via getter methods. </p>
 *
 * @author Matthew Berzinskas
 * @since 2020-6-23
 * @see AbstractAppointment
 */
public class Appointment extends AbstractAppointment
		implements Comparable<Appointment>{

	/* String containing the appointment description. */
	private final String desc;
	/* String containing the begin time in MM/DD/YYYY HH:MM format */
	private final Date beginTime;
	/* String containing the end time in MM/DD/YYYY HH:MM format */
	private final Date endTime;
	/* DateFormat object containing the date format */
	DateFormat dateFormat =
			new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.ENGLISH);

	/**
	 * Parameterized constructor for an appointment object. Sets the private
	 * data members to the values of the passed in parameters.
	 *
	 * @param d String for the appointment description.
	 * @param bt String for the appt. begin time. {@code MM/DD/YYYY HH:MM}
	 * @param et String for the appt. end time. {@code MM/DD/YYYY HH:MM}
	 */
	Appointment (String d, String bt, String et) throws ParserException {
		this.desc = d;

		try {
			this.beginTime = dateFormat.parse(bt.trim());
		} catch (ParseException e){
			throw (new ParserException("Error: Unable to parse begin date."));
		}

		try {
			this.endTime = dateFormat.parse(et.trim());
		} catch (ParseException e){
			throw (new ParserException("Error: Unable to parse begin date."));
		}

		if (this.getBeginTime().getTime() > this.getEndTime().getTime()){
			System.err.println("Error: End time is before begin time of the" +
					"appointment.");
			System.exit(1);
		}
	}

	/**
	 * Method to get the raw date object for beginTime, for comparable.
	 *
	 * @return Date object containing the full date.
	 */
	@Override
	public Date getBeginTime() {
		return beginTime;
	}


	/**
	 * Method to return the appointment's begin time as a formatted string.
	 *
	 * @return String containing begin time. {@code MM/DD/YYYY HH:MM am/pm}
	 * @throws UnsupportedOperationException if beginTime is null.
	 */
	@Override
	public String getBeginTimeString() {
		if (beginTime == null)
			throw new UnsupportedOperationException
					("Appointment not implemented.");

		return DateFormat.getDateInstance(DateFormat.SHORT).format(beginTime)
				+ " "
				+ DateFormat.getTimeInstance(DateFormat.SHORT).format(beginTime);

	}

	/**
	 * Method to get the raw date object for endTime, for comparable.
	 *
	 * @return Date object containing the full date.
	 */
	@Override
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * Method to return the appointment's end time as a formatted string.
	 *
	 * @return String containing end time. {@code MM/DD/YYYY HH:MM am/pm}
	 * @throws UnsupportedOperationException if endTime is null.
	 */
	@Override
	public String getEndTimeString() {
		if (endTime == null)
		    throw new UnsupportedOperationException
				    ("Appointment not implemented.");

		return DateFormat.getDateInstance(DateFormat.SHORT).format(endTime)
				+ " "
				+ DateFormat.getTimeInstance(DateFormat.SHORT).format(endTime);
	}

	/**
	 * Method to return the appointment's description.
	 *
	 * @return String containing the description.
	 * @throws UnsupportedOperationException if desc is null.
	 */
	@Override
	public String getDescription() {
		if (desc == null)
		    throw new UnsupportedOperationException
				    ("Appointment not implemented.");
		return this.desc;
	}

	/**
	 * Method to enforce implementation of {@link Comparable}. Checks two
	 * appointment objects for relative ordering based first on start time, then
	 * on ending time if the their start times are the same, then on the lexi-
	 * graphical value of their descriptions if both start/end time are same.
	 *
	 * @param app Appointment to compare against this instance.
	 * @return Value based on comparability.
	 */
	@Override
	public int compareTo(Appointment app) {

		/* If any of the argument values are null, throw exception */
		if (app.getBeginTime() == null
				|| app.getEndTime() == null
				|| app.getDescription() == null)
			throw (new NullPointerException("Error: Missing date/time."));

		/* Get initial difference value */
		int diff = this.getBeginTime().compareTo(app.getBeginTime());

		/* Same beginning date/time. */
		if (diff == 0)

			/* Check ending time. */
			diff = this.getEndTime().compareTo(app.getEndTime());

			if (diff == 0)

				/* Same beginning and end times. Compare desc. */
				return this.getDescription().compareTo(app.getDescription());

		return diff;
	}
}