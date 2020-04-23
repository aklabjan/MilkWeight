package application;

public class CheeseFactory {
	public String name;
	public Farm[] milkDataFromFarms;
	public int numFarms;

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
	public boolean insertSingleData(int year, int month, int day, String farmID, int weight) {
		for (int i = 0; i < milkDataFromFarms.length; i++) {
			if (farmID.equals(milkDataFromFarms[i].farmID)) {
				milkDataFromFarms[i].insertMilkForDate(year, month, day, weight);
				return true;
			}
		}
		Farm newFarm = new Farm(farmID);
		newFarm.insertMilkForDate(year, month, day, weight);
		resize();
		milkDataFromFarms[numFarms] = newFarm;
		numFarms++;
		return true;
	}

	public boolean insertData() {
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

}
