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


	TextParser(String fileName){
		this.fileName = fileName;
	}

	@Override
	public T parse() throws ParserException {

		System.out.println(this.fileName);

		/*
		try {
			File myObj = new File("filename.txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		*/

		AppointmentBook<Appointment> appBook =
				new AppointmentBook<>();

		ArrayList<String> owners = new ArrayList<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(this.fileName));
			ArrayList<String> listOfLines = new ArrayList<>();
			String line;

			while((line = br.readLine()) != null) {
				String []  parsedApp = line.split(",");
				System.out.println(Arrays.toString(parsedApp));
				Appointment app = new Appointment(parsedApp[1], parsedApp[2]+parsedApp[3], parsedApp[4]+parsedApp[5]);
				System.out.println(app);

				if(owners.contains(parsedApp[0])){
					System.out.println("Owner exists");

				} else {
					System.out.println("Owner does not exist");
					owners.add(parsedApp[0]);
				}


			}

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}