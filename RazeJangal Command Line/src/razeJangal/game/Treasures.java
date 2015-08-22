package razeJangal.game;

import java.util.Random;

import razeJangal.components.IntArrayMethods;

/**
 * @author Peyman Jahani <jahani.peyman @ gmail.com>
 * @version 1.0
 * @since 2012-03-25
 */
public class Treasures {
	private Random rnd = new Random();
	private int[] treasureIndex = new int[0];
	private String[] treasureName = new String[0];
	private boolean[] eatenTreasure;
	private int goalTreasure;

	/**
	 * Constructor
	 * 
	 * Counts number of treasures and assign name to them and then random their
	 * places
	 * 
	 * @param myBoard
	 *            Board of the game
	 */
	public Treasures(Board myBoard) {
		for (int index = 0; index < myBoard.cell.length; index++)
			if (myBoard.cell[index].getColor().equals(
					BoardCell.getOrangeColor())) {
				treasureIndex = IntArrayMethods.addNew(treasureIndex, index);
			}

		treasureName = new String[treasureIndex.length];
		for (int i = 0; i < treasureName.length; i++)
			treasureName[i] = "" + ((char) ((int) 'A' + i));

		for (int i = 0; i < treasureName.length; i++) {
			int randNum = rnd.nextInt(treasureName.length);
			String temp = treasureName[i];
			treasureName[i] = treasureName[((i + randNum) % treasureName.length)];
			treasureName[((i + randNum) % treasureName.length)] = temp;
		}

		goalTreasure = rnd.nextInt(treasureIndex.length);

		eatenTreasure = new boolean[treasureIndex.length];
		for (int i = 0; i < eatenTreasure.length; i++)
			eatenTreasure[i] = false;

	}

	/**
	 * When a player win a treasure, this will delete treasure from next goal
	 * treasures
	 * 
	 * @param name
	 *            Treasure name that should delete from goal treasures
	 */
	public void wonTreasure(String name) {
		for (int i = 0; i < treasureName.length; i++)
			if (name.equals(treasureName[i])) {
				eatenTreasure[i] = true;
				return;
			}
		System.err.println("NOT FOUND TREASURE NAME!!!");
	}

	/**
	 * Get goal treasure name
	 * 
	 * @return Goal treasure name
	 */
	public String getGoal() {
		return treasureName[goalTreasure];
	}

	/**
	 * Changes goal treasure
	 * 
	 * Return false when treasures are finished and can't change goal treasure
	 * 
	 * @return True if goal treasure has changed
	 */
	public boolean changeGoal() {
		if (finishedTreasures()) {
			System.err.println("can NOT change goal, treasures are finished!");
			return false;
		}
		while (true) {
			int rand = rnd.nextInt(eatenTreasure.length);
			if (eatenTreasure[rand] == false) {
				goalTreasure = rand;
				return true;
			}
		}
	}

	/**
	 * Whether treasures are finished or not
	 * 
	 * @return True if treasures are finished
	 */
	public boolean finishedTreasures() {
		for (int i = 0; i < eatenTreasure.length; i++)
			if (eatenTreasure[i] == false)
				return false;
		return true;
	}

	/**
	 * Get round number according to the treasures that are eaten by players
	 * 
	 * @return Round number
	 */
	public int getRoundNumber() {
		int roundNum = 1;
		for (int i = 0; i < eatenTreasure.length; i++)
			if (eatenTreasure[i] == true)
				roundNum++;
		return roundNum;
	}

	/**
	 * Get total number of treasures
	 * 
	 * @return Total number of treasures
	 */
	public int treasureNumbers() {
		return treasureIndex.length;
	}

	/**
	 * Get treasures indexes on the board
	 * 
	 * @return Treasures indexes
	 */
	public int[] getTreasureIndexes() {
		return treasureIndex;
	}

	/**
	 * Converts a cell index to the cell treasure
	 * 
	 * @param index
	 *            Cell index of treasure
	 * @return Treasure name
	 */
	public String index2Treasure(int index) {
		for (int i = 0; i < treasureIndex.length; i++)
			if (treasureIndex[i] == index)
				return treasureName[i];
		System.err.println("Not Found Treasure Name!");
		return "NOT FOUND!";
	}

	/**
	 * Converts a cell treasure to the cell index
	 * 
	 * @param name
	 *            Treasure name
	 * @return Cell index of treasure
	 */
	public int treasure2Index(String name) {
		for (int i = 0; i < treasureName.length; i++)
			if (name.equals(treasureName[i]))
				return treasureIndex[i];
		System.err.println("Not Found Treasure Index!");
		return -1;
	}
}