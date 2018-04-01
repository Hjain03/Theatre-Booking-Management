package com.theatre.service;

import java.util.List;

import com.theatre.model.BookingMembers;
import com.theatre.model.TheatreRows;

/**
 * 
 * @author Himanshu Jain Theatre Booking Service Interface to create contract or
 *         Abstract behavior .
 *
 */
public interface TheatreSeatBookingService {
	List<String> processBookingRequests(TheatreRows rows, BookingMembers members);
}
