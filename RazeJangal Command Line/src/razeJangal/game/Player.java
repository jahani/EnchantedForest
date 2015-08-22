package razeJangal.game;

/**
 * @author Peyman Jahani <jahani.peyman @ gmail.com>
 * @version 1.0
 * @since 2012-03-25
 */
public class Player {
	private int place = 0;
	private int score = 0;

	/**
	 * Set player cell index to the parameter
	 * 
	 * @param index
	 *            Player index
	 */
	public void setPlace(int index) {
		place = index;
	}

	/**
	 * Increase player score when player win a treasure
	 */
	public void wonTreasure() {
		score++;
	}

	/**
	 * Get player cell index
	 * 
	 * @return Player index
	 */
	public int getPlace() {
		return place;
	}

	/**
	 * Get player score
	 * 
	 * @return Player score
	 */
	public int getScore() {
		return score;
	}
}