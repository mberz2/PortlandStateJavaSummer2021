package edu.pdx.cs410J.mberz2;

import org.junit.jupiter.api.Test;
import java.io.InputStreamReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Project4Test {

	@Test
	void loadingValidReadMeDoesNotThrow (){
		assertDoesNotThrow(() -> {
			getClass().getResourceAsStream("README.txt");
		});
	}

	@Test
	void nonexistentFileCannotBeLoaded() {
		NullPointerException e = assertThrows(
				NullPointerException.class, () -> Project4.printRes("foo.txt")
		);
		assertEquals(e.getMessage(), "Error: File foo.txt not found.");
	}


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
	void loadingInvalidReadmeDoesNot () {
		assertThrows(NullPointerException.class, () -> {
			Project4.printRes("read");
		});
	}

}
