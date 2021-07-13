package edu.pdx.cs410J.mberz2;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TextDumperTest {

	InputStream loader (String f) {
		return Project2.class.getResourceAsStream(f);
	}

	@Test
	void dumperDumpsAppointmentBookOwner() throws IOException {
		String owner = "Owner";

		Appointment app = new Appointment("Description",
				"1/1/1000 10:00", "1/1/1000 10:30");

		AppointmentBook book = new AppointmentBook(owner, app);

		StringWriter sw = new StringWriter();
		TextDumper dumper = new TextDumper(sw);
		dumper.dump(book);

		sw.flush();

		String dumpedText = sw.toString();
		assertThat(dumpedText, containsString(owner));

	}

}


		/*
	@Test
	void readmeCanBeReadAsResource() throws IOException {
		try (
				InputStream readme =
						Project1.class.getResourceAsStream("README.txt")
		) {
			assertThat(readme, not(nullValue()));
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(readme));
			String line = reader.readLine();
			assertThat(line, containsString("README"));
		}
	}

	@Test
	void fileCanBePrintedAsResource() {
		assertDoesNotThrow(()-> Project1.printRes("README.txt"));
	}
	*/