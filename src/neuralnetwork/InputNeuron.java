package neuralnetwork;

import java.util.*;

/**
 *
 * @author sander van kasteel, svankasteel@gmail.com
 *
 * Deze klasse is specifiek voor neuronen in de inputlaag
 *
 */
public class InputNeuron extends Neuron {

	private double lower;
	private double upper;


	// constructormethode waar de range van de input aan mee wordt
	// gegeven
	public InputNeuron(Neuron[] out,double l,double u) {
		this.outgoing = new HashMap<Neuron,Double>(out.length);
		initWeights(out);
		construct(l,u);
	}

	// constructormethode voor een opgeslagen netwerk
	public InputNeuron(Map<Neuron,Double> out,double l,double u) {
		this.outgoing = new HashMap<Neuron,Double>(out);
		construct(l,u);
	}


	// lost mogelijke problemen van de range op
	private void construct(double l,double u) throws ArithmeticException {
		if (l==u) throw new ArithmeticException(
				"Range is zerodimensional: division by 0 imminent!");
		this.lower = l;
		this.upper = u;
	}


	// input wordt niet bepaald adhv connecties, maar de waarde die
	// hij toegeschreven krijgt, dus geen berekening mogelijk
	@Override
	public double getInput() throws NullPointerException {
		if (this.input==null)
			throw new NullPointerException();
		else return this.input;
	}


	// de activatiefunctie van een inputneuron schaalt de input naar de
	// range [0-1]
	@Override
	protected void activate() {
		this.output = new Double((this.getInput()-this.lower)/Math.abs(upper-lower));
	}


	// correctieterm voor neuron j is o(j)*(1-o(j))*Downstream(j)
	// Downstream komt uit een map waarin alle voorgaande neuronen
	// met kun deltawaarden in opgeslagen zitten
	public double calcDelta(Map<Neuron, Double> deltas, double target) {
		double sum = 0;

		for (Neuron n: this.outgoing.keySet()) {
			double deltaK = deltas.get(n);
			double w = this.outgoing.get(n);
			sum += deltaK*w;
		}

		return this.output*(1-this.output)*sum;
	}


	// uniek voor inputneuronen
	public double setInput(double in) throws IllegalStateException {
		if (in<this.lower) throw new IllegalStateException(
				"Inputvalue out of bounds: smaller than" + this.lower);
		else if (in>this.upper) throw new IllegalStateException(
				"Inputvalue out of bounds: greater than" + this.upper);
		else {
			return this.input = new Double(in);
		}
	}

	@Override
	public void reset() {
		this.input = null;
		this.output = null;
	}

}
