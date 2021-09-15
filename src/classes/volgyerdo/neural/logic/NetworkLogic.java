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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import volgyerdo.commons.math.tensor.Tensor;
import volgyerdo.neural.structure.Activation;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Sample;
import volgyerdo.neural.structure.TestRecord;

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

    public static boolean train(Network network, List<Sample> samples, int periods, double maxError, int iterations) {
        checkSamples(network, samples);
        int testRowLength = samples.size() * 3;
        int n = 0;
        for (int i = 0; i < iterations; i++) {
            NetworkLogic.randomizeWeights(network);
            for (int j = 0; j < periods; j++) {
                n++;
                Sample sample = getRandomSample(samples);
                train(network, sample);
                List<TestRecord> testData = network.testData;
                if (testData.size() >= testRowLength && j % 10 == 0) {
                    if (TestAnalysesLogic.getErrorArithmeticMean(
                            testData.subList(
                                    testData.size() - testRowLength, testData.size()))
                            < maxError / 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void train(Network network, Sample sample) {
        long startTime = System.currentTimeMillis();
        NetworkLogic.propagate(network, sample.input);
        network.testData.add(
                createTestRecord(network, sample,
                        System.currentTimeMillis(), System.currentTimeMillis() - startTime));
        NetworkLogic.backPropagate(network, sample.target);
        sample.lastTrainCycle = network.testData.size() - 1;
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
            Layer prevLayer = network.layers.get(i - 1);
            delta = LayerLogic.backPropagate(layer, prevLayer, delta);
        }
    }

    public static List<TestRecord> test(Network network, Collection<Sample> samples) {
        List<TestRecord> testData = new ArrayList<>();
        for (Sample sample : samples) {
            testData.add(test(network, sample));
        }
        return testData;
    }

    public static TestRecord test(Network network, Sample sample) {
        long startTime = System.currentTimeMillis();
        propagate(network, sample.input);
        return createTestRecord(network, sample,
                System.currentTimeMillis(), System.currentTimeMillis() - startTime);
    }

    private static TestRecord createTestRecord(Network network, Sample sample, long timestamp, long time) {
        Tensor errors = sample.target.copy();
        errors.substract(NetworkUtils.getOutputLayer(network).states);
        errors.abs();
        TestRecord testRecord = new TestRecord();
        testRecord.error = errors.floatAverage();
        testRecord.timestamp = timestamp;
        testRecord.runTime = (int) time;
        return testRecord;
    }

    public static void serializeNetwork(Network network, OutputStream outputStream) throws IOException {
        try(GZIPOutputStream zipStream = new GZIPOutputStream(outputStream);
                ObjectOutputStream objectStream = new ObjectOutputStream(zipStream)){
            objectStream.writeObject(network);
        }
    }

    public static Network deserializeNetwork(InputStream inputStream) throws IOException, ClassNotFoundException {
        try(GZIPInputStream zipStream = new GZIPInputStream(inputStream);
                ObjectInputStream objectStream = new ObjectInputStream(zipStream)){
            return (Network) objectStream.readObject();
        }
    }

}
