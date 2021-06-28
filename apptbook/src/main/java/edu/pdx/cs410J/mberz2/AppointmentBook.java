package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;

public class AppointmentBook extends AbstractAppointmentBook {

    private final String owner;
    private final ArrayList<Appointment> appList;

    AppointmentBook(String o){
        this.owner = o;
        this.appList = new ArrayList<Appointment>();
    }

    @Override
    public String getOwnerName() {
        return this.owner;
    }

    @Override
    public ArrayList getAppointments() {
        return appList;
    }

    @Override
    public void addAppointment(AbstractAppointment app) {
        appList.add((Appointment) app);
    }
}
