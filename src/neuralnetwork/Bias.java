package neuralnetwork;

import java.util.*;

/**
 *
 * @author sander van kasteel, svankasteel@gmail.com
 *
 * Ongebruikte klasse voor eventuele bias
 *
 */

public class Bias implements INeuron {

	public Map<Neuron,Double> outgoing;

	public Bias(Neuron[] layer) {
		this.outgoing = new HashMap<Neuron,Double>();

		for (Neuron n: layer) {
			this.outgoing.put(n,Math.random()-0.5);
		}
	}

	@Override
	public double getInput() {
		return 0;
	}

	@Override
	public double getOutput() {
		return 1;
	}

	@Override
	public void fire() {
		Iterator<Neuron> it = this.outgoing.keySet().iterator();
		while (it.hasNext()) {
			Neuron j = it.next();
			j.incoming.add(this.outgoing.get(j));
		}
	}

}
