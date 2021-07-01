README for Project 1
Author: Matthew Berzinskas (mberz2)
Course: CS410P, Advanced Programming with Java
Portland State University, Summer 2021

This program implements a simple command-line appointment book.

Usage: java -jar /apptbook/target/apptbook-2021.0.0.jar [options] <args>

Options are (options may appear in any order");
    -PRINT            - Prints a description of the new appointment
    -README           - Prints a README for this project and exits

args are (in this order):
    owner             - The person whose owns the appt book
    description       - A description of the appointment
    beginTime         - When the appt begins (24-hour time)
    endTime           - When the appt ends (24-hour time)
Owner and description should be enclosed in double-quotes
Date and time should be in the format: mm/dd/yyyy hh:mm