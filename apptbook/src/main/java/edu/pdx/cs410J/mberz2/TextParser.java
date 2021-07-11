package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.nio.file.NoSuchFileException;

import static edu.pdx.cs410J.mberz2.Project2.*;

/**
 *
 */
public class TextParser implements AppointmentBookParser<AppointmentBook> {

	private final FileInputStream file;
	private String owner;

	TextParser (FileInputStream file){
		this.file = file;
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

	@Override
	public AppointmentBook parse () throws ParserException {

		//Check empty file.

		AppointmentBook tempBook = new AppointmentBook();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.file));
			String line;

			while((line = br.readLine()) != null) {

				// Parse line based on comma to the end of the line.
				String [] parsedApp = line.split("\\|");

				//Check parsedApp
				StringParser sp = new StringParser();
				if(!sp.validateString(parsedApp)){
					System.err.println("Run with -README to see " +
							"proper formatting.");
					System.err.println(README);
					System.exit(1);
				}

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

			return tempBook;

		} catch(FileNotFoundException | NoSuchFileException exception) {
			//System.out.println("The file or directory was not found.");
			//System.exit(1);

			return null;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return null;
	}


}