package digitrecognizer;

public class Main {

	/**
	 *
	 * @author sander van kasteel, svankasteel@gmail.com
	 *
	 * Helaas is de performance van het netwerk niet naar behoren. Of dit ligt aan een
	 * fout in de code of aan slechte parameters kan ik in de gegeven tijd niet achterhalen.
	 *
	 * Het feit dat ik uberhaupt output heb kunnen krijgen terwijl ik de opdracht alleen deed
	 * mag al een klein wonder heten.
	 *
	 * @param args
	 *
	 *  Het mooist was geweest om de argumenten voor iteratedTraining en iteratedTesting
	 *  als argumenten mee te geven, maar voor nu lijkt me dat wat triviaal.
	 *
	 */
	public static void main(String[] args) {
		DigitRecognizer dr = new DigitRecognizer(0.5,0.2,0.1);
		dr.iteratedTraining("src/resources/train-images-idx3-ubyte","src/resources/train-labels-idx1-ubyte",60000);
		dr.iteratedTesting("src/resources/t10k-images-idx3-ubyte","src/resources/t10k-labels-idx1-ubyte",10000);
	}

}
