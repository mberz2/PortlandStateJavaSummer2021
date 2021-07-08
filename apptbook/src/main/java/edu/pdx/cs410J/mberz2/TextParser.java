package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.util.ArrayList;
import java.util.Collection;

public class TextParser <T extends AbstractAppointmentBook<Appointment>>
		implements AppointmentBookParser<T> {

	private final String fileName;
	private final Collection<T> appList;

	TextParser(String fileName, T app){
		this.fileName = fileName;
		this.appList = new ArrayList<>();
	}

	@Override
	public T parse() throws ParserException {


		return null;
	}
}