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

import java.text.DecimalFormat;
import volgyerdo.math.tensor.IndexIterator;
import volgyerdo.math.tensor.Tensor;
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
    }

    private static void randomize(ConvolutionalLayer layer) {
        NetworkUtils.randomizeWeigths(layer.kernel);
    }

    private static void randomize(GraphLayer layer) {
        NetworkUtils.randomizeWeigths(layer.links);
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
        outputStates = outputStates.convolve(layer.bias);

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
//        DecimalFormat format = new DecimalFormat("0.0000");
//        for (int outputId = 0; outputId < layer.outputIds.length; outputId++) {
//            System.out.print(format.format(output[outputId]) + ";");
//        }
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
        deltaW.multiply(layer.learningRate);

        layer.weights.add(deltaW);

        Tensor deltaBias = delta.copy();
        deltaBias.multiply(layer.learningRate);
        layer.bias.add(deltaBias);

        return delta.multiply(layer.weights, layer.states.dimensions.length);
    }

    private static Tensor backPropagate(ConvolutionalLayer layer, Layer prevLayer, Tensor delta) {
        ActivationLogic.deactivate(layer.states, layer.activations);
        delta.hadamardProduct(layer.states);

        Tensor deltaKernel = prevLayer.states.convolvePartial(delta, layer.kernel.dimensions);
        deltaKernel.multiply(layer.learningRate);

        layer.kernel.add(deltaKernel);

        Tensor deltaBias = delta.sum();
        deltaBias.multiply(layer.learningRate);
        layer.bias.add(deltaBias);

        return delta.convolve(layer.kernel);
    }

    private static Tensor backPropagate(GraphLayer layer, Layer prevLayer, Tensor delta) {
        Neuron[] neurons = layer.neurons;
        Link[] links = layer.links;
        float[] localErrors = new float[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            IndexIterator deltaIterator = delta.indexIterator();
            int[] outputIds = layer.outputIds;
            int outputId = 0;
            while (deltaIterator.hasNext() && outputId < outputIds.length) {
                localErrors[outputId] = delta.getFloatValue(deltaIterator.next());
                outputId++;
            }
            Link link;
            Neuron neuron;
            for (int neuronId = 0; neuronId < neurons.length; neuronId++) {
                neuron = neurons[neuronId];
                float localError = 0;
                for (int linkId = 0; linkId < links.length; linkId++) {
                    link = links[linkId];
                    if (link.inputId == neuronId) {
                        localError += link.weight * localErrors[link.outputId];
                    }
                    localErrors[neuronId]
                            = localError * ActivationLogic.deactivate(neuron.state,
                                    neuron.activation);
                }
            }
        }
        Tensor newDelta = prevLayer.states.createSimilar();
        IndexIterator deltaIterator = prevLayer.states.indexIterator();
        int[] inputIds = layer.inputIds;
        int inputId = 0;
        while (deltaIterator.hasNext() && inputId < inputIds.length) {
            newDelta.setFloatValue(localErrors[inputIds[inputId]], deltaIterator.next());
        }
        return newDelta;
    }

    public static void setLearningRate(Layer layer, float learningRate) {
        if (layer instanceof DenseLayer) {
            ((DenseLayer) layer).learningRate = learningRate;
        } else if (layer instanceof ConvolutionalLayer) {
            ((ConvolutionalLayer) layer).learningRate = learningRate;
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
