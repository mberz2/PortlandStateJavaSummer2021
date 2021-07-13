package edu.pdx.cs410J.mberz2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A unit test for code in the {@link Project1} class.  This is different from
 * {@link Project1IT} which is an integration test (and can handle the calls
 * to {@link System#exit(int)} and the like.
 */
class Project1Test {

	@Test
	void invalidResourcesCannotBeRead() {
		NullPointerException e = assertThrows(
				NullPointerException.class, () -> Project1.printRes("foo.txt")
		);
		assertEquals(e.getMessage(), "File foo.txt not found.");
	}

	@Test
	void testParserTooFew(){
		String [] test = {"test", "test"};
		assertThrows(
			NullPointerException.class, () -> Project1.parseInput(test)
		);
	}
}
