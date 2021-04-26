/*
 * Copyright 2021 Volgyerdo Nonprofit Kft.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package volgyerdo.neural.logic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import volgyerdo.neural.structure.Activation;
import static org.junit.Assert.*;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class ActivationFactoryTest {

    private static final float MAXIMUM = Float.MAX_VALUE / 10f;
    private static final float START_STEP = 0.1f;

    private static final double MAXIMUM_ERROR = 2;
    private static final double MAXIMUM_AVERAGE_ERROR = 0.2;

    private static final DecimalFormat FORMATTER = new DecimalFormat("0.0000");

    private static final double LEAKYRELUVALUE = 0.01; //between 0.01 to 0.1-0.2

    public ActivationFactoryTest() {
    }

    @Test
    public void testCreateSigmoid() {
        Activation sigmoidParameters = ActivationFactory.createSigmoid();
        testActivationFunction("Sigmoid", sigmoidParameters, (x) -> idealSigmoid(x));
    }

    private float idealSigmoid(float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    @Test
    public void testCreateSwish() {
        Activation swishParameters = ActivationFactory.createSwish();
        testActivationFunction("Swish", swishParameters, (x) -> idealSwish(x));
    }

    private float idealSwish(float x) {
        return (float) (x * idealSigmoid(x));
    }

    @Test
    public void testCreateTanH() {         
        Activation tanhParameters = ActivationFactory.createTanH();
        testActivationFunction("TanH", tanhParameters, (x) -> idealTanh(x));
    }
    
    private float idealTanh(float x){
        return (float) Math.tanh(x);
    }

    @Test
    public void testCreateReLu() {        
        Activation tanhParameters = ActivationFactory.createReLu();
        testActivationFunction("ReLu", tanhParameters, (x) -> idealReLu(x));
    }
    
    private float idealReLu(float x){
        return (float) Math.max(0, x);
    }
//
//    @Test
//    public void testCreateLeakyReLu() {
//        System.out.println("LeakyReLu: ");
//        Activation LeakyReLuParameters = ActivationFactory.createLeakyReLu();
//        double averageError = 0;
//        double maximumError = 0;
//        int n = 0;
//        for(float x = MINIMUM; x <= MAXIMUM; x += STEP){
//            double error = Math.abs(idealLeakyReLu(x)-ActivationLogic.activate(x, LeakyReLuParameters));
//            averageError += error;
//            maximumError = Math.max(maximumError, error);              
//            n++;  
//            
////            logResultBetween(x, -1, 1, error);
//        }
//        averageError /= n;
//        errorLogger("LeakyReLu averageError: ", averageError);
//    
//        
//        assertTrue("LeakyReLu average difference", averageError < MAXIMUM_AVERAGE_ERROR);
//        assertTrue("LeakyReLu maximum difference", maximumError < MAXIMUM_ERROR);
//        
////        float minimum = -Float.MAX_VALUE;
////        float maximum = Float.MAX_VALUE;
////        assertEquals("LeakyReLu minimum value", ActivationLogic.activate(minimum, LeakyReLuParameters), idealLeakyReLu(minimum), MAXIMUM_ERROR);
////        assertEquals("LeakyReLu maximum value", ActivationLogic.activate(maximum, LeakyReLuParameters), idealLeakyReLu(maximum), MAXIMUM_ERROR);
//    }
//    
//    private float idealLeakyReLu(float x){
//        return (float) Math.max(LEAKYRELUVALUE*x, x);
//    }
//
//    @Test
//    public void testCreateStep() {
//        System.out.println("Step:");
//        Activation StepParameters = ActivationFactory.createStep();
//        double averageError = 0;
//        double maximumError = 0;
//        int n = 0;
//        for(float x = MINIMUM; x <= MAXIMUM; x += STEP){
//            double error = Math.abs(idealStep(x)-ActivationLogic.activate(x, StepParameters));
//            averageError += error;
//            maximumError = Math.max(maximumError, error);              
//            n++;         
////            logResultBetween(x, -1, 1, error);
//        }
//        averageError /= n;
//        errorLogger("Step: ", averageError);
//
//        assertTrue("Step average difference", averageError < MAXIMUM_AVERAGE_ERROR);
//        assertTrue("Step maximum difference", maximumError < MAXIMUM_ERROR);
//        
////        float minimum = -Float.MAX_VALUE;
////        float maximum = Float.MAX_VALUE;
////        assertEquals("Step minimum value", ActivationLogic.activate(minimum, StepParameters), idealStep(minimum), MAXIMUM_ERROR);
////        assertEquals("Step maximum value", ActivationLogic.activate(maximum, StepParameters), idealStep(maximum), MAXIMUM_ERROR);
//    }
//    
//    public float idealStep(float x){
//        if(x>0) { 
//            return 1; }
//        else {
//            return 0; }
//    }
//
//    @Test
//    public void testCreateLinear() {
//        Activation LinearParameters = ActivationFactory.createLinear();
//        double averageError = 0;
//        double maximumError = 0;
//        int n = 0;
//        System.out.println("Linear:");
//        for(float x = MINIMUM; x <= MAXIMUM; x += STEP){
//            double error = Math.abs(idealLinear(x)-ActivationLogic.activate(x, LinearParameters));
//            
//            System.out.println(x + ";" +idealLinear(x) + ";" + ActivationLogic.activate(x, LinearParameters));
//            
//            averageError += error;
//            maximumError = Math.max(maximumError, error);              
//            n++;         
//
////            logResultBetween(x, -1, 1, error);
//        }
//        averageError /= n;
//        errorLogger("Linear: ", averageError);
//    
//        
//        assertTrue("Linear average difference", averageError < MAXIMUM_AVERAGE_ERROR);
//        assertTrue("Linear maximum difference", maximumError < MAXIMUM_ERROR);
//        
////        float minimum = -Float.MAX_VALUE;
////        float maximum = Float.MAX_VALUE;
////        assertEquals("Linear minimum value", ActivationLogic.activate(minimum, LinearParameters), idealLinear(minimum), MAXIMUM_ERROR);
////        assertEquals("Linear maximum value", ActivationLogic.activate(maximum, LinearParameters), idealLinear(maximum), MAXIMUM_ERROR);
//    }
//    
//    public float idealLinear(float x){
//        return x;
//    }


    public void testActivationFunction(String functionName, Activation parameters, IdealActivationFunction idealFunction) {
        
        float step = START_STEP;

        List<Float> positiveValues = new ArrayList<>();
        List<Float> negativeValues = new ArrayList<>();
        for (float x = 0; x <= MAXIMUM; x += step) {
            positiveValues.add(x);
            if (x > 0) {
                negativeValues.add(0, -x);
            }
            step *= 2;
        }

        List<Float> inputValues = new ArrayList<>(negativeValues);
        inputValues.addAll(positiveValues);

        double testedValue, idealValue, error;

        double averageError = 0;
        double maximumError = 0;
        int n = 0;
        
        for (float x : inputValues) {
            testedValue = ActivationLogic.activate(x, parameters);
            idealValue = idealFunction.activate(x);
            error = Math.abs(idealValue - testedValue);
            System.out.println(
                    functionName + ";"
                    + FORMATTER.format(x) + ";"
                    + FORMATTER.format(testedValue) + ";"
                    + FORMATTER.format(idealValue) + ";"
                    + FORMATTER.format(error));
            averageError += error;
            maximumError = Math.max(maximumError, error);
            n++;
        }

        averageError /= n;

        System.out.println(functionName + " averageError: " + FORMATTER.format(averageError));
        System.out.println(functionName + " maximumError: " + FORMATTER.format(maximumError));

        assertTrue(functionName + " average difference", averageError < MAXIMUM_AVERAGE_ERROR);
        assertTrue(functionName + " maximum difference", maximumError < MAXIMUM_ERROR);
    }

    private interface IdealActivationFunction {

        public float activate(float x);

    }
}
