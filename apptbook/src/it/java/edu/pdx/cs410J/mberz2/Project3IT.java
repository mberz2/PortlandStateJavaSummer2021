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

	@Test
	void CommandLineParserTest () {

		String [] readme = {"-print", "-textfile", "test.txt",
		"-pretty", "test"};
		MainMethodResult result = invokeMain(readme);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());


	}

	@Test
	void readmeOptionPrintsNewReadme() {
		String [] readme = {"-readme"};
		MainMethodResult result = invokeMain(readme);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getTextWrittenToStandardOut(),
				containsString("README"));
		assertThat(result.getTextWrittenToStandardOut(),
				containsString("Project 3"));
	}

	@Test
	void invalidOptionThrowsError() {
		String [] invalidOption = {"-read", "Owner",
				"Description", "7/13/2021", "10:00", "am",
				"7/13/2021", "10:05"};
		MainMethodResult result = invokeMain(invalidOption);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getTextWrittenToStandardError(),
				containsString("Error"));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("invalid option"));
	}

	@Test
	void usageErrorWithNoCommandLineArguments() {
		String [] none = {};
		MainMethodResult result = invokeMain(none);
		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Error: No command line arguments"));
	}

	@Test
	void usageErrorWithTooFewCommandLineArguments() {
		String [] tooFew = {"Owner",
				"Description", "7/13/2021", "10:00", "am",
				"7/13/2021", "10:05"};
		MainMethodResult result = invokeMain(tooFew);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Too FEW command line arguments"));
	}

	@Test
	void usageErrorWithTooManyCommandLineArguments(){
		String [] tooMany = {"Owner", "Description",
				"1/1/1000", "10:00", "1/1/1000", "10:30", "Owner", "Description",
				"1/1/1000", "10:00", "1/1/1000", "10:30",
				"1/1/1000", "10:00", "1/1/1000", "10:30",};

		MainMethodResult result = invokeMain(tooMany);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getExitCode(), equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				containsString("Too many"));
	}

	@Test
	void tooFewForPrintOption (){
		String [] args = {"-print", "Owner",
				"Description", "7/13/2021", "10:00", "am",
				"7/13/2021", "10:05"};

		MainMethodResult result = invokeMain(args);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getTextWrittenToStandardError(),
				containsString("Error: Invalid"));
	}

	@Test
	void tooFewForTextFileOption (){
		String [] args = {"-textFile", "Owner",
				"Description", "7/13/2021", "10:00", "am",
				"7/13/2021", "10:05"};

		MainMethodResult result = invokeMain(args);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getTextWrittenToStandardError(),
				containsString("Error: Invalid"));
	}

	@Test
	void tooManyWithOptionError (){
		String [] args = {"-textFile", "Owner",
				"Description", "7/13/2021", "10:00", "am",
				"7/13/2021", "10:05"};

		MainMethodResult result = invokeMain(args);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getTextWrittenToStandardError(),
				containsString("Error: Invalid"));
	}


	@Test
	void tooFewForPrinterOption (){
		String [] args = {"-pretty", "Owner",
				"Description", "7/13/2021", "10:00", "am",
				"7/13/2021", "10:05"};

		MainMethodResult result = invokeMain(args);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getTextWrittenToStandardError(),
				containsString("Error: Invalid"));
	}

	@Test
	void printWithValidArgsPrintsToOut (){
		String [] args = {"-print", "Owner",
				"Description", "7/13/2021", "10:00", "am",
				"7/13/2021", "10:05", "am"};

		MainMethodResult result = invokeMain(args);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getTextWrittenToStandardOut(),
				containsString("Owner's appointment book"));
	}

	@Test
	void prettyWithValidArgsPrintsToOut (){
		String [] args = {"-pretty", "-", "Owner",
				"Description", "7/13/2021", "10:00", "am",
				"7/13/2021", "10:05", "am"};

		MainMethodResult result = invokeMain(args);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getTextWrittenToStandardOut(),
				containsString("Description"));
	}

	@Test
	void textFileWithValidArgsCreatesNewBook (){
		assertDoesNotThrow(() -> {
			String [] args = {"-textfile", "test", "Owner",
					"Description", "7/13/2021", "10:00", "am",
					"7/13/2021", "10:05", "am"};

			MainMethodResult result = invokeMain(args);

			System.out.println(result.getTextWrittenToStandardError());
			System.out.println(result.getTextWrittenToStandardOut());
		});
	}

	@Test
	void loadAndCombineWithNewBook (){
		assertDoesNotThrow(() -> {
			String [] args = {"-textfile", "test", "Owner",
					"Description", "7/13/2021", "10:00", "am",
					"7/13/2021", "10:05", "am"};

			String [] args2 = {"-textfile", "test", "Owner",
					"Description", "7/13/2021", "10:00", "am",
					"7/13/2021", "10:05", "am"};

			MainMethodResult result = invokeMain(args);
			result = invokeMain(args2);

			System.out.println(result.getTextWrittenToStandardError());
			System.out.println(result.getTextWrittenToStandardOut());
		});
	}

	@Test
	void cannotCombineWithDifferentOwner (){
		assertDoesNotThrow(() -> {
			String [] args = {"-textfile", "test2", "Owner",
					"Description", "7/13/2021", "10:00", "am",
					"7/13/2021", "10:05", "am"};

			String [] args2 = {"-textfile", "test2", "Owner2",
					"Description", "7/13/2021", "10:00", "am",
					"7/13/2021", "10:05", "am"};

			MainMethodResult result = invokeMain(args);
			result = invokeMain(args2);

			System.out.println(result.getTextWrittenToStandardError());
			System.out.println(result.getTextWrittenToStandardOut());

			assertThat(result.getTextWrittenToStandardError(),
					containsString("Error: Incompatible"));
			assertThat(result.getTextWrittenToStandardError(),
					containsString("loaded file"));
			assertThat(result.getExitCode(), equalTo(1));
		});
	}

	@Test
	void cannotFindIncorrectDirectory (){
		assertDoesNotThrow(() -> {
			String [] args = {"-textfile", "/foo/test2", "Owner",
					"Description", "7/13/2021", "10:00", "am",
					"7/13/2021", "10:05", "am"};

			String [] args2 = {"-textfile", "test2", "Owner2",
					"Description", "7/13/2021", "10:00", "am",
					"7/13/2021", "10:05", "am"};

			MainMethodResult result = invokeMain(args);
			//result = invokeMain(args2);

			System.out.println(result.getTextWrittenToStandardError());
			//System.out.println(result.getTextWrittenToStandardOut());

			assertThat(result.getTextWrittenToStandardError(),
					containsString("Error: Incompatible"));
			assertThat(result.getTextWrittenToStandardError(),
					containsString("loaded file"));
			assertThat(result.getExitCode(), equalTo(1));
		});
	}
}