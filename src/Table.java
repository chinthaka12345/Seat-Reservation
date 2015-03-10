/**
 * The Class Table.
 */
public class Table {

	/** Number of chairs around this table. */
	final int size; 
	
	/** Identification for table. */
	final String name;
	
	/** Number of vacant seats in table. */
	int vacantSeats; 
	
	/**
	 * Instantiates a new table.
	 *
	 * @param size the size
	 * @param name the name
	 */
	public Table(int size, String name) {
		this.size = size;
		this.name = name;
		this.vacantSeats = size;
	}

	/**
	 * Gets the vacant seats.
	 *
	 * @return the vacant seats
	 */
	public int getVacantSeats() {
		return vacantSeats;
	}

	/**
	 * Sets the vacant seats.
	 *
	 * @param vacantSeats the new vacant seats
	 */
	public void setVacantSeats(int vacantSeats) {
		this.vacantSeats = vacantSeats;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
