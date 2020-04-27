package application;

import java.util.Calendar;

public class DataManager {

	private CheeseFactory cheeseFactory;
	private int total;
	private int min;
	private int max;
	private DataEntry[] data;

	public class DataEntry {
		public String column1Data;
		public double column2Data;

	}

	public DataManager(CheeseFactory cheeseFactory) {
		this.cheeseFactory = cheeseFactory;
		data = new DataEntry[10];
	}

	public int getMonthlyAverageForFarm(String farmID, int month) {
		return total / 12;
	}

	public int getMonthlyMinForFarm() {
		return min;
	}

	public int getMonthlyMaxForFarm() {
		return max;
	}

	public DataEntry[] getDataForFarmReport(String farmID) {
		int[] monthlyTotal = new int[12];
		total = 0;
		min = 0;
		data = new DataEntry[12];
		Farm farm = new Farm("");
		// gets the farm
		for (int i = 0; i < cheeseFactory.numFarms; i++) {
			if (cheeseFactory.milkDataFromFarms[i].farmID.equals(farmID)) {
				farm = cheeseFactory.milkDataFromFarms[i];
				break;
			}
		}
		// loops through and addes each milkEntry to correct monthly total
		for (int i = 0; i < farm.numEntries; i++) {
			monthlyTotal[farm.milkData[i].date.get(Calendar.MONTH)] += farm.milkData[i].weight;
			total += farm.milkData[i].weight;
		}
		//loops through to find monthly percentage and 
		min = monthlyTotal[0];
		for (int i = 0; i < monthlyTotal.length; i++) {
			data[i].column1Data = Integer.toString(i+1);
			data[i].column2Data = monthlyTotal[i] / total;
			if (max < monthlyTotal[i]) {
				max = monthlyTotal[i];
			}
			if (min > monthlyTotal[i]) {
				min = monthlyTotal[i];
			}
		}
		return data;
	}
}
