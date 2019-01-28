package commandline;

public class Card {
	private String cardName = "";
	private int size = 0;
	private int speed = 0;
	private int range = 0;
	private int firepower = 0;
	private int cargo = 0;
	
	public Card(String cardName, int size, int speed, int range, int firepower, int cargo) {
		this.cardName = cardName;
		this.size = size;
		this.speed = speed;
		this.range = range;
		this.firepower = firepower;
		this.cargo = cargo;
	}
	
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getFirepower() {
		return firepower;
	}
	public void setFirepower(int firepower) {
		this.firepower = firepower;
	}

	public int getCargo() {
		return cargo;
	}

	public void setCargo(int cargo) {
		this.cargo = cargo;
	}
	
	public String log() {
		return cardName + " " + size + " " + speed + " "
				+ range + " " + firepower + " " + cargo;
	}
	
	public String toString(int cat) {
		String line =  "\t> Size: " + size;
		if(cat == 1) line = line + " <----";
		line = line + "\n\t> Speed: " + speed;
		if(cat == 2) line = line + " <----";
		line = line + "\n\t> Range: " + range;
		if(cat == 3) line = line + " <----";
		line = line + "\n\t> Firepower: " + firepower;
		if(cat == 4) line = line + " <----";
		line = line +  "\n\t> Cargo: " + cargo;
		if(cat == 5) line = line + " <----";
		return line;
	}
}
