package razeJangal.game;

import razeJangal.components.File;
import razeJangal.components.IntArrayMethods;

/**
 * @author Peyman Jahani <jahani.peyman @ gmail.com>
 * @version 1.0
 * @since 2012-03-25
 */
public class Board {
	private static final String indexTag = "index";
	private static final String valueTag = "value";
	private static final String branchTag = "branch";
	private static final String colorTag = "color";
	private static final String cordinateXTag = "cordinateX";
	private static final String cordinateYTag = "cordinateY";
	private int numberOfCells;
	private String map = File.read(".//src/razeJangal/files/map.xml");
	// TODO ".//src/"

	public BoardCell[] cell;

	/**
	 * Constructor
	 * 
	 * Exports cells information from file and put it in the BoardCell array
	 * 
	 * @see makeBoardCell
	 */
	public Board() {
		for (int i = 0;; i++)
			if (!map.contains("<" + indexTag + " " + valueTag + "=\"" + i
					+ "\">")) {
				numberOfCells = i;
				break;
			}
		cell = new BoardCell[numberOfCells];
		for (int i = 0; i < numberOfCells; i++) {
			cell[i] = makeBoardCell(i);
		}
	}

	/**
	 * Exports an special cell properties from file
	 * 
	 * @param index
	 *            Cell index
	 * @return Index cell information
	 */
	private String boardCellSpecifications(int index) {
		int start = map.indexOf("<" + indexTag + " " + valueTag + "=\"" + index
				+ "\">");
		int end = map.substring(start).indexOf("</" + indexTag + ">") + start
				+ ("</" + indexTag + ">").length() - 1;
		return "\t" + map.substring(start, end + 1);
	}

	/**
	 * Exports cell color from an string
	 * 
	 * @param str
	 *            Cell informations
	 * @return Color
	 */
	private String boardCellColor(String str) {
		int start = str.indexOf("<" + colorTag + ">");
		int end = str.indexOf("</" + colorTag + ">");
		return str.substring(start + ("<" + colorTag + ">").length(), end);
	}

	/**
	 * Exports cell branches from an string
	 * 
	 * @param str
	 *            Cell informations
	 * @return Array of branches
	 */
	private int[] boardCellBranches(String str) {
		int[] output = new int[0];
		while (str.contains("<" + branchTag + ">")) {
			int start = str.indexOf("<" + branchTag + ">");
			int end = str.indexOf("</" + branchTag + ">");
			int branchIndex = Integer.parseInt(str.substring(start
					+ ("<" + branchTag + ">").length(), end));
			str = str.substring(end + ("</" + branchTag + ">").length());

			output = IntArrayMethods.addNew(output, branchIndex);
		}
		return output;
	}
	
	private int boardCellX (String str) {
		int start = str.indexOf("<" + cordinateXTag + ">");
		int end = str.indexOf("</" + cordinateXTag + ">");
		return Integer.parseInt(str.substring(start + ("<" + cordinateXTag + ">").length(), end));
	}
	
	private int boardCellY (String str) {
		int start = str.indexOf("<" + cordinateYTag + ">");
		int end = str.indexOf("</" + cordinateYTag + ">");
		return Integer.parseInt(str.substring(start + ("<" + cordinateYTag + ">").length(), end));
	}

	/**
	 * Exports index cell color and branches and makes a BoardCell
	 * 
	 * @param index
	 *            Cell number
	 * @return BoardCell that contains index cell information
	 */
	private BoardCell makeBoardCell(int index) {
		String str = boardCellSpecifications(index);
		BoardCell output = new BoardCell(boardCellColor(str),
				boardCellBranches(str), boardCellX(str), boardCellY(str));
		return output;
	}

	/**
	 * Get index cell color
	 * 
	 * @param index
	 *            Cell number
	 * @return Color
	 */
	public String getColor(int index) {
		return cell[index].getColor();
	}

	/**
	 * Get index cell branches
	 * 
	 * @param index
	 *            Cell number
	 * @return Cell branches
	 */
	public int[] getBranches(int index) {
		return cell[index].getBranches();
	}

	/**
	 * Get number of total cells
	 * 
	 * @return Number of cells
	 */
	public int getNumberOfCells() {
		return numberOfCells;
	}
}