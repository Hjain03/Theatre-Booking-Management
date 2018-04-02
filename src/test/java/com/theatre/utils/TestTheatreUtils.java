package com.theatre.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.theatre.model.BookingMembers;
import com.theatre.model.TheatreRows;
import com.theatre.utils.exception.InvalidRowInputException;

/**
 * 
 * @author Himanshu Jain Testing class for TheatreUtils class which covers its
 *         unit test cases.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TheatreUtils.class, Scanner.class })
public class TestTheatreUtils {
	/* Attributes declaration */
	Scanner mockScanner = null;
	int lineNumber = 0;
	List<List<Integer>> expectedRows1 = null;
	List<List<Integer>> expectedRows2 = null;
	List<Integer> firstRow = null;
	List<String> expectedOrders = null;
	TheatreRows validRows = null;
	TheatreRows invalidRows = null;
	BookingMembers validMembers = null;
	BookingMembers invalidMembers = null;

	@Before
	public void setUp() throws Exception {
		mockAndInitializeObjects();
	}

	/*
	 * Testing of parseStringAndReturnNumbers method for failure case where it
	 * contains non numeric value and throwing InvalidRowInputException
	 */
	@Test
	public void TestParseStringAndReturnNumbersInputContainsNonNumericValue()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String inputLine = "5 5 abc";
		try {
			TheatreUtils.setInputReader(mockScanner);
			MockToReadValidRowsFromConsole();
		} catch (Exception ex) {
			assertTrue(ex.getCause() instanceof InvalidRowInputException);
			assertTrue((" Row [" + inputLine + "] contains non integer value").equals(ex.getCause().getMessage()));
		}
	}

	/* Testing of parseStringAndReturnNumbers method for success case */
	@Test
	public void TestParseStringAndReturnNumbersSuccess()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		TheatreUtils.setInputReader(mockScanner);
		MockToReadValidRowsFromConsole();

		Method method = Whitebox.getMethod(TheatreUtils.class, "parseStringAndReturnNumbers", String.class);

		List<Integer> actualRow = (List<Integer>) method.invoke(null, "5 5 5");

		assertTrue(actualRow.containsAll(firstRow));

	}

	/* Testing of inputRequests method for success case and valid input*/
	@Test
	public void TestInputRequestsWithValidInputs() {

		TheatreUtils.setInputReader(mockScanner);
		MockToReadPartyOrdersFromConsole();
		List<String> actualOrders = TheatreUtils.inputRequests();
		assertTrue(actualOrders.size() == expectedOrders.size());
		assertTrue(actualOrders.containsAll(expectedOrders));
	}

	/* Testing of inputRequests method for one empty input line*/
	@Test
	public void TestInputRequestsWithOneEmptyInputLine() {

		TheatreUtils.setInputReader(mockScanner);
		MockToReadPartyOrdersFromConsoleWithEmptyInputLine();
		List<String> actualOrders = TheatreUtils.inputRequests();
		assertTrue(actualOrders.size() == expectedOrders.size());
		assertTrue(actualOrders.containsAll(expectedOrders));
	}

	/* Testing of isValidInput method for success case */
	@Test
	public void TestisValidInputReturnsTrue() {
		// both input valid
		boolean isValid = TheatreUtils.isValidInput(validRows, validMembers);
		assertTrue(isValid);
	}
	
	/* Testing of isValidInput method for failure case i.e invalid theater rows */
	@Test
	public void TestisValidInputReturnsFalseWithInvalidTheatreRows() {
		boolean isValid = TheatreUtils.isValidInput(invalidRows, validMembers);
		assertFalse(isValid);
	}

	/* Testing of isValidInput method for failure case i.e invalid booking members */
	@Test
	public void TestisValidInputReturnsFalseWithInvalidBookingMembers() {
		boolean isValid = TheatreUtils.isValidInput(validRows, invalidMembers);
		assertFalse(isValid);
	}

	/* Testing of isValidInput method for failure case both invalid i.e Theatre Rows & Booking Members */
	@Test
	public void TestisValidInputReturnsFalseWithBothArgsInvalid() {
		boolean isValid = TheatreUtils.isValidInput(invalidRows, invalidMembers);
		assertFalse(isValid);
	}
	
	private void mockAndInitializeObjects() {
		mockScanner = PowerMockito.mock(Scanner.class);
		PowerMockito.mockStatic(Scanner.class);

		expectedRows1 = new ArrayList<List<Integer>>();
		firstRow = new ArrayList<Integer>();
		firstRow.add(5);
		firstRow.add(5);
		firstRow.add(5);
		List<Integer> secondRow = new ArrayList<Integer>();

		secondRow.add(null);
		secondRow.add(6);
		secondRow.add(5);

		expectedRows1.add(firstRow);
		//expectedRows1.add(secondRow);
		expectedRows2 = new ArrayList<List<Integer>>();
		expectedRows2.add(secondRow);

		validRows = new TheatreRows();
		validRows.setRow(expectedRows1);

		invalidRows = new TheatreRows();
		invalidRows.setRow(expectedRows2);

		validMembers = new BookingMembers();
		List<String> validRequest = new ArrayList<String>();
		validRequest.add("Smith 5");
		validMembers.setName(validRequest);

		invalidMembers = new BookingMembers();
		List<String> invalidRequest = new ArrayList<String>();
		invalidRequest.add("Smith 5");
		invalidRequest.add(null);
		invalidMembers.setName(invalidRequest);

	}

	private void MockToReadValidRowsFromConsole() {
		Mockito.when(mockScanner.nextLine()).thenAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				if (lineNumber == 0) {
					lineNumber++;
					return "5 5 5";

				}
				if (lineNumber == 1) {
					lineNumber++;
					return "4 6 5";

				}
				if (lineNumber == 2) {
					lineNumber++;
					return "";

				}

				return "";
			}
		});
	}

	private void MockToReadRowsWithSecondRowHavingNonNumericValue() {
		Mockito.when(mockScanner.nextLine()).thenAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				if (lineNumber == 0) {
					lineNumber++;
					return "5 5 5";

				}
				if (lineNumber == 1) {
					lineNumber++;
					return "4 6 abc";

				}
				if (lineNumber == 2) {
					lineNumber++;
					return "";

				}

				return "";
			}
		});
	}

	private void MockToReadPartyOrdersFromConsole() {
		Mockito.when(mockScanner.nextLine()).thenAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				if (lineNumber == 0) {
					lineNumber++;
					return "Smith 4";

				}
				if (lineNumber == 1) {
					lineNumber++;
					return "John 5";

				}

				return "";
			}
		});
		expectedOrders = new ArrayList<String>();
		expectedOrders.add("Smith 4");
		expectedOrders.add("John 5");

	}

	private void MockToReadPartyOrdersFromConsoleWithEmptyInputLine() {
		Mockito.when(mockScanner.nextLine()).thenAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				if (lineNumber == 0) {
					lineNumber++;
					return "Smith 4";

				}
				if (lineNumber == 1) {
					lineNumber++;
					return "";

				}

				return "";
			}
		});
		expectedOrders = new ArrayList<String>();
		expectedOrders.add("Smith 4");

	}
}