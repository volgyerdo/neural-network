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

import java.util.ArrayList;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.ConvolutionalLayer;
import volgyerdo.neural.structure.DenseLayer;
import volgyerdo.neural.structure.GraphLayer;
import volgyerdo.neural.structure.InputLayer;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class NetworkFactory {

    public static Network createNetwork() {
        Network network = new Network();
        network.layers = new ArrayList<>();
        network.testData = new ArrayList<>();
        return network;
    }

    public static Network createDenseNetwork(
            int[] dimensions,
            int layerCount) {
        Network network = NetworkFactory.createNetwork();
        addInputLayer(network, LayerFactory.createInputLayer(dimensions));
        for (int i = 0; i < layerCount; i++) {
            DenseLayer layer = LayerFactory.createDenseLayer(dimensions);
            addDenseLayer(network, layer);
        }
        return network;
    }

    public static void addDenseLayer(Network network, DenseLayer layer) {
        if (!network.layers.isEmpty()) {
            Layer previousLayer = network.layers.get(network.layers.size() - 1);
            int[] weightsDimensions = new int[previousLayer.states.dimensions.length + layer.states.dimensions.length];
            System.arraycopy(layer.states.dimensions, 0, weightsDimensions, 0, layer.states.dimensions.length);
            System.arraycopy(previousLayer.states.dimensions, 0, weightsDimensions, layer.states.dimensions.length, previousLayer.states.dimensions.length);
            layer.weights = Tensor.create(Tensor.TYPE.FLOAT, weightsDimensions);
            layer.weightsLearningRates = Tensor.create(Tensor.TYPE.FLOAT, weightsDimensions);
            layer.weightsLearningRates.fill(NetworkConstants.DEFAULT_LEARNING_RATE);
            layer.bias = Tensor.create(Tensor.TYPE.FLOAT, layer.states.dimensions);
            layer.biasLearningRates = Tensor.create(Tensor.TYPE.FLOAT, layer.states.dimensions);
            layer.biasLearningRates.fill(NetworkConstants.DEFAULT_LEARNING_RATE);
        }
        network.layers.add(layer);
    }

    public static Network createConvolutionalNetwork(
            int[] dimensions,
            int layerCount) {
        Network network = NetworkFactory.createNetwork();
        addInputLayer(network, LayerFactory.createInputLayer(dimensions));
        for (int i = 0; i < layerCount; i++) {
            ConvolutionalLayer layer = LayerFactory.createConvolutionalLayer(dimensions);
            addConvolutionalLayer(network, layer, dimensions);
        }
        return network;
    }

    public static void addConvolutionalLayer(Network network, ConvolutionalLayer layer, int... kernelDimensions) {
        layer.kernel = Tensor.create(Tensor.TYPE.FLOAT, kernelDimensions);
        layer.kernelLearningRates = Tensor.create(Tensor.TYPE.FLOAT, kernelDimensions);
        layer.kernelLearningRates.fill(NetworkConstants.DEFAULT_LEARNING_RATE);
        layer.biasLearningRate = NetworkConstants.DEFAULT_LEARNING_RATE;
        network.layers.add(layer);
    }

    public static Network createGraphNetwork(int neurons, int inputs, int outputs) {
        Network network = NetworkFactory.createNetwork();
        addInputLayer(network, LayerFactory.createInputLayer(inputs));
        GraphLayer layer = LayerFactory.createGraphLayer(neurons, inputs, outputs);
        network.layers.add(layer);
        return network;
    }

    public static void addInputLayer(Network network, InputLayer layer) {
        network.layers.add(layer);
    }

}
