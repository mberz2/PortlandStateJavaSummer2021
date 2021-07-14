package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class Project3Test {

	@Test
	void nonexistentFileCannotBeLoaded() {
		NullPointerException e = assertThrows(
				NullPointerException.class, () -> Project3.printRes("foo.txt")
		);
		assertEquals(e.getMessage(), "File foo.txt not found.");
	}


	@Test
	void emptyFileCannotBeParsed() {
		InputStream resource = getClass().getResourceAsStream("emptyFile.txt");
		assertNotNull(resource);

		TextParser parser = new TextParser(new InputStreamReader(resource));
		assertThrows(ParserException.class, parser::parse);
	}
}
