import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class SeatingManager.
 */
public class SeatingManager {

	/** The max group size. */
	final int maxGroupSize;
	
	/** The vacant seats. 
	 * Keep real time status of vacant seats
	 * */
	Map<Integer,List<Table>> vacantSeats = new HashMap<Integer, List<Table>>();
	
	/** The customer group list. */
	List<CustomerGroup> customerGroupList = new ArrayList<CustomerGroup>();
	
	/** The customer group index. */
	static int customerGroupIdx = 1;
	
	/**
	 * Instantiates a new seating manager. Constructor
	 *
	 * @param tables the tables
	 * @param maxGroupSize the max group size
	 */
	public SeatingManager(List<Table> tables, int maxGroupSize) {
		initVacantSeats(tables, maxGroupSize);
		this.maxGroupSize = maxGroupSize;		
	}
	
	/**
	 * Initializes the vacant seats map.
	 *
	 * @param tables the tables
	 * @param maxGroupSize the max group size
	 */
	public void initVacantSeats(List<Table> tables, int maxGroupSize) {
		// Initialize rows of map
		for(int i=0; i<=maxGroupSize; i++) {
			List<Table> vacantSize = new ArrayList<Table>();
			vacantSeats.put(i, vacantSize);
		}

		// Filling each row, at initialize all seats are vacant
		for(Table table: tables) {
			List<Table> vacantRow = vacantSeats.get(table.getSize());
			vacantRow.add(table);
		}
	}
	
	/**
	 * Group arrives and wants to be seated..
	 *
	 * @param group the group
	 */
	public void arrives(CustomerGroup group) {
		int groupSize = group.getSize();
		customerGroupList.add(group);
		for(int i=groupSize; i<=this.maxGroupSize; i++) {
			List<Table> tableList = vacantSeats.get(i);
			if(tableList.size()!=0) {
				Table table = tableList.get(0); // Always get very first table in list
				group.setTable(table);
				int vacants = table.getVacantSeats()-groupSize;
				table.setVacantSeats(vacants);
				tableList.remove(0);
				List<Table> newTableList = vacantSeats.get(vacants);
				addTable(newTableList, table);
				return;
			}
		}
	}
	
	/**
	 * Whether seated or not, the group leaves the restaurant.
	 *
	 * @param group the group
	 */
	public void leaves(CustomerGroup group) {
		// Remove from customer group list
		for(int i=0; i<this.customerGroupList.size(); i++) {
			if(group.getIndex()==this.customerGroupList.get(i).getIndex()) {
				System.out.println("remove : " + group.getIndex());
				this.customerGroupList.remove(i);
				break;
			} else {
				System.out.println("Not match : " + this.customerGroupList.get(i).getIndex());
			}
		}
		
		// Release table
		if(group.getTable()!=null) {
			Table table = group.getTable();
			List<Table> currList = this.vacantSeats.get(table.getVacantSeats());
			for(int i=0; i<currList.size(); i++) {
				if(table.getName().compareTo(currList.get(i).getName())==0) {
					currList.remove(i);
					break;
				}
			}
			table.setVacantSeats(table.getVacantSeats() + group.getSize());
			allocateTableForWaitings(table);
		}

	}
	/* Return the table at which the group is seated, or null if
	they are not seated (whether they're waiting or already left). */
	/**
	 * Return the table at which the group is seated, or null if
	 * they are not seated (whether they're waiting or already left).
	 *
	 * @param group the group
	 * @return the table
	 */
	public Table locate(CustomerGroup group) {
		Table table = null;
		return table;
	}
 
	/**
	 * Gets the customer group index.
	 *
	 * @return the customer group index
	 */
	public static int getCustomerGroupIdx() {
		return customerGroupIdx++;
	}
	
	/**
	 * Gets the customer group by index.
	 *
	 * @param index the index
	 * @return the customer group by index
	 */
	public CustomerGroup getCustomerGroupByIndex(int index) {
		for(CustomerGroup cusGroup: this.customerGroupList) {
			if(cusGroup.getIndex() == index) {
				return cusGroup;
			}
		}
		return null;
	}
	
	/**
	 * Add table in to vacant map list.
	 *
	 * @param tables the tables
	 * @param table the table
	 */
	void addTable(List<Table> tables, Table table) {
		// Need to sort list according to seat capacity of table
		for(int j=0; j<tables.size(); j++) {
			if(table.getSize()<=tables.get(j).getSize()) {
				tables.add(j, table);
				return;
			}
		}
		tables.add(table);
		return;
	}
	
	/**
	 * Check for waiting groups and allocate vacant table.
	 *
	 * @param table the table
	 */
	void allocateTableForWaitings(Table table) {
		for(CustomerGroup waitingGroup: this.customerGroupList) {
			if(waitingGroup.getTable()==null) {
				if(waitingGroup.getSize()<=table.getVacantSeats()) {
					// Allocate table
					waitingGroup.setTable(table);
					int vacants = table.getVacantSeats()-waitingGroup.getSize();
					table.setVacantSeats(vacants);
					List<Table> newTableList = vacantSeats.get(vacants);
					addTable(newTableList, table);
					return;
				}
			}
		}
		// Not matched any waiting group
		List<Table> newTableList = vacantSeats.get(table.getVacantSeats());
		addTable(newTableList, table);
	}
	
}
