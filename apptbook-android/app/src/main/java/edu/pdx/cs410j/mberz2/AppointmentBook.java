package edu.pdx.cs410j.mberz2;

import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class, an extension of {@link AbstractAppointmentBook} class, implements
 * methods for an "appointment book" object.
 *
 * <p> An object is created via a parameterized constructor and private data
 * members are accessed/retrieved via getter methods. </p>
 *
 * @author Matthew Berzinskas
 * @since 2020-6-23
 * @see AbstractAppointmentBook
 */
public class AppointmentBook extends AbstractAppointmentBook<Appointment> {

	/* String containing the appointment book owner */
	private String owner;

	/* Collection containing an arrayList of appointment objects. */
	private ArrayList<Appointment> appList = new ArrayList<>();

	/**
	 * Default constructor for an AppointmentBook Object.
	 */
	public AppointmentBook(){}

	/**
	 * Parameterized constructor for an appointment object. Sets the private
	 * data members to the values of the passed in parameters. Initializes an
	 * arrayList collection and adds the first appointment to the book.
	 *
	 * @param o String for the appointment owner.
	 * @param app Appointment object to add to collection.
	 * @see Appointment
	 */
	public AppointmentBook(String o, Appointment app){
		this.owner = o;
		this.appList = new ArrayList<>();
		addAppointment(app);
	}

	/**
	 * Returns the appointment book's owner
	 *
	 * @return String containing owner.
	 * @throws UnsupportedOperationException if owner is null.
	 */
	@Override
	public String getOwnerName() {
		if (owner == null)
			throw new UnsupportedOperationException("Book not implemented.");
		return this.owner;
	}

	/**
	 * Setter to set the owner data member to the passed in argument.
	 *
	 * @param o String containing the owner argument.
	 */
	public void setOwnerName(String o) {
		this.owner = o;
	}

	/**
	 * Returns the collection containing all appointments.
	 *
	 * @return String containing owner.
	 */
	@Override
	public ArrayList<Appointment> getAppointments() {
		if (this.appList == null || this.appList.isEmpty())
			throw new UnsupportedOperationException("Appointment is empty.");
		return appList; }

	/**
	 * Adds an appointment to the give appointment book. Uses sorting from
	 * {@link java.lang.reflect.Array}, implemented in Appointment.
	 *
	 * @param app Appointment object to be added to the boo.
	 * @throws UnsupportedOperationException if appointment is null.
	 * @see Appointment
	 */
	@Override
	public void addAppointment(Appointment app) {
		if (app == null)
			throw new UnsupportedOperationException("Appointment is empty.");

		appList.add(app);
		Collections.sort(appList);
	}

}