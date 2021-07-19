/*
 * Copyright 2021 antal.
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

import volgyerdo.math.fast.FastMath;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.ConvolutionalLayer;
import volgyerdo.neural.structure.DenseLayer;
import volgyerdo.neural.structure.GraphLayer;
import volgyerdo.neural.structure.Neuron;
import volgyerdo.neural.structure.Link;

/**
 *
 * @author antal
 */
public class LayerFactory {

    public static DenseLayer createDenseLayer(int... dimensions) {
        DenseLayer layer = new DenseLayer();
        layer.states = Tensor.create(Tensor.TYPE.FLOAT, dimensions);
        layer.activations = ActivationFactory.createDefaultActivations(dimensions);
        layer.learningRate = NetworkConstants.DEFAULT_LEARNING_RATE;
        return layer;
    }

    public static ConvolutionalLayer createConvolutionalLayer(int... dimensions) {
        ConvolutionalLayer layer = new ConvolutionalLayer();
        layer.states = Tensor.create(Tensor.TYPE.FLOAT, dimensions);
        layer.activations = ActivationFactory.createDefaultActivations(dimensions);
        layer.learningRate = NetworkConstants.DEFAULT_LEARNING_RATE;
        return layer;
    }

    public static GraphLayer createGraphLayer(int neuronCount, int[] inputIds, int[] outputIds) {
        GraphLayer graphLayer = new GraphLayer();
        graphLayer.neurons = new Neuron[neuronCount];
        Neuron neuron;
        for (int i = 0; i < neuronCount; i++) {
            neuron = new Neuron();
            neuron.activation = ActivationFactory.createCopy(NetworkConstants.DEFAULT_ACTIVATION);
            graphLayer.neurons[i] = neuron;
        }
        graphLayer.links = new Link[FastMath.pow2(neuronCount)];
        Link link;
        for (int i = 0; i < neuronCount; i++) {
            for (int j = 0; j < neuronCount; j++) {
                link = new Link();
                link.inputId = i;
                link.outputId = j;
                graphLayer.links[i * j + j] = link;
            }
        }
        graphLayer.inputIds = inputIds;
        graphLayer.outputIds = outputIds;
        return graphLayer;
    }

}
