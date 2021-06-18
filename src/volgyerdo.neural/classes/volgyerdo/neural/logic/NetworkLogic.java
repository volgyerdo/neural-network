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
import java.util.Objects;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.Activation;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Sample;

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
    
    public static void setLearningRate(Network network, float learningRate){
        for (Layer layer : network.layers) {
            LayerLogic.setLearningRate(layer, learningRate);
        }
    }
    
    public static void setActivation(Network network, Activation activation){
        for (Layer layer : network.layers) {
            LayerLogic.setActivation(layer, activation);
        }
    }

    public static void fit(Network network, Collection<Sample> samples, int periods) {
        Sample[] sampleArray = (Sample[]) samples.toArray(new Sample[samples.size()]);
        checkSamples(network, sampleArray);
        for (int i = 0; i < periods; i++) {
            int pairNumber = (int) (Math.random() * sampleArray.length);
            Sample sample = sampleArray[pairNumber];
            NetworkLogic.propagate(network, sample.input);
            NetworkLogic.backPropagate(network, sample.target);
        }
    }

    private static void checkSamples(Network network, Sample[] samples) {
        Layer inputLayer = NetworkUtils.getInputLayer(network);
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        for (Sample sample : samples) {
            if (!Arrays.equals(sample.input.dimensions, inputLayer.states.dimensions)) {
                throw new IllegalArgumentException("Input dimension is wrong.");
            }
            if (!Objects.equals(sample.input.type, inputLayer.dataType)) {
                throw new IllegalArgumentException("Input data type is wrong.");
            }
            if (!Arrays.equals(sample.target.dimensions, outputLayer.states.dimensions)) {
                throw new IllegalArgumentException("Target dimension is wrong.");
            }
            if (!Objects.equals(sample.target.type, outputLayer.dataType)) {
                throw new IllegalArgumentException("Target data type is wrong.");
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
        Tensor actualOutput = NetworkUtils.converToNormalFloat(outputLayer.states);
        Tensor delta = NetworkUtils.converToNormalFloat(target);
        delta.substract(actualOutput);
        for (int i = network.layers.size() - 1; i > 0; i--) {
            Layer layer = network.layers.get(i);
            Layer nextLayer = network.layers.get(i - 1);
            delta = LayerLogic.backPropagate(layer, nextLayer, delta);
        }
    }

}
