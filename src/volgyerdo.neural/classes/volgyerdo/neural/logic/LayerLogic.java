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

import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.Activation;
import volgyerdo.neural.structure.ConvolutionalLayer;
import volgyerdo.neural.structure.DenseLayer;
import volgyerdo.neural.structure.Layer;

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
        }
    }

    private static void randomize(DenseLayer layer) {
        NetworkUtils.randomizeWeigths(layer.weights);
    }

    private static void randomize(ConvolutionalLayer layer) {
        NetworkUtils.randomizeWeigths(layer.bias);
    }

    public static void propagate(Layer prevLayer, Layer layer) {
        if (layer instanceof DenseLayer) {
            propagate(prevLayer, (DenseLayer) layer);
        } else if (layer instanceof ConvolutionalLayer) {
            propagate(prevLayer, (ConvolutionalLayer) layer);
        }
    }

    private static void propagate(Layer prevLayer, DenseLayer layer) {
        Tensor weights = NetworkUtils.converToNormalFloat(layer.weights);
        Tensor bias = NetworkUtils.converToNormalFloat(layer.bias);

        Tensor inputStates = NetworkUtils.converToNormalFloat(prevLayer.states);
        Tensor outputStates;

        outputStates = weights.multiply(inputStates, prevLayer.states.dimensions.length);

        outputStates.add(bias);
        outputStates.processFloat((x) -> ActivationLogic.activate(x, layer.activation));

        layer.states = NetworkUtils.convertToType(outputStates, layer.states.type);
    }

    private static void propagate(Layer prevLayer, ConvolutionalLayer layer) {
        Tensor kernel = NetworkUtils.converToNormalFloat(layer.kernel);
        Tensor bias = NetworkUtils.converToNormalFloat(layer.bias);

        Tensor inputStates = NetworkUtils.converToNormalFloat(prevLayer.states);
        Tensor outputStates;

        outputStates = kernel.convolve(inputStates);

        outputStates.add(bias);
        outputStates.processFloat((x) -> ActivationLogic.activate(x, layer.activation));

        layer.states = NetworkUtils.convertToType(outputStates, layer.states.type);
    }

    public static Tensor backPropagate(Layer layer, Layer nextLayer, Tensor delta) {
        if (layer instanceof DenseLayer) {
            return backPropagate((DenseLayer) layer, nextLayer, delta);
        } else if (layer instanceof ConvolutionalLayer) {
            return backPropagate((ConvolutionalLayer) layer, nextLayer, delta);
        }
        return null;
    }

    private static Tensor backPropagate(DenseLayer layer, Layer nextLayer, Tensor delta) {
        Tensor actualOutput = NetworkUtils.converToNormalFloat(layer.states);

        Tensor processedOutput = actualOutput;
        processedOutput.processFloat((x) -> ActivationLogic.deactivate(x, layer.activation));
        delta.hadamardProduct(processedOutput);

        Tensor nextOutput = NetworkUtils.converToNormalFloat(nextLayer.states);
        Tensor deltaW = delta.multiply(nextOutput, 0);
        deltaW.multiply(layer.learningRate);

        Tensor actualW = NetworkUtils.converToNormalFloat(layer.weights);
        actualW.add(deltaW);
        layer.weights
                = NetworkUtils.convertToType(actualW, layer.weights.type);

        Tensor newBias = NetworkUtils.converToNormalFloat(layer.bias);
        Tensor deltaBias = delta.copy();
        deltaBias.multiply(layer.learningRate);
        newBias.add(deltaBias);
        layer.bias = NetworkUtils.convertToType(newBias, layer.bias.type);
  
        return delta.multiply(actualW, layer.states.dimensions.length);
    }

    private static Tensor backPropagate(ConvolutionalLayer layer, Layer nextLayer, Tensor delta) {
        return null;
    }

    public static void setLearningRate(Layer layer, float learningRate){
        if (layer instanceof DenseLayer) {
            ((DenseLayer)layer).learningRate = learningRate;
        } else if (layer instanceof ConvolutionalLayer) {
            ((ConvolutionalLayer)layer).learningRate = learningRate;
        }
    }
    
    public static void setActivation(Layer layer, Activation activation){
        if (layer instanceof DenseLayer) {
            ((DenseLayer)layer).activation = ActivationFactory.createCopy(activation);
        } else if (layer instanceof ConvolutionalLayer) {
            ((ConvolutionalLayer)layer).activation = ActivationFactory.createCopy(activation);
        }
    }
}
