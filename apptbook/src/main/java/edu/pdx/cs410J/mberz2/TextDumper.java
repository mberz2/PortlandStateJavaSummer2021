package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collection;

/**
 *
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

	private final Writer writer;
	private String fileName;

	public TextDumper(StringWriter writer) {
		this.writer = writer;
	}

	public void setFileName(String f){
		this.fileName = f;
	}

	/**
	 *
	 * @throws IOException
	 */
	@Override
	public void dump(AppointmentBook appBook) throws IOException {

		Collection<Appointment> apps = appBook.getAppointments();
		for (Appointment a: apps) {
			String[] btSplit = a.getBeginTimeString().split("\\s");
			String[] etSplit = a.getBeginTimeString().split("\\s");

			writer.write(appBook.getOwnerName() + "|" + a.getDescription()
					+ "|" + btSplit[0] + "|" + btSplit[1]
					+ "|" + etSplit[0] + "|" + etSplit[1]
					+ "\n");
		}

		if(fileName == null)
			throw new NullPointerException("Error: No filename.");

		try {
			if (!Files.exists(Path.of(fileName)))
				Files.createFile(Path.of(fileName));

			FileWriter fileWriter = new FileWriter(fileName);
			fileWriter.write(String.valueOf(writer));
			fileWriter.flush();
			fileWriter.close();

		} catch (InvalidPathException e){
			System.err.println("Error: Invalid character in path.");
			System.exit(1);
		} catch (NoSuchFileException e){
			System.err.println("Error: No such directory.");
			System.exit(1);
		}

		writer.flush();

	}
}