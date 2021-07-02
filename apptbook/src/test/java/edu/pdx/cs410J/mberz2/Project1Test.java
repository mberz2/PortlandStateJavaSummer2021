package edu.pdx.cs410J.mberz2;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A unit test for code in the {@link Project1} class.  This is different from
 * {@link Project1IT} which is an integration test (and can handle the calls
 * to {@link System#exit(int)} and the like.
 */
class Project1Test {

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

	@Test
	void invalidResourcesCannotBeRead() {
		assertThrows(NullPointerException.class, () ->
				Project1.printRes("test.txt"));
	}

}
