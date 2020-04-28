package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * will take in a file from user and output data
 * 
 * @author evandeutsch
 *
 */
public class FileReader {

	private ArrayList<String> farmData;
	private ArrayList<MilkEntry> milkData;

	/**
	 * will instantiate the FileReader, moving the information in the given file
	 * into usable data
	 * 
	 * @param filePath
	 * @throws FileNotFoundException
	 */
	public FileReader(String filePath, String theYear) throws FileNotFoundException {
		farmData = new ArrayList<String>();
		milkData = new ArrayList<MilkEntry>();
		int n = 0;
		File test = new File(filePath);
		if (!test.exists()) {
			throw new FileNotFoundException();
		}
		for (int i = 1; i <= 12; i++) {
			boolean valid = true;
			File file = new File(filePath + "/" + theYear + "-" + i + ".csv");
			Scanner input = null;
			try {
				input = new Scanner(file);
			} catch (FileNotFoundException e) {
				valid = false;
			}
			if (valid) {
				input.nextLine();
				while (input.hasNextLine()) {
					try {
						String temp = input.nextLine();
						String date = temp.substring(0, temp.indexOf(","));
						String id = temp.substring(temp.indexOf(",") + 1);
						temp = id;
						id = id.substring(0, id.indexOf(","));
						String stringWeight = temp.substring(temp.indexOf(",") + 1);

						temp = date;
						String stringYear = temp.substring(0, temp.indexOf("-"));
						String stringMonth = temp.substring(temp.indexOf("-") + 1);
						stringMonth = stringMonth.substring(0, stringMonth.indexOf("-"));
						temp = stringMonth;
						String stringDay = temp.substring(temp.indexOf("-") + 1);
						int year = Integer.parseInt(stringYear);
						int month = Integer.parseInt(stringMonth);
						int day = Integer.parseInt(stringDay);
						int weight = Integer.parseInt(stringWeight);

						farmData.add(id);
						milkData.add(new MilkEntry(year, month, day, weight));
					} catch (NumberFormatException e) {
					} catch (StringIndexOutOfBoundsException e) {
					}
				}
				input.close();
			}
		}

	}

	/**
	 * displays the constructed data
	 */
	public void displayData() {
		for (int i = 0; i < farmData.size(); i++) {
			System.out.println("Farm " + farmData.get(i) + ": " + milkData.get(i));
		}
	}

	/**
	 * gets the array representation of the farm data
	 * 
	 * @return farmData
	 */
	public ArrayList<String> getFarmData() {
		return farmData;
	}

	/**
	 * gets the array representation of the milk data
	 * 
	 * @return milkData
	 */
	public ArrayList<MilkEntry> getMilkData() {
		return milkData;
	}
}
