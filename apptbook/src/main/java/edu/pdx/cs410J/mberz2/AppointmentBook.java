package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;

public class AppointmentBook extends AbstractAppointmentBook {

    private final String owner;
    private Collection<AbstractAppointment> appList;

    AppointmentBook(String o){
        this.owner = o;
        this.appList = new ArrayList<>();
    }

    @Override
    public String getOwnerName() {
        return this.owner;
    }

    @Override
    public Collection getAppointments() {
        return appList;
    }

    @Override
    public void addAppointment(AbstractAppointment app) {
        appList.add(app);
    }
}
