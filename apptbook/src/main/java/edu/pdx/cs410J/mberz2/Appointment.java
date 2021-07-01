package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {

  private String owner;
  private String desc;
  private String beginTime;
  private String endTime;

  Appointment(){}

  Appointment (String o, String d, String bt, String et){
    this.owner = o;
    this.desc = d;
    this.beginTime = bt;
    this.endTime = et;
  }

  @Override
  public String getBeginTimeString() { return this.beginTime; }

  @Override
  public String getEndTimeString() { return this.endTime; }

  @Override
  public String getDescription() { return this.desc; }
}
