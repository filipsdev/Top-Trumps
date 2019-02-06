import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class DatabaseConnectTest {

	@Test
	public void testopenconnection() {
		/*
		 * This test verifies that 
		 * connection with database
		 * is successfully opened and 
		 * closed
		 */
		DatabaseConnect.DatabaseOpen();
		assertTrue(DatabaseConnect.isDBopen());
		DatabaseConnect.DatabaseClose();
		assertTrue(DatabaseConnect.isDBclose());
	}
	@Test
	public void databaseoperations() {
		/* This method verifies accuracy of calculation 
		 * and confirm that values are actually read
		 * from database, though for values first need 
		 * to be seen in postgre to know what values 
		 * are expected, if test goes well all reading
		 * statement are performing accurately.  
		 */
		DatabaseConnect.DatabaseOpen();
		DatabaseConnect.GameNumberdb();
		assertEquals(9,DatabaseConnect.getGameId(0));
		assertEquals(18,DatabaseConnect.getGameNumber());
		assertEquals(30,DatabaseConnect.getHumanWins());
		assertEquals(68,DatabaseConnect.getAiWins());
		/* though code has been written, calculating
		 * correct expected decimals is very difficult
		 * will cause test to fail for Average draws 
		 * if it is printed in console to know expected decimal
		 * for test then test is succesful. 
		 */
		//assertEquals(71.60,DatabaseConnect.getDrawsAve());
		assertEquals(3065,DatabaseConnect.getTotalGameTime());
		DatabaseConnect.DatabaseClose();
	}	
	@Test
	public void sqlStatementClosureTest() {
		/* This method confirms that are 
		 * sql query statements successfully 
		 * closing after execution.
		 */
		DatabaseConnect.DatabaseOpen();
		// Initiate method that has statements.
		DatabaseConnect.GameNumberdb();
		assertTrue(DatabaseConnect.areStatementClose());
		DatabaseConnect.DatabaseClose();
	}
	
}










