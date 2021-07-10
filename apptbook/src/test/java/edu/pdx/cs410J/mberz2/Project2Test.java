package edu.pdx.cs410J.mberz2;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class Project2Test {

	@Test
	void invalidResourcesCannotBeRead() {
		NullPointerException e = assertThrows(
				NullPointerException.class, () -> Project2.printRes("test.txt")
		);
		assertEquals(e.getMessage(), "File test.txt not found.");
	}

	@Test
	void textDumper() throws IOException {
		assertThrows (FileNotFoundException.class, ()
				-> Project2.writeFile(null) );
	}



}
