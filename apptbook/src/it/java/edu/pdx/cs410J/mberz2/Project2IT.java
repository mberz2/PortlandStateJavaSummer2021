package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

	private final String [] tooFew = {};
	private final String [] tooMany = {"Owner", "Description",
			"1/1/1000", "10:00", "1/1/1000", "10:30", "Owner", "Description",
			"1/1/1000", "10:00", "1/1/1000", "10:30",};
	private final String [] validAppt = {"Owner", "Description",
			"1/1/1000", "10:00", "1/1/1000", "10:30"};
	private final String [] validPrint = {"-print", "Owner", "Description",
			"1/1/1000", "10:00", "1/1/1000", "10:30"};
	private final String [] validFile = {"-textFile", "test", "Test Owner",
			"Description", "1/1/1000", "10:00", "1/1/1000", "10:30"};
	private final String [] invalidFile = {"-textFile", "Owner", "Description",
			"1/1/1000", "10:00", "1/1/1000", "10:30"};
	private final String [] invalidFileOwner = {"-textFile", "test",
			"Test Owner2", "Description",
			"1/1/1000", "10:00", "1/1/1000", "10:30"};
	private final String [] invalidApptBd = {"Owner", "Description",
			"a1/1/1000", "10:00", "1/1/1000", "10:30"};
	private final String [] invalidApptBt = {"Owner", "Description",
			"1/1/1000", "a10:00", "1/1/1000", "10:30"};
	private final String [] invalidApptEd = {"Owner", "Description",
			"1/1/1000", "10:00", "a1/1/1000", "10:30"};
	private final String [] invalidApptEt = {"Owner", "Description",
			"1/1/1000", "10:00", "1/1/1000", "a10:30"};

	@Test
	void testNoCommandLineArguments() {
		MainMethodResult result = invokeMain();
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Missing command line arguments"));
	}

	@Test
	void testTooManyCommandLineArguments(){
		MainMethodResult result = invokeMain(tooMany);
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Too many"));
	}

	@Test
	void validAppointment(){
		assertDoesNotThrow(() -> {MainMethodResult result = invokeMain(validAppt);
		assertThat(result.getExitCode(), equalTo(0));});
	}

	@Test
	void validPrint(){
		assertDoesNotThrow(() -> {MainMethodResult result = invokeMain(validPrint);
			assertThat(result.getTextWrittenToStandardOut(),
					containsString("appointment"));
			assertThat(result.getExitCode(), equalTo(0));});
	}

	@Test
	void invalidTextFile(){
		MainMethodResult result = invokeMain(invalidFile);
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Invalid number of arguments"));
	}

	@Test
	void validTextFile(){
		MainMethodResult result = invokeMain(validFile);
		assertThat(result.getExitCode(), equalTo(0));
	}

	@Test
	void incorrectOwnerWithTestFile(){
		MainMethodResult result = invokeMain(invalidFileOwner);
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Incompatible owners"));
		assertThat(result.getExitCode(), equalTo(1));
	}

	@Test
	void incorrectBeginDate(){
		MainMethodResult result = invokeMain(invalidApptBd);
		assertThat(result.getTextWrittenToStandardError(),
				containsString("begin time (date) [from file]"));
		assertThat(result.getExitCode(), equalTo(1));
	}

	@Test
	void incorrectBeginTime(){
		MainMethodResult result = invokeMain(invalidApptBt);
		assertThat(result.getTextWrittenToStandardError(),
				containsString("begin time (time) [from file]"));
		assertThat(result.getExitCode(), equalTo(1));
	}

	@Test
	void incorrectEndDate(){
		MainMethodResult result = invokeMain(invalidApptEd);
		assertThat(result.getTextWrittenToStandardError(),
				containsString("end time (date) [from file]"));
		assertThat(result.getExitCode(), equalTo(1));
	}

	@Test
	void incorrectEndTime(){
		MainMethodResult result = invokeMain(invalidApptEt);
		assertThat(result.getTextWrittenToStandardError(),
				containsString("end time (time) [from file]"));
		assertThat(result.getExitCode(), equalTo(1));
	}
}