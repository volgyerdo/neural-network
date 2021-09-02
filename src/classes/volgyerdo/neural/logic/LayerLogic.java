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

import volgyerdo.commons.primitive.ArrayUtils;
import volgyerdo.commons.primitive.PrimitiveUtils;
import volgyerdo.commons.math.tensor.IndexIterator;
import volgyerdo.commons.math.tensor.Tensor;
import volgyerdo.neural.structure.Activation;
import volgyerdo.neural.structure.ConvolutionalLayer;
import volgyerdo.neural.structure.DenseLayer;
import volgyerdo.neural.structure.GraphLayer;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Link;
import volgyerdo.neural.structure.Neuron;

/**
 *
 * @author antal
 */
public class LayerLogic {

    public static void randomize(Layer layer) {
        if (layer instanceof DenseLayer) {
            randomize((DenseLayer) layer);
        } else if (layer instanceof ConvolutionalLayer) {
            randomize((ConvolutionalLayer) layer);
        } else if (layer instanceof GraphLayer) {
            randomize((GraphLayer) layer);
        }
    }

    private static void randomize(DenseLayer layer) {
        NetworkUtils.randomizeWeigths(layer.weights);
        NetworkUtils.randomizeBias(layer.bias);
    }

    private static void randomize(ConvolutionalLayer layer) {
        NetworkUtils.randomizeWeigths(layer.kernel);
        layer.bias = NetworkUtils.randomizeBias();
    }

    private static void randomize(GraphLayer layer) {
        NetworkUtils.randomizeWeigths(layer.links);
        NetworkUtils.randomizeBias(layer.biases);
    }

    public static void propagate(Layer prevLayer, Layer layer) {
        if (layer instanceof DenseLayer) {
            propagate(prevLayer, (DenseLayer) layer);
        } else if (layer instanceof ConvolutionalLayer) {
            propagate(prevLayer, (ConvolutionalLayer) layer);
        } else if (layer instanceof GraphLayer) {
            propagate(prevLayer, (GraphLayer) layer);
        }
    }

    private static void propagate(Layer prevLayer, DenseLayer layer) {
        Tensor weights = layer.weights.copy();

        Tensor outputStates = weights.multiply(prevLayer.states, prevLayer.states.dimensions.length);

        outputStates.add(layer.bias);
        ActivationLogic.activate(outputStates, layer.activations);

        layer.states = outputStates;
    }

    private static void propagate(Layer prevLayer, ConvolutionalLayer layer) {
        Tensor kernel = layer.kernel.copy();

        Tensor outputStates = prevLayer.states.convolve(kernel);
        outputStates.multiply(layer.bias);

        ActivationLogic.activate(outputStates, layer.activations);

        layer.states = outputStates;
    }

    private static void propagate(Layer prevLayer, GraphLayer layer) {
        Tensor inputStates = prevLayer.states;
        IndexIterator inputIterator = inputStates.indexIterator();
        int[] inputIds = layer.inputIds;
        int inputId = 0;
        Neuron[] neurons = layer.neurons;
        Link[] links = layer.links;
        while (inputIterator.hasNext() && inputId < inputIds.length) {
            layer.neurons[inputId].state = inputStates.getFloatValue(inputIterator.next());
            inputId++;
        }
        float[] output = new float[layer.outputIds.length];
        for (int i = 0; i < layer.neurons.length; i++) {
            float[] newStates = new float[layer.neurons.length];
            Link link;
            for (int neuronId = 0; neuronId < neurons.length; neuronId++) {
                for (int linkId = 0; linkId < links.length; linkId++) {
                    link = links[linkId];
                    if (link.outputId == neuronId) {
                        newStates[neuronId] += link.weight * neurons[link.inputId].state;
                    }
                }
            }
            for (int neuronId = 0; neuronId < neurons.length; neuronId++) {
                newStates[neuronId] += layer.biases[neuronId];
                neurons[neuronId].state = ActivationLogic.activate(newStates[neuronId], neurons[neuronId].activation);
            }
            for (int outputId = 0; outputId < layer.outputIds.length; outputId++) {
                output[outputId] += neurons[layer.outputIds[outputId]].state;
            }
        }
        for (int outputId = 0; outputId < layer.outputIds.length; outputId++) {
            output[outputId] /= layer.neurons.length;
            neurons[layer.outputIds[outputId]].state = output[outputId];
        }

    }

