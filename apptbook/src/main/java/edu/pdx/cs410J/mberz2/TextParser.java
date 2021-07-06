package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;

public class TextParser implements AppointmentBookParser {

	private final String fileName;

	TextParser(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public AbstractAppointmentBook parse() throws ParserException {
		try {
			FileReader input = new FileReader(this.fileName);
			BufferedReader bufferedReader = new BufferedReader(input);
			String oneLine;

			while ((oneLine = bufferedReader.readLine()) != null) {
				String[] parsedAppointment = oneLine.split(";");

				Appointment app = new Appointment (parsedAppointment[1],
						parsedAppointment[2], parsedAppointment[3]);

				AppointmentBook<AbstractAppointment> appBook =
						new AppointmentBook<>(parsedAppointment[0], app);

			}
		} catch (FileNotFoundException e) {
			throw new ParserException("FIle not Found.");
		} catch (IOException e) {
			throw new ParserException("Invalid file.");
		}

		return appBook;
	}
}
