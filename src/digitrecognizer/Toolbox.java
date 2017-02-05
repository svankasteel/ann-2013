package digitrecognizer;


/**
 *
 * @author sander van kasteel, svankasteel@gmail.com
 *	Class voor methodes die van pas kunnen komen.
 */
public class Toolbox {

	public Toolbox() {

	}

	// Deze methode is gebaseerd op code van:
	//	http://stackoverflow.com/questions/80476/how-to-concatenate-two-arrays-in-java
	public int[] concatAll(int[][] matrix) {
		int totalLength = 0;
		for (int[] array: matrix) {
			totalLength += array.length;
		}
		int[] result = new int[totalLength];
		int offset = 0;
		for (int[] array: matrix) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	// converteert int[] naar double[]
	public double[] toDoubleAll(int[] array) {
		double[] result = new double[array.length];
		for (int i=0;i<array.length;i++) {
			result[i] = (double) array[i];
		}
		return result;
	}


}
