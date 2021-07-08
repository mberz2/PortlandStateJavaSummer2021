package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
		DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy HH:mm");

		try {
			BufferedReader br = new BufferedReader(new FileReader(this.fileName));
			String line;

			while((line = br.readLine()) != null) {

				// Parse line based on comma to the end of the line.
				String [] parsedApp = line.split(",\\s");

				parsedApp[2] = String.valueOf(dateFormat.parse(parsedApp[2]+" "+parsedApp[3]));
				System.out.println(parsedApp[2]);

				// Create a temporary appointment.
				Appointment app = new Appointment(parsedApp[1],
						parsedApp[2]+parsedApp[3],
						parsedApp[4]+parsedApp[5]);

				// If the owner of the parser is null, no owner has been set.
				// Set the owner to the first arg of the first line.
				// Otherwise, check if the owner matches, if it does, add.
				if (owner == null) {
					setOwner(parsedApp[0]);
				} else if (checkOwner(parsedApp[0])) {
					this.owner = parsedApp[0];
					tempBook.setOwnerName(owner);
				} else {
					continue;
				}

				tempBook.addAppointment(app);
			}

			br.close();

			return (T) tempBook;

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return null;
	}
}