package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

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
			String [] args = {"-search", "Owner",
					"1/1/2021", "14:00", "am",
					"1/2/2021", "1:05", "am"};

			MainMethodResult result = invokeMain(Project4.class, args);

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
					"-port", "8080", "Owner"};

			MainMethodResult result = invokeMain(Project4.class, args);
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