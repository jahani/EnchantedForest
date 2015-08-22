package razeJangal.game;

/**
 * @author Peyman Jahani <jahani.peyman @ gmail.com>
 * @version 1.0
 * @since 2012-03-25
 */
public class Player {
	private int place = 0;
	private int score = 0;
	private String name = "Default Player";

	/**
	 * Set player cell index to the parameter
	 * 
	 * @param index
	 *            Player index
	 */
	public void setPlace(int index) {
		place = index;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public String getName() {
		return name;
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