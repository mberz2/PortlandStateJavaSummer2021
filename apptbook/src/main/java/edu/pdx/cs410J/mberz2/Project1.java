package edu.pdx.cs410J.mberz2;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static void main(String[] args) {

    if (args.length == 0) {
      System.err.println("Error: Missing command line arguments");
    } else if (args.length == 5) {
      System.err.println("Error: Too many command line arguments");
    } else {
      for (String arg : args) {
        System.out.println("You entered:");
        System.out.println(arg);

        System.out.println("Adding info to new appointment.");
        Appointment appointment = new Appointment();

      }

      System.exit(1);
    }
  }

}