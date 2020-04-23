package application;

import java.util.Calendar;

/*
 * creates a Farm object
 */
public class Farm {
	public String farmID;
	public MilkEntry[] milkData;
	public int numEntries;

	/**
	 * creates a farm
	 * 
	 * @param farmID
	 */
	public Farm(String farmID) {
		numEntries = 0;
		this.farmID = farmID;
		milkData = new MilkEntry[10];
	}

	/**
	 * resizes the milkData array if it reaches its capacity
	 */
	private void resize() {
		if (numEntries == milkData.length) {
			MilkEntry[] temp = new MilkEntry[milkData.length * 2];
			for (int i = 0; i < milkData.length; i++) {
				temp[i] = milkData[i];
			}
			milkData = temp;
		}
	}

	/**
	 * addes a milk entry
	 * 
	 * @param year   the year
	 * @param month  the month
	 * @param day    the day
	 * @param weight the wieght
	 * @return true when inserted
	 */
	public boolean insertMilkForDate(int year, int month, int day, int weight) {
		resize();
		milkData[numEntries] = new MilkEntry(year, month, day, weight);
		numEntries++;
		return true;
	}

	/**
	 * edits the milk weight for a date
	 *
	 * @param year   the year
	 * @param month  the month
	 * @param day    the day
	 * @param weight the new wieght
	 * @return true when new milk weight was edited
	 */
	public boolean editMilkForDate(int year, int month, int day, int weight) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.YEAR, year);
		for (int i = 0; i < milkData.length; i++) {
			if (calendar.equals(milkData[i].date)) {
				milkData[i].weight = weight;
				return true;
			}
		}
		return false;
	}

	/**
	 * removes the milk weight at the given date
	 * 
	 * @param year  the year
	 * @param month the month
	 * @param day   the day
	 * @return the milk entires
	 */
	public MilkEntry[] removeMilkForDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.YEAR, year);
		for (int i = 0; i < milkData.length; i++) {
			if (calendar.equals(milkData[i].date)) {
				milkData[i].weight = 0;
				break;
			}
		}
		return milkData;
	}

	/**
	 * clears the milk entires
	 * 
	 * @return the milk entires
	 */
	public MilkEntry[] clearData() {
		MilkEntry[] temp = milkData;
		milkData = new MilkEntry[10];
		return temp;
	}

}
