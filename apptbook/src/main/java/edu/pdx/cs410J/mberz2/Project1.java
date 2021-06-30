package edu.pdx.cs410J.mberz2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static void main(String[] args) throws ParseException {

    checkArgs(args);
    String[] newArgs = parseInput(args);

    AppointmentBook appBook = new AppointmentBook;

    //Lookup owner name

    appBook.add(new newArgs[0]);

    Appointment app = new Appointment(newArgs[1], newArgs[2]+" "+newArgs[3], newArgs[4]+" "+newArgs[5]);

    appBooks.get(0).addAppointment(app);

    ArrayList<Appointment> tempBook = (ArrayList<Appointment>) appBooks.get(0).getAppointments();

    System.out.println(appBooks.get(0).getOwnerName());
    for (Appointment i : tempBook){
      System.out.println(i.getDescription());
      System.out.println(i.getBeginTimeString());
      System.out.println(i.getEndTimeString());
    }

    System.exit(0);
  }

  public static void checkArgs(String[] args) {

    if (args.length == 0) {
      System.err.println("Error: Missing command line arguments");
      printUsage();
      System.exit(1);
    } else if (args.length > 7) {
      System.err.println("Error: Too many command line arguments");

      System.exit(1);
    }
  }

  public static String[] parseInput(String[] args){

    boolean err = false;
    SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");


    String[] newArgs = new String[0];
    if (args.length == 7)
      //Two options flags, take from index 1-7
      newArgs = Arrays.copyOfRange(args, 1, 7);

    else if (args.length == 6)
      //One option flag, take from index 0-6
      newArgs = Arrays.copyOfRange(args, 0, 6);

    if(!(newArgs[0].matches("^[a-zA-Z\"\\s]*$"))){
      printError("owner", newArgs[0]);
      err = true;
    }

    if(!(newArgs[2].matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))){
      printError("begin time (date)", newArgs[2]);
      err = true;
    }

    if(!(newArgs[3].matches("^([01]?\\d|2[0-3]):?([0-5]\\d)$"))){
      printError("begin time (time)", newArgs[3]);
      err = true;
    }

    if(!(newArgs[4].matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))){
      printError("end time (date)", newArgs[4]);
      err = true;
    }

    if(!(newArgs[5].matches("^([01]?\\d|2[0-3]):?([0-5]\\d)$"))){
      printError("end time (time)", newArgs[5]);
      err = true;
    }

    if (err)
      printUsage();

    return newArgs;
  }

  public static void printReadme() {
    System.out.println("README for Project 1");
    System.out.println("Author: Matthew Berzinskas (mberz2)");
    System.out.println("Course: CS410P, Advanced Programming with Java");
    System.out.println("Portland State University, Summer 2021");
    System.out.println("\nThis program implements a simple command-line appointment book.");
    System.out.println("Usage: java -jar /apptbook/target/apptbook-2021.0.0.jar [options] <args>\n");
    System.out.println("Options are (options may appear in any order");
    System.out.println("\t-print            - Prints a description of the new appointment");
    System.out.println("\t-README           - Prints a README for this project and exits");
    System.out.println("\nargs are (in this order):");
    System.out.println("\towner             - The person whose owns the appt book");
    System.out.println("\tdescription       - A description of the appointment");
    System.out.println("\tbeginTime         - When the appt begins (24-hour time)");
    System.out.println("\tendTime           - When the appt ends (24-hour time)");
    System.out.println("Owner and description should be enclosed in double-quotes");
    System.out.println("Date and time should be in the format: mm/dd/yyyy hh:mm \n");
    System.exit(0);
  }

  public static void printAppt() {

  }

  public static void printError(String s, String x){
    System.err.println("Error in <" + s + "> argument.");
    System.err.println("<" + x + "> contains improper characters.");
  }

  public static void printUsage(){
    System.err.println("Usage: java -jar /apptbook/target/apptbook-2021.0.0.jar [options] <args>\n");
    System.exit(1);
  }


}