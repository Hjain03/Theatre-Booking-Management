package com.theatre.model;

import java.util.List;

/**
 * 
 * @author Himanshu Jain POJO Class for Booking and Member Related Information .
 */
public class BookingMembers {
	private List<String> name;
	private int partyCount;
	private int totalSum;

	public int getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(int totalSum) {
		this.totalSum = totalSum;
	}

	public int getPartyCount() {
		return partyCount;
	}

	public void setPartyCount(int partyCount) {
		this.partyCount = partyCount;
	}

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> list) {
		this.name = list;
	}
}
