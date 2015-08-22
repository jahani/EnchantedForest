package razeJangal.components;

/**
 * @author Peyman Jahani <jahani.peyman @ gmail.com>
 * @version 1.0
 * @since 2012-03-25
 */
public class IntArrayMethods {

	/**
	 * Delete duplicate members of array
	 * 
	 * @param input
	 *            An array of integers
	 * @return An array of integers that doesn't have repeated member
	 */
	public static int[] deleteDuplicates(int[] input) {
		if (input.length == 0)
			return input;
		int[] output = new int[1];
		output[0] = input[0];
		outer: for (int i = 1; i < input.length; i++) {
			for (int j = 0; j < i; j++)
				if (input[i] == input[j])
					break outer;
			output = addNew(output, input[i]);
		}
		return output;
	}

	/**
	 * Add an integer to array of integers
	 * 
	 * @param array
	 *            Array of integers that we want to add an integer to it
	 * @param newInt
	 *            New integer that we want to add to array
	 * @return New array of integers that contains new member
	 */
	public static int[] addNew(int[] array, int newInt) {
		int[] temp = array;
		array = new int[temp.length + 1];
		for (int i = 0; i < temp.length; i++)
			array[i] = temp[i];
		array[temp.length] = newInt;
		return array;
	}

	/**
	 * Deletes an integer from an array of integers
	 * 
	 * @param array
	 *            Array of integers that we want to delete one of it's members
	 * @param delete
	 *            An integer that we want to delete from array
	 * @return New array that doesn't contains deleted integer
	 */
	public static int[] deleteOne(int[] array, int delete) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == delete) {
				int[] temp = array;
				array = new int[array.length - 1];
				for (int j = 0; j < i; j++)
					array[j] = temp[j];
				for (int j2 = i; j2 < array.length; j2++)
					array[j2] = temp[j2 + 1];
				return array;
			}
		}
		return array;
	}

	/**
	 * Combines two array of integers
	 * 
	 * @param first
	 *            First array of integers
	 * @param second
	 *            Second array of integers
	 * @return New array of integers that contains both parameters
	 */
	public static int[] combine(int[] first, int[] second) {
		int[] output = new int[first.length + second.length];
		for (int i = 0; i < first.length; i++)
			output[i] = first[i];
		for (int i = first.length; i < output.length; i++)
			output[i] = second[i - first.length];
		return output;
	}
}
