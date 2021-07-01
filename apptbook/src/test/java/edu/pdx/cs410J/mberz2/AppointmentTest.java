package edu.pdx.cs410J.mberz2;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the @see:edu.pdx.cs410J.mberz2.Appointment class.
 */
public class AppointmentTest {

  @Test
  void getBeginTimeStringNeedsToBeImplemented() {
    Appointment appt =
            new Appointment(null, null, null, null);
    assertThrows(UnsupportedOperationException.class,
            appt::getBeginTimeString);
  }

  @Test
  void getBeginTimeStringImplemented() {
    Appointment appt =
            new Appointment(null, null, "11:30", null);
    assertNotNull(appt.getBeginTimeString());
    assertEquals("11:30",appt.getBeginTimeString());
  }

  @Test
  void getEndTimeStringNeedsToBeImplemented() {
    Appointment appt =
            new Appointment(null, null, null, null);
    assertThrows(UnsupportedOperationException.class,
            appt::getEndTimeString);
  }

  @Test
  void getEndTimeStringImplemented() {
    Appointment appt =
            new Appointment(null, null, null, "11:30");
    assertNotNull(appt.getEndTimeString());
    assertEquals("11:30",appt.getEndTimeString());
  }

  @Test
  void initiallyAllAppointmentsHaveTheSameDescription() {
    Appointment appt =
            new Appointment(null, null, null, null);
    assertThrows(UnsupportedOperationException.class,
            appt::getDescription);
  }

  @Test
  void getDescriptionStringImplemented() {
    Appointment appt =
            new Appointment(null, "test", null, null);
    assertNotNull(appt.getDescription());
    assertEquals("test",appt.getDescription());
  }

}
