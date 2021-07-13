package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;
import java.io.*;
import static edu.pdx.cs410J.mberz2.Project2.*;

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

		AppointmentBook tempBook = new AppointmentBook();

		try{
			if(!reader.ready()){
				throw new ParserException("Error: Malformed or empty file.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		try {

			String line;

			while((line = reader.readLine()) != null) {

				// Parse line based on comma to the end of the line.
				String [] parsedApp = line.split("\\|");

				//Check parsedApp
				StringParser sp = new StringParser();
				if(!sp.validateString(parsedApp)){
					System.err.println("Error: File not properly formatted.\n" +
							"Run with -README to see proper formatting.");
					System.err.println(README);
					System.exit(1);
				}

				// Create a temporary appointment.
				Appointment app = new Appointment(parsedApp[1],
						parsedApp[2]+" "+parsedApp[3],
						parsedApp[4]+" "+parsedApp[5]);

				tempBook.setOwnerName(parsedApp[0]);
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