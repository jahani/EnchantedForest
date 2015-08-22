package razeJangal.game;

import razeJangal.components.IntArrayMethods;
import razeJangal.sound.Sound;

/**
 * @author Peyman Jahani <jahani.peyman @ gmail.com>
 * @version 1.0
 * @since 2012-03-25
 */
public class Game {
	public Board board = new Board();
	public Treasures treasures = new Treasures(board);
	private int playerNumbers;
	public Player[] player;
	private int turn = 0;
	private int redCellIndex = -1;
	private int violetCellIndex = -1;

	{
		for (int i = 0; i < board.cell.length; i++)
			if (board.cell[i].getColor().equals(BoardCell.getVioletColor())) // Violet
				violetCellIndex = i;
			else if (board.cell[i].getColor().equals(BoardCell.getRedColor())) // Red
				redCellIndex = i;
	}

	/**
	 * Constructor
	 * 
	 * Receive number of players
	 * 
	 * @param n
	 *            Number of players
	 */
	public Game(int n) {
		playerNumbers = n;
		player = new Player[n];
		for (int i = 0; i < playerNumbers; i++)
			player[i] = new Player();
	}

	/**
	 * Throw two dices
	 * 
	 * @return Dices number
	 */
	public static int[] diceThrow() {
		int output[] = { DicePool.diceThrowing(), DicePool.diceThrowing() };
		return output;
	}

	/**
	 * Moves a player to a new cell
	 * 
	 * @param playerNum
	 *            Which player should move
	 * @param index
	 *            Where should player go
	 * @return Dead player number
	 */	
	public int movePlayer(int playerNum, int index) {
		if (index == 0) {
			player[playerNum].setPlace(0);
			return playerNum;
		} else {
			int deadPlayer = -1;
			for (int i = 0; i < playerNumbers; i++)
				if (player[i].getPlace() == index && i!=playerNum) {
					player[i].setPlace(0);
					deadPlayer = i;
				}
			player[playerNum].setPlace(index);
			return deadPlayer;
		}
	}

	/**
	 * where player go with a dice number that he has
	 * 
	 * @param playerNum
	 *            Which player we are checking
	 * @param DiceNum
	 *            Dice number
	 * @return an array of cell indexes that player can go
	 */
	public int[] wherePlayerCanGo(int playerNum, int DiceNum) {
		return wherePlayerCanGoHelp(player[playerNum].getPlace(), DiceNum, -1);
	}

	/**
	 * Checks which cells player can go according to his current place
	 * 
	 * Used in {@see wherePlayerCanGo}
	 * 
	 * @param index
	 *            Player index
	 * @param DiceNum
	 *            Dice number
	 * @param lastIndex
	 *            Last cell that player was in
	 * @return An array of players possible choices according to his dice number
	 */
	private int[] wherePlayerCanGoHelp(int index, int DiceNum, int lastIndex) {
		int[] output = new int[0];

		if (DiceNum == 1) {
			for (int i = 0; i < board.cell[index].getBranches().length; i++)
				if ((board.cell[index].getBranches()[i] != lastIndex)
						&& (board.cell[index].getBranches()[i] != 0))
					output = IntArrayMethods.addNew(output,
							board.cell[index].getBranches()[i]);
			return output;
		}

		for (int j = 0; j < board.cell[index].getBranches().length; j++) {
			if (board.cell[index].getBranches()[j] != lastIndex) {
				output = IntArrayMethods.combine(
						output,
						wherePlayerCanGoHelp(
								board.cell[index].getBranches()[j],
								DiceNum - 1, index));
			}
		}

		output = IntArrayMethods.deleteDuplicates(output);
		return output;
	}

	/**
	 * Removes current treasure from goal treasures and increase player score
	 * 
	 * @param playerNum
	 *            Which player win treasure
	 * @param treasureName
	 *            Which treasure should removes from goal treasures in the next
	 *            rounds
	 */
	public void wonTreasure(int playerNum, String treasureName) {
		player[playerNum].wonTreasure();
		treasures.wonTreasure(treasureName);
		Sound.playClip("wonTreasure.wav");
	}

	/**
	 * Get players turn
	 * 
	 * @return Game turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Changes turn
	 */
	public void changeTurn() {
		turn = ((turn + 1) % playerNumbers);
	}

	/**
	 * Get all players place
	 * 
	 * @return Players place
	 */
	public int[] getPlaces() {
		int[] places = new int[playerNumbers];
		for (int i = 0; i < playerNumbers; i++)
			places[i] = player[i].getPlace();
		return places;
	}

	/**
	 * Check if a cell is free or not
	 * 
	 * @param index
	 *            Cell index
	 * @return True if index cell is free
	 */
	public boolean isCellFree(int index) {
		for (int i = 0; i < getPlaces().length; i++) {
			if (index == getPlaces()[i])
				return false;
		}
		return true;
	}

	/**
	 * Returns Violet cell index
	 * 
	 * @return Violet cell index
	 */
	public int getVioletCellIndex() {
		return violetCellIndex;
	}

	/**
	 * Returns Red cell index
	 * 
	 * @return Red cell index
	 */
	public int getRedCellIndex() {
		return redCellIndex;
	}
}