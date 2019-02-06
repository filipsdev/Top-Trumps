
import java.sql.Connection;  

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 

public class DatabaseConnect {
	static Connection c = null;
	private static int gamenumber;
	private static int gameHuWin;
	private static int gameAiWin;
	private static double gamedraws;
	private static int gameLen;
	private static boolean verifydbopen;
	private static boolean verifydbclose;
	private static boolean confirmstatementclosure;
	
	
	public static void DatabaseOpen() {       
		try {          
			Class.forName("org.postgresql.Driver");          
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MainTest",            
					"postgres", "Psql8544");         
			
			if(c !=null) 
			{
			verifydbopen = true;
		//	System.out.println("Opened database successfully");
			}else {
				verifydbopen = false;
			}
		} catch ( Exception e ) { 
			e.printStackTrace(); 
		}
	} 
	public static void DatabaseClose() {
		try {
			c.close();
			if(c.isClosed()) {
				verifydbclose = true;
			}else {
				verifydbclose = false;
			}
		//	System.out.println("Data Base Close");
		}catch ( Exception e ) { 
			e.printStackTrace(); 
	}
   }
	
	public static void insertValues(int gnumber, int humanwin, int aiwin, int draws, int gamelength) {
		int gameIdNum = 0;
		gameIdNum = getGameId(gameIdNum);
		gameIdNum++;
		try {
			
			Statement stmt = c.createStatement();
			
		String insertquery = "INSERT INTO Game(GameId, GamesNo, HumanWin, AIWin, NoDraws, GameLength)"
				+ "VALUES ('"+gameIdNum+"','"+gnumber+"','"+humanwin+"', '"+aiwin+"', '"+draws+"', '"+gamelength+"')";
	    stmt.executeUpdate(insertquery);
		// System.out.println("Values Inserted");
		stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void GameNumberdb() {
		try {
			 
			 PreparedStatement stmtGsum =  c.prepareStatement("SELECT SUM(GamesNo) As Total_games FROM   Game");
			 ResultSet result = stmtGsum.executeQuery();
			 result.next();
		     String Gamesum = result.getString(1);
		  //   System.out.println(Gamesum);
		     gamenumber = Integer.parseInt(Gamesum);
			 stmtGsum.close();
			 if (stmtGsum.isClosed()) {confirmstatementclosure = true;
			                          }else{confirmstatementclosure = false;}
			 
			 PreparedStatement stmtHwin = c.prepareStatement("SELECT SUM(HumanWin) As Human_score FROM   Game");
			 ResultSet getHquery = stmtHwin.executeQuery();
	         getHquery.next();
	         String Hwinsum = getHquery.getString(1);
			// System.out.println(Hwinsum);
			 gameHuWin = Integer.parseInt(Hwinsum);
			 stmtHwin.close();
			 if (stmtHwin.isClosed()) {confirmstatementclosure = true;
			                          }else{confirmstatementclosure = false;}
			 
			 PreparedStatement stmtAiwin = c.prepareStatement("SELECT SUM(AIWin) As AI_score FROM   Game");
			 ResultSet getAiquery = stmtAiwin.executeQuery();
	         getAiquery.next();
	         String Aiwinsum = getAiquery.getString(1);
			// System.out.println(Aiwinsum);
			 gameAiWin = Integer.parseInt(Aiwinsum);
			 stmtAiwin.close();
			 if (stmtAiwin.isClosed()) {confirmstatementclosure = true;
                                       }else{confirmstatementclosure = false;}
			 
			 PreparedStatement stmtDraws = c.prepareStatement("SELECT AVG(NoDraws) As Ave_Draws  FROM   Game");
			 ResultSet getDraws = stmtDraws.executeQuery();
	         getDraws.next();
	         String AveDraws = getDraws.getString(1);
			// System.out.println(AveDraws);
			 gamedraws = Double.parseDouble(AveDraws);
			 stmtDraws.close();
			 if (stmtDraws.isClosed()) {confirmstatementclosure = true;
                                       }else{confirmstatementclosure = false;}
			 
			 PreparedStatement stmtGlength = c.prepareStatement("SELECT MAX(GameLength) As Length_Game FROM   Game");
			 ResultSet getGlquery = stmtGlength.executeQuery();
			 getGlquery.next();
	         String Gamelength = getGlquery.getString(1);
			// System.out.println(Gamelength);
			 gameLen = Integer.parseInt(Gamelength);
			 stmtGlength.close();
			 if (stmtGlength.isClosed()) {confirmstatementclosure = true;
                                         }else{confirmstatementclosure = false;}
			 
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	/*
	 * These getter and setter have been created
	 * for junit testing and in case if they are needed
	 * at some point though game structure does not require 
	 * them, other wise print statements will have to be 
	 * written in above method which will affect game in cli
	 * version. 
	 */
	public static int getGameNumber() {
		return gamenumber;
	}
	public static int getHumanWins() {
		return gameHuWin;
	}
	public static int getAiWins() {
		return gameAiWin;
	}
	public static double getDrawsAve() {
		return gamedraws;
	}
	public static int getTotalGameTime() {
		return gameLen;
	}
	public static int getGameId(int gameIdint) {
		/*
		 * This Method get game id from database
		 * so the program know the previous game 
		 * Id number and can assign a new Number
		 * to new game, it is called by insertvalues
		 * method.
		 */
		try {
			PreparedStatement stmtGId = c.prepareStatement("SELECT MAX(GameId) As Game_Id  FROM   Game");
			ResultSet result = stmtGId.executeQuery();
			result.next();
		    String gameId = result.getString(1);
		    gameIdint = Integer.parseInt(gameId);
		   // System.out.println("This is gameId" + gameIdint);
		    stmtGId.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gameIdint;
	}
	public static boolean isDBopen() {
		return verifydbopen;
		}
	public static boolean isDBclose() {
		return verifydbclose;
	}
	public static boolean areStatementClose() {
		return confirmstatementclosure;
	}
}