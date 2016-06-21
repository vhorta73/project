package app.core.dataSet;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * The Map Transform for the ideal columns from the source columns,
 * mapping between indexes and making any required mathematical operation
 * from the source to the output.
 * 
 * @author Vasco
 *
 */
public class MapTransform implements Function<Double, Double> {

    /**
     * The operations to be applied to the given double.
     */
    private final MathOperator operation;
    
    /**
     * The biasValue to be used with the operation to transform
     * the apply given value.
     */
    private final BigDecimal biasValue;
    
    /**
     * If no operation is supplied at the start, then no transformation 
     * will be required, and the input value will be returned.
     */
    public MapTransform() {
        this.operation = null;
        this.biasValue = new BigDecimal(0d);
    }

    /**
     * Constructor requiring an operator that will be applied to the 
     * supplied value. Only operators that do not require two arguments
     * will be allowed to use this constructor.
     * 
     * @param operation MathOperator
     */
    public MapTransform(MathOperator operation) {
        // An operation must be supplied
        if ( operation == null ) {
            throw new IllegalArgumentException("No operation list supplied");
        }
        // allowed operations for this constructor
        if ( operation.equals(MathOperator.INV) | operation.equals(MathOperator.BIN)) {
            this.operation = operation;
            this.biasValue = BigDecimal.valueOf(0.0);
        }
        else {
        	throw new IllegalArgumentException("MathOperator supplied require a biased Value: "+operation);
        }
    }

    /**
     * Constructor requiring an operator and a double where the input
     * value will suffer the operator action with the given bias value.<p>
     * e.g.: apply(value) will return value + operation + biasValue.
     * 
     * @param operation MathOperator
     * @param biasValue double
     */
    public MapTransform(MathOperator operation, double biasValue) {
        // An operation must be supplied
        if ( operation == null ) {
            throw new IllegalArgumentException("No operation list supplied");
        }
        if ( operation.equals(MathOperator.DIV) & biasValue == 0 ) 
            throw new ArithmeticException("Illegal division by zero");
        
        this.operation = operation;
        this.biasValue = BigDecimal.valueOf(biasValue);
    }

    /**
     * If an operator was supplied, it will take the bias value, and operate
     * with this supplied double to return the result.
     * 
     * If no operation was supplied, this value will be returned without any
     * transformation.
     * 
     * @param value Double
     * @return Double
     */
    @Override
    public Double apply(Double value) {

        // If no mathematical calculations are to be made, return given value.
        if ( operation == null ) return value;
        
        BigDecimal bigDecimalValue = BigDecimal.valueOf(value);
        if ( operation.equals(MathOperator.ADD) ) return bigDecimalValue.add(biasValue).doubleValue();
        if ( operation.equals(MathOperator.SUB) ) return bigDecimalValue.subtract(biasValue).doubleValue();
        if ( operation.equals(MathOperator.MUL) ) return bigDecimalValue.multiply(biasValue).doubleValue();
        if ( operation.equals(MathOperator.DIV) ) return bigDecimalValue.doubleValue() / biasValue.doubleValue();
        if ( operation.equals(MathOperator.INV) ) {
        	if ( value == 0 ) return 1.0;
        	return 0.0;
        }
        if ( operation.equals(MathOperator.BIN) ) {
        	if ( value == 0 ) return 0.0;
        	return 1.0;
        }

        throw new IllegalStateException("Don't supplied operation: "+operation);
    }
    
    
}