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
import java.util.Comparator;
import volgyerdo.math.fast.FastMath;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.Activation;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Sample;
import volgyerdo.neural.structure.TestResults;
import volgyerdo.neural.structure.TrainResults;
import volgyerdo.neural.structure.TrainSample;

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

    public static void train(Network network, Collection<Sample> samples, int periods) {
        Sample[] sampleArray = (Sample[]) samples.toArray(new Sample[samples.size()]);
        checkSamples(network, sampleArray);
        for (int i = 0; i < periods; i++) {
            int pairNumber = (int) (Math.random() * sampleArray.length);
            Sample sample = sampleArray[pairNumber];
            NetworkLogic.propagate(network, sample.input);
            NetworkLogic.backPropagate(network, sample.target);
        }
    }

    public static void train(Network network, Collection<Sample> samples) {
        Sample[] sampleArray = (Sample[]) samples.toArray(new Sample[samples.size()]);
        checkSamples(network, sampleArray);
        TrainSample[] trainSamples = createTrainSamples(sampleArray);
        TrainResults results = new TrainResults();
        int cycle = 1;
        do {
            TrainSample trainSample = getOldRandomSample(trainSamples);
            trainSample.cycle = cycle;
            NetworkLogic.propagate(network, trainSample.sample.input);
            Tensor errors = trainSample.sample.target.copy();
            errors.substract(NetworkUtils.getOutputLayer(network).states);
            errors.abs();
            double error = errors.floatAverage();
            double errorDelta = error - results.error;
            results.cycle = cycle;
            results.errorSum += error;
            results.errorDeltaSum += errorDelta;
            results.error = error;
            results.errorDelta = errorDelta;
//            System.out.println(trainSample.sample.input + ";" + trainSample.sample.target + ";"
//                    + results.error + ";" + results.errorDelta + ";"
//                    + (results.errorSum / results.cycle) + ";" + (results.errorDeltaSum / results.cycle));
            NetworkLogic.backPropagate(network, trainSample.sample.target);
            cycle++;
        } while (cycle < samples.size()
                || !(results.error < 0.1 &&
                results.errorDeltaSum / results.cycle < 0.00001));
        System.out.println("CYCLES: " + cycle);
    }

    private static TrainSample getOldRandomSample(TrainSample[] sampleArray) {
        Arrays.sort(sampleArray, new TrainSampleComparator());
        int index = (int) (Math.random() * sampleArray.length / 2);
        return sampleArray[index];
    }

    private static class TrainSampleComparator implements Comparator<TrainSample> {

        @Override
        public int compare(TrainSample o1, TrainSample o2) {
            return o1.cycle - o2.cycle;
        }

    }

    private static TrainSample[] createTrainSamples(Sample[] samples) {
        TrainSample[] trainSamples = new TrainSample[samples.length];
        for (int i = 0; i < samples.length; i++) {
            TrainSample trainSample = new TrainSample();
            trainSample.sample = samples[i];
            trainSamples[i] = trainSample;
        }
        return trainSamples;
    }

    private static void checkSamples(Network network, Sample[] samples) {
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
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        int[] outputLayerSize = outputLayer.states.dimensions; //? 
        Tensor errorTensor = Tensor.create(Tensor.TYPE.FLOAT, outputLayerSize); //típus!
        Tensor averageErrorTensor = Tensor.create(Tensor.TYPE.FLOAT, outputLayerSize);
        float maxError = 0;
        float minError = 10;
        results.sampleCount = samples.size();
        for (Sample sample : samples) {
            propagate(network, sample.input);
            errorTensor.set(outputLayer.states);
            errorTensor.substract(sample.target);
            errorTensor.abs();
            averageErrorTensor.add(errorTensor);
            if (errorTensor.floatMax() > maxError) {
                maxError = errorTensor.floatMax();
            }
            if (errorTensor.floatMin() < minError) {
                minError = errorTensor.floatMin();
            }
        }
        averageErrorTensor.divide(results.sampleCount);
        results.avgError = averageErrorTensor.floatAverage();
        results.minError = minError;
        results.maxError = maxError;
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        results.runTime = duration / 1000000; //divide by 1000000 to get milliseconds.
        return results;
    }

}
