package edu.pdx.cs410J.mberz2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Project2Test {

	@Test
	void invalidResourcesCannotBeRead() {
		NullPointerException e = assertThrows(
				NullPointerException.class, () -> Project2.printRes("test.txt")
		);
		assertEquals(e.getMessage(), "File test.txt not found.");
	}



}
