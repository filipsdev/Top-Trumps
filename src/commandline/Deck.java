package commandline;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Deck {
	//use a HashMap to link 40 cards with position 0~39th for illustration in log
	private HashMap<Integer, String> card = new HashMap<Integer, String>();
	
	private ArrayList<Card> cardDeck = new ArrayList<Card>();
	
	private ArrayList<Player> players = new ArrayList<Player>();
	

	//this ArrayList store card number used to be shuffled, its value link to card name one on one 
	private ArrayList<Integer> cardNumber = new ArrayList<Integer>();

	//common deck
	private ArrayList<Integer> commonDeck = new ArrayList<Integer>();

	
	public void loadDeck() {
		String cardName = "";
		int size = 0;
		int speed = 0;
		int range = 0;
		int firepower = 0;
		int cargo = 0;
		try { 
			BufferedReader bfReader = new BufferedReader(new FileReader("StarCitizenDeck.txt"));
	        bfReader.readLine();//the header will not be read
	        String line = null; 
	        while((line=bfReader.readLine())!=null){     
	           String item[] = line.split(" ");//data in file is divided by space
	           cardName = item[0];
	           size = Integer.valueOf(item[1]);
	           speed = Integer.valueOf(item[2]);
	           range = Integer.valueOf(item[3]);
	           firepower = Integer.valueOf(item[4]);
	           cargo = Integer.valueOf(item[5]);
	           Card c = new Card(cardName, size, speed, range, firepower, cargo);
	           cardDeck.add(c);
	        }      
	        bfReader.close();
	     }catch (Exception e) { 
	                e.printStackTrace(); 
	      }
		
	}
	
	public void shuffleCards() {
	int index = 0;
	for (Card c: cardDeck) {
		card.put(index,cardDeck.get(index).getCardName());
		cardNumber.add(index);
		index++;
	}
	Collections.shuffle(cardNumber);
	}
	 
		
		public void ShareCards() {	
			players.add(new Player("You"));
			for(int i = 1; i <= 4; i++) {
				players.add(new Player("AIPlayer" + i));
			}
			for (int i = 0; i < cardNumber.size();i++) {
				if(i%5==0) {
					players.get(0).addCard(cardNumber.get(i));
				}else if(i%5==1) {
					players.get(1).addCard(cardNumber.get(i));
				}else if (i%5==2) {
					players.get(2).addCard(cardNumber.get(i));
				}else if (i%5==3) {
					players.get(3).addCard(cardNumber.get(i));
				}else {
					players.get(4).addCard(cardNumber.get(i));
				}
			}
		}
		
		
//////////////GETTERS////////////////////////
		public ArrayList<Integer> getCommonDeck() {
			return commonDeck;
		}
		
		public ArrayList<Player> getPlayers() {
			return players;
		}

		public void setPlayers(ArrayList<Player> players) {
			this.players = players;
		}

		public ArrayList<Card> getCardDeck() {
			return cardDeck;
		}

		public void setCardDeck(ArrayList<Card> deck) {
			this.cardDeck = deck;
		}

		/*
 * 		setCommonDeck loads the card number of index 0 of each player in to
 * 		the corresponding indexes of the array. The numbers are loaded in reverse 
 * 		order in to the lefthand side of the array (0) so that 0-4 of the common deck 
 * 		always points to the correct player, if we agree that 0 = player and 1-4 are used 
 *		for the ai players
 */
		public void setCommonDeck() {
			int i = 0;
			for(Player p : players) {
				commonDeck.add(players.get(i).getHand().get(0));
				i++;
			}	
		}
		
		public int clearActiveCards() {
			int i = 0;
			int humanNoCardsLeft = 0;
			for(Player p : players) {
				players.get(i).getHand().remove(0);
				i++;
			}
			if(this.players.get(0).getHand().isEmpty()) {
				humanNoCardsLeft = 1;
			}
			removePlayersWithNoCards();
			return humanNoCardsLeft;
		}
		
		
private void removePlayersWithNoCards() {
	ArrayList<Player> tempList = new ArrayList<Player>();
	for(int i=0; i<this.players.size(); i++) {
		if(!this.players.get(i).getHand().isEmpty()) {
			tempList.add(this.players.get(i));
		}
		}
	this.players = tempList; 
}

///////////////////////LOGS////////////////////////////////////////
		
		
		public String fullDeckLog() {
			String log = "\r\n------------DECK LOG-------------------"
					+ "\r\nComplete deck: " + card + "\r\n---------------------------------------"
					+ "\r\nShuffled deck: " + cardNumber + "\r\n---------------------------------------\r\n";
			return log;
		}
		
		public String playersDeckLog() {
			String log = "\r\n-----PLAYERS DECKS---------------------\r\n";
			int i = 0;
			for(Player p : players) {
				log = log + players.get(i).getPName() + ": " + players.get(i).getHand() 
						+ "\r\n---------------------------------------\r\n";
				i++;
			}

			return log;		
		}
		
		public String activeCardsLog() {
			String log = "\r\n---------ACTIVE CARDS------------------\r\n";
			int i = 0;
			for(Player p : players) {
				log = log + players.get(i).getPName() + ": " 
						+ cardDeck.get(players.get(i).getHand().get(0)).log() 
						+ "\r\n---------------------------------------\r\n";
				i++;
			}
			return log;
		}
		
		public String printCommonDeck() {
			String log = "\r\n-------------COMMON DECK---------------\r\n"
					+ "Common deck: " + commonDeck + "\r\n---------------------------------------\r\n";
			return log;
		}
		
		// creates new file and overwrites existing
		public void createLogFile() {
			FileWriter fileW = null;
			try {
				fileW = new FileWriter("toptrumps.log");
				fileW.write("---------------------------------------"
						  + "\r\n------------TOP TRUMPS LOG-------------"
						  + "\r\n---------------------------------------\r\n");
			}catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(fileW!=null) 
					try {
						fileW.close();
					}catch (IOException e) {
						e.printStackTrace();
					}
			}
		}//createLogFile-end

	// required data sent to print as it is created and appended to log file
		public void printToFile(String line) {
			FileWriter fileW = null;
			try {
				fileW = new FileWriter("toptrumps.log", true);
				fileW.append(line);
			}catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(fileW!=null) 
					try {
						fileW.close();
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
		}//printToFile-end

		
	}


