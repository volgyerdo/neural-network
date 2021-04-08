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

import org.junit.Test;
import volgyerdo.neural.structure.Activation;
import static org.junit.Assert.*;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class ActivationFactoryTest {

    private static final float MINIMUM = -10;
    private static final float MAXIMUM = 10;
    private static final float STEP = 0.1f;
    
    private static final double MAXIMUM_ERROR = 1;
    private static final double MAXIMUM_AVERAGE_ERROR = 1;
    
    private static final double LEAKYRELUVALUE=0.01; //between 0.01 to 0.1-0.2
    
    
    public ActivationFactoryTest() {
    }
    
    @Test
    public void testCreateSigmoid() {
        Activation sigmoidParameters = ActivationFactory.createSigmoid();
        double averageError = 0;
        double maximumError = 0;
        int n = 0;
        for (float x = MINIMUM; x <= MAXIMUM; x += STEP) {
            double error = Math.abs(idealSigmoid(x)-ActivationLogic.activate(x, sigmoidParameters));
            averageError += error;
            maximumError = Math.max(maximumError, error);
            n++;
        }
        averageError /= n;
        assertTrue("Sigmoid average difference", averageError < MAXIMUM_AVERAGE_ERROR);
        assertTrue("Sigmoid maximum difference", maximumError < MAXIMUM_ERROR);
        
        float minimum = -Float.MAX_VALUE;
        float maximum = Float.MAX_VALUE;
        assertEquals("Sigmoid minimum value", ActivationLogic.activate(minimum, sigmoidParameters), idealSigmoid(minimum), MAXIMUM_ERROR);
        assertEquals("Sigmoid maximum value", ActivationLogic.activate(maximum, sigmoidParameters), idealSigmoid(maximum), MAXIMUM_ERROR);
    }

    private float idealSigmoid(float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    @Test
    public void testCreateSwish() { //DONE SAMPLE
        Activation swishParameters = ActivationFactory.createSwish();
        double averageError = 0;
        double maximumError = 0;
        int n = 0;
        System.out.println("Swish: ");
        for(float x = MINIMUM; x <=MAXIMUM; x += STEP){
              double error = Math.abs(idealSwish(x)-ActivationLogic.activate(x, swishParameters));
              averageError += error;
              maximumError = Math.max(maximumError, error);
              n++;
              
              logResultBetween(x, -1, 1, error); //Console Logging
        }
        averageError /= n;
        
        errorLogger("Swish: ", averageError); //Console Logging
        
        assertTrue("Swish average difference", averageError < MAXIMUM_AVERAGE_ERROR);
        assertTrue("Swish maximum difference", maximumError < MAXIMUM_ERROR);
        
        float minimum = -Float.MAX_VALUE;
        float maximum = Float.MAX_VALUE;
//        assertEquals("swish minimum value", ActivationLogic.activate(minimum, swishParameters), idealSwish(minimum), MAXIMUM_ERROR);
//        assertEquals("Swish maximum value", ActivationLogic.activate(maximum, swishParameters), idealSwish(maximum), MAXIMUM_ERROR);
    }
    
    private float idealSwish(float x){
        return (float) (x * idealSigmoid(x));
    }

    @Test
    public void testCreateTanH() { 
        Activation tanhParameters = ActivationFactory.createTanH();
        double averageError = 0;
        double maximumError = 0;
        int n = 0;
        System.out.println("TanH:");
        for(float x = MINIMUM; x <= MAXIMUM; x += STEP){
            double error = Math.abs(idealTanh(x)-ActivationLogic.activate(x, tanhParameters));
            averageError += error;
            maximumError = Math.max(maximumError, error);
            n++;
            
            logResultBetween(x, -1, 1, error);
        }
        averageError /= n;               
        
        errorLogger("TanH: ", averageError);
        
        assertTrue("TanH average difference", averageError < MAXIMUM_AVERAGE_ERROR);
        assertTrue("TanH maximum difference", maximumError < MAXIMUM_ERROR);
        
        float minimum = -Float.MAX_VALUE;
        float maximum = Float.MAX_VALUE;
//        assertEquals("TanH minimum value", ActivationLogic.activate(minimum, tanhParameters), idealTanh(minimum), MAXIMUM_ERROR);
//        assertEquals("TanH maximum value", ActivationLogic.activate(maximum, tanhParameters), idealTanh(maximum), MAXIMUM_ERROR);
    }
    
    private float idealTanh(float x){
        return (float) Math.tanh(x);
    }

    @Test
    public void testCreateReLu() {
        Activation ReLuParameters = ActivationFactory.createReLu();
        double averageError = 0;
        double maximumError = 0;
        int n = 0;
        System.out.println("ReLu:");
        for(float x = MINIMUM; x <= MAXIMUM; x += STEP){
            double error = Math.abs(idealReLu(x)-ActivationLogic.activate(x, ReLuParameters));
            averageError += error;
            maximumError = Math.max(maximumError, error);              
            n++;         
            logResultBetween(x, -1, 1, error);
        }
        averageError /= n;
        
        errorLogger("ReLu: ", averageError);
    
        
        assertTrue("ReLu average difference", averageError < MAXIMUM_AVERAGE_ERROR);
        assertTrue("ReLu maximum difference", maximumError < MAXIMUM_ERROR);
        
        float minimum = -Float.MAX_VALUE;
        float maximum = Float.MAX_VALUE;
//        assertEquals("ReLu minimum value", ActivationLogic.activate(minimum, ReLuParameters), idealReLu(minimum), MAXIMUM_ERROR);
//        assertEquals("ReLu maximum value", ActivationLogic.activate(maximum, ReLuParameters), idealReLu(maximum), MAXIMUM_ERROR);       
        errorLogger("ReLu: ", averageError);
    }
    
    private float idealReLu(float x){
        return (float) Math.max(0, x);
    }

    @Test
    public void testCreateLeakyReLu() {
        Activation LeakyReLuParameters = ActivationFactory.createLeakyReLu();
        double averageError = 0;
        double maximumError = 0;
        int n = 0;
        System.out.println("LeakyReLu:");
        for(float x = MINIMUM; x <= MAXIMUM; x += STEP){
            double error = Math.abs(idealLeakyReLu(x)-ActivationLogic.activate(x, LeakyReLuParameters));
            averageError += error;
            maximumError = Math.max(maximumError, error);              
            n++;         
            logResultBetween(x, -1, 1, error);
        }
        averageError /= n;
        
        errorLogger("LeakyReLu: ", averageError);
    
        
        assertTrue("LeakyReLu average difference", averageError < MAXIMUM_AVERAGE_ERROR);
        assertTrue("LeakyReLu maximum difference", maximumError < MAXIMUM_ERROR);
        
        float minimum = -Float.MAX_VALUE;
        float maximum = Float.MAX_VALUE;
//        assertEquals("LeakyReLu minimum value", ActivationLogic.activate(minimum, LeakyReLuParameters), idealLeakyReLu(minimum), MAXIMUM_ERROR);
//        assertEquals("LeakyReLu maximum value", ActivationLogic.activate(maximum, LeakyReLuParameters), idealLeakyReLu(maximum), MAXIMUM_ERROR);
        
        errorLogger("LeakyReLu: ", averageError);
    }
    
    private float idealLeakyReLu(float x){
        return (float) Math.max(LEAKYRELUVALUE*x, x);
    }

    @Test
    public void testCreateStep() {
        Activation StepParameters = ActivationFactory.createStep();
        double averageError = 0;
        double maximumError = 0;
        int n = 0;
        System.out.println("Step:");
        for(float x = MINIMUM; x <= MAXIMUM; x += STEP){
            double error = Math.abs(idealStep(x)-ActivationLogic.activate(x, StepParameters));
            averageError += error;
            maximumError = Math.max(maximumError, error);              
            n++;         
            logResultBetween(x, -1, 1, error);
        }
        averageError /= n;
        
        errorLogger("Step: ", averageError);
    
        
        assertTrue("Step average difference", averageError < MAXIMUM_AVERAGE_ERROR);
        assertTrue("Step maximum difference", maximumError < MAXIMUM_ERROR);
        
        float minimum = -Float.MAX_VALUE;
        float maximum = Float.MAX_VALUE;
//        assertEquals("Step minimum value", ActivationLogic.activate(minimum, StepParameters), idealStep(minimum), MAXIMUM_ERROR);
//        assertEquals("Step maximum value", ActivationLogic.activate(maximum, StepParameters), idealStep(maximum), MAXIMUM_ERROR);
        
        errorLogger("Step: ", averageError);
    }
    
    public float idealStep(float x){
        if(x>0) { 
            return 1; }
        else {
            return 0; }
    }

    @Test
    public void testCreateLinear() {
        Activation LinearParameters = ActivationFactory.createLinear();
        double averageError = 0;
        double maximumError = 0;
        int n = 0;
        System.out.println("Linear:");
        for(float x = MINIMUM; x <= MAXIMUM; x += STEP){
            double error = Math.abs(idealLinear(x)-ActivationLogic.activate(x, LinearParameters));
            System.out.println(x + ";" +idealLinear(x) + ";" + ActivationLogic.activate(x, LinearParameters));
            averageError += error;
            maximumError = Math.max(maximumError, error);              
            n++;         
            //logResultBetween(x, -1, 1, error);
        }
        averageError /= n;
        
        errorLogger("Linear: ", averageError);
    
        
        assertTrue("Linear average difference", averageError < MAXIMUM_AVERAGE_ERROR);
        assertTrue("Linear maximum difference", maximumError < MAXIMUM_ERROR);
        
        float minimum = -Float.MAX_VALUE;
        float maximum = Float.MAX_VALUE;
        //assertEquals("Linear minimum value", ActivationLogic.activate(minimum, LinearParameters), idealLinear(minimum), MAXIMUM_ERROR);
//        assertEquals("Linear maximum value", ActivationLogic.activate(maximum, LinearParameters), idealLinear(maximum), MAXIMUM_ERROR);
        
        errorLogger("Linear: ", averageError);
    }
    
    //kell ez?
    public float idealLinear(float x){
        return x;
    }

    
    //LOG AVG AND MAX AVG ERROR    
    private void errorLogger(String functionName,double avgErr){
        System.out.println(functionName + "Average error: " + avgErr);
        System.out.println("Max  average error: " + MAXIMUM_AVERAGE_ERROR);    
        System.out.println("");
    }
    
    //LOG EVERY STEP BETWEEN MIN AND MAX WITH ERROR VALUE
    private void logResultBetween(float x,float min,float max, double error){
                if(min < x && x < max){ 
                System.out.println("for x = " + x + " error: " + error);
            }
    }
}
