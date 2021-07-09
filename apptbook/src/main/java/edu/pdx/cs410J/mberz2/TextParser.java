package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @param <T>
 */
public class TextParser <T extends AbstractAppointmentBook<Appointment>>
		implements AppointmentBookParser<T> {

	private final String fileName;
	private String owner;

	TextParser(String fileName){
		this.fileName = fileName;
	}

	public void setOwner(String s){
		this.owner = s;
	}

	public boolean checkOwner(String s){
		if (!this.owner.equals(s)){
			System.out.println("Wrong appointment owner. Not adding.");
			return false;
		}
		return true;
	}

	public T parse() throws ParserException {

		AppointmentBook<Appointment> tempBook = new AppointmentBook<>();

		try {

			BufferedReader br = new BufferedReader(new FileReader(this.fileName));
			String line;

			while((line = br.readLine()) != null) {

				// Parse line based on comma to the end of the line.
				String [] parsedApp = line.split("\\|");

				// Create a temporary appointment.
				Appointment app = new Appointment(parsedApp[1],
						parsedApp[2]+" "+parsedApp[3],
						parsedApp[4]+" "+parsedApp[5]);

				// If the owner of the parser is null, no owner has been set.
				// Set the owner to the first arg of the first line.
				// Otherwise, check if the owner matches, if it does, add.
				if (owner == null) {
					setOwner(parsedApp[0]);
					tempBook.setOwnerName(owner);
					tempBook.addAppointment(app);

				} else if (checkOwner(parsedApp[0])) {
					tempBook.addAppointment(app);

				}
			}

			br.close();

			return (T) tempBook;

		} catch (FileNotFoundException e){
			System.out.println("File not found.");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}