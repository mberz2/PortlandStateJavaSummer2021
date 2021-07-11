package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

	private final StringWriter stream;
	private String fileName;

	public TextDumper(StringWriter stream) {
		this.stream = stream;
	}

	public void setFileName(String f){
		this.fileName = f;
	}

	/**
	 *
	 * @param appointment
	 * @throws IOException
	 */
	@Override
	public void dump(AppointmentBook appointment) throws IOException {

		if (!Files.exists(Path.of(fileName)))
			Files.createFile(Path.of(fileName));

		FileWriter fileWriter = new FileWriter(fileName);
		fileWriter.write(String.valueOf(stream));
		fileWriter.flush();
		fileWriter.close();
	}
}