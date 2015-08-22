package razeJangal.components;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Peter Lawrey
 * @version 1.0
 * @since 2010-04-18
 * @source 
 *         http://stackoverflow.com/questions/326390/how-to-create-a-java-string-
 *         from-the-contents-of-a-file
 */
public class File {
	/**
	 * Converts a text file to an string
	 * 
	 * @param filePath
	 *            File path
	 * @return String that contains file text
	 */
	public static String read(String filePath) {
		FileInputStream input = null;
		try {
			input = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return read(input);
	}

	public static String read(FileInputStream input) {
		byte[] fileData = null;
		try {
			fileData = new byte[input.available()];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			input.read(fileData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			return new String(fileData, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ERROR!!1";
	}
}
