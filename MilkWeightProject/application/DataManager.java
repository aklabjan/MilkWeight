package application;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Calendar;

/*
 * creates a dataManager to analyse cheesefactory
 */
public class DataManager {

	public CheeseFactory cheeseFactory;
	public int total;
	private int min;
	private int max;

	/*
	 * creates an object for data entry to be displayed
	 */
	public class DataEntry {
		public String column1Data;
		public double column2Data;

		public String toString() {
			return "Column 1: " + column1Data + " Column 2: " + column2Data;
		}

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
	 * @throws Exception
	 */
	public DataEntry[] getDataForFarmReport(String farmID) throws Exception {
		int[] monthlyTotal = new int[12];
		total = 0;
		DataEntry[] data = new DataEntry[12];
		for (int i = 0; i < data.length; i++) {
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
		// if far is not in the cheesefactory
		if (farm == null)
			throw new Exception("farm does not exist in cheesefactory");

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
			data[i].column2Data = ((double) monthlyTotal[i] / (double) total) * 100;
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
		for (int i = 0; i < data.length; i++) {
			data[i] = new DataEntry();
		}
		for (int i = 0; i < cheeseFactory.numFarms; i++) {
			data[i].column1Data = cheeseFactory.milkDataFromFarms[i].farmID;
			data[i].column2Data = ((double) cheeseFactory.milkDataFromFarms[i].totalWeight
					/ (double) cheeseFactory.totalWeight) * 100;
		}
		return data;
	}

	public DataEntry[] getDataForDateRange(LocalDate begin, LocalDate end) throws Exception {
		// creation of calendar instance of begnning and end date
		Calendar beginningDate = Calendar.getInstance();
		beginningDate.set(Calendar.MONTH, begin.getMonthValue()-1);
		beginningDate.set(Calendar.DAY_OF_MONTH, begin.getDayOfMonth());
		beginningDate.set(Calendar.YEAR, begin.getYear());
		Calendar endDate = Calendar.getInstance();
		endDate.set(Calendar.MONTH, end.getMonthValue()-1);
		endDate.set(Calendar.DAY_OF_MONTH, end.getDayOfMonth());
		endDate.set(Calendar.YEAR, end.getYear());
		if (beginningDate.after(endDate))
			throw new Exception("Beginning Date is after end date.");
		// set up to start collecting weights
		total = 0;
		DataEntry[] data = new DataEntry[cheeseFactory.numFarms];
		for (int i = 0; i < data.length; i++) {
			data[i] = new DataEntry();
		}
		int[] dateRangeTotalByFarm = new int[cheeseFactory.numFarms];
		// loops through farms and calculates total weight for date range for each farm
		for (int i = 0; i < cheeseFactory.numFarms; i++) {
			int monthlyTotal = 0;
			for (int j = 0; j < cheeseFactory.milkDataFromFarms[i].numEntries; j++) {
				if (cheeseFactory.milkDataFromFarms[i].milkData[j].date.after(beginningDate)
						&& cheeseFactory.milkDataFromFarms[i].milkData[j].date.before(endDate)) {
					monthlyTotal += cheeseFactory.milkDataFromFarms[i].milkData[j].weight;
					total += cheeseFactory.milkDataFromFarms[i].milkData[j].weight;
				}
			}
			dateRangeTotalByFarm[i] = monthlyTotal;
		}
		min = dateRangeTotalByFarm[0];
		max = 0;
		// created data array and finds min and max
		for (int i = 0; i < dateRangeTotalByFarm.length; i++) {
			data[i].column1Data = cheeseFactory.milkDataFromFarms[i].farmID;
			data[i].column2Data = ((double) dateRangeTotalByFarm[i] / (double) total) * 100;
		}
		return data;
	}

	/**
	 * the monthly report data
	 * 
	 * @param month the month being analysed
	 * @return the data
	 * @throws Exception
	 */
	public DataEntry[] getDataforMonthlyReport(String month) throws Exception {
		int m = Integer.parseInt(month) - 1;
		if (m < 0 || m > 11)
			throw new Exception("Month is not in range.");
		total = 0;
		DataEntry[] data = new DataEntry[cheeseFactory.numFarms];
		for (int i = 0; i < data.length; i++) {
			data[i] = new DataEntry();
		}
		int[] monthlyTotalByFarm = new int[cheeseFactory.numFarms];
		// loops through farms and calculates total weight for month for each farm
		for (int i = 0; i < cheeseFactory.numFarms; i++) {
			int monthlyTotal = 0;
			for (int j = 0; j < cheeseFactory.milkDataFromFarms[i].numEntries; j++) {
				if (cheeseFactory.milkDataFromFarms[i].milkData[j].date.get(Calendar.MONTH) == m) {
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
			data[i].column2Data = ((double) monthlyTotalByFarm[i] / (double) total) * 100;
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

	public static void main(String[] args) throws Exception {
		CheeseFactory cheese = new CheeseFactory("");
		try {
			cheese.insertData("/Users/ana/Desktop/CompSci400/csv/small", "2019");
		} catch (FileNotFoundException e) {
		}
		DataManager dm = new DataManager(cheese);
		DataEntry[] data = dm.getDataforMonthlyReport("1");
		for (int i = 0; i < data.length; i++) {
			System.out.println(data[i].toString());
		}
	}
}
