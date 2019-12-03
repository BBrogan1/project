package ie.gmit.mypackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoxerManager implements Serializable{

	private static final long serialVersionUID = 1L;
    // Declare a List called boxers to hold the boxer objects
	private List<Boxer> boxerList;

	// Constructor
	public BoxerManager() {
		// Instantiate a boxer ArrayList
		boxerList = new ArrayList<Boxer>();
	}

	// Getters and Setters
	public List<Boxer> getBoxers() {
		return boxerList;
	}

	public void setBoxers(List<Boxer> boxerList) {
		this.boxerList = boxerList;
	}

	/**
	 * This method adds a Boxer to the Boxer List.
	 *
	 * @param boxer a boxer object that is to be added to the boxer list
	 * @return a boolean value indicating if the add was successful		
	 */                     
	public boolean addBoxer(Boxer boxer) {
		try {
			// Using Collections add method. It returns true if this collection
			// changed as a result of the call
			return boxerList.add(boxer);
		} catch (Exception error) {
			error.printStackTrace();
			return false;
		}
	}

	public boolean deleteBoxer(Boxer boxer) {
		try {
			// Using Collections remove method. It returns true if this list 
			// contained the specified element
			return boxerList.remove(boxer);
		} catch (Exception error) {
			error.printStackTrace();
			return false;
		}
	}

	public boolean deleteBoxerById(String boxerId) {
		// Search for the Boxer by ID
		Boxer boxer = searchForBoxerById(boxerId);
		// If a Boxer was found then delete the boxer
		if (boxer != null) {
			return deleteBoxer(boxer);
		} else {
			// If no boxer was found Return false
			return false;
		}
	}

	public Boxer searchForBoxerById(String boxerId) {

		// 3. Iterator
		Iterator<Boxer> boxerIterator = boxerList.iterator();
		Boxer boxerObjectHolder;
		while (boxerIterator.hasNext()) {
			// Store next Boxer
			boxerObjectHolder = boxerIterator.next();
			// Check if boxerId equals that of current boxer object
			if (boxerObjectHolder.getBoxerId().equals(boxerId)) {
				return boxerObjectHolder;
			}
		}

		// Return null if Boxer ID was not found
		return null;
	}

	// Find a list of boxer by first name
	public List<Boxer> getBoxersByFirstName(String firstName) {
		// Create a new ArrayList to Hold Boxers with same names
		List<Boxer> sameNames = new ArrayList<Boxer>();
		// Loop over arrayList for Boxer type elements in the boxers ArrayList do
		for (Boxer boxer : boxerList) {
			// If I find a boxer with the given first name then add to list
			if (boxer.getFirstName().equalsIgnoreCase(firstName)) {
				sameNames.add(boxer);
			}
		}
		// Check if list has any boxers
		if (sameNames.size() > 0) {
			// If boxers were found then return the list
			return sameNames;
		}
		// If no boxers were found with that first name then return null
		return null;
	}

	public void loadBoxerFile(String pathToFile) {
		File inFile = new File(pathToFile);
		FileReader fileReader = null;
		BufferedReader br = null;
		String record = null;

		try {
			fileReader = new FileReader(inFile);
			br = new BufferedReader(fileReader);
			br.readLine(); //discard first line of csv file
			while ((record = br.readLine()) != null) {
				String[] elements = record.split(",");
				Boxer newBoxer = new Boxer(elements[0], elements[1], elements[2]);
				this.addBoxer(newBoxer);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int findTotalBoxers() {
		// returns the current number of Boxers in the ArrayList
		return boxerList.size();
	}
	
	public BoxerManager loadDB(String dbPath){
    	BoxerManager bm = null;
    	try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(dbPath));
			bm = (BoxerManager) in.readObject();
    		in.close();
    	} catch (Exception e) {
    		System.out.print("[Error] Cannont load DB. Cause: ");
    		e.printStackTrace();
    	}
		return bm;
    }

}
