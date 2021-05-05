import junit.framework.Assert;

import com.imirzaev.calculator.Evaluator;
import com.imirzaev.calculator.Validator;

import org.junit.Test;

public class CalculatorTests {

	@Test
	public void testValidator() {
		
        boolean result;
		result = Validator.checkValidity("(12÷13)");
        Assert.assertTrue(result);

        result = Validator.checkValidity("12-13");
        Assert.assertTrue(result);

        result = Validator.checkValidity("12++");
        Assert.assertTrue(!result);

        result = Validator.checkValidity("(12+)");
        Assert.assertTrue(!result);

        result = Validator.checkValidity(")");
        Assert.assertTrue(!result);

        result = Validator.checkValidity("(234+45))");
        Assert.assertTrue(!result);
	}

    @Test
	public void testEvaluator() {
        double result;
		result = Evaluator.eval("12/3");
        Assert.assertEquals(result, 4.0);
        
        result = Evaluator.eval("(12+3)/5");
        Assert.assertEquals(result, 3.0);

        result = Evaluator.eval("12*5");
        Assert.assertEquals(result, 60.0);
    }
}