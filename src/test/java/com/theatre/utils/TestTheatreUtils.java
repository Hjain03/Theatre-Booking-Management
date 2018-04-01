package com.theatre.utils;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.BeforeClass;
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

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TheatreUtils.class, Scanner.class })
public class TestTheatreUtils {

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

	@Test
	public void testInputRowsSuccess() {

		TheatreUtils.setInputReader(mockScanner);
		MockToReadValidRowsFromConsole();
		List<List<Integer>> rows = TheatreUtils.inputRows();
		assertTrue(rows.size() == expectedRows1.size());
		verifyExpectedAndActualOutput(rows, expectedRows1);
	}

	@Test
	public void testInputRowsSecondRowInvalid() {

		TheatreUtils.setInputReader(mockScanner);
		MockToReadRowsWithSecondRowHavingNonNumericValue();
		List<List<Integer>> rows = TheatreUtils.inputRows();
		assertTrue(rows.size() == expectedRows2.size());
		verifyExpectedAndActualOutput(rows, expectedRows2);
	}

	@Test
	public void TestParseStringAndReturnNumbersInputContainsNonNumericValue()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String inputLine = "5 5 abc";
		try {
			TheatreUtils.setInputReader(mockScanner);
			MockToReadValidRowsFromConsole();
		} catch (Exception ex) {
			// asert that thrown exception is same as the method invoked throws and message
			// is also same
			assertTrue(ex.getCause() instanceof InvalidRowInputException);
			assertTrue((" Row [" + inputLine + "] contains non integer value").equals(ex.getCause().getMessage()));
		}
	}

	@Test
	public void TestParseStringAndReturnNumbersSuccess()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		TheatreUtils.setInputReader(mockScanner);
		MockToReadValidRowsFromConsole();

		Method method = Whitebox.getMethod(TheatreUtils.class, "parseStringAndReturnNumbers", String.class);

		List<Integer> actualRow = (List<Integer>) method.invoke(null, "5 5 5");

		assertTrue(actualRow.containsAll(firstRow));

	}

	@Test
	public void TestInputNamesWithValidInputs() {

		TheatreUtils.setInputReader(mockScanner);
		MockToReadPartyOrdersFromConsole();
		List<String> actualOrders = TheatreUtils.inputNames();
		assertTrue(actualOrders.size() == expectedOrders.size());
		assertTrue(actualOrders.containsAll(expectedOrders));
	}

	@Test
	public void TestInputNamesWithOneEmptyInputLine() {

		TheatreUtils.setInputReader(mockScanner);
		MockToReadPartyOrdersFromConsoleWithEmptyInputLine();
		List<String> actualOrders = TheatreUtils.inputNames();
		assertTrue(actualOrders.size() == expectedOrders.size());
		assertTrue(actualOrders.containsAll(expectedOrders));
	}

	@Test
	public void TestisValidInputReturnsTrue() {
		// both input valid
		boolean isValid = TheatreUtils.isValidInput(validRows, validMembers);
		assertTrue(isValid);
	}

	public void TestisValidInputReturnsFalseWithInvalidTheatreRows() {
		// first input arg TheatreRows is not valid

		boolean isValid = TheatreUtils.isValidInput(invalidRows, validMembers);
		assertFalse(isValid);
	}

	public void TestisValidInputReturnsFalseWithInvalidBookingMembers() {
		// second input arg BookingMembers is not valid

		boolean isValid = TheatreUtils.isValidInput(validRows, invalidMembers);
		assertFalse(isValid);
	}

	public void TestisValidInputReturnsFalseWithBothArgsInvalid() {
		// both input arg BookingMembers and TheatreRows are not valid

		boolean isValid = TheatreUtils.isValidInput(invalidRows, invalidMembers);
		assertFalse(isValid);
	}

	private void verifyExpectedAndActualOutput(List<List<Integer>> rows, List<List<Integer>> rowsOutput) {
		for (int i = 0; i < rows.size(); i++) {
			List<Integer> outputRow = rows.get(i);
			List<Integer> expectedRow = rowsOutput.get(i);
			assertTrue(outputRow.size() == expectedRow.size());
			assertTrue(outputRow.containsAll(expectedRow));
		}

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

		secondRow.add(4);
		secondRow.add(6);
		secondRow.add(5);

		expectedRows1.add(firstRow);
		expectedRows1.add(secondRow);
		expectedRows2 = new ArrayList<List<Integer>>();
		expectedRows2.add(firstRow);

		validRows = new TheatreRows();
		validRows.setRow(expectedRows1);

		invalidRows = new TheatreRows();
		invalidRows.setRow(expectedRows2);

		validMembers = new BookingMembers();
		List<String> validOrders = new ArrayList<String>();
		validOrders.add("Smith 5");
		validMembers.setName(validOrders);

		invalidMembers = new BookingMembers();
		List<String> invalidOrders = new ArrayList<String>();
		invalidOrders.add("Smith 5");
		invalidOrders.add("4 Smith");

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
