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

    private static final float MAXIMUM = 1000000;
    private static final float START_STEP = 0.1f;

    private static final double PROPORTIONAL_BORDER = 1;

    private static final double MAXIMUM_ERROR = 2;
    private static final double MAXIMUM_AVERAGE_ERROR = 0.5;

    private static final DecimalFormat FORMATTER = new DecimalFormat("0.0000");

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

    private float idealTanh(float x) {
        return (float) Math.tanh(x);
    }

    @Test
    public void testCreateReLu() {
        Activation tanhParameters = ActivationFactory.createReLu();
        testActivationFunction("ReLu", tanhParameters, (x) -> idealReLu(x));
    }

    private float idealReLu(float x) {
        return (float) Math.max(0, x);
    }

    @Test
    public void testCreateLeakyReLu() {
        Activation leakyReLuParameters = ActivationFactory.createLeakyReLu();
        testActivationFunction("Leaky ReLu", leakyReLuParameters, (x) -> idealLeakyReLu(x));
    }

    private float idealLeakyReLu(float x) {
        return (float) Math.max(0.01 * x, x);
    }

    @Test
    public void testCreateStep() {
        Activation stepParameters = ActivationFactory.createStep();
        testActivationFunction("Step", stepParameters, (x) -> idealStep(x));
    }

    public float idealStep(float x) {
        return x <= 0 ? 0 : 1;
    }

    @Test
    public void testCreateLinear() {
        Activation linearParameters = ActivationFactory.createLinear();
        testActivationFunction("Linear", linearParameters, (x) -> idealLinear(x));
    }

    public float idealLinear(float x) {
        return x;
    }

    private void testActivationFunction(String functionName, Activation parameters, IdealActivationFunction idealFunction) {

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

        double testedValue, idealValue, error, proportionalError, aggregatedError;

        double averageError = 0;
        double avrProportionalError = 0;
        double maximumError = 0;
        double maximumProportionalError = 0;
        double aggregatedMaxError = 0;
        double aggregatedAvrError = 0;
        int n = 0;

        for (float x : inputValues) {
            testedValue = ActivationLogic.activate(x, parameters);
            idealValue = idealFunction.activate(x);
            error = Math.abs(idealValue - testedValue);
            proportionalError = Math.abs(idealValue < PROPORTIONAL_BORDER ? 0 : testedValue / idealValue);
            aggregatedError = Math.min(error, proportionalError);
            System.out.println(
                    functionName + ";"
                    + FORMATTER.format(x) + ";"
                    + FORMATTER.format(testedValue) + ";"
                    + FORMATTER.format(idealValue) + ";"
                    + FORMATTER.format(idealValue - testedValue) + ";"
                    + FORMATTER.format(proportionalError));
            averageError += error;
            avrProportionalError += proportionalError;

            aggregatedAvrError += aggregatedError;

            maximumError = Math.max(maximumError, error);
            maximumProportionalError = Math.max(maximumProportionalError, proportionalError);

            aggregatedMaxError = Math.max(aggregatedMaxError, aggregatedError);

            n++;
        }

        averageError /= n;
        avrProportionalError /= n;
        aggregatedAvrError /= n;

        System.out.println(functionName + " averageError: " + FORMATTER.format(averageError));
        System.out.println(functionName + " maximumError: " + FORMATTER.format(maximumError));
        System.out.println(functionName + " averageProportioanlError: " + FORMATTER.format(avrProportionalError));
        System.out.println(functionName + " maximumProportioanlError: " + FORMATTER.format(maximumProportionalError));
        System.out.println(functionName + " averageAggregatedError: " + FORMATTER.format(aggregatedAvrError));
        System.out.println(functionName + " maximumAggregatedError: " + FORMATTER.format(aggregatedMaxError));

        assertTrue(functionName + " average error", aggregatedAvrError < MAXIMUM_AVERAGE_ERROR);
        assertTrue(functionName + " maximum error", aggregatedMaxError < MAXIMUM_ERROR);

    }

    private interface IdealActivationFunction {

        public float activate(float x);

    }
}
