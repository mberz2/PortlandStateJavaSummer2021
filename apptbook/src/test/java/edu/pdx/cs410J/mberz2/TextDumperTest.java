package edu.pdx.cs410J.mberz2;

import java.io.InputStream;

public class TextDumperTest {

	InputStream loader (String f) {
		return Project2.class.getResourceAsStream(f);
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