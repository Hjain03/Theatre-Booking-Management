package com.theatre.service;

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
	@Override
	public void processBookingRequests(TheatreRows rows, BookingMembers members) {
		if (TheatreUtils.isValidInput(rows, members)) {
			for (String customer : members.getName()) {
				String[] temp = customer.split(" ");
				members.setPartyCount(Integer.parseInt(temp[1]));
				checkAndAllocateSeats(rows, members, temp[0]);
			}
		} else {
			TheatreUtils.printMessage(message);
		}

	}

	/**
	 * This method implements business logic for seating arrangement and allocation
	 * as per requirement : 1.Fill as many orders as possible 2.Put parties as close
	 * to the front as possible. 3.If there are not enough seats available in the
	 * theater to handle a party, tell them "Sorry, we can't handle your party." 4.
	 * Each party must sit in a single row in a single section. If they won't fit,
	 * tell them "Call to split party".
	 */
	private static void checkAndAllocateSeats(TheatreRows rows, BookingMembers members, String name) {
		int valueOfi = -1;
		int valueOfj = -1;
		int sum = 0;
		for (int i = 0; i < rows.getRow().size(); i++) {
			List<Integer> row = rows.getRow().get(i);
			for (int j = 0; j < row.size(); j++) {
				sum += row.get(j);
				if (row.get(j) == members.getPartyCount()
						|| (row.get(j) > members.getPartyCount() && row.get(j) % members.getPartyCount() == 0)) {
					valueOfi = i;
					valueOfj = j;
					break;
				}
			}
			if (valueOfi == i) {
				break;
			}
		}
		getAllocationStatus(valueOfi, valueOfj, rows, members, name, sum);
	}

	/**
	 * 
	 * @param indexOfi
	 *            : Current index of iterator i after serving requests
	 * @param indexOfj
	 *            : Current index of iterator j after serving requests
	 * @param rows
	 *            : Instance of TheatreRows Class contains the layout and section
	 *            information per row.
	 * @param members
	 *            : Instance of BookingMembers Class contains the Member information
	 *            and Party count request.
	 * @param name
	 *            : Variable containing name of member whose request is getting
	 *            served .
	 * @param sum
	 *            : Summation of the seats information getting used per request .
	 *            This method delegate the status of ticket booking to print on the
	 *            console using Utility Class.
	 * 
	 */
	private static void getAllocationStatus(int indexOfi, int indexOfj, TheatreRows rows, BookingMembers members,
			String name, int sum) {
		String result;
		if (indexOfi != -1 && indexOfj != -1) {
			result = name + " Row " + (indexOfi + 1) + " Section " + (indexOfj + 1);
			TheatreUtils.printMessage(result);
			List<Integer> row = rows.getRow().get(indexOfi);
			int temp = row.get(indexOfj);
			row.remove((int) indexOfj);
			row.add(indexOfj, temp - members.getPartyCount());
			return;
		}
		if (members.getPartyCount() <= sum) {
			result = name + " Call to split party.";
			TheatreUtils.printMessage(result);
			return;
		} else {
			result = name + " Sorry, we can't handle your party.";
			TheatreUtils.printMessage(result);
			return;
		}

	}
}
