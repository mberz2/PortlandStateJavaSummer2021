package edu.pdx.cs410J.mberz2;

import java.util.Arrays;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

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

  public static void checkInput(String[] args) {

    if (args.length == 0) {
      System.err.println("Error: Missing command line arguments");
      System.err.println("Run as:\nJava -jar /apptbook/target/apptbook-2021.0.0.jar -README");
      System.err.println("\nFor more detailed information.");
    } else if (args.length > 7) {
      System.err.println("Error: Too many command line arguments");
      System.err.println("Run as:\nJava -jar /apptbook/target/apptbook-2021.0.0.jar -README");
      System.err.println("\nFor more detailed information.");
    }

  }

  public static void parseInput(String[] args){

    String[] newArgs = new String[0];
    if (args.length == 7)
      //Two options flags, take from index 1-7
      newArgs = Arrays.copyOfRange(args, 1, 7);

    else if (args.length == 6)
      //One option flag, take from index 0-6
      newArgs = Arrays.copyOfRange(args, 0, 6);

    AppointmentBook appBook = new AppointmentBook(newArgs[0]);
    Appointment app = new Appointment(newArgs[1], newArgs[2]+" "+newArgs[3], newArgs[4]+" "+newArgs[5]);

    //Temp print
    System.out.println("Appointment Book of: "+appBook.getOwnerName());
    System.out.println("Desc: "+app.getDescription());
    System.out.println("Start: "+app.getBeginTimeString());
    System.out.println("End: "+app.getEndTimeString());

  }

  public static void main(String[] args) {

    checkInput(args);
    parseInput(args);


    System.exit(0);
  }
}