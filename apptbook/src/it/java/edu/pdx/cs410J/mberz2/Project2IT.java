package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for the {@link Project2} main class.
 */
class Project2IT extends InvokeMainTestCase {

	/**
	* Invokes the main method of {@link Project1} with the given arguments.
	*/
	private MainMethodResult invokeMain(String... args) {
		return invokeMain( Project2.class, args );
	}

	/**
	* Tests that invoking the main method with no arguments issues an error
	*/
	@Test
	void testNoCommandLineArguments() {
		MainMethodResult result = invokeMain();
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Missing command line arguments"));
	}

	@Test
	void validAppointment(){
		MainMethodResult result =
				invokeMain("Matthew Berzinskas",
						"test description", "1/1/1999", "10:00",
						"2/2/2000", "20:00");

		System.out.println(result.getTextWrittenToStandardOut());
	}

	@Test
	void invalidTextFile(){
		MainMethodResult result =
				invokeMain("-textFile", "-print", "Matthew Berzinskas",
						"test description", "1/1/1999", "10:00",
						"2/2/2000", "20:00");
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Invalid number of arguments"));
	}

	@Test
	void validTextFile(){
		MainMethodResult result =
				invokeMain("-textFile", "file", "-print", "Matthew Berzinskas",
						"test description", "1/1/1999", "10:00",
						"2/2/2000", "20:00");
		assertThat(result.getExitCode(), equalTo(0));
	}

	@Test
	void incorrectOwnerWithTestFile(){
		String [] args = {"-textFile", "test", "-print", "Matthew Berzinskas",
				"test description", "1/1/1999", "10:00",
				"2/2/2000", "20:00"};
		MainMethodResult result = invokeMain(args);
		System.out.println(result.getTextWrittenToStandardOut());
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Incompatible owners"));
		assertThat(result.getExitCode(), equalTo(1));
	}

	@Test
	void incorrectBeginDate(){
		MainMethodResult result =
				invokeMain("-textFile", "file", "-print", "Matthew Berzinskas",
						"test description", "a1/1/1999", "10:00",
						"2/2/2000", "20:00");
		assertThat(result.getTextWrittenToStandardError(),
				containsString("begin time (date) [from file]"));
		assertThat(result.getExitCode(), equalTo(1));
	}

}