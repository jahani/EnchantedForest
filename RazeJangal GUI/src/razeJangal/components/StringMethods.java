package razeJangal.components;

/**
 * @author Peyman Jahani <jahani.peyman @ gmail.com>
 * @version 1.0
 * @since 2012-03-25
 */
public class StringMethods {

	/**
	 * Replace a word or an string in a string with another one
	 * 
	 * @param input
	 *            String that we want to change
	 * @param fromWord
	 *            String or a word that we want to delete and replace the other
	 *            word
	 * @param toWord
	 *            String that we should replace it with parameter "fromWord"
	 * @return New String that is done replacement on it
	 */
	public static String replaceWord(String input, String fromWord,
			String toWord) {
		if (input.contains(fromWord)) {
			int start = input.indexOf(fromWord);
			int end = start + fromWord.length();
			return input.substring(0, start) + toWord + input.substring(end);
		}
		return input;
	}
}
