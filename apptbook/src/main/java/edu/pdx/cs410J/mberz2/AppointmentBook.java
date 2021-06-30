package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;

public class AppointmentBook<T extends AbstractAppointment> extends AbstractAppointmentBook<T> {

    private final String owner;
    private final Collection<T> appList;

    AppointmentBook(String o, T app){
        this.owner = o;
        this.appList = new ArrayList<>();
        appList.add(app);
    }

    @Override
    public String getOwnerName() {
        return this.owner;
    }

    @Override
    public Collection<T> getAppointments() {
        return appList;
    }

    @Override
    public void addAppointment(T app) { appList.add((T) app); }
}
