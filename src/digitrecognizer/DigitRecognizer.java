/**
 *
 */
package digitrecognizer;

import java.io.IOException;
import mnist.tools.MnistManager;
import neuralnetwork.*;


/**
 * @author sander van kasteel, svankasteel@gmail.com
 *
 * Deze klasse is een NetController die specifiek voor het herkennen van integers
 * uit een .ppm
 *
 */
public class DigitRecognizer extends NetController<Integer> {

	// Tools bevat methoden die handig, maar niet direct relevant zijn voor deze
	// specifieke klasse
	public Toolbox Tools;


	// de constructor maakt een nieuw netwerk aan; hier wordt ook de grootte van het netwerk bepaald
	// en de range van de inputwaarden, i.e. 0-255 voor .ppm
	public DigitRecognizer(double theta,double alpha,double epsilon) {
		this.Tools = new Toolbox();
		this.ann = new NeuralNetwork(new int[] {784,400,200,10},theta,alpha,0,255);
		this.maxError = epsilon;
	}


	// deze methode gebruikt Mnist-dataset als input en blijft daarop trainen
	// totdat de fout kleiner of gelijk is dan de maximale fout of de training-
	// set op is (60000 items)
	@Override
	public void iteratedTraining(String dataPath, String labelPath, int samplesize) {
		System.out.print("Training network");
		try {
			double error;
			double lowest = 100;
			MnistManager m = new MnistManager(dataPath, labelPath);
			int i = 1;

			do {
				m.setCurrent(i);
				int target = m.readLabel();
				int[][] image = m.readImage();
				double[] input = Tools.toDoubleAll(Tools.concatAll(image));
				error = train(input,target);
				if (error<lowest) {
					lowest = error;
					//System.out.println("New record: " + error);
				}
				if (i%100==0)
					System.out.print(".");
				i++;
			} while (error>this.maxError&&i<=samplesize);
			System.out.println();
			System.out.println("Training ended after iteration " + i + ".");
			System.out.println("Final error: " + error);
			System.out.println();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	// iteratedTesting lijkt op iteratedTraining, maar itereert test
	// uiteindelijke console output geeft de nauwkeurigheid van het netwerk weer in
	// procenten, afgerond op 2 decimalen
	@Override
	public void iteratedTesting(String dataPath, String labelPath, int samplesize) {
		System.out.print("Testing network");
		try {
			int successes = 0;

			MnistManager m = new MnistManager(dataPath, labelPath);
			int i = 1;

			do {
				m.setCurrent(i);
				int target = m.readLabel();
				int[][] image = m.readImage();
				double[] input = Tools.toDoubleAll(Tools.concatAll(image));
				if (test(input,target)) {
					successes++;
					if (i%100==0)
						System.out.print(".");
				} else System.out.print("X");

				i++;
			} while (i<=samplesize);
			double acc = Math.round(((((double) successes)/samplesize)*10000)/100);
			System.out.println();
			System.out.println("Testing complete!");
			System.out.println("Final accuracy: " +  acc + "%");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	// Deze methode zet labels om in target-data
	@Override
	protected double[] parse(Integer label) {
		double[] result = new double[10];
		for (int i=0;i<10;i++) {
			if (i==label)
				result[i] = 1;
			else
				result[i] = 0;
		}
		return result;
	}


	// Deze methode zet output om in begrijpelijke informatie
	@Override
	protected Integer translate(double[] data) {
		double max = -1;
		int best = -1;
		for (int i=0;i<data.length;i++) {
			if (data[i]>max) {
				max = data[i];
				best = i;
			}
		}
		return new Integer(best);
	}

	@Override
	public void loadNetwork() {
		// TODO Methode om een opgeslagen netwerk in te laden

	}

	@Override
	public void saveNetwork() {
		// TODO Methode om een netwerk op te slaan in een file

	}


}