    public static Tensor backPropagate(Layer layer, Layer prevLayer, Tensor delta) {
        if (layer instanceof DenseLayer) {
            return backPropagate((DenseLayer) layer, prevLayer, delta);
        } else if (layer instanceof ConvolutionalLayer) {
            return backPropagate((ConvolutionalLayer) layer, prevLayer, delta);
        } else if (layer instanceof GraphLayer) {
            return backPropagate((GraphLayer) layer, prevLayer, delta);
        }
        return null;
    }

    private static Tensor backPropagate(DenseLayer layer, Layer prevLayer, Tensor delta) {
        ActivationLogic.deactivate(layer.states, layer.activations);
        delta.hadamardProduct(layer.states);

        Tensor deltaW = delta.multiply(prevLayer.states, 0);
        deltaW.hadamardProduct(layer.weightsLearningRates);

        layer.weights.add(deltaW);

        Tensor deltaBias = delta.copy();
        deltaBias.hadamardProduct(layer.biasLearningRates);
        layer.bias.add(deltaBias);

        return delta.multiply(layer.weights, layer.states.dimensions.length);
    }

    private static Tensor backPropagate(ConvolutionalLayer layer, Layer prevLayer, Tensor delta) {
        ActivationLogic.deactivate(layer.states, layer.activations);
        delta.hadamardProduct(layer.states);

        Tensor deltaKernel = prevLayer.states.convolvePartial(delta, layer.kernel.dimensions);
        deltaKernel.hadamardProduct(layer.kernelLearningRates);

        layer.kernel.add(deltaKernel);

        float deltaBias = delta.floatSum();
        deltaBias *= layer.biasLearningRate;
        layer.bias += deltaBias;

        return delta.convolve(layer.kernel);
    }

    private static Tensor backPropagate(GraphLayer layer, Layer prevLayer, Tensor delta) {
        Neuron[] neurons = layer.neurons;
        Link[] links = layer.links;
        float[] biases = layer.biases;
        float[] localErrors = new float[neurons.length];
        IndexIterator deltaIterator = delta.indexIterator();
        int[] outputIds = layer.outputIds;
        int outputId = 0;
        while (deltaIterator.hasNext() && outputId < outputIds.length) {
            localErrors[outputId] = delta.getFloatValue(deltaIterator.next());
            outputId++;
        }
        Neuron neuron;
        for (int neuronId = 0; neuronId < neurons.length; neuronId++) {
            neuron = neurons[neuronId];
            float localError = 0;
            for (int linkId = 0; linkId < links.length; linkId++) {
                Link link = links[linkId];
                if (link.inputId == neuronId) {
                    localError += link.weight * localErrors[link.outputId];
                }
            }
            localErrors[neuronId]
                    = localError * ActivationLogic.deactivate(neuron.state,
                            neuron.activation);
        }
        for(Link link : links){
            link.weight += localErrors[link.outputId] * neurons[link.inputId].state * link.learningRate;
            biases[link.outputId] += localErrors[link.outputId] * link.learningRate;
        }
        Tensor newDelta = prevLayer.states.createSimilar();
        int[] inputIds = layer.inputIds;
        int inputId = 0;
        while (deltaIterator.hasNext() && inputId < inputIds.length) {
            newDelta.setFloatValue(localErrors[inputIds[inputId]], deltaIterator.next());
        }
        return newDelta;
    }

    public static void setLearningRate(Layer layer, float learningRate) {
        if (layer instanceof DenseLayer) {
            ((DenseLayer) layer).weightsLearningRates.fill(learningRate);
            ((DenseLayer) layer).biasLearningRates.fill(learningRate);
        } else if (layer instanceof ConvolutionalLayer) {
            ((ConvolutionalLayer) layer).kernelLearningRates.fill(learningRate);
            ((ConvolutionalLayer) layer).biasLearningRate = learningRate;
        } else if (layer instanceof GraphLayer) {
            for(Link link : ((GraphLayer) layer).links){
                link.learningRate = learningRate;
            }
        }
    }

    public static void setActivation(Layer layer, Activation activation) {
        if (layer instanceof DenseLayer) {
            ((DenseLayer) layer).activations.fillWithObject(() -> ActivationFactory.createCopy(activation));
        } else if (layer instanceof ConvolutionalLayer) {
            ((ConvolutionalLayer) layer).activations.fillWithObject(() -> ActivationFactory.createCopy(activation));
        }
    }
}
