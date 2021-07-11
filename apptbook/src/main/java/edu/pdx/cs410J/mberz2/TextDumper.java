package edu.pdx.cs410J.mberz2;


import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 *
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

	/* File name/path passed in during object instantiation. */
	private final String fileName;

	/**
	 *
	 * @param fileName
	 */
	public TextDumper(String fileName) {
		this.fileName = fileName;
	}

	/**
	 *
	 * @param appointment
	 * @throws IOException
	 */
	@Override
	public void dump(AppointmentBook appointment) throws IOException {
		try{
			File file = new File(String.valueOf(Paths.get(fileName)));

			try{
				FileWriter fileWriter = new FileWriter(file, false);

				Collection<Appointment> apps = appointment.getAppointments();
				//System.out.println(apps);

				for (Appointment a: apps) {

					String[] btSplit = a.getBeginTimeString().split("\\s");
					String[] etSplit = a.getBeginTimeString().split("\\s");

					String toFile = appointment.getOwnerName()
							+ "|" + a.getDescription()
							+ "|" + btSplit[0] + "|" + btSplit[1]
							+ "|" + etSplit[0] + "|" + etSplit[1]
							+ "\n";

					fileWriter.write(toFile);
				}
				fileWriter.close();

			} catch(FileNotFoundException | NoSuchFileException exception) {
				System.err.println("Error: No such directory.");
				System.err.println(file.getAbsolutePath());
				System.exit(1);
			}


		} catch(InvalidPathException e){
			System.err.println("Error: Invalid character in path.");
			System.exit(1);
		}
	}
}