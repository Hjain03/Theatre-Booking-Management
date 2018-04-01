package com.theatre.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.theatre.model.BookingMembers;
import com.theatre.model.TheatreRows;

/**
 * 
 * @author Himanshu Jain Utility Class for Input/Output and other cross cutting
 *         concerns.
 *
 */

public class TheatreUtils {

	private static Scanner scanner;
	// Regular expression in Java to check if String is number or not
	private static final Pattern pattern = Pattern.compile("([ ]*+[0-9]++[ ]*+)+");

	/*
	 * private constructor declaration to hide the implicit public constructor for
	 * this class
	 */
	private TheatreUtils() {
	}

	/**
	 * Row Input Utility Method
	 * 
	 * @return List of List of Integers which contains information about each row
	 *         corresponding sections.
	 */
	public static List<List<Integer>> inputRows() {
		scanner = new Scanner(System.in);
		List<List<Integer>> rows = new ArrayList<>();
		while (true) {
			String line = scanner.nextLine();
			if (line.trim().length() > 0) {
				if (pattern.matcher(line).matches()) {
					rows.add(parseStringAndReturnNumbers(line));
				} else {
					TheatreUtils.printMessage("Sorry , The given input can only accept Number . Please try again..!");
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
	private static List<Integer> parseStringAndReturnNumbers(String line) {
		List<Integer> row = new ArrayList<>();
		try {
			String[] temp = line.split(" ");
			for (int i = 0; i < temp.length; i++) {
				row.add(Integer.parseInt(temp[i]));
			}
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		return row;
	}

	/**
	 * Member Name Input Utility Method
	 * 
	 * @return List of String which contains the Name of Member and No. of Seats
	 *         Required (Space Separated).
	 */
	public static List<String> inputNames() {
		List<String> names = new ArrayList<>();
		while (true) {
			String line = scanner.nextLine();
			if (line.trim().length() > 0) {
				names.add(line);
			} else
				break;
		}
		return names;
	}

	/**
	 * This method validates for rows and members are properly provided in input by
	 * iterating and checking the instance of each value.
	 * 
	 * @param rows
	 * @param members
	 * @return Truw if all rows contains Integer and all members are String Type.
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
