package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.*;
import java.util.Collection;

public class TextDumper implements AppointmentBookDumper {

	private String fileName;

	public TextDumper(String f){
		this.fileName = f;
	}

	@Override
	public void dump(AbstractAppointmentBook appBook) throws IOException {
		File file = new File(fileName);
		FileWriter fileWriter = new FileWriter(file, false);
		Collection<Appointment> appointments = appBook.getAppointments();

		for (Appointment each : appointments) {
			String save = appBook.getOwnerName() + ";" + each.getDescription()
					+ ";" + each.getBeginTime() + ";" + each.getEndTime() + "\n";
			fileWriter.write(save);
		}
		fileWriter.close();
	}
}
