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
	private final long year = 2019;

	/**
	 * testing purposes
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		FileReader fr = new FileReader("/Users/Rachael/Desktop/Eclipse/MilkWeightProject/csv/small");
		fr.displayData();

	}

	/**
	 * will instantiate the FileReader, moving the information in the given file
	 * into usable data
	 * 
	 * @param filePath
	 * @throws FileNotFoundException
	 */
	public FileReader(String filePath) throws FileNotFoundException {
		farmData = new ArrayList<String>();
		milkData = new ArrayList<MilkEntry>();
		for (int i = 1; i <= 12; i++) {
			File file = new File(filePath + "/" + year + "-" + i + ".csv");
			Scanner input = new Scanner(file);
			input.nextLine();
			while (input.hasNextLine()) {
				String temp = input.nextLine();
				String date = temp.substring(0, temp.indexOf(","));
				String id = temp.substring(temp.indexOf(",") + 1);
				temp = id;
				id = id.substring(0, id.indexOf(","));
				String weight = temp.substring(temp.indexOf(",") + 1);
				
				farmData.add("" + id.charAt(id.length() - 1));
				
				temp = date;
				String year = temp.substring(0, temp.indexOf("-"));
				String month = temp.substring(temp.indexOf("-") + 1);
				month = month.substring(0, month.indexOf("-"));
				temp = month;
				String day = temp.substring(temp.indexOf("-") + 1);

				milkData.add(new MilkEntry(Integer.parseInt(year), Integer.parseInt(month),
						Integer.parseInt(day), Integer.parseInt(weight)));

				// data.add();
			}
			input.close();
		}

	}

	/**
	 * displays the constructed data
	 */
	public void displayData() {
		for(int i = 0; i < farmData.size(); i++) {
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
