package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the {@link Project3} main class.
 */
class Project3IT extends InvokeMainTestCase {

	/**
	* Invokes the main method of {@link Project3} with the given arguments.
	*/
	private MainMethodResult invokeMain(String... args) {
		return invokeMain( Project3.class, args );
	}

	private final String [] tooFew = {};
	private final String [] tooMany = {"Owner", "Description",
			"1/1/1000", "10:00", "1/1/1000", "10:30", "Owner", "Description",
			"1/1/1000", "10:00", "1/1/1000", "10:30",};

	private final String [] first = {"-textfile", "TEST", "Owner",
			"Description", "7/13/2021", "10:00", "am", "7/13/2021", "10:05", "am"};

	private final String [] mid = {"-textfile", "TEST", "Owner",
			"Description", "7/13/2021", "10:10", "am", "7/13/2021", "11:00", "am"};

	private final String [] last = {"-textfile", "TEST", "Owner",
			"Description", "7/13/2021", "11:00", "am", "7/13/2021", "11:05", "am"};

	@Test
	void testNoCommandLineArguments() {
		MainMethodResult result = invokeMain(tooFew);
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Missing command line arguments"));
	}

	@Test
	void testTooManyCommandLineArguments(){
		MainMethodResult result = invokeMain(tooMany);
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Too MANY"));
	}

	@Test
	void sortingOrderofThreeAppointments(){
		MainMethodResult result = invokeMain(mid);
		System.out.println(result.getTextWrittenToStandardOut());
		System.out.println(result.getTextWrittenToStandardError());

		result = invokeMain(last);
		System.out.println(result.getTextWrittenToStandardOut());
		System.out.println(result.getTextWrittenToStandardError());

		result = invokeMain(first);
		System.out.println(result.getTextWrittenToStandardOut());
		System.out.println(result.getTextWrittenToStandardError());
	}

}