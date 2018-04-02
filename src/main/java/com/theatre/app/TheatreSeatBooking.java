package com.theatre.app;

import java.util.Scanner;

import com.theatre.model.BookingMembers;
import com.theatre.model.TheatreRows;
import com.theatre.service.TheatreSeatBookingFactory;
import com.theatre.service.TheatreSeatBookingService;
import com.theatre.utils.TheatreUtils;

/**
 * 
 * @author Himanshu Jain Java main class for initiating and starting up the
 *         application.
 *
 */
public class TheatreSeatBooking {

	static Scanner scanner;

	public static void main(String[] args) {
		/*
		 * Taking Input from user using console for creating theatre layout and incoming
		 * request . Please follow the Input Format : The theater layout is made up of 1
		 * or more rows. Each row is made up of 1 or more sections separated by a
		 * space.After the theater layout, there is one empty line, followed by 1 or
		 * more theater requests. The theater request is made up of a name followed by a
		 * space and the number of requested tickets.
		 */
		TheatreRows rows = new TheatreRows();
		BookingMembers members = new BookingMembers();
		rows.setRow(TheatreUtils.inputRows());
		members.setName(TheatreUtils.inputRequests());

		/*
		 * Now after taking the Theatre layout details and Request for the Ticket
		 * seating arrangement we will using Facorty Pattern to call the Implementation
		 * class for business logic to allocate as much requests as possible.
		 */
		try {
			TheatreSeatBookingService bookingService = TheatreSeatBookingFactory.createFactoryInstance(rows, members);
			bookingService.processBookingRequests(rows, members);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
