
public class Application {
	
	public static int rounds = 7;
	public static int playerCount = 2;

	public static void main(String[] args) {
		// create deck
		Card[] deck = new Card[52];
		for(int i = 0; i < 52; i++) deck[i] = new Card(i);
		
		//initialize players
		Player[] players = new Player[playerCount];
		int[] penaltyPoints = new int[2];
		//players[0] = new ComputerPlayer(playerCards);
		penaltyPoints[0] = penaltyPoints[1] = 0;
		
		int dealer = 0;
		do{
			rounds = (int)(6 + Math.random() * 2);
			System.out.println(String.format("New round, with %d as dealer dealing %d cards",dealer,rounds));
			//shuffle
			for(int i = 0; i < 200; i++) {
				int a = (int)(52 * Math.random());
				int b = (int)(52 * Math.random());
				Card temp = deck[a];
				deck[a] = deck[b];
				deck[b] = temp;
			}
			
			//deal
			Card[] playerCards = new Card[rounds];
			Card[] computerCards = new Card[rounds];
			for(int i = 0; i < rounds; i++) {
				playerCards[i] = deck[2*i];
				computerCards[i] = deck[2*i + 1];
			}			
			players[0] = new HumanPlayer(playerCards);
			players[1] = new ComputerPlayer(computerCards);
			
			
			int onLead = dealer;
			for(int i = 0; i < rounds-1; i++){
				Card[] tableCards = new Card[0];
				//everybody plays a card
				for(int j = 0; j < playerCount; j++){
					Card c = players[(onLead + j) % playerCount].play(tableCards);
					Card[] newTableCards = new Card[tableCards.length + 1];
					for(int k = 0; k < tableCards.length; k++) newTableCards[k] = tableCards[k];
					newTableCards[tableCards.length] = c; 
					System.out.println(c.toString() + " was played by Player " + (onLead + j) % 2);
					tableCards = newTableCards;
				}			
				// who played the highest the last?
				int highestPosition = -1;
				for(int k = 0; k < playerCount; k++) {
					if(highestPosition == -1 || tableCards[highestPosition].rankValue() <= tableCards[k].rankValue()){
						highestPosition = k;
					}
				}
				//he'll lead the next turn
				onLead = (onLead + highestPosition) % playerCount;
			}
			
			int max = 0;
			for(int i = 0; i < playerCount; i++){
				max = Math.max(max, players[i].lastCard().rankValue());
			}
			for(int i = 0; i < playerCount; i++){
				if(players[i].lastCard().rankValue() >= max){
					int p = players[i].lastCard().rankValue() + 2;
					if(p == Card.aceValue()) p = 21;
					penaltyPoints[i] += p;
					System.out.println(String.format(
							"Player %d was penalized for %d points for ending with %s and now has %d penalty points.", 
							i, p, players[i].lastCard().toString(), penaltyPoints[i]
					));
				} else {
					System.out.println(String.format("Player %d's last card was %s, and remains with %d penalty points.", i, players[i].lastCard().toString(), penaltyPoints[i]));			
				}
			}
			dealer = (dealer + 1) % playerCount;
		} while(penaltyPoints[0] <= 21 && penaltyPoints[1] <= 21);
		
		if(penaltyPoints[0] >  penaltyPoints[1]) System.out.println("Sorry, you lost.");
		else System.out.println("Congratulations, you WON!");		
	}

}
