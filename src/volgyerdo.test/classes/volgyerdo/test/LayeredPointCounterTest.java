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
package volgyerdo.test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.logic.ActivationFactory;
import volgyerdo.neural.logic.LayerFactory;
import volgyerdo.neural.logic.NetworkFactory;
import volgyerdo.neural.logic.NetworkLogic;
import volgyerdo.neural.logic.NetworkUtils;
import volgyerdo.neural.logic.SampleFactory;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Sample;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class LayeredPointCounterTest {

    private static final DecimalFormat FORMAT = new DecimalFormat("0.000");
    private static final Random RANDOM_INT = new Random();
    private static final int MATRIX_SIZE = 5;
    private static final int HIDDEN_LAYER_SIZE1 = 15;
    private static final int MAX_POINT_COUNT = 5;
    private static final int TRAINING_SAMPLE_COUNT = 400;
    private static final int CONTROL_SAMPLE_COUNT = 400;

    public static void main(String[] args) {
        Network network = NetworkFactory.createNetwork();

        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, MATRIX_SIZE, MATRIX_SIZE));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, HIDDEN_LAYER_SIZE1, HIDDEN_LAYER_SIZE1));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, MAX_POINT_COUNT + 1));

        NetworkLogic.setLearningRate(network, 0.01f);
        NetworkLogic.setActivation(network, ActivationFactory.createSigmoid());
        NetworkLogic.randomizeWeights(network);

        List<Sample> trainingSamples = new ArrayList<>();
        generateSamples(trainingSamples, TRAINING_SAMPLE_COUNT);
        
        System.out.println("\nBefore training:\n");
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        float averageError = 0;
        int matches = 0;
        for (Sample sample : trainingSamples) {
            NetworkLogic.propagate(network, sample.input);
            float[] errors = new float[MAX_POINT_COUNT + 1];
            System.out.print("Error: ");
            float resultValue = 0;
            int resultIndex = 0;
            for (int i = 0; i < MAX_POINT_COUNT + 1; i++) {
                if(resultValue < outputLayer.states.getFloatValue(i)){
                    resultValue = outputLayer.states.getFloatValue(i);
                    resultIndex = i;
                }
                errors[i] = Math.abs(sample.target.getFloatValue(i) - outputLayer.states.getFloatValue(i));
                averageError += errors[i];
                if (i > 0) {
                    System.out.print(",");
                }
                System.out.print(FORMAT.format(errors[i]));
            }
            if(Float.compare(sample.target.getFloatValue(resultIndex), 1) == 0){
                System.out.print(" > OK");
                matches++;
            }
            System.out.println();
        }
        averageError /= trainingSamples.size() * (MAX_POINT_COUNT + 1);
        System.out.println("Average error: " + FORMAT.format(averageError));
        System.out.println("Average match: " + FORMAT.format(matches/(double)trainingSamples.size()) + "\n");

        NetworkLogic.fit(network, trainingSamples, 100000);

        System.out.println("\nAfter training:\n");
        averageError = 0;
        matches = 0;
        for (Sample sample : trainingSamples) {
            NetworkLogic.propagate(network, sample.input);
            float[] errors = new float[MAX_POINT_COUNT + 1];
            System.out.print("Error: ");
            float resultValue = 0;
            int resultIndex = 0;
            for (int i = 0; i < MAX_POINT_COUNT + 1; i++) {
                if(resultValue < outputLayer.states.getFloatValue(i)){
                    resultValue = outputLayer.states.getFloatValue(i);
                    resultIndex = i;
                }
                errors[i] = Math.abs(sample.target.getFloatValue(i) - outputLayer.states.getFloatValue(i));
                averageError += errors[i];
                if (i > 0) {
                    System.out.print(",");
                }
                System.out.print(FORMAT.format(errors[i]));
            }
            if(Float.compare(sample.target.getFloatValue(resultIndex), 1) == 0){
                System.out.print(" > OK");
                matches++;
            }
            System.out.println();
        }
        averageError /= trainingSamples.size() * (MAX_POINT_COUNT + 1);
        System.out.println("Average error: " + FORMAT.format(averageError));
        System.out.println("Average match: " + FORMAT.format(matches/(double)trainingSamples.size()) + "\n");

        List<Sample> controlSamples = new ArrayList<>();
        generateSamples(controlSamples, CONTROL_SAMPLE_COUNT);
        
        System.out.println("\nControl:\n");
        averageError = 0;
        matches = 0;
        for (Sample sample : controlSamples) {
            NetworkLogic.propagate(network, sample.input);
            float[] errors = new float[MAX_POINT_COUNT + 1];
            System.out.print("Error: ");
            float resultValue = 0;
            int resultIndex = 0;
            for (int i = 0; i < MAX_POINT_COUNT + 1; i++) {
                if(resultValue < outputLayer.states.getFloatValue(i)){
                    resultValue = outputLayer.states.getFloatValue(i);
                    resultIndex = i;
                }
                errors[i] = Math.abs(sample.target.getFloatValue(i) - outputLayer.states.getFloatValue(i));
                averageError += errors[i];
                if (i > 0) {
                    System.out.print(",");
                }
                System.out.print(FORMAT.format(errors[i]));
            }
            if(Float.compare(sample.target.getFloatValue(resultIndex), 1) == 0){
                System.out.print(" > OK");
                matches++;
            }
            System.out.println();
        }
        averageError /= controlSamples.size() * (MAX_POINT_COUNT + 1);
        System.out.println("Average error: " + FORMAT.format(averageError));
        System.out.println("Average match: " + FORMAT.format(matches/(double)trainingSamples.size()) + "\n");
    }

    private static void generateSamples(Collection<Sample> samples, int count) {
        for (int i = 0; i < count; i++) {
            samples.add(createPointedSample());
        }
    }

    private static Sample createPointedSample() {
        int pointCount = RANDOM_INT.nextInt(MAX_POINT_COUNT + 1);
        Tensor input = createPointedTensor(pointCount, MATRIX_SIZE, MATRIX_SIZE);
        Tensor target = Tensor.create(Tensor.TYPE.FLOAT, MAX_POINT_COUNT + 1);
        target.setFloatValue(1, pointCount);
        return SampleFactory.createSample(input, target);
    }

    private static Tensor createPointedTensor(int pointCount, int... dimensions) {
        Tensor tensor = Tensor.create(Tensor.TYPE.FLOAT, dimensions);
        for (int i = 0; i < pointCount; i++) {
            int[] coordinates;
            do {
                coordinates = NetworkUtils.randomizeCoordinates(dimensions);
            } while (Float.compare(1.0f, tensor.getFloatValue(coordinates)) == 0);
            tensor.setFloatValue(1, coordinates);
        }
        return tensor;
    }

}
