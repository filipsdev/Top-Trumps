package commandline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication  {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
 	 * @param args
	 */
	
// system in scanner in main scope for use throughout class
	static Scanner input = new Scanner(System.in);

///////////////////MAIN////////////////////////////////////
	public static void main(String[] args) {
//		create instance so methods do not have to be static
		TopTrumpsCLIApplication topTrumps = new TopTrumpsCLIApplication();
		
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application
		
		boolean writeGameLogsToFile = true; // Should we write game logs to file?
		if (args[0].equalsIgnoreCase("true")) {
			writeGameLogsToFile=true; // Command line selection
		}
		
		while(!userWantsToQuit) {
		System.out.println("Do you want to see past results or play a game?\n"
				+ "\t1: Print Game Statistics\n\t2: Play game\n"
				+ "Enter the number for your selection"
				+ "\nor type 'quit' to exit application:");
		String choice = input.next();
		if(choice.equals("1")) {
			topTrumps.printStats();
		}
		else if(choice.equals("2")) {
			topTrumps.playGame(writeGameLogsToFile);
			
		}
		else if(choice.equals("quit")) {
		 // DatabaseConnect.DatabaseOpen();
		 // DatabaseConnect.insertValues(NumberofGames,HumanWin,AiWin,NumberofDraws,LengthofGames);
		 // DatabaseConnect.DatabaseOpen();
			userWantsToQuit = true;
		}
		}
		input.close();
		System.exit(0);
	}//main-end

///////////////////PLAY GAME/////////////////////////
	private void playGame(boolean logsToFile) {
		System.out.println("\nGame start\n");
		// State
		boolean exitGame = false; // flag to check whether the user wants to exit the game
		boolean playerOut = false;
		Deck deck = new Deck(); 
		dealCards(deck, logsToFile); 
		// choose first player randomly 
		Random rand = new Random();
		int firstPlay = rand.nextInt(4);
		// initialise round 1
		int round = 1;
		int activePlayer = firstPlay;
		// Loop until the user wants to exit the game
		while (!exitGame) {
			System.out.println("\nRound " + round + "\nPlayers have drawn their cards"
					+ "\nYour card is " + deck.getCardDeck().get(deck.getPlayers().get(0).getHand().get(0)).getCardName());
			if(!playerOut) {
				System.out.println(deck.getCardDeck().get(deck.getPlayers().get(0).getHand().get(0)).toString(0));
			}
			printWhosTurn(activePlayer);
			deck.setCommonDeck();
			if(logsToFile) {
				deck.printToFile(deck.playersDeckLog());
				deck.printToFile(deck.activeCardsLog());
				deck.printToFile(deck.printCommonDeck());
			}
			int category = chooseCategory(activePlayer);
			System.out.println("The category is " + printCategory(category));
			int roundWinner = roundWinner(category, deck);

//	add common deck to winners array or print draw
			if(roundWinner == -1) {
				System.out.println("This round was a draw, the common deck has " + deck.getCommonDeck().size() + " cards");
			}else {
				addCommonDeck(roundWinner, deck);
				activePlayer = roundWinner;
				System.out.println(deck.getPlayers().get(roundWinner).getPName() + " won this round\n"
						+ "The winning card was " + deck.getCardDeck().get(deck.getPlayers().get(roundWinner).getHand().get(0)).getCardName()
						+ "\n" + deck.getCardDeck().get(deck.getPlayers().get(roundWinner).getHand().get(0)).toString(category));
			}
			deck.clearActiveCards();
// check for winner		
			boolean win = false;//checkWin(deck);
			if(win) {
				exitGame=true; // use this when the user wants to exit the game
			}else {
				round++;
			}
				}//while-end
		
		return;
	}//playGame-end
	
///////////////DEAL CARDS////////////////////////////////////
	private Deck dealCards(Deck deck, boolean logsToFile) {
	// load, shuffle, and deal the cards
		deck.loadDeck();
		deck.shuffleCards();
		deck.ShareCards();
		// if flag selected print info to log
		if(logsToFile) {
			deck.createLogFile();
			deck.printToFile(deck.fullDeckLog());
		}
		return deck;
	}//deal-end
	
//////////////////WHOS TURN?////////////////////////////////////
	private void printWhosTurn(int player) {
		if(player == 0) {
			System.out.println("It's your turn to choose a category, your choices are:\n"
								+ "\t1. Size\n"
								+ "\t2. Speed\n"
								+ "\t3. Range\n"
								+ "\t4. Firepower\n"
								+ "\t5. Cargo\n"
								+ "Enter the number for your attribute: ");
		}else {
			System.out.println("It's the computers turn to choose a category");
		}
	}//turn-end
	
	
///////////////////ADD COMMON DECK////////////////////////////
	public void addCommonDeck(int roundWinner, Deck deck) {
		int i = 0;
		for(int j  : deck.getCommonDeck()) {
			deck.getPlayers().get(roundWinner).getHand().add((deck.getCommonDeck().get(i)));
			i++;
		}
		deck.getCommonDeck().clear();
	}
	
//////////////CHOOSE CATEGORY//////////////////
	private int chooseCategory(int ap) {
		int cat = -1;
		if(ap == 0) {
			cat = input.nextInt();
		}else {
			Random rand = new Random();
			cat = rand.nextInt(5) + 1;
		}
		return cat;
	}//cat-end
	
/////////////PRINT CATEGORY/////////////////
	private String printCategory(int cat) {
		String category = "";
		if(cat == 1) {
			category = "Size";
		}else if(cat == 2) {
			category = "Speed";
		}else if(cat == 3) {
			category = "Range";
		}else if(cat == 4) {
			category = "Firepower";
		}else {
			category = "Cargo";
		}
		return category;
	}//print cat-end
	
//////////////WINNER OF ROUND/////////////////
	public int roundWinner(int cat, Deck deck) {
		ArrayList<Integer> scores = new ArrayList<Integer>();
		
		int i = 0;
		for(Player p : deck.getPlayers()) {
			if(!deck.getPlayers().get(i).getHand().isEmpty()) {
				if(cat == 1) scores.add(deck.getCardDeck().get(deck.getPlayers().get(i).getHand().get(0)).getSize());
				if(cat == 2) scores.add(deck.getCardDeck().get(deck.getPlayers().get(i).getHand().get(0)).getSpeed());
				if(cat == 3) scores.add(deck.getCardDeck().get(deck.getPlayers().get(i).getHand().get(0)).getRange());
				if(cat == 4) scores.add(deck.getCardDeck().get(deck.getPlayers().get(i).getHand().get(0)).getFirepower());
				if(cat == 5) scores.add(deck.getCardDeck().get(deck.getPlayers().get(i).getHand().get(0)).getCargo());
				i++;
			} 
		}
/*
 * to determine the name of round winner we ask for the index of the player who holds the card with the max score
 * we can then match this with the index of the winning player
 */
		int maxScoreIndex = scores.indexOf(Collections.max(scores));
		i = 0;
		for(int s : scores) {
			if(scores.get(maxScoreIndex) == scores.get(i))  {
				if(scores.indexOf(maxScoreIndex) != scores.indexOf(i)) {
				maxScoreIndex = -1;
				break;	
				}
			}
			i++;
		}
		return maxScoreIndex;
	}

///////////////////PRINT GAME STATISTICS////////////////
	private void printStats() {
//--------------------------------
// DATABASE WIZARDRY TO GO HERE	
//--------------------------------
		System.out.println("these are stats");
		return;
	}//printStats-end




}
