package neuralnetwork;

import java.util.*;

/**
 *
 * @author sander van kasteel, svankasteel@gmail.com
 *
 * Deze klasse is specifiek voor neuronen in de outputlaag
 */
public class OutputNeuron extends Neuron {

	// constructormethode
	public OutputNeuron() {
		this.incoming = new HashSet<Double>();
	}


	// deze overridden methode verzekert dat er geen uitgaande verbindingen worden
	// geinitialiseerd
	@Override
	public void initWeights() {
		throw new NullPointerException("Output neurons cannot be assigned weights.");
	}


	// deze gebruikt sigmoide als activatiefunctie
	@Override
	protected void activate() {
		this.output = new Double(1/(1+Math.pow(Math.E,(-this.getInput()))));
	}


	// correctieterm voor outputneuron j is o(j)*(1-o(j))*(t(j)-o(j))
	@Override
	public double calcDelta(Map<Neuron,Double> deltas, double target) {
		return this.output*(1-this.output)*(target-this.output);
	}


	// deze methode is onnodig, maar kan eventueel gebruikt worden om
	// de outputwaarde in de console weer te geven
	@Override
	public void fire() {
		System.out.println(this.getOutput());
	}


}
