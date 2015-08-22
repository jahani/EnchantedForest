package razeJangal.console;

import java.util.Scanner;

import razeJangal.components.StringMethods;
import razeJangal.game.Game;

/**
 * @author Peyman Jahani <jahani.peyman @ gmail.com>
 * @version 1.0
 * @since 2012-03-25
 */
public class ConsoleServer {
	private Scanner sc = new Scanner(System.in);
	private Game game;

	/**
	 * Constructor
	 * 
	 * Main process of the game done here. this constructor uses all other
	 * methods and classes that we have (directly or indirectly)
	 */
	public ConsoleServer() {
		int choice;
		int[] dice = new int[2];

		System.out.println("How many players are going to play?");
		game = new Game(sc.nextInt());

		print("Round number ROUNDNUMBER started, this round's goal treasure is GOALTREASURE");

		while (game.treasures.finishedTreasures() == false) {
			printCurentPositions();
			dice = Game.diceThrow();
			int[] whereCanGo1 = game.wherePlayerCanGo(game.getTurn(), dice[0]);
			int[] whereCanGo2 = game.wherePlayerCanGo(game.getTurn(), dice[1]);
			print("Dice Numbers for Player PLAYERNUM: DICE1 DICE2", dice[0],
					dice[1]);

			print("Player PLAYERNUM's Choices:");
			for (int i = 0; i < whereCanGo1.length; i++)
				print(whereCanGo1[i], (i + 1)
						+ ". Move to INDEX, and then move " + dice[1]
						+ " cells");

			/**
			 * What happens when player get the same dices
			 */
			if (dice[0] == dice[1]) {
				int freeTreasures = 0;
				for (int i = 0; i < game.treasures.getTreasureIndexes().length; i++)
					if (game.isCellFree(game.treasures.getTreasureIndexes()[i])) {
						print(game.treasures.getTreasureIndexes()[i],
								whereCanGo1.length + freeTreasures + 1
										+ ". Move to INDEX");
						freeTreasures++;
					}
				print(game.getVioletCellIndex(), (whereCanGo1.length
						+ freeTreasures + 1)
						+ ". Move to INDEX");
				print((whereCanGo1.length + freeTreasures + 2)
						+ ". Change current round's goal treasure");

				System.out.flush();
				choice = sc.nextInt() - 1;
				if (choice < whereCanGo1.length) {
					moveCurrentPlayer(whereCanGo1[choice]);
					takePlaceAction();
					secondChoice(dice[1]);
				}
				if (choice >= whereCanGo1.length
						&& choice < whereCanGo1.length + freeTreasures) { // Orange
					for (int i = 0; i < game.treasures.treasureNumbers(); i++) {
						if (!game.isCellFree(game.treasures
								.getTreasureIndexes()[i]))
							choice++;
						if (choice == whereCanGo1.length + i) {
							moveCurrentPlayer(game.treasures
									.getTreasureIndexes()[i]);
							takePlaceAction();
							break;
						}
					}
				} else {
					if (choice == whereCanGo1.length + freeTreasures + 0) // Violet
						moveCurrentPlayer(game.getVioletCellIndex());
					if (choice == whereCanGo1.length + freeTreasures + 1) { // change
						// round
						game.treasures.changeGoal();
						print("Round number ROUNDNUMBER, this round's goal treasure has changed to GOALTREASURE");
					}
				}
				/**
				 * What happens if player doesn't get the same dices
				 */
			} else {

				for (int i = 0; i < whereCanGo2.length; i++)
					print(whereCanGo2[i], (whereCanGo1.length + i + 1)
							+ ". Move to INDEX, and then move " + dice[0]
							+ " cells");

				System.out.flush();
				choice = sc.nextInt() - 1;

				if (choice < whereCanGo1.length) {
					moveCurrentPlayer(whereCanGo1[choice]);
					takePlaceAction();
					secondChoice(dice[1]);
				} else {
					moveCurrentPlayer(whereCanGo2[choice - whereCanGo1.length]);
					takePlaceAction();
					secondChoice(dice[0]);
				}
			}

			game.changeTurn();
		}
		// TODO Game is finished, so what should happen?
	}

	/**
	 * Receives player second choice according to player's unused dice number
	 * 
	 * @param dice
	 *            Unused dice number
	 */
	private void secondChoice(int dice) {
		print("Player PLAYERNUM's Choices(Second Dice):");
		int[] whereCanGo = game.wherePlayerCanGo(game.getTurn(), dice);
		for (int i = 0; i < whereCanGo.length; i++)
			print(whereCanGo[i], i + 1 + ". Move to INDEX");
		System.out.flush();
		int choice = sc.nextInt() - 1;
		moveCurrentPlayer(whereCanGo[choice]);
		takePlaceAction();
	}

