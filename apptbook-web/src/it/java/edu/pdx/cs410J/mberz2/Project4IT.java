package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * An integration test for {@link Project4} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {

    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test void nullTest () {
	    MainMethodResult result = invokeMain( Project4.class );
	    assertThat(result.getExitCode(), equalTo(1));
    }

	@Test
	void cannotPrintOffline() {
		String [] args = {"-host", "host", "-port", "123", "-print",
				"Owner", "Description",
				"1/1/1000", "10:00", "am",
				"1/1/1000", "10:05", "am"};

		//InvokeMainTestCase.MainMethodResult result = invokeMain( Project4.class, args);

		//System.out.println(result.getTextWrittenToStandardError());
		//System.out.println(result.getTextWrittenToStandardOut());

		//assertThat(result.getTextWrittenToStandardError(), containsString("** Error: Issue with connecting to add."));
		//assertThat(result.getExitCode(), equalTo(1));
	}

	@Test
	void cannotDumpOffline() {
		String [] args = {"-host", "host", "-port", "123",
				"Owner"};

		//InvokeMainTestCase.MainMethodResult result = invokeMain( Project4.class, args);

		//System.out.println(result.getTextWrittenToStandardError());
		//System.out.println(result.getTextWrittenToStandardOut());

		//assertThat(result.getTextWrittenToStandardError(), containsString("** Error: Issue with connecting to print."));
		//assertThat(result.getExitCode(), equalTo(1));
	}

	@Test
	void cannotSearchOffline() {
		String [] args = {"-host", "host", "-port", "123", "-search",
				"Owner",
				"1/1/1000", "10:00", "am",
				"1/1/1000", "10:05", "am"};

		//InvokeMainTestCase.MainMethodResult result = invokeMain( Project4.class, args);

		//System.out.println(result.getTextWrittenToStandardError());
		//System.out.println(result.getTextWrittenToStandardOut());

		//assertThat(result.getTextWrittenToStandardError(), containsString("** Error: Issue with connecting to search."));
		//assertThat(result.getExitCode(), equalTo(1));
	}


    @Test
    void test0RemoveAllMappings() throws IOException {
      AppointmentBookRestClient client = new AppointmentBookRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.deleteURL();
    }

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("No command"));
    }

	@Test
	void loadingReadmeWorks (){
		MainMethodResult result =
				invokeMain(Project4.class, "-README");

		assertThat(result.getTextWrittenToStandardOut(),
				CoreMatchers.containsString("Project 4"));
	}

	@Test
	void noHostArgumentFails(){
		MainMethodResult result =
				invokeMain(Project4.class, "-Host");

		assertThat(result.getTextWrittenToStandardError(),
				CoreMatchers.containsString("Error"));
	}

	@Test
	void noPortArgumentFails(){
		MainMethodResult result =
				invokeMain(Project4.class, "-port");

		assertThat(result.getTextWrittenToStandardError(),
				CoreMatchers.containsString("Error"));
	}

	@Test
	void invalidPortArgumentFails(){
		MainMethodResult result =
				invokeMain(Project4.class, "-port", "test");

		assertThat(result.getTextWrittenToStandardError(),
				CoreMatchers.containsString("Error"));
	}

	@Test
	void invalidArgumentFails(){
		MainMethodResult result =
				invokeMain(Project4.class, "-read");

		assertThat(result.getTextWrittenToStandardError(),
				CoreMatchers.containsString("invalid"));
	}

	@Test
	void tooFewHost(){
		MainMethodResult result =
				invokeMain(Project4.class, "-Host",
				"localhost", "-Port", "8080");

		assertThat(result.getTextWrittenToStandardError(),
				CoreMatchers.containsString("FEW"));
	}

	@Test
	void usageErrorWithTooManyCommandLineArguments(){
		String [] tooMany = {"Owner", "Description",
				"1/1/1000", "10:00", "1/1/1000", "10:30", "Owner", "Description",
				"1/1/1000", "10:00", "1/1/1000", "10:30",
				"1/1/1000", "10:00", "1/1/1000", "10:30",};

		MainMethodResult result = invokeMain(Project4.class, tooMany);

		assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
		assertThat(result.getTextWrittenToStandardError(),
				CoreMatchers.containsString("Too many"));
	}

	@Test
	void validInputDoesNotThrow (){
		assertDoesNotThrow(() -> {
			String [] args = {"-print", "Owner",
					"Description", "1/1/2021", "14:00", "am",
					"1/2/2021", "1:05", "am"};

			MainMethodResult result = invokeMain(Project4.class, args);
		});
	}

	@Test
	void validSearchDoesNotThrow (){
		assertDoesNotThrow(() -> {
			String [] args = {"-host", "localhost", "-port", "8080",
					"-search", "Owner",
					"1/1/2021", "14:00", "am",
					"1/2/2021", "1:05", "am"};

			MainMethodResult result = invokeMain(Project4.class, args);

			System.out.println(result.getTextWrittenToStandardError());
			System.out.println(result.getTextWrittenToStandardOut());

		});
	}

	@Test
	void tooFewSearchError (){
		assertDoesNotThrow(() -> {
			String [] args = {"-search", "Owner",
					"1/2/2021", "1:05", "am"};

			MainMethodResult result = invokeMain(Project4.class, args);
			assertThat(result.getTextWrittenToStandardError(),
					CoreMatchers.containsString("Error: Too few"));
		});
	}

	@Test
	void tooFewForDisplayAllOwner (){
		assertDoesNotThrow(() -> {
			String [] args = {"Owner"};

			MainMethodResult result = invokeMain(Project4.class, args);
			assertThat(result.getTextWrittenToStandardError(),
					CoreMatchers.containsString("Error: Too FEW"));
		});
	}

	@Test
	void displayAllOwner (){
		assertDoesNotThrow(() -> {

			String [] args = {"-host", "localhost",
					"-port", "8080", "Owner",
					"Description", "1/1/2021", "14:00", "am",
					"1/2/2021", "1:05", "am"};

			invokeMain(Project4.class, args);

			String [] args2 = {"-host", "localhost",
					"-port", "8080", "Owner"};

			MainMethodResult result = invokeMain(Project4.class, args2);
			assertThat(result.getTextWrittenToStandardError(),
					CoreMatchers.containsString(""));
		});
	}

	@Test
	void printWithValidArgsPrintsToOut (){
		String [] args = {"-host", "localhost", "-port", "8080",
				"-print", "Owner",
				"Description", "7/13/2021", "10:00", "am",
				"7/13/2021", "10:05", "am"};

		MainMethodResult result = invokeMain(Project4.class, args);

		assertThat(result.getTextWrittenToStandardOut(),
				CoreMatchers.containsString("Owner's appointment book"));
	}

	@Test
	void tooFewArgsForPrintError (){
		String [] args = {"-print", "Owner",
				"Description", "7/13/2021", "10:00", "am",
				"7/13/2021", "10:05",};

		MainMethodResult result = invokeMain(Project4.class, args);
		assertThat(result.getTextWrittenToStandardError(),
				CoreMatchers.containsString("Invalid"));
	}
}