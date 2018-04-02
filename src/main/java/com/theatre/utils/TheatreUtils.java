package com.theatre.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.theatre.model.BookingMembers;
import com.theatre.model.TheatreRows;
import com.theatre.utils.exception.InvalidRowInputException;

/**
 * 
 * @author Himanshu Jain Utility Class for Input/Output and other cross cutting
 *         concerns.
 *
 */

public class TheatreUtils {

	private static Scanner scanner;
	// Regular expression for Row as format "number number" .
	private static final Pattern patternForRow = Pattern.compile("([ ]*+[0-9]++[ ]*+)+");
	// Regular expression for Member Request as format "char number"
	private static final Pattern patternForNames = Pattern.compile("([ ]*+[0-9A-Za-z]++[ ]*+)+");
	private static String rowValidationMessage = "Sorry,The input given is expected to be Number seperated by space for section in rows e.g '4 4 5' . Please try again..!";
	private static String requestValidationMessage = "Sorry,The input given is expected to be Characters seperated by space followed by number e.g 'ABC 3' . Please try again..!";
	private static String welcomeMessage = "Welcome,Please enter Layout details as no. of seats in section separated by space e.g  '2 2' . Press Press Enter to Start Entering Bookign Request..!";
	private static String welcomeRequestMessage = "Please enter Booking Request with name and no. tickets separted by space 'ABC 3' Press Press Enter to View Booking Allocation Status and Exit..!";
	private static String welcomeErrorRequestMessage = "Sorry, Problem with the Input format . Do not enter extra spaces and more than one entry in single row . Use the Format : 'Name TicketCount..!'";

	
	
	/*
	 * private constructor declaration to hide the implicit public constructor for
	 * this class
	 */
	private TheatreUtils() {
	}

	public static void setInputReader(Scanner extScanner) {
		scanner = extScanner;
	}

	/**
	 * Row Input Utility Method
	 * 
	 * @return List of List of Integers which contains information about each row
	 *         corresponding sections.
	 */
	public static List<List<Integer>> inputRows() {
		scanner = new Scanner(System.in);
		List<List<Integer>> rows = new ArrayList<List<Integer>>();
		TheatreUtils.printMessage(welcomeMessage);
		while (true) {
			String line = scanner.nextLine();
			if (line.trim().length() > 0) {
				if (patternForRow.matcher(line).matches()) {
					try {
						rows.add(parseStringAndReturnNumbers(line));
					} catch (InvalidRowInputException irie) {
						TheatreUtils.printMessage(irie.getMessage());
					}
				} else {
					TheatreUtils.printMessage(rowValidationMessage);
					continue;
				}

			} else
				break;
		}
		return rows;
	}

	/**
	 * 
	 * @param line
	 *            is each set of section contains in row
	 * @return List of Integer
	 */
	private static List<Integer> parseStringAndReturnNumbers(String line) throws InvalidRowInputException {
		List<Integer> row = new ArrayList<Integer>();
		try {
			String[] temp = line.split(" ");
			for (int i = 0; i < temp.length; i++) {
				row.add(Integer.parseInt(temp[i]));
			}
		} catch (NumberFormatException nfe) {
			throw new InvalidRowInputException(" Row [" + line + "] contains non integer value");
		}
		return row;
	}

	/**
	 * Member Name Input Utility Method
	 * 
	 * @return List of String which contains the Name of Member and No. of Seats
	 *         Required (Space Separated).
	 */
	public static List<String> inputRequests() {
		List<String> names = new ArrayList<String>();
		TheatreUtils.printMessage(welcomeRequestMessage);
		while (true) {
			String line = scanner.nextLine();
			if(line.length() - line.replaceAll(" ", "").length()>1) {
				TheatreUtils.printMessage(welcomeErrorRequestMessage);
			}else {
			if (line.trim().length() > 0) {
				if (patternForNames.matcher(line).matches()) {
					names.add(line);
				} else {
					TheatreUtils.printMessage(requestValidationMessage);
				}
			} else
				break;
			}
		}
		return names;
	}

	/**
	 * This method validates for rows and members are properly provided in input by
	 * iterating and checking the instance of each value.
	 * 
	 * @param rows
	 * @param members
	 * @return True if all rows contains Integer and all members are String Type.
	 */
	public static boolean isValidInput(TheatreRows rows, BookingMembers members) {
		boolean isValid = true;
		try {
			for (List<Integer> outerRow : rows.getRow()) {
				for (Integer innerRow : outerRow) {
					if (!(innerRow instanceof Integer)) {
						throw new NumberFormatException();
					}
				}
			}
			for (String name : members.getName()) {
				if (!(name instanceof String)) {
					throw new NumberFormatException();
				}
			}
		} catch (NumberFormatException ex) {
			isValid = false;
		}
		return isValid;
	}

	/**
	 * Simple Print Message Utility class to provide output in console.
	 * 
	 * @param message
	 */
	public static void printMessage(String message) {
		System.out.println(message);

	}

}
