package app.core.activationFunction.function;

import org.encog.engine.network.activation.ActivationBipolarSteepenedSigmoid;
import org.encog.engine.network.activation.ActivationFunction;

import app.core.activationFunction.ActivationFunctionCore;
import app.core.activationFunction.ActivationFunctionKey;

/**
 * The BiPolar activation function adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ActivationBiPolarSteepenedSigmoidAdaptor implements ActivationFunctionCore<ActivationFunction> {

    /**
     * The BiPolar Steempened Sigmoid activation function description.
     */
    private final String DESCRIPTION = "BiPolar Steempened Sigmoid Activation Function - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "BiPolar Steempened Sigmoid ";
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivationFunction getActivationFunction() {
        return new ActivationBipolarSteepenedSigmoid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return PATTERN_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivationFunctionKey getId() {
        return ActivationFunctionKey.ActivationBipolarSteepenedSigmoid;
    }
}
