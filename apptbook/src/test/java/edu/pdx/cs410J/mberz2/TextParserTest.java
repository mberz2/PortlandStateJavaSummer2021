package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {

	@Test
	void emptyFileCannotBeParsed() {
		InputStream resource = getClass().getResourceAsStream("emptyFile.txt");
		assertNotNull(resource);

		TextParser parser = new TextParser(new InputStreamReader(resource));
		assertThrows(ParserException.class, parser::parse);
	}

	@Test
	void appointmentBookOwnerCanBeDumpedAndParsed() throws IOException, ParserException {
		String owner = "Owner";
		Appointment app = new Appointment("Description",
				"1/1/1000 10:00", "1/1/1000 10:30");

		AppointmentBook book = new AppointmentBook(owner, app);

		StringWriter sw = new StringWriter();
		TextDumper dumper = new TextDumper(sw);
		dumper.dump(book);

		TextParser parser = new TextParser(new StringReader(sw.toString()));
		book = parser.parse();

		assertThat(book.getOwnerName(), equalTo(owner));
	}

}

	/*
	@Test
	void appointmentBookOwnerCanBeDumpedToFileAndParsed(@TempDir File dir) throws IOException, ParserException {
		File textFile = new File(dir, "appointments.txt");

		Appointment app = new Appointment("Description",
				"1/1/1000 10:00", "1/1/1000 10:30");

		String owner = "Owner";
		AppointmentBook book = new AppointmentBook(owner, app);

		TextDumper dumper = new TextDumper(new FileWriter(textFile));
		dumper.setFileName("TEST");
		dumper.dump(book);

		TextParser parser = new TextParser(new FileReader(textFile));
		book = parser.parse();

		assertThat(book.getOwnerName(), equalTo(owner));
	}
	 */