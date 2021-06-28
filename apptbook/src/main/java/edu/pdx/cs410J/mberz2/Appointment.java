package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {

  private String desc;
  private String beginTime;
  private String endTime;

  Appointment(){}

  Appointment (String d, String bt, String et){
    this.desc = d;
    this.beginTime = bt;
    this.endTime = et;
  }

  @Override
  public String getBeginTimeString() {
    if(this.desc == null)
      throw new UnsupportedOperationException("This method is not implemented yet");
    return this.beginTime;
  }

  @Override
  public String getEndTimeString() {
      if(this.desc == null)
        throw new UnsupportedOperationException("This method is not implemented yet");
      return this.endTime;
  }

  @Override
  public String getDescription() {
    if (this.desc == null)
      return "This method is not implemented yet";
    return this.desc;
  }
}
