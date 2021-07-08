package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.util.*;

/**
 *
 * @param <T>
 */
public class TextParser <T extends AbstractAppointmentBook<Appointment>>
		implements AppointmentBookParser<T> {

	private final String fileName;

	private final Map<String, AbstractAppointmentBook<Appointment>> appMap
			= new HashMap<>();

	TextParser(String fileName){
		this.fileName = fileName;
	}

	@Override
	public T parse() throws ParserException {

		try {
			BufferedReader br = new BufferedReader(new FileReader(this.fileName));
			String line;

			while((line = br.readLine()) != null) {

				String []  parsedApp = line.split(",");
				Appointment app = new Appointment(parsedApp[1],
						parsedApp[2]+parsedApp[3],
						parsedApp[4]+parsedApp[5]);

				if(appMap.containsKey(parsedApp[0])){
					AbstractAppointmentBook<Appointment> temp =
							appMap.get(parsedApp[0]);
					temp.addAppointment(app);
					appMap.put(parsedApp[0], temp);

				} else {
					AppointmentBook<Appointment> tempBook
							= new AppointmentBook<>(parsedApp[0], app);
					appMap.put(parsedApp[0],tempBook);
				}
			}
			br.close();

			/*
			//Test Printer
			for (Map.Entry<String, AbstractAppointmentBook<Appointment>> entry
					: appMap.entrySet()) {
				System.out.println(entry.getValue().toString());
				for(Appointment a : entry.getValue().getAppointments() ){
					System.out.println(a);
				}
			}
			 */

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}