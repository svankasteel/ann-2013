package neuralnetwork;

import java.util.*;

/**
 *
 * @author sander van kasteel, svankasteel@gmail.com
 *
 * Klasse die specifiek voor hidden neuronen is
 *
 */
public class HiddenNeuron extends Neuron {

	public HiddenNeuron(Neuron[] out) {
		this.incoming = new HashSet<Double>();
		this.outgoing = new HashMap<Neuron,Double>(out.length);

		initWeights(out);
	}

	// constructormethode voor opgeslagen netwerk
	public HiddenNeuron(Map<Neuron,Double> out) {
		this.incoming = new HashSet<Double>();
		this.outgoing = new HashMap<Neuron,Double>(out);
	}


	// activatiefunctie is sigmoid
	@Override
	protected void activate() {
		this.output = new Double(1/(1+Math.pow(Math.E,(-this.getInput()))));
	}

	// zie InputNeuron
	@Override
	public double calcDelta(Map<Neuron, Double> deltas, double target) {
		double sum = 0;

		for (Neuron n: this.outgoing.keySet()) {
			double deltaK = deltas.get(n);
			double w = this.outgoing.get(n);
			sum += deltaK*w;
		}

		return this.output*(1-this.output)*sum;
	}


}
