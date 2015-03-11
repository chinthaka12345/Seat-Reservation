import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Class UserInterface.
 */
public class UserInterface {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		// Sample data
		int maxGroupSize = 6;
		int minGroupSize = 2;
		int tableNos = 10;
		
		List<Table> tables = new ArrayList<Table>();
		Random r = new Random();
		int Low = minGroupSize;
		int High = maxGroupSize+1;
		
		// Random table data for testing purpose
		for(int i=0; i<tableNos; i++) {
			int size = r.nextInt(High-Low) + Low;
			tables.add(new Table(size, i+"-"+size));
		}
				
		SeatingManager sMgr = new SeatingManager(tables, maxGroupSize);
		
		System.out.println("##### UI for seat #####");		
		for(;;) {
			
			BufferedReader bRead = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter commnad");
			final String inCmd = "in:";
			final String outCmd = "out:";
			System.out.println(inCmd + "# (number of customers, 2~6)");
			System.out.println(outCmd + "# (group number)");
			System.out.println();
	        String command;
			try {
				command = bRead.readLine();
				
				if(command.contains(inCmd)) {					
					int groupSize = Integer.parseInt(command.substring(inCmd.length()));
					if(groupSize<minGroupSize || groupSize>maxGroupSize) {
						System.out.println("out of bounds, must be in " + minGroupSize + "~" + maxGroupSize);
					} else {
						CustomerGroup cGroup = new CustomerGroup(groupSize);
						sMgr.arrives(cGroup);
					}	
				}else if(command.contains(outCmd)) {
					int groupIndex = Integer.parseInt(command.substring(outCmd.length()));
					CustomerGroup leavingGroup = sMgr.getCustomerGroupByIndex(groupIndex);
					if(leavingGroup != null) {
						sMgr.leaves(leavingGroup);
					}
				}else {
					System.out.println("invalid command");
				}
				printStatus(sMgr);
			} catch (IOException e) {				
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Prints the status.
	 *
	 * @param sMgr the seating manager
	 */
	public static void printStatus(SeatingManager sMgr) {
		System.out.println("### current status ###");
		System.out.println("Table name \t| No. of empty seats \t| Table capacity");
		for(int i=0; i<sMgr.vacantSeats.size(); i++) {
			if(sMgr.vacantSeats.get(i)!=null) {
				for(Table vacant : sMgr.vacantSeats.get(i)) {
					if(vacant!=null) {
						System.out.println(vacant.getName() + "\t\t| " + vacant.getVacantSeats() + "\t\t\t| " + vacant.getSize());
					}
				}
			}
		}		
		System.out.println("\n");
		
		for(CustomerGroup customerGroup: sMgr.customerGroupList) {
			System.out.print("Customer Group : " + customerGroup.getIndex()+ "("+ customerGroup.getSize() + ")" + " : ");
			if(customerGroup.getTable() != null) {
				System.out.println(customerGroup.getTable().name);
			} else {
				System.out.println("waiting");
			}
		}
		System.out.println("### ---------------- ###");
	}

}
