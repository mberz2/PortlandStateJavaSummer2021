package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

	/**
	* Invokes the main method of {@link Project1} with the given arguments.
	*/
	private MainMethodResult invokeMain(String... args) {
		return invokeMain( Project1.class, args );
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
	void testTooManyCommandLineArguments() {
		MainMethodResult result =
				invokeMain("1", "2","3","4","5","6","7","8","9");
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Too many"));
	}

	@Test
	void sevenCommandLineArguments(){
		MainMethodResult result =
				invokeMain("1", "2","3","4","5","6","7");
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Error in"));
	}

	@Test
	void printOptionEnabled(){
		MainMethodResult result =
				invokeMain("-print", "Matthew Berzinskas",
						"test description", "1/1/1999", "10:00",
						"2/2/2000", "20:00");
		assertThat(result.getExitCode(), equalTo(0));
		assertThat(result.getTextWrittenToStandardOut(),
				containsString("Appointment Book for"));
		assertThat(result.getTextWrittenToStandardOut(),
				containsString("Description"));
		assertThat(result.getTextWrittenToStandardOut(),
				containsString("Begin"));
		assertThat(result.getTextWrittenToStandardOut(),
				containsString("End"));
	}

	@Test
	void readmeOptionEnabled() {
		MainMethodResult result =
				invokeMain("-readme");
		assertThat(result.getExitCode(), equalTo(0));
		assertThat(result.getTextWrittenToStandardOut(),
				containsString("README"));
	}

	@Test
	void onlyPrintAndReadmeOptionsAllowed() {
		MainMethodResult result =
				invokeMain("-readmeeeeeee");
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("not a correct"));
	}

	@Test
	void incorrectBeginDateRegEx(){
		MainMethodResult result =
				invokeMain("Matthew Berzinskas", "test description",
						"aa1/1/1999", "10:00", "2/2/2000", "20:00");
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("improper"));
	}

	@Test
	void incorrectBeginTimeRegEx(){
		MainMethodResult result =
				invokeMain("Matthew Berzinskas", "test description",
						"1/1/1999", "aa10:00", "2/2/2000", "20:00");
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("improper"));
	}

	@Test
	void incorrectEndDateRegEx(){
		MainMethodResult result =
				invokeMain("Matthew Berzinskas", "test description",
						"aa1/1/1999", "10:00", "aa2/2/2000", "20:00");
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("improper"));
	}

	@Test
	void incorrectEndTimeRegEx(){
		MainMethodResult result =
				invokeMain("Matthew Berzinskas", "test description",
						"1/1/1999", "10:00", "2/2/2000", "aa20:00");
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("improper"));
	}

}