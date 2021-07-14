package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;
import java.io.*;

/**
 * This class implements read-only class {@link AppointmentBookParser}.
 * This class contains methods for the parsing of a BufferedReader into an
 * appointmentBook object.
 *
 *  @author Matthew Berzinskas
 *  @since 2020-6-23
 *  @see AppointmentBook
 */
public class TextParser implements AppointmentBookParser<AppointmentBook> {

	/* Buffered reader containing the input stream. */
	private final BufferedReader reader;

	/**
	 * Default constructor.
	 *
	 * @param reader Reader object containing the input stream.
	 */
	TextParser (Reader reader){
		this.reader = new BufferedReader(reader);
	}

	/**
	 * Method for creating, parsing, and returning an AppointmentBook object
	 * from the parser's BufferedReader data member. The method first checks
	 * if the reader is ready, then reads line by line. If the method is unable
	 * to validate the input against the requirements of an apptBook object, it
	 * will exit and notify the user of a variety of errors.
	 *
	 * @return AppointmentBook object containing the contents of the reader.
	 * @throws ParserException Exception handling for empty files.
	 */
	@Override
	public AppointmentBook parse () throws ParserException {

		try{
			if(!reader.ready()){
				throw new ParserException("Error: Malformed or empty file.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		try {

			AppointmentBook tempBook = new AppointmentBook();
			String line;

			while((line = reader.readLine()) != null) {

				// Parse line based on comma to the end of the line.
				String [] newArgs = line.split("\\|");

				if(newArgs[0].equals("\n") || newArgs[0].equals("")
						|| newArgs.length < 8)
					throw (new ParserException("Error: Malformed file."));

				// Create a temporary appointment.
				Appointment app = new Appointment(newArgs[1],
						newArgs[2]+" "+newArgs[3]+" "+newArgs[4],
						newArgs[5]+" "+newArgs[6]+" "+newArgs[7]);

				tempBook.setOwnerName(newArgs[0]);
				tempBook.addAppointment(app);

			}

			reader.close();
			return tempBook;

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return null;
	}
}