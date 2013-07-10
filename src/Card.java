
public class Card {
	public static final String suits = "SHDC";
	public static final String ranks = "23456789TJQKA";
	private int value = 0;
	
	public Card(int initValue){
		value = initValue;
	}
	
	public char suit(){
		return suits.charAt(value%4);
	}
	
	public char rank(){
		return ranks.charAt(value/4);
	}
	
	public int rankValue(){
		return (int)(value/4);
	}
	
	public String toString(){
		return "" + suit() + rank();
	}
	
	public static int aceValue(){
		return ranks.length() - 1;
	}
}
