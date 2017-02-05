package neuralnetwork;

import java.util.*;

/**
 *
 * @author sander van kasteel, svankasteel@gmail.com
 *
 * Deze klasse implementeert een neuraal netwerk zonder bias met momentum

 *
 * @param <E>
 * E is het type van het te herkennen symbool.
 *
 */

public class NeuralNetwork {

	protected InputNeuron[] inputLayer;
	protected HiddenNeuron[][] hiddenLayers;
	protected OutputNeuron[] outputLayer;
	public double theta;
	public double alpha;
	protected Map<Neuron,Double> Dw;


	// constructor voor een nieuw netwerk, dimensions is een array van ints die voor elke laag het aantal
	// neuronen aangeeft.
	public NeuralNetwork(int[] dimensions, double learnRate, double momentum, double bound1, double bound2) {

		this.theta = learnRate;
		this.alpha = momentum;
		int last = dimensions.length-1;

		this.inputLayer = new InputNeuron[dimensions[0]];
		this.hiddenLayers = new HiddenNeuron[dimensions.length-2][];
		this.outputLayer = new OutputNeuron[dimensions[last]];

		this.Dw = new HashMap<Neuron,Double>();

		for (int n=0;n<dimensions[last];n++)
			this.outputLayer[n] = new OutputNeuron();

		Neuron[] scope = this.outputLayer;

		for (int l=dimensions.length-2;l>0;l--) {
			this.hiddenLayers[l-1] = new HiddenNeuron[dimensions[l]];
			for (int n=0;n<dimensions[l];n++) {
				this.hiddenLayers[l-1][n] = new HiddenNeuron(scope);
				this.Dw.put(this.hiddenLayers[l-1][n],0.0);
			}
			scope = this.hiddenLayers[l-1];
		}

		for (int n=0;n<dimensions[0];n++) {
			this.inputLayer[n] = new InputNeuron(scope,Math.min(bound1,bound2),Math.max(bound1,bound2));
			this.Dw.put(this.inputLayer[n],0.0);
		}

	}


	// constructor voor een opgeslagen netwerk
	public NeuralNetwork(Map<Neuron,Map<Neuron,Double>> net, double learnRate, double momentum, double bound1, double bound2) {
		// TODO
	}


	// calculeer output gegeven een input
	public double[] calcOutput(double[] input) {
		setInput(input);
		for (Neuron i: this.inputLayer) {
			i.fire();
		}
		for (int l=0;l<this.hiddenLayers.length;l++) {
			for (Neuron h: this.hiddenLayers[l]) {
				h.fire();
			}
		}
		return getOutput();
	}


	// geef input aan inputneuronen
	protected void setInput(double[] input) {
		for (int i=0;i<this.inputLayer.length;i++)
			inputLayer[i].setInput(input[i]);
	}


	// vraag de output van de outputneuronen
	protected double[] getOutput() {
		double[] output = new double[this.outputLayer.length];
		for (int n=0;n<this.outputLayer.length;n++) {
			output[n] = this.outputLayer[n].getOutput();
		}
		return output;
	}


	public double calcError(double[] target, double[] output) {
		double result = 0;
		for (int i=0;i<target.length;i++) {
			result += Math.pow((target[i]-output[i]),2)/2;
		}
		return result;
	}


	// bereken correctietermen voor het hele netwerk
	// ieder neuron-delta-paar wordt in een Map opgeslagen en teruggegeven
	protected Map<Neuron,Double> calcDeltas(double[] target) {

			Map<Neuron,Double> deltaMap = new HashMap<Neuron,Double>();

			for (int i=0;i<this.outputLayer.length;i++) {
				double di = this.outputLayer[i].calcDelta(deltaMap,target[i]);
				deltaMap.put(outputLayer[i],di);
			}

			for (int l=this.hiddenLayers.length-1;l>=0;l--) {
				for (Neuron h: this.hiddenLayers[l]) {
					double dk = h.calcDelta(deltaMap,0);
					deltaMap.put(h,dk);
				}
			}

			for (Neuron i: this.inputLayer) {
				double dk = i.calcDelta(deltaMap,0);
				deltaMap.put(i, dk);
			}

			return deltaMap;
	}


	// update de gewichten adhv correctietermen
	public void updateWeights(double[] target) {

		Map<Neuron,Double> deltas = calcDeltas(target);

		for (Neuron n: this.Dw.keySet()) {

			double dw =
					(this.theta * deltas.get(n) * n.getOutput()) +
					(this.alpha * this.Dw.get(n));
			Dw.put(n, dw);
			n.updateWeights(dw);
		}

	}


	// zet alle waarden weer op null
	public void resetValues() {
		for (Neuron o: this.outputLayer)
			o.reset();
		for (int l=this.hiddenLayers.length-1;l>=0;l--) {
			for (Neuron h: this.hiddenLayers[l])
				h.reset();
		}
		for (Neuron i: this.inputLayer)
			i.reset();
	}


}
