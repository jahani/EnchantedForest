package razeJangal.game;

/**
 * @author Peyman Jahani <jahani.peyman @ gmail.com>
 * @version 1.0
 * @since 2012-03-25
 */
public class BoardCell {
	private static final String[] colorType = { "unset", "Blue", "White",
			"Green", "Orange", "Violet", "Red" };
	private int colorCode = 0;
	private int[] branches;
	private int cordinateX;
	private int cordinateY;

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
	public BoardCell(String color, int[] branches, int cordinateX, int cordinateY) {
		for (int i = 0; i < colorType.length; i++)
			if (color.equals(colorType[i]))
				colorCode = i;
		this.branches = branches;
		this.cordinateX=cordinateX;
		this.cordinateY=cordinateY;
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
	
	public int getX() {
		return cordinateX;
	}
	public int getY() {
		return cordinateY;
	}

	/**
	 * Return Blue cells name
	 * 
	 * @return Blue cells name
	 */
	public static final String getBlueColor() {
		return colorType[1];
	}
	
	
	public static final String getWhiteColor() {
		return colorType[2];
	}
	
	public static final String getGreenColor() {
		return colorType[3];
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
