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

    private static final float MINIMUM = -100;
    private static final float MAXIMUM = 100;
    private static final float STEP = 0.1f;
    
    private static final double MAXIMUM_ERROR = 0.01;
    private static final double MAXIMUM_AVERAGE_ERROR = 0.0001;
    
    
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
        assertEquals("Sigmoid minimum value", ActivationLogic.activate(minimum, sigmoidParameters));
        assertEquals("Sigmoid maximum value", ActivationLogic.activate(maximum, sigmoidParameters));
    }

    private float idealSigmoid(float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    @Test
    public void testCreateSwish() {
    }

    @Test
    public void testCreateTanH() {
    }

    @Test
    public void testCreateReLu() {
    }

    @Test
    public void testCreateLeakyReLu() {
    }

    @Test
    public void testCreateStep() {
    }

    @Test
    public void testCreateLinear() {
    }

}
