package com.theatre.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
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
import com.theatre.utils.TheatreUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TheatreUtils.class })
public class TestTheatreSeatBookingServiceImpl {

	TheatreSeatBookingServiceImpl theatreSeatBookingServiceImpl = Mockito.spy(new TheatreSeatBookingServiceImpl());
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(System.out);
		System.setErr(System.err);
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(TheatreUtils.class);
	}
	@Test
	public void testProcessBookingRequests_Failure() {
		TheatreRows rows = null;
		BookingMembers members = null;
		when(TheatreUtils.isValidInput(rows, members)).thenReturn(false);
		theatreSeatBookingServiceImpl.processBookingRequests(rows, members);
		PowerMockito.verifyStatic(times(1));
	}

	@Test
	public void testProcessBookingRequests_Success() {
		List<List<Integer>> finalRow = new ArrayList<List<Integer>>();
		List<Integer> row = new ArrayList<Integer>();
		List<String> name = new ArrayList<String>();
		TheatreRows rows = new TheatreRows();
		BookingMembers members = new BookingMembers();
		row.add(6);row.add(6);
		name.add("Smith 2");
		finalRow.add(row);
		rows.setRow(finalRow);
		members.setName(name);
		members.setPartyCount(2);
	
		when(TheatreUtils.isValidInput(rows, members)).thenReturn(true);
		theatreSeatBookingServiceImpl.processBookingRequests(rows, members);
		PowerMockito.verifyStatic(times(1));
	}
	
	public void testProcessBookingRequests_Success_Message() {
		
	}

}