	/**
	 * Look that where player is now and do what when a player goes to that
	 * place
	 * 
	 * This method is used every time player goes to a new cell
	 */
	private void takePlaceAction() {
		for (int i = 0; i < game.treasures.treasureNumbers(); i++)
			if (game.getPlaces()[game.getTurn()] == game.treasures
					.getTreasureIndexes()[i]) {
				print("Cell PLAYERINDEX's treasure as seen by player PLAYERNUM is "
						+ game.treasures.index2Treasure(game.treasures
								.getTreasureIndexes()[i]));
				return;
			}
		int choice;
		boolean canContinue = true;
		while (game.getPlaces()[game.getTurn()] == game.getRedCellIndex()
				&& canContinue && game.treasures.finishedTreasures() == false) {
			print("Player PLAYERNUM is in 25(Red), and can attempt to guess goal treasure's place:");
			print("1. I don't know");
			for (int i = 0; i < game.treasures.treasureNumbers(); i++) {
				print(i + 1 + 1 + ". " + game.treasures.getTreasureIndexes()[i]);
			}
			System.out.flush();
			choice = sc.nextInt() - 1 - 1;
			if (choice == -1) {
				canContinue = false;
			} else {
				print("Cell "
						+ game.treasures.getTreasureIndexes()[choice]
						+ "'s treasure as seen by player PLAYERNUM is "
						+ game.treasures.index2Treasure(game.treasures
								.getTreasureIndexes()[choice])); // TODO should
																	// be here?
				if (game.treasures.getTreasureIndexes()[choice] == game.treasures
						.treasure2Index(game.treasures.getGoal())) {
					game.wonTreasure(game.getTurn(), game.treasures.getGoal());
					print("Player PLAYERNUM has won this round's goal treasure, GOALTREASURE");
					game.treasures.changeGoal();
					print("Round number ROUNDNUMBER started, this round's goal treasure is GOALTREASURE");
				} else {
					moveCurrentPlayer(0);
					canContinue = false;
				}
			}
		}
	}

	/**
	 * Receives current player place and moves it to the parameter index
	 * 
	 * @param index
	 *            Where should player move
	 */
	private void moveCurrentPlayer(int index) {
		int deadPlayer = game.movePlayer(game.getTurn(), index);
		if (deadPlayer != -1)
			print("Player PLAYERNUM is moved to Blue cells.", deadPlayer);
	}

	/**
	 * Prints player's current positions
	 */
	public void printCurentPositions() {
		System.out.print("Current Positions:");
		int[] positions = game.getPlaces();
		for (int i = 0; i < positions.length; i++)
			System.out.print(" " + (i + 1) + "->" + positions[i]);
		System.out.println();
	}

	/**
	 * replace "PLAYERNUM", "ROUNDNUMBER", "GOALTREASURE" and "PLAYERINDEX" with
	 * valid informations in the input string and then prints it
	 * 
	 * @param sentence
	 *            Input string
	 */
	public void print(String sentence) {
		String fromWord[] = { "PLAYERNUM", "ROUNDNUMBER", "GOALTREASURE",
				"PLAYERINDEX" };
		String toWord[] = { "" + (game.getTurn() + 1),
				"" + game.treasures.getRoundNumber(), game.treasures.getGoal(),
				"" + game.getPlaces()[game.getTurn()] };
		for (int i = 0; i < toWord.length; i++)
			sentence = StringMethods.replaceWord(sentence, fromWord[i],
					toWord[i]);
		System.out.println(sentence);
	}

	/**
	 * Replace "INDEX" tag in the sentence with index parameter and prints it
	 * 
	 * When the cell has special color, this will put cell color after than it's
	 * index
	 * 
	 * @param index
	 *            Index of the cell
	 * @param sentence
	 *            Sentence that should print
	 */
	public void print(int index, String sentence) {
		String fromWord = "INDEX";
		if (index == game.getRedCellIndex())
			sentence = StringMethods.replaceWord(sentence, fromWord, index
					+ "(Red)");
		else if (index == game.getVioletCellIndex())
			sentence = StringMethods.replaceWord(sentence, fromWord, index
					+ "(Violet)");
		for (int i = 0; i < game.treasures.getTreasureIndexes().length; i++)
			if (index == game.treasures.getTreasureIndexes()[i])
				sentence = StringMethods.replaceWord(sentence, fromWord, index
						+ "(Orange)");
		sentence = StringMethods.replaceWord(sentence, fromWord, index + "");
		System.out.println(sentence);
	}

	/**
	 * Replace "PLAYERNUM" tag with the "playerNum" parameter
	 * 
	 * This method is used when we want to print a non-current player is moved
	 * to blue cell
	 * 
	 * @param sentence
	 *            Input string that we should print
	 * @param playerNum
	 *            Player number that we want to replace with "PLAYERNUM" tag
	 */
	public void print(String sentence, int playerNum) {
		String fromWord = "PLAYERNUM";
		String toWord = "" + (playerNum + 1);
		sentence = StringMethods.replaceWord(sentence, fromWord, toWord);
		System.out.println(sentence);
	}

	/**
	 * Replace "DICE1", "DICE2" and "PLAYERNUM" tag with first dice, second dice
	 * and current player
	 * 
	 * Used when we want to print player dices
	 * 
	 * @param sentence
	 *            Message that should shown
	 * @param dice1
	 *            Player's first dice
	 * @param dice2
	 *            Player's second dice
	 */
	public void print(String sentence, int dice1, int dice2) {
		String fromWord[] = { "PLAYERNUM", "DICE1", "DICE2" };
		String toWord[] = { "" + (game.getTurn() + 1), "" + dice1, "" + dice2 };
		for (int i = 0; i < toWord.length; i++)
			sentence = StringMethods.replaceWord(sentence, fromWord[i],
					toWord[i]);
		System.out.println(sentence);
	}

}
