package razeJangal.game;

/**
 * @author Peyman Jahani <jahani.peyman @ gmail.com>
 * @version 1.0
 * @since 2012-03-25
 */
class BoardCell {
	private static final String[] colorType = { "unset", "Blue", "White",
			"Green", "Orange", "Violet", "Red" };
	private int colorCode = 0;
	private int[] branches;

	/**
	 * Constructor
	 * 
	 * Make a BoardCell with given information
	 * 
	 * @param color
	 *            Color of cell
	 * @param branches
	 *            Cell branches
	 */
	public BoardCell(String color, int[] branches) {
		for (int i = 0; i < colorType.length; i++)
			if (color.equals(colorType[i]))
				colorCode = i;
		this.branches = branches;
	}

	/**
	 * Returns cell color
	 * 
	 * @return Cell color
	 */
	public String getColor() {
		return colorType[colorCode];
	}

	/**
	 * Returns cell branches
	 * 
	 * @return Cell branches
	 */
	public int[] getBranches() {
		return branches;
	}

	/**
	 * Return Blue cells name
	 * 
	 * @return Blue cells name
	 */
	public static final String getBlueColor() {
		return colorType[1];
	}

	/**
	 * Return Orange cells name
	 * 
	 * @return Orange cells name
	 */
	public static final String getOrangeColor() {
		return colorType[4];
	}

	/**
	 * Return Violet cells name
	 * 
	 * @return Violet cells name
	 */
	public static final String getVioletColor() {
		return colorType[5];
	}

	/**
	 * Return Red cells name
	 * 
	 * @return Red cells name
	 */
	public static final String getRedColor() {
		return colorType[6];
	}
}
