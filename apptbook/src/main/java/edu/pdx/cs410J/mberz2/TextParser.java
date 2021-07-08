package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @param <T>
 */
public class TextParser <T extends AbstractAppointmentBook<Appointment>>
		implements AppointmentBookParser<T> {

	private final String fileName;
	//private final Collection<T> appList;

	private Map<String, AbstractAppointmentBook<Appointment>> appMap
			= new HashMap<String, AbstractAppointmentBook<Appointment>>();


	TextParser(String fileName){
		this.fileName = fileName;
	}

	@Override
	public T parse() throws ParserException {

		AppointmentBook<Appointment> appBook =
				new AppointmentBook<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(this.fileName));
			ArrayList<String> listOfLines = new ArrayList<>();
			String line;

			while((line = br.readLine()) != null) {
				String []  parsedApp = line.split(",");
				//System.out.println(Arrays.toString(parsedApp));
				Appointment app = new Appointment(parsedApp[1], parsedApp[2]+parsedApp[3], parsedApp[4]+parsedApp[5]);

				if(appMap.containsKey(parsedApp[0])){
					//System.out.println("Owner exists");
					AbstractAppointmentBook<Appointment> temp = appMap.get(parsedApp[0]);
					//System.out.println("Current apps:");
					//System.out.println(temp);
					//System.out.println(temp.getAppointments());
					//System.out.println("Adding app...");
					temp.addAppointment(app);
					//System.out.println(temp);
					//System.out.println(temp.getAppointments());
					appMap.put(parsedApp[0], temp);

				} else {
					//System.out.println("Owner does not exist");
					AppointmentBook<Appointment> tempBook
							= new AppointmentBook<Appointment>(parsedApp[0], app);
					appMap.put(parsedApp[0],tempBook);
					//System.out.println("Adding NEW app...");
					//System.out.println(app);
					//System.out.println();
				}
			}
			br.close();

			System.out.println("MAP CONTAINS "+appMap.size()+" owners");
			for (Map.Entry<String, AbstractAppointmentBook<Appointment>> entry : appMap.entrySet()) {
				System.out.println(entry.getValue().toString());
				for(Appointment a : entry.getValue().getAppointments() ){
					System.out.println(a);
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}