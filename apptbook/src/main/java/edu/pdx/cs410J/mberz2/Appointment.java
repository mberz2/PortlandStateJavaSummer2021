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
public class Appointment extends AbstractAppointment {

    // Suppresses default constructor, ensuring non-instatiability.
	//private Appointment(){}

	/* String containing the appointment description. */
	private final String desc;
	/* String containing the begin time in MM/DD/YYY HH:MM format */
	private final Date beginTime;
	/* String containing the end time in MM/DD/YYY HH:MM format */
	private final Date endTime;

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

		DateFormat format =
				new SimpleDateFormat("MM/dd/yyyy hh:mm", Locale.ENGLISH);
		try {
			this.beginTime = format.parse(bt);
		} catch (ParseException e){
			throw (new ParserException("Error: Unable to parse begin date."));
		}

		try {
			this.endTime = format.parse(et);
		} catch (ParseException e){
			throw (new ParserException("Error: Unable to parse begin date."));
		}
	}

	/**
	 * Method to return the appointment's begin time.
	 * @return String containing begin time. {@code MM/DD/YYYY HH:MM}
	 * @throws UnsupportedOperationException if beginTime is null.
	 */
	@Override
	public String getBeginTimeString() {
		if (beginTime == null)
			throw new UnsupportedOperationException
					("Appointment not implemented.");
		return String.valueOf(this.beginTime);
	}


	/**
	 * Method to return the appointment's begin time.
	 * @return String containing end time. {@code MM/DD/YYYY HH:MM}
	 * @throws UnsupportedOperationException if endTime is null.
	 */
	@Override
	public String getEndTimeString() {
		if (endTime == null)
		    throw new UnsupportedOperationException
				    ("Appointment not implemented.");
		return String.valueOf(this.endTime);
	}

	/**
	 * Method to return the appointment's description.
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
}
