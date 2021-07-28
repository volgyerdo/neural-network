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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.Activation;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Sample;
import volgyerdo.neural.structure.TestResults;
import volgyerdo.neural.structure.TestType;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class NetworkLogic {

    private NetworkLogic() {
    }

    public static void randomizeWeights(Network network) {
        for (Layer layer : network.layers) {
            LayerLogic.randomize(layer);
        }
    }

    public static void setLearningRate(Network network, float learningRate) {
        for (Layer layer : network.layers) {
            LayerLogic.setLearningRate(layer, learningRate);
        }
    }

    public static void setActivation(Network network, Activation activation) {
        for (Layer layer : network.layers) {
            LayerLogic.setActivation(layer, activation);
        }
    }

    public static void train(Network network, List<Sample> samples, int periods) {
        checkSamples(network, samples);
        for (int cycle = 0; cycle < periods; cycle++) {
            Sample sample = getRandomSample(samples);
            train(network, sample);
        }
    }

    public static void train(Network network, List<Sample> samples) {
        checkSamples(network, samples);
        do {
            Sample sample = getRandomSample(samples);
            train(network, sample);
        } while (NetworkUtils.getTrainingCycle(network) < 1000);
    }

    public static void train(Network network, Sample sample) {
        long startTime = System.currentTimeMillis();
        NetworkLogic.propagate(network, sample.input);
        NetworkLogic.backPropagate(network, sample.target);
        storeTestData(network, sample, TestType.TRAIN,
                System.currentTimeMillis(), System.currentTimeMillis() - startTime);
        sample.lastTrainCycle = network.testData.errors.size() - 1;
    }

    private static Sample getRandomSample(List<Sample> samples) {
        int randomSampleNumber = (int) (Math.random() * samples.size());
        return samples.get(randomSampleNumber);
    }

    private static void checkSamples(Network network, Collection<Sample> samples) {
        Layer inputLayer = NetworkUtils.getInputLayer(network);
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        for (Sample sample : samples) {
            if (!Arrays.equals(sample.input.dimensions, inputLayer.states.dimensions)) {
                throw new IllegalArgumentException("Input dimension is wrong.");
            }
            if (!Arrays.equals(sample.target.dimensions, outputLayer.states.dimensions)) {
                throw new IllegalArgumentException("Target dimension is wrong.");
            }
        }
    }

    public static void propagate(Network network, Tensor input) {
        NetworkUtils.getInputLayer(network).states = input;
        for (int i = 0; i < network.layers.size() - 1; i++) {
            Layer inputLayer = network.layers.get(i);
            Layer outputLayer = network.layers.get(i + 1);
            LayerLogic.propagate(inputLayer, outputLayer);
        }
    }

    public static void backPropagate(Network network, Tensor target) {
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        Tensor actualOutput = outputLayer.states.copy();
        Tensor delta = target.copy();
        delta.substract(actualOutput);
        for (int i = network.layers.size() - 1; i > 0; i--) {
            Layer layer = network.layers.get(i);
            Layer nextLayer = network.layers.get(i - 1);
            delta = LayerLogic.backPropagate(layer, nextLayer, delta);
        }
    }

    public static TestResults test(Network network, Collection<Sample> samples) {
        long startTime = System.nanoTime();
        TestResults results = new TestResults();
        results.sampleCount = samples.size();
        for (Sample sample : samples) {
            propagate(network, sample.input);
            storeTestData(network, sample, TestType.TEST, 
                    System.currentTimeMillis(), System.currentTimeMillis() - startTime);
        }
        return results;
    }

    private static void storeTestData(Network network, Sample sample, TestType type, long timestamp, long time) {
        Tensor errors = sample.target.copy();
        errors.substract(NetworkUtils.getOutputLayer(network).states);
        errors.abs();
        network.testData.errors.add(errors.floatAverage());
        network.testData.testTypes.add(type);
        network.testData.timestamps.add(timestamp);
        network.testData.runTimes.add((int) time);
    }

}
