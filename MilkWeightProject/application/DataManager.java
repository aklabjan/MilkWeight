package application;

import java.util.Calendar;

/*
 * creates a dataManager to analyse cheesefactory
 */
public class DataManager {

	private CheeseFactory cheeseFactory;
	private int total;
	private int min;
	private int max;

	/*
	 * creates an object for data entry to be displayed
	 */
	public class DataEntry {
		public String column1Data;
		public double column2Data;

	}

	/**
	 * creates a dataManager
	 * 
	 * @param cheeseFactory the cheesefactory being analysed
	 */
	public DataManager(CheeseFactory cheeseFactory) {
		this.cheeseFactory = cheeseFactory;
	}

	/**
	 * gets monthlyaverage for farm
	 * 
	 * @return the average
	 */
	public int getMonthlyAverageForFarm() {
		return total / 12;
	}

	/**
	 * gets monthly min for farm
	 * 
	 * @return the min
	 */
	public int getMonthlyMinForFarm() {
		return min;
	}

	/**
	 * gets monthly max for farm
	 * 
	 * @return the max
	 */
	public int getMonthlyMaxForFarm() {
		return max;
	}

	/**
	 * the farm report data
	 * 
	 * @param farmID the farm looking for report
	 * @return array of the data entries analysing the data
	 */
	public DataEntry[] getDataForFarmReport(String farmID) {
		int[] monthlyTotal = new int[12];
		total = 0;
		DataEntry[] data = new DataEntry[12];
		for(int i = 0; i < data.length; i++) {
			data[i] = new DataEntry();
		}
		Farm farm = null;
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
		// loops through to find monthly percentage and
		min = monthlyTotal[0];
		max = 0;
		for (int i = 0; i < monthlyTotal.length; i++) {
			data[i].column1Data = Integer.toString(i + 1);
			data[i].column2Data = (double)monthlyTotal[i] / (double)total;
			if (max < monthlyTotal[i]) {
				max = monthlyTotal[i];
			}
			if (min > monthlyTotal[i]) {
				min = monthlyTotal[i];
			}
		}
		return data;
	}

	/**
	 * the annual report data
	 * 
	 * @return data entries containing annual report
	 */
	public DataEntry[] getDataforAnnualReport() {
		DataEntry[] data = new DataEntry[cheeseFactory.numFarms];
		for (int i = 0; i < cheeseFactory.numFarms; i++) {
			data[i].column1Data = cheeseFactory.milkDataFromFarms[i].farmID;
			data[i].column2Data = cheeseFactory.milkDataFromFarms[i].totalWeight / cheeseFactory.totalWeight;
		}
		return data;
	}

	/**
	 * the monthly report data
	 * 
	 * @param month the month being analysed
	 * @return the data
	 */
	public DataEntry[] getDataforMonthlyReport(int month) {
		total = 0;
		DataEntry[] data = new DataEntry[cheeseFactory.numFarms];
		int[] monthlyTotalByFarm = new int[cheeseFactory.numFarms];
		// loops through farms and calculates total weight for month for each farm
		for (int i = 0; i < cheeseFactory.numFarms; i++) {
			int monthlyTotal = 0;
			for (int j = 0; j < cheeseFactory.milkDataFromFarms[i].numEntries; j++) {
				if (cheeseFactory.milkDataFromFarms[i].milkData[j].date.get(Calendar.MONTH) == month - 1) {
					monthlyTotal += cheeseFactory.milkDataFromFarms[i].milkData[j].weight;
					total += cheeseFactory.milkDataFromFarms[i].milkData[j].weight;
				}
			}
			monthlyTotalByFarm[i] = monthlyTotal;
		}
		min = monthlyTotalByFarm[0];
		max = 0;
		// created data array and finds min and max
		for (int i = 0; i < monthlyTotalByFarm.length; i++) {
			data[i].column1Data = cheeseFactory.milkDataFromFarms[i].farmID;
			data[i].column2Data = monthlyTotalByFarm[i] / total;
			if (max < monthlyTotalByFarm[i]) {
				max = monthlyTotalByFarm[i];
			}
			if (min > monthlyTotalByFarm[i]) {
				min = monthlyTotalByFarm[i];
			}
		}
		return data;
	}

	/**
	 * gets monthly average
	 * 
	 * @return int
	 */
	public int getMonthlyAverage() {
		return total / cheeseFactory.numFarms;
	}

	/**
	 * gets monthly max
	 * 
	 * @return int
	 */
	public int getMonthlyMax() {
		return max;
	}

	/**
	 * gets monthly min
	 * 
	 * @return int
	 */
	public int getMonthlyMin() {
		return min;
	}

}
