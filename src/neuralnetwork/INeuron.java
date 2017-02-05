/**
 *
 */
package neuralnetwork;

/**
 * @author sander van kasteel, svankasteel@gmail.com
 *
 * Wat een Neuron in ieder geval moet kunnen
 *
 */
public interface INeuron {

	public double getInput();
	public double getOutput();
	public void fire();

}
