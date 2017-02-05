package neuralnetwork;

/**
 *
 * @author sander van kasteel, svankasteel@gmail.com
 *
 *	Deze abstracte klasse implementeert de generieke methoden train en test
 *
 * @param <E>
 * E is het type van het label dat het netwerk moet herkennen
 */
public abstract class NetController<E> {

	protected NeuralNetwork ann;
	public double maxError;

	public abstract void iteratedTraining(String dataPath,String labelPath, int samplesize);
	public abstract void iteratedTesting(String dataPath,String labelPath, int samplesize);


	// deze methode komt uit de opdracht en zit in de iteratedTraining-methode
	protected double train(double[] input,E label) {
		double[] O = this.ann.calcOutput(input);
		double[] R = parse(label);
		double error = this.ann.calcError(R,O);
		this.ann.updateWeights(R);
		this.ann.resetValues();
		return error;
	}


	// deze methode zit in de iteratedTesting-methode
	protected boolean test(double[] input,E label) {
		System.out.println("Label: " + label);
		double[] O = this.ann.calcOutput(input);
		E output = translate(O);
		System.out.println("Output: " + output);
		return (output==label);
	}

	protected abstract double[] parse(E label);
	protected abstract E translate(double[] data);

	public abstract void loadNetwork();
	public abstract void saveNetwork();

}
