package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link AppointmentBookRestClient}
 */
@TestMethodOrder(MethodName.class)
class AppointmentBookRestClientIT {

  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AppointmentBookRestClient newAppointmentBookRestClient() {
    int port = Integer.parseInt(PORT);
    return new AppointmentBookRestClient(HOSTNAME, port);
  }

  @Test
  void test0RemoveAllAppointments() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    client.deleteAllAppointments();
  }

  @Test
  void test1EmptyServerContainsNoAppointments() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    Map<String, String> map = client.getMap();
    assertThat(map.size(), equalTo(0));
  }

  @Test
  void test2MissingRequiredParameterReturnsPreconditionFailed() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    HttpRequestHelper.Response response = client.postToMyURL(Map.of());
    assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
    //assertThat(response.getContent(), containsString(Messages.missingRequiredParameter("owner")));
  }

	@Test
	void test3AddAppointment() throws IOException {
  	    String owner = "test owner";
  	    String description = "test description";
  	    String beginTime = "1/1/2020 09:00 AM";
  	    String endTime = "1/1/2020 10:00 AM";

		AppointmentBookRestClient client = newAppointmentBookRestClient();
		HttpRequestHelper.Response response =
				client.addAppointment(owner, description, beginTime, endTime);
		assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_OK));
		assertThat(response.getContent(), containsString(Messages.printOwner("test owner")));
	}

	@Test
	void test3PostAppointment() throws IOException {
		String owner = "test owner2";
		String description = "test description";
		String beginTime = "1/1/2020 09:00 AM";
		String endTime = "1/1/2020 10:00 AM";

		AppointmentBookRestClient client = newAppointmentBookRestClient();
		HttpRequestHelper.Response response =
				client.postToMyURL(Map.of("owner", owner,
				"description", description,
				"beginTime", beginTime,
				"endTime", endTime));

		assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_OK));
		assertThat(response.getContent(), containsString(Messages.printOwner("test owner2")));
	}

	@Test
	void test4SearchAppointment() throws IOException {
		AppointmentBookRestClient client = newAppointmentBookRestClient();

		String owner = "test owner";
		String beginTime = "1/1/2020 07:00 AM";
		String endTime = "1/1/2020 9:05 AM";

		HttpRequestHelper.Response response =
				client.searchTime(owner, beginTime, endTime);
		assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_OK));
		assertThat(response.getContent(), containsString(Messages.printOwner("test owner")));
	}

	@Test
	void test5OwnerArgumentReturnsAppointments() throws IOException {
		AppointmentBookRestClient client = newAppointmentBookRestClient();

		String owner = "test owner";

		HttpRequestHelper.Response response =
				client.getAllAppointments(owner);
		assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_OK));
		assertThat(response.getContent(), containsString(Messages.printOwner("test owner")));
	}
}
