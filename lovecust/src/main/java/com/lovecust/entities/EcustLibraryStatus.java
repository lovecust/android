package com.lovecust.entities;


public class EcustLibraryStatus {
	private int[] occupied;
	private int[] available;
	private int today;

	public int[] getOccupied () {
		return occupied;
	}

	public void setOccupied ( int[] occupied ) {
		this.occupied = occupied;
	}

	public int[] getAvailable () {
		return available;
	}

	public void setAvailable ( int[] available ) {
		this.available = available;
	}

	public int getToday () {
		return today;
	}

	public void setToday ( int today ) {
		this.today = today;
	}
}
