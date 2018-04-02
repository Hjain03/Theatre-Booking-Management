package com.theatre.service;

import java.util.List;

import com.theatre.model.BookingMembers;
import com.theatre.model.TheatreRows;

/**
 * 
 * @author Himanshu Jain Theatre Booking Service Interface will create set of
 *         rules for signature and i/o params. Basically We are using Interface
 *         here in case we want to export our application as Library or 3rd
 *         Party is using our app then Interface would be key player in setting
 *         the contract between implementor/invoker.Also benefits if enhancement
 *         is required apart from current functionality
 *
 */
public interface TheatreSeatBookingService {
	List<String> processBookingRequests(TheatreRows rows, BookingMembers members);
}
