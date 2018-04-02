package com.theatre.service;

import com.theatre.model.BookingMembers;
import com.theatre.model.TheatreRows;

/**
 * 
 * @author Himanshu Jain This is TheatreSeatBooking Factory Class which will be
 *         invoked as per the type of Theatre and Booking Members used .
 *
 */
public class TheatreSeatBookingFactory {

	/* To hide the implicit public constructor */
	private TheatreSeatBookingFactory() {
	}

	/*
	 * This class will return the object of desired impl class. Usage of Factory
	 * pattern will increase the scope further extension or enhancement in different
	 * types of theatre which can be implemented using factory class. Factory class
	 * will delegate call to specified Impl class .
	 * 
	 * We can also go with Proxy Pattern too if we want to expose our application as
	 * service so that it can be consumed using Proxy obj.
	 */

	public static TheatreSeatBookingService createInstance() {
		return new TheatreSeatBookingServiceImpl();
	}
}
