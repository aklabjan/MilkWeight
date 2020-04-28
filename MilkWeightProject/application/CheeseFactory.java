package application;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

public class CheeseFactory {
	public String name;
	public Farm[] milkDataFromFarms;
	public int numFarms;
	public int totalWeight;

	/**
	 * creates a new cheese factory
	 * 
	 * @param year   the year
	 * @param month  the month
	 * @param day    the day
	 * @param farmID the farmID
	 * @param weight the weight
	 * @return
	 */
	public CheeseFactory(String name) {
		this.name = name;
		milkDataFromFarms = new Farm[10];
		numFarms = 0;
		totalWeight = 0;
	}

	/**
	 * resizes the milk data from farm
	 */
	private void resize() {
		if (numFarms == milkDataFromFarms.length) {
			Farm[] temp = new Farm[milkDataFromFarms.length * 2];
			for (int i = 0; i < milkDataFromFarms.length; i++)
				temp[i] = milkDataFromFarms[i];
			milkDataFromFarms = temp;
		}
	}

	/**
	 * inserts a data entry into the cheese factory
	 * 
	 * @param year   the year
	 * @param month  the month
	 * @param day    the day
	 * @param farmID the farmID
	 * @param weight the weight
	 * @return true when inserted
	 */
	public boolean insertSingleData(MilkEntry entry, String farmID) {
		for (int i = 0; i < numFarms; i++) {
			if (farmID.equals(milkDataFromFarms[i].farmID)) {
				milkDataFromFarms[i].insertMilkForDate(entry);
				totalWeight += entry.weight;
				return true;
			}
		}
		Farm newFarm = new Farm(farmID);
		newFarm.insertMilkForDate(entry);
		resize();
		milkDataFromFarms[numFarms] = newFarm;
		numFarms++;
		totalWeight += entry.weight;
		return true;
	}

	/**
	 * inserts data into program from a file
	 * 
	 * @param filePath
	 * @return true if works and false otherwise
	 * @throws FileNotFoundException
	 */
	public boolean insertData(String filePath,String year) throws FileNotFoundException {
		FileReader fr;
		fr = new FileReader(filePath,year);
		ArrayList<String> farmData = fr.getFarmData();
		ArrayList<MilkEntry> milkData = fr.getMilkData();
		for (int i = 0; i < farmData.size(); i++) {
			this.insertSingleData(milkData.get(i), farmData.get(i));
		}
		return true;
	}

	/**
	 * edits the milk weight of a farm
	 * 
	 * @param year   the year
	 * @param month  the month
	 * @param day    the day
	 * @param farmID the farmID
	 * @param weight the weight
	 * @return true when the milk weight was edited for the farm
	 */
	public boolean editSingleData(int year, int month, int day, String farmID, int weight) {
		for (int i = 0; i < milkDataFromFarms.length; i++) {
			if (farmID.equals(milkDataFromFarms[i].farmID))
				return milkDataFromFarms[i].editMilkForDate(year, month, day, weight);
		}
		return false;
	}

	/**
	 * removes a milk entry
	 * 
	 * @param year   the year
	 * @param month  the month
	 * @param day    the day
	 * @param farmID the farm id
	 * @return the milk data for the farms
	 */
	public Farm[] removeSingleData(int year, int month, int day, String farmID) {
		for (int i = 0; i < milkDataFromFarms.length; i++) {
			if (farmID.equals(milkDataFromFarms[i].farmID))
				milkDataFromFarms[i].removeMilkForDate(year, month, day);
		}
		return milkDataFromFarms;
	}

	public static void main(String[] args) {
		CheeseFactory cheese = new CheeseFactory("");
		cheese.insertSingleData(new MilkEntry(2019, 1, 2, 10),"Farm 0");
		cheese.insertSingleData(new MilkEntry(2019, 2, 4, 5),"Farm 1");
		cheese.insertSingleData(new MilkEntry(2019, 4, 30,3), "Farm 0");
		for (int i = 0; i < cheese.numFarms; i++) {
			System.out.println(cheese.milkDataFromFarms[i].farmID);
			for (int j = 0; j < cheese.milkDataFromFarms[i].numEntries; j++) {
				System.out.println(cheese.milkDataFromFarms[i].milkData[j].weight + " , "
						+ cheese.milkDataFromFarms[i].milkData[j].date.get(Calendar.MONTH));
			}
		}
	}

}
