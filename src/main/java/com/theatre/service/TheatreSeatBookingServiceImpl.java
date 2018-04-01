package com.theatre.service;

import java.util.ArrayList;
import java.util.List;

import com.theatre.model.BookingMembers;
import com.theatre.model.TheatreRows;
import com.theatre.utils.TheatreUtils;

/**
 * 
 * @author Himanshu Jain Theatre Ticket Booking Service Implementation class or
 *         Concrete Class which contains business logic for actual seating
 *         allocation .
 *
 */
public class TheatreSeatBookingServiceImpl implements TheatreSeatBookingService {
	/* Logger Message for Validation failure */
	private static String message = "The Input for layout is expected to be number";

	/**
	 * Implementation of the Interface Method which will process all the booking
	 * request as FIFO basis
	 */
	public List<String> processBookingRequests(TheatreRows rows, BookingMembers members) {
		List<String> bookinStatuses = new ArrayList<String>();
		if (TheatreUtils.isValidInput(rows, members)) {
			for (String customer : members.getName()) {
				String[] temp = customer.split(" ");
				members.setPartyCount(Integer.parseInt(temp[1]));
				String bookingStatus = checkAndAllocateSeats(rows, members, temp[0]);
				TheatreUtils.printMessage(bookingStatus);
				bookinStatuses.add(bookingStatus);
			}
		} else {
			TheatreUtils.printMessage(message);
		}
		return bookinStatuses;
	}

	/**
	 * This method implements business logic for seating arrangement and allocation
	 * as per requirement : 1.Fill as many orders as possible 2.Put parties as close
	 * to the front as possible. 3.If there are not enough seats available in the
	 * theater to handle a party, tell them "Sorry, we can't handle your party." 4.
	 * Each party must sit in a single row in a single section. If they won't fit,
	 * tell them "Call to split party".
	 */
	private static String checkAndAllocateSeats(TheatreRows rows, BookingMembers members, String name) {
		int valueOfRow = -1;
		int valueOfSection = -1;
		int sumOfSeats = 0;
		for (int row = 0; row < rows.getRow().size(); row++) {
			List<Integer> tempRow = rows.getRow().get(row);
			for (int section = 0; section < tempRow.size(); section++) {
				sumOfSeats += tempRow.get(section);
				if (tempRow.get(section) == members.getPartyCount() || (tempRow.get(section) > members.getPartyCount()
						&& tempRow.get(section) % members.getPartyCount() == 0)) {
					valueOfRow = row;
					valueOfSection = section;
					break;
				}
			}
			if (valueOfRow == row) {
				break;
			}
		}
		return getAllocationStatus(valueOfRow, valueOfSection, rows, members, name, sumOfSeats);
	}

	/**
	 * 
	 * @param valueOfRow
	 *            : Current index of iterator i after serving requests
	 * @param valueOfSection
	 *            : Current index of iterator j after serving requests
	 * @param rows
	 *            : Instance of TheatreRows Class contains the layout and section
	 *            information per row.
	 * @param members
	 *            : Instance of BookingMembers Class contains the Member information
	 *            and Party count request.
	 * @param memberName
	 *            : Variable containing name of member whose request is getting
	 *            served .
	 * @param sumOfSeats
	 *            : Summation of the seats information getting used per request .
	 *            This method delegate the status of ticket booking to print on the
	 *            console using Utility Class.
	 * 
	 */
	private static String getAllocationStatus(int valueOfRow, int valueOfSection, TheatreRows rows,
			BookingMembers members, String memberName, int sumOfSeats) {
		String result = null;
		boolean isDone = true;
		if (valueOfRow != -1 && valueOfSection != -1) {
			result = memberName + " Row " + (valueOfRow + 1) + " Section " + (valueOfSection + 1);
			List<Integer> row = rows.getRow().get(valueOfRow);
			int temp = row.get(valueOfSection);
			row.remove((int) valueOfSection);
			row.add(valueOfSection, temp - members.getPartyCount());
			isDone = false;
		}
		if (isDone) {
			if (members.getPartyCount() <= sumOfSeats) {
				result = memberName + " Call to split party.";
			} else {
				result = memberName + " Sorry, we can't handle your party.";
			}
		}
		return result;
	}
}
