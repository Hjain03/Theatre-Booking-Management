package com.theatre.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.theatre.model.BookingMembers;
import com.theatre.model.TheatreRows;
import com.theatre.service.TheatreSeatBookingServiceImpl;
import com.theatre.utils.TheatreUtils;

/**
 * 
 * @author Himanshu Jain Testing class for TheatreSeatBookingServiceImpl class
 *         which covers its unit test cases.
 *
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TheatreUtils.class })
public class TestTheatreSeatBookingServiceImpl {

	TheatreSeatBookingServiceImpl theatreSeatBookingServiceImpl = Mockito.spy(new TheatreSeatBookingServiceImpl());

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(TheatreUtils.class);
	}

	/* In case of Validation is not passed */
	@Test
	public void testProcessBookingRequests_Failure() {
		TheatreRows rows = null;
		BookingMembers members = null;
		when(TheatreUtils.isValidInput(rows, members)).thenReturn(false);
		List<String> bookingStatuses = theatreSeatBookingServiceImpl.processBookingRequests(rows, members);
		assertEquals(bookingStatuses.size(), 0);
		PowerMockito.verifyStatic(times(1));
	}

	/* In case of Successful allocation of seats to the member */
	@Test
	public void testProcessBookingRequests_Success() {
		List<List<Integer>> finalRow = new ArrayList<List<Integer>>();
		List<Integer> row = new ArrayList<Integer>();
		List<String> name = new ArrayList<String>();
		TheatreRows rows = new TheatreRows();
		BookingMembers members = new BookingMembers();
		row.add(6);
		row.add(6);
		name.add("Smith 2");
		finalRow.add(row);
		rows.setRow(finalRow);
		members.setName(name);
		members.setPartyCount(2);

		when(TheatreUtils.isValidInput(rows, members)).thenReturn(true);
		List<String> bookingStatuses = theatreSeatBookingServiceImpl.processBookingRequests(rows, members);
		assertEquals(bookingStatuses.size(), 1);
		assertEquals(bookingStatuses.get(0), "Smith Row 1 Section 1");
		PowerMockito.verifyStatic(times(1));
	}

	/*
	 * In case of request cannot be handled due to not enough seats available in
	 * theater to handle party
	 */
	@Test
	public void testProcessBookingRequestsCanNotBeHandled() {
		List<List<Integer>> finalRow = new ArrayList<List<Integer>>();
		List<Integer> row = new ArrayList<Integer>();
		List<String> name = new ArrayList<String>();
		TheatreRows rows = new TheatreRows();
		BookingMembers members = new BookingMembers();
		row.add(6);
		row.add(6);
		name.add("Smith 14");
		finalRow.add(row);
		rows.setRow(finalRow);
		members.setName(name);
		members.setPartyCount(2);

		when(TheatreUtils.isValidInput(rows, members)).thenReturn(true);
		List<String> bookingStatuses = theatreSeatBookingServiceImpl.processBookingRequests(rows, members);
		assertEquals(bookingStatuses.size(), 1);
		assertEquals(bookingStatuses.get(0), "Smith Sorry, we can't handle your party.");
		PowerMockito.verifyStatic(times(1));

	}

	/*
	 * In case of request cannot be fulfilled due to non-contiguous seat allocation
	 * . Call for Split
	 */
	@Test
	public void testProcessBookingRequests_SuccessWithSplit() {
		List<List<Integer>> finalRow = new ArrayList<List<Integer>>();
		List<Integer> row = new ArrayList<Integer>();
		List<String> name = new ArrayList<String>();
		TheatreRows rows = new TheatreRows();
		BookingMembers members = new BookingMembers();
		row.add(6);
		row.add(6);
		name.add("Smith 7");
		finalRow.add(row);
		rows.setRow(finalRow);
		members.setName(name);
		members.setPartyCount(2);

		when(TheatreUtils.isValidInput(rows, members)).thenReturn(true);
		List<String> bookingStatuses = theatreSeatBookingServiceImpl.processBookingRequests(rows, members);
		assertEquals(bookingStatuses.size(), 1);
		assertEquals(bookingStatuses.get(0), "Smith Call to split party.");
		PowerMockito.verifyStatic(times(1));
	}

}
