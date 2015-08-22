package razeJangal.game;

import java.util.Random;

/**
 * @author Peyman Jahani <jahani.peyman @ gmail.com>
 * @version 1.0
 * @since 2012-03-25
 */
public class DicePool {
	static Random rnd = new Random();

	/**
	 * Throw a dice
	 * 
	 * @return Dice number: a number between 1 and 6
	 */
	public static int diceThrowing() {
		return rnd.nextInt(6) + 1;
	}
}