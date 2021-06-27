package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Arrays;
import java.util.Collection;

public class AppointmentBook extends AbstractAppointmentBook {

    private String owner;
    private Appointment appList[];
    int tail;

    AppointmentBook(String o){
        this.owner = o;
        this.tail = 0;
    }

    @Override
    public String getOwnerName() {
        return this.owner;
    }

    @Override
    public Collection getAppointments() {
        return Arrays.asList(appList);
    }

    @Override
    public void addAppointment(AbstractAppointment app) {
        this.appList[tail] = (Appointment) app;
    }
}
