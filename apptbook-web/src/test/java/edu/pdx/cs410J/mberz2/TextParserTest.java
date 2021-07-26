package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TextParserTest {

	@Test
	void emptyFileCannotBeParsed() {
		assertThrows(NullPointerException.class, () -> {
			TextParser parser = new TextParser(
					new InputStreamReader(Objects.requireNonNull(
							getClass().getResourceAsStream("emptyFile.txt"))));
			parser.parse();
		});
	}

	@Test
	void fileWithAppointmentsCanBeParsed() throws ParserException {
		InputStream resource = getClass().getResourceAsStream("example.txt");

		assert resource != null;
		TextParser parser = new TextParser(new InputStreamReader(resource));
		AppointmentBook book = parser.parse();

		assertThat(book.getAppointments().size(), is(5));

	}

	@Test
	void malFormedFileCannotBeParsed() {
		InputStream resource =
				getClass().getResourceAsStream("bogus.txt");

		assert resource != null;
		TextParser parser = new TextParser(new InputStreamReader(resource));
		assertThrows(ParserException.class, parser::parse);
	}

	@Test
	void erroneousDateInFileCannotBeParsed() {
		InputStream resource =
				getClass().getResourceAsStream("malformed2.txt");
		assertNotNull(resource);

		TextParser parser = new TextParser(new InputStreamReader(resource));
		assertThrows(ParserException.class, parser::parse);
	}

}