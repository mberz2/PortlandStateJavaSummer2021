package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class, an extension of AbstractAppointmentBook class, implements
 * methods for an "appointment book" object.
 *
 * <p> An object is created via a parameterized constructor and private data
 * members are accessed/retrieved via getter methods. </p>
 *
 * @author Matthew Berzinskas
 * @since 2020-6-23
 * @see AbstractAppointmentBook
 */
public class AppointmentBook<T extends AbstractAppointment>
		extends AbstractAppointmentBook<T> {

	// Suppresses default constructor, ensuring non-instatiability.
	private AppointmentBook(){}

	private String owner;
	private Collection<T> appList;

	/**
	 * Parameterized constructor for an appointment object. Sets the private
	 * data members to the values of the passed in parameters. Initializes an
	 * arrayList collection and adds the first appointment to the book.
	 *
	 * @param o String for the appointment owner.
	 * @param app Appointment object to add to collection.
	 * @see Appointment
	 */
	AppointmentBook(String o, T app){
		this.owner = o;
		this.appList = new ArrayList<>();
		appList.add(app);
	}

	/**
	 * Returns the appointment book's owner
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
	 * Returns the collection containing all appointments.
	 * @return String containing owner.
	 * @throws NullPointerException if appList is not initialized.
	 */
	@Override
	public Collection<T> getAppointments() {
		if (appList == null)
			throw new NullPointerException("Book not implemented.");
		return appList;
	}

	/**
	 * Adds an appointment to the give appointment book.
	 * @param app Appointment object to be added to the boo.
	 * @throws NullPointerException if app to add is of Null type.
	 * @see Appointment
	 */
	@Override
	public void addAppointment(T app) {
		appList.add((T) app);
	}
}
