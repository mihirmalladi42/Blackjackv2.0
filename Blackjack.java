import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Blackjack {
	//Sets variables and card deck
	static final String[] randCard = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
	static int[] value = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
	static ArrayList<String> dealerDealt = new ArrayList<>();
	static ArrayList<Double> seedGeneration = new ArrayList<>();
	static int dealerSum = 0;
	static double betN = 0;
	static double original;
	static double money;
	static int roundNum;
	static boolean playerHas21 = false;
	//The game runner / the actual game
	public static void game(ArrayList<String> playerDealt, int playerSum) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Your cards are " + playerDealt + " (sum of " + playerSum + "). Would you like to hit or stand (type hit or stand (or you can type h or s))?");
		String hitorstand = scan.nextLine();
		//If you choose hit, it gives a new random card
		if (hitorstand.equalsIgnoreCase("hit") || hitorstand.equalsIgnoreCase("h")) {
			Random randCardR = new Random();
			int rand = randCardR.nextInt(13);
			if (rand == 0) {
				Scanner aValue = new Scanner(System.in);
				//If you get an ace, you can choose it to be valued at 1 or 11
				System.out.println("Do you want the ace to be valued at 1 or 11 (type 1 or 11)?");
				String res = aValue.nextLine();
				if (res.equalsIgnoreCase("1")) {
					value[0] = 1;
				} else if (res.equalsIgnoreCase("11")) {
					value[0] = 11;
				} else {
					System.out.println("Assuming you said 1.");
					value[0] = 1;
				}
			}
			//Adds a card to the player's deck
			playerSum += value[rand];
			playerDealt.add(randCard[rand]);
			//If the player's card sum goes over 21, you lose.
			if (playerSum > 21) {
				System.out.println("__________♠♠♠BLACKJACK♠♠♠__________");
				System.out.println("Bust! Computer wins. Your cards were " + playerDealt + " with a sum of " + playerSum + ".");
				money -= betN;
				money = Math.round(money * 100.0) / 100.0;
				System.out.println("Your total money is: $" + money);
				newGame();
			} else if (playerSum == 21) {
				//If the player's sum is 21, the player wins
				playerHas21 = true;
				/*System.out.println("__________♠♠♠BLACKJACK♠♠♠__________");
				System.out.println("Win! Player wins. Your cards were " + playerDealt + " with a sum of " + playerSum + ".");
				money += betN*1.5;
				money = Math.round(money * 100.0) / 100.0;
				System.out.println("Your total money is: $" + money);
				newGame();*/
				dealer(dealerDealt, dealerSum, playerSum);
			} else if (playerSum < 21) {
				//if the player's sum is less than 21, the next card is dealt
				game(playerDealt, playerSum);
			}
		} else if (hitorstand.equalsIgnoreCase("stand") || hitorstand.equalsIgnoreCase("s")) {
			//if the player chooses stand, it's the computer's turn to get cards.
			dealer(dealerDealt, dealerSum, playerSum);
		}
	}
	public static void dealer(ArrayList<String> dealerDealt, int dealerSum, int playerSum) {
		//loops until the card sum is greater than or equal to 17
		while (dealerSum < 17) {
			//generates random card
			Random randCardR = new Random();
			int card = randCardR.nextInt(13);
			if (card == 0) {
				//sets the value of an ace card depending on the total sum of the dealer's cards
				if (dealerSum <= 10) {
					value[0] = 11;
				} else if (dealerSum > 10) {
					value[0] = 1;
				}
			}
			dealerSum += value[card];
			dealerDealt.add(randCard[card]);
			//if the dealer's sum is over 21, player wins
			if (dealerSum > 21) {
				System.out.println("__________♠♠♠BLACKJACK♠♠♠__________");
				System.out.println("Bust! Player wins. Computer's cards were " + dealerDealt + ". The sum was " + dealerSum + ".");
				money += betN*1.5;
				money = Math.round(money * 100.0) / 100.0;
				System.out.println("Your total money is: $" + money);
				newGame();
			}
		}
		if (!(dealerSum >= 21)) {
			// if the dealer's sum is less than to 21 and has a greater sum than the player, the dealer wins.
			if (dealerSum > playerSum) {
				System.out.println("__________♠♠♠BLACKJACK♠♠♠__________");
				System.out.println("Lose! Computer wins. Computer's cards were " + dealerDealt + ". The sum was " + dealerSum + ".");
				money -= betN;
				money = Math.round(money * 100.0) / 100.0;
				System.out.println("Your total money is: $" + money);
				// if both sums are equal, it is a tie.
			} else if (dealerSum == playerSum) {
				System.out.println("__________♠♠♠BLACKJACK♠♠♠__________");
				System.out.println("Tie! Computer's cards were " + dealerDealt + ". The sum was " + dealerSum + ".");
				System.out.println("Your total money is: $" + money);
			} else if (dealerSum < playerSum) {
				//if dealer sum is less than player sum, player wins.
				System.out.println("__________♠♠♠BLACKJACK♠♠♠__________");
				System.out.println("Win! Computer's cards were " + dealerDealt + ". The sum was " + dealerSum + ".");
				money += betN*1.5;
				money = Math.round(money * 100.0) / 100.0;
				System.out.println("Your total money is: $" + money);
			}
			newGame();
		}
		if (dealerSum == 21 && playerHas21) {
			System.out.println("__________♠♠♠BLACKJACK♠♠♠__________");
			System.out.println("Tie! Computer's cards were " + dealerDealt + ". The sum was " + dealerSum + ".");
			System.out.println("Your total money is: $" + money);
			newGame();
		} else if (dealerSum == 21 && !playerHas21) {
			System.out.println("__________♠♠♠BLACKJACK♠♠♠__________");
			System.out.println("Lose! Computer wins. Computer's cards were " + dealerDealt + ". The sum was " + dealerSum + ".");
			money -= betN;
			money = Math.round(money * 100.0) / 100.0;
			System.out.println("Your total money is: $" + money);
			newGame();
		}
	}
	public static void newGame() {
		//asks if the player wants to play another game
		Scanner scan = new Scanner(System.in);
		System.out.println("Would you like to play again? (Enter yes or no / y or n):");
		String res = scan.nextLine();
		if (res.equalsIgnoreCase("yes") || res.equalsIgnoreCase("y")) {
			//if yes, loads a new game
			loadGame(true);
		} else if (res.equalsIgnoreCase("no") || res.equalsIgnoreCase("n")) {
			//if no, calculates the total money gained / lost.
			if (money < original) {
				System.out.println("Better luck next time. You lost $" + (Math.round((original - money) * 100.0) / 100.0) + ".");
			} else if (money > original) {
				System.out.println("Nice job! You won $" + (Math.round((money - original) * 100.0) / 100.0) + ".");
			}
			//asks if the player wants a game seed
			Scanner getSeed = new Scanner(System.in);
			System.out.println("Do you want a seed so you can play this same game again? (type yes or no): ");
			String getSeedRes = getSeed.nextLine();
			//if the player wants a game seed, it generates one.
			if (getSeedRes.equalsIgnoreCase("yes")) {
				String str1 = Double.toString(original);
				String str2 = Double.toString(Math.round(money*100.0)/100.0);
				String newStr1 = "";
				String newStr2 = "";
				for (var i = 0; i < str1.length(); i++) {
					if (str1.charAt(i) == '.') {
						newStr1 += "A";
					} else {
						newStr1 += Character.toString(str1.charAt(i));
					}
				}
				for (var j = 0; j < str2.length(); j++) {
					if (str2.charAt(j) == '.') {
						newStr2 += "A";
					} else {
						newStr2 += Character.toString(str2.charAt(j));
					}
				}
				System.out.println(newStr1 + "S" + newStr2);
				System.out.println("To use the seed, copy it from above. When you play a new game, type yes when it asks if you want to use a seed. \nThen paste the seed to continue a game.");
				System.out.println("If the seed has E in it, take the half (half is on the left of the "
				+ "\nS, half is on the right) which has the E in it and replace the A with a '.'. For example, if the seed is 1A0E9S1A00015E9,"
				);
				System.out.println("you would take any half (this example would be the first half), and replace the A with a '.'. This would give "
						+ "\n1.0E9. Then, you expand the number. 1.0E9 is 1 billion so you would type 1000000000A0S instead of 1A0E9S. Do the same "
						+ "\nfor 1A00015E9. This becomes 1.00015E9 which is 1000150000A0. Finally, merge the two together. 1A0E9S1A00015E9 "
						+ "\nthen becomes 1000000000A0S1000150000A0.");
			}
		} else {
			//if the player types an invalid choice, it asks the player if another game should be played.
			System.out.println("Enter a correct response.");
			newGame();
		}
	}
	public static void loadGame(boolean doPrint) {
		//loads / starts a new game
		if (doPrint) {
			System.out.println("__________♠♠♠BLACKJACK♠♠♠__________");	
		}
		betN = 0;
		System.out.println("IF ONE OF THE FIRST TWO CARDS HAVE AN ACE, THEY ARE COUNTED AS 1. MINIMUM BET IS $15. If you win, you earn 150% of the money bet. If you lose, you lose your bet.");
		Scanner bet = new Scanner(System.in);
		System.out.println("Enter the amount of money you want to bet: ");
		betN = bet.nextDouble();
		if (betN < 15) {
			System.out.println("Enter a valid bet.");
			loadGame(false);
		}
		ArrayList<String> playerDealt = new ArrayList<>();
		dealerDealt = new ArrayList<>();
		int playerSum = 0;
		dealerSum = 0;
		Random randCardR = new Random();
		int cd1 = randCardR.nextInt(13);
		int cd2 = randCardR.nextInt(13);
		int cp1 = randCardR.nextInt(13);
		int cp2 = randCardR.nextInt(13);
		dealerDealt.add(randCard[cd1]);
		dealerDealt.add(randCard[cd2]);
		playerDealt.add(randCard[cp1]);
		playerDealt.add(randCard[cp2]);
		playerSum += (value[cp1] + value[cp2]);
		dealerSum += (value[cd1] + value[cd2]);
		System.out.println("__________♠♠♠BLACKJACK♠♠♠__________");
		game(playerDealt, playerSum);
	}
	public static void main(String[] args) {
		//asks if the player has a seed to input
		System.out.println("__________♠♠♠BLACKJACK♠♠♠__________");
		Scanner openSeed = new Scanner(System.in);
		System.out.println("Do you want to input a seed? (type yes or no): ");
		String inputSeed = openSeed.nextLine();
		if (inputSeed.equalsIgnoreCase("yes")) {
			Scanner seedInp = new Scanner(System.in);
			System.out.println("Enter the seed: ");
			String seed = seedInp.nextLine();
			String[] vals = seed.split("S");
			String[] originalInput = vals[0].split("A");
			String[] moneyInput = vals[1].split("A");
			double tempOriginal = Double.parseDouble(originalInput[0]);
			if (originalInput[1].length() == 1) {
				tempOriginal += (Double.parseDouble(originalInput[1])*10.0)/100.0;
			} else if (originalInput[1].length() == 2) {
				tempOriginal += Double.parseDouble(originalInput[1])/100.0;
			}
			double tempMoney = Double.parseDouble(moneyInput[0]);
			if (moneyInput[1].length() == 1) {
				tempMoney += (Double.parseDouble(moneyInput[1])*10.0)/100.0;
			} else if (moneyInput[1].length() == 2) {
				tempMoney += Double.parseDouble(moneyInput[1])/100.0;
			}
			original = tempOriginal;
			money = tempMoney;
			loadGame(false);
			//if there is no seed to input or the player doesn't want to input a seed, it starts a new game
		} else if (inputSeed.equalsIgnoreCase("no")) {
			Scanner originalScan = new Scanner(System.in);
			System.out.println("How much money do you have to start with?");
			original = Math.round(originalScan.nextDouble() * 100.0) / 100.0;
			money = original;
			loadGame(false);
		} else {
			//if the player inputs an invalid choice, the string below gets printed: 
			System.out.println("Please enter a valid choice.");
		}
	}
}
