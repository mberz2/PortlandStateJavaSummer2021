package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class Project4Test extends InvokeMainTestCase {

	@Test
	void cannotPrintOffline() {
		String [] args = {"-host", "host", "-port", "123", "-print",
				"Owner", "Description",
				"1/1/1000", "10:00", "am",
				"1/1/1000", "10:05", "am"};

		InvokeMainTestCase.MainMethodResult result = invokeMain( Project4.class, args);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getTextWrittenToStandardError(), containsString("** Error: Issue with connecting to add."));
		assertThat(result.getExitCode(), equalTo(1));
	}

	@Test
	void cannotSearchOffline() {
		String [] args = {"-host", "host", "-port", "123", "-search",
				"Owner",
				"1/1/1000", "10:00", "am",
				"1/1/1000", "10:05", "am"};

		InvokeMainTestCase.MainMethodResult result = invokeMain( Project4.class, args);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getTextWrittenToStandardError(), containsString("** Error: Issue with connecting to search."));
		assertThat(result.getExitCode(), equalTo(1));
	}

	@Test
	void portMustBeAnInteger() {
		String [] args = {"-host", "host", "-port", "abc", "-print",
				"Owner", "Description",
				"1/1/1000 10:00 am",
				"1/1/1000 10:05 am"};

		InvokeMainTestCase.MainMethodResult result = invokeMain( Project4.class, args);

		System.out.println(result.getTextWrittenToStandardError());
		System.out.println(result.getTextWrittenToStandardOut());

		assertThat(result.getTextWrittenToStandardError(), containsString("must be an integer"));
		assertThat(result.getExitCode(), equalTo(1));
	}

}
