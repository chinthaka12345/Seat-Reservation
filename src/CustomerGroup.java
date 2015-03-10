/**
 * The Class CustomerGroup.
 */
public class CustomerGroup {

	/** number of people in the group. */
	final int size;
	
	/** The table. */
	Table table;
	
	/** The index. */
	int index;

	/**
	 * Instantiates a new customer group.
	 *
	 * @param size the size
	 */
	public CustomerGroup(int size) {
		this.size = size;
		this.index = SeatingManager.getCustomerGroupIdx();
	}

	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Sets the table.
	 *
	 * @param table the new table
	 */
	public void setTable(Table table) {
		this.table = table;
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
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the index.
	 *
	 * @param index the new index
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
