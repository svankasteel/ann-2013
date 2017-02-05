package neuralnetwork;

import java.util.*;

/**
 *
 * @author sander van kasteel, svankasteel@gmail.com
 *
 * Deze abstracte klasse geeft de basics van een neuron
 *
 */

public abstract class Neuron implements INeuron {

	public Set<Double> incoming;
	protected Double input;
	protected Double output;
	public Map<Neuron,Double> outgoing;


	// zet alle gewichten op een random waarde tussen -0.5 en 0.5
	protected void initWeights(Neuron[] out) {
		for (Neuron n: out) {
			this.outgoing.put(n,Math.random()-0.5);
		}
	}


	public void initWeights() {
		for (Neuron n: this.outgoing.keySet()) {
			this.outgoing.put(n,Math.random()-0.5);
		}
	}


	@Override
	public double getInput() {
		if (this.input==null) {
			double result = 0;
			Iterator<Double> it = this.incoming.iterator();
			while (it.hasNext()) {
				result += it.next();
				it.remove();
			}
			this.input = new Double(result);
		}
		return this.input;
	}

	@Override
	public double getOutput() {
		if (this.output==null)
			activate();
		return this.output;
	}

	@Override
	public void fire() {
		Iterator<Neuron> it = this.outgoing.keySet().iterator();
		while (it.hasNext()) {
			Neuron j = it.next();
			j.incoming.add(this.outgoing.get(j)*this.getOutput());
		}
	}

	// de activatiefunctie kan verschillen per neurontype en
	// is dus abstract
	protected abstract void activate();

	// reset alle waarden die bij een specifieke input horen
	public void reset() {
		this.incoming = new HashSet<Double>();
		this.input = null;
		this.output = null;
	}

	public abstract double calcDelta(Map<Neuron,Double> deltas, double target);


	// pas alle gewichten aan
	public void updateWeights(double dw) {

		for (Neuron n: this.outgoing.keySet()) {
			double wprime = this.outgoing.get(n) + dw;
			this.outgoing.put(n,wprime);
		}

	}

}
