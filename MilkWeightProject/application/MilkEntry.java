package application;

import java.util.Calendar;

public class MilkEntry {
	public Calendar date;
	public int weight;

	public MilkEntry(int year, int month, int day, int weight) {
		date = Calendar.getInstance();
		date.set(Calendar.MONTH, month - 1);
		date.set(Calendar.DAY_OF_MONTH, day);
		date.set(Calendar.YEAR, year);
		this.weight = weight;
	}
}
