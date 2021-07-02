package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.AbstractAppointment;

/**
 * This class, an extension of {@link AbstractAppointment}, implements methods
 * for an "appointment" object.
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

	private final String desc;
	private final String beginTime;
	private final String endTime;

	/**
	 * Parameterized constructor for an appointment object. Sets the private
	 * data members to the values of the passed in parameters.
	 *
	 * @param d String for the appointment description.
	 * @param bt String for the appt. begin time. {@code MM/DD/YYYY HH:MM}
	 * @param et String for the appt. end time. {@code MM/DD/YYYY HH:MM}
	 */
	Appointment (String d, String bt, String et){
		this.desc = d;
		this.beginTime = bt;
		this.endTime = et;
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
		return this.beginTime;
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
		return this.endTime;
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
