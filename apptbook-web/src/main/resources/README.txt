README for Project 3
Author: Matthew Berzinskas (mberz2)
Course: CS410P, Advanced Programming with Java
Portland State University, Summer 2021

This program expands on the simple command line appointment book implemented
in Program 1 and 2, and adds support for dates and sorting as well as the
ability to output a prettier format to either the standard out, or a file.

Provided a text document argument, the given appointment will be added. If the
file contains appointments, they will also be read in, creating continuity.

Usage: java -jar /apptbook/target/apptbook-2021.0.0.jar [options] <args>

Options are (options may appear in any order");
    -PRINT            - Prints a description of the new appointment
    -README           - Prints a README for this project and exits
    -textFile file    - Where to read/write the appointment book
    -pretty file      - Where to write the pretty output.
                        Note: Use (-pretty -) for printing to standard out.

args are (in this order):
    owner             - The person whose owns the appt book
    description       - A description of the appointment
    begin             - When the appt begins (12-hour time)
    end               - When the appt ends (12-hour time)

Owner and description should be enclosed in double-quotes
Date and time should be in the format:
   mm/dd/yyyy hh:mm am/pm