package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {

  String desc;
  String beginTime;
  String endTime;

  Appointment(){}

  Appointment (String d, String bt, String et){
    this.desc = d;
    this.beginTime = bt;
    this.endTime = et;
  }

  @Override
  public String getBeginTimeString() {

    return this.beginTime;
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getEndTimeString() {
    return this.endTime;
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDescription() {

    return this.desc;
    //return "This method is not implemented yet";
  }
}
