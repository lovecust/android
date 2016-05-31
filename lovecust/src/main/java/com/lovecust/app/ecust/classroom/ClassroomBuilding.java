package com.lovecust.app.ecust.classroom;


public class ClassroomBuilding {
	private String building;
	private String course;
	private int amount;
	private int amount_available;
	private int amount_occupied;
	private String[] available;
	private String[] occupied;


	public String getBuilding () {
		return building;
	}

	public void setBuilding ( String building ) {
		this.building = building;
	}

	public String getCourse () {
		return course;
	}

	public void setCourse ( String course ) {
		this.course = course;
	}

	public int getAmount () {
		return amount;
	}

	public void setAmount ( int amount ) {
		this.amount = amount;
	}

	public int getAmount_available () {
		return amount_available;
	}

	public void setAmount_available ( int amount_available ) {
		this.amount_available = amount_available;
	}

	public int getAmount_occupied () {
		return amount_occupied;
	}

	public void setAmount_occupied ( int amount_occupied ) {
		this.amount_occupied = amount_occupied;
	}

	public String[] getAvailable () {
		return available;
	}

	public void setAvailable ( String[] available ) {
		this.available = available;
	}

	public String[] getOccupied () {
		return occupied;
	}

	public void setOccupied ( String[] occupied ) {
		this.occupied = occupied;
	}
}
