package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @param <T>
 */
public class TextParser <T extends AbstractAppointmentBook<Appointment>>
		implements AppointmentBookParser<T> {

	private final String fileName;
	private final Collection<T> appList;

	/**
	 *
	 * @param fileName
	 * @param app
	 */
	TextParser(String fileName, T app){
		this.fileName = fileName;
		this.appList = new ArrayList<>();
	}

	/**
	 *
	 * @return
	 * @throws ParserException
	 */
	@Override
	public T parse() throws ParserException {
		try {
			FileReader input = new FileReader(this.fileName);
			BufferedReader bufferedReader = new BufferedReader(input);
			String oneLine;

			while ((oneLine = bufferedReader.readLine()) != null) {
				String[] parsedAppointment = oneLine.split(";");
				appointmentBook.setOwner(parsedAppointment[0]);
				Appointment appointment = new Appointment(parsedAppointment[1], parsedAppointment[2], parsedAppointment[3]);
				appointmentBook.addAppointment(appointment);
			}
		} catch (FileNotFoundException e) {
			throw new ParserException("FIle not Found.");
		} catch (IOException e) {
			throw new ParserException("Invalid file.");
		}

		return appointmentBook;
	}
}