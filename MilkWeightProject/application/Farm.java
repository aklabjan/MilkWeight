package application;

import java.util.Calendar;

/*
 * creates a Farm object
 */
public class Farm {
	public String farmID;
	public MilkEntry[] milkData;
	public int numEntries;
	public int totalWeight;

	/**
	 * creates a farm
	 * 
	 * @param farmID
	 */
	public Farm(String farmID) {
		numEntries = 0;
		this.farmID = farmID;
		milkData = new MilkEntry[50];
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
	public boolean insertMilkForDate(MilkEntry entry) {
		resize();
		milkData[numEntries] = entry;
		numEntries++;
		totalWeight += entry.weight;
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

	public String toString() {
		return "Farm ID: " + farmID + " Number of entries: " + numEntries;
	}

	public static void main(String[] args) {
		Farm farm = new Farm("Farm 0");
		farm.insertMilkForDate(new MilkEntry(2019, 1, 20, 10));
		farm.insertMilkForDate(new MilkEntry(2019, 2, 3, 5));
		farm.insertMilkForDate(new MilkEntry(2019, 3, 1, 3));
		for (int i = 0; i < farm.numEntries; i++) {
			System.out.println(farm.milkData[i].weight + " , " + farm.milkData[i].date.get(Calendar.MONTH));
		}
	}
}
