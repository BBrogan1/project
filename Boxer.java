package ie.gmit.mypackage;

import java.io.Serializable;
import java.util.Date;

public class Boxer implements Serializable{
	private static final long serialVersionUID = 1L;
    // Instance Variables
	private String boxerId;
	private String firstName;
	private String surname;
	private int fights;
	private int wins;
	private int ko;

	// Constructors
	public Boxer(String boxerId) {
		this.boxerId = boxerId;
	}
	
	public Boxer(String boxerId, String firstName, String surname) {
		// this(boxerId); - could set boxerId this way
		this.boxerId = boxerId;
		this.firstName = firstName;
		this.surname = surname;
	}
	
	public Boxer(String boxerId, String firstName, String surname, int fights) {
		// this(boxerId); - could set boxerId this way
		this.boxerId = boxerId;
		this.firstName = firstName;
		this.surname = surname;
		this.fights = fights;
	}

	// Getters and Setters
	public String getBoxerId() {
		return boxerId;
	}

	public void setBoxerId(String boxerId) {
		this.boxerId = boxerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getFights() {
		return fights;
	}

	public void setFights(int fights) {
		this.fights = fights;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getKo() {
		return ko;
	}

	public void setKo(int ko) {
		this.ko = ko;
	}

}
