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

import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.LayerConnection;
import volgyerdo.neural.structure.LayeredNetwork;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.ConnectionType;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class NetworkLogic {

    private NetworkLogic() {
    }

    public static void randomize(LayeredNetwork network) {
        for (LayerConnection connection : network.connections) {
            switch (connection.weights.type) {
                case BYTE:
                    connection.weights.randomize(Byte.MIN_VALUE, Byte.MAX_VALUE);
                    break;
                case SHORT:
                    connection.weights.randomize(Short.MIN_VALUE, Short.MAX_VALUE);
                    break;
                case FLOAT:
                    connection.weights.randomize(-1, 1);
                    break;
            }
        }
    }

    public static void propagate(LayeredNetwork network) {
        for (int i = 0; i < network.layers.size() - 1; i++) {
            Layer inputLayer = network.layers.get(i);
            Layer outputLayer = network.layers.get(i + 1);
            LayerConnection connection = network.connections.get(i);

            Tensor weights = NetworkUtils.converToNormalFloat(connection.weights);
            Tensor bias = NetworkUtils.converToNormalFloat(connection.bias);

            if (connection.type == ConnectionType.CONVOLUTION) {
                outputLayer.states = inputLayer.states.convolve(weights);
            } else {
                outputLayer.states = weights.multiply(inputLayer.states, inputLayer.dimensions.length);

            }
            outputLayer.states.add(bias);
            outputLayer.states.processFloat((x) -> ActivationLogic.activate(x, network.activation));
        }
    }

    public static void backPropagate(LayeredNetwork network, Tensor target) {

        Tensor actualOutput = NetworkUtils.converToNormalFloat(network.layers.get(network.layers.size() - 1).states);
        Tensor errorTensor = NetworkUtils.converToNormalFloat(target);
        errorTensor.substract(actualOutput);

        Tensor delta;
        Tensor deltaW;
        Tensor previousOutput;
        Tensor actualW;
        Tensor processedOutput;
        LayerConnection actualConnection;
        Tensor newBias;

        processedOutput = actualOutput;
        processedOutput.processFloat((x) -> ActivationLogic.deactivate(x, network.activation));
        delta = processedOutput;
        delta.hadamardProduct(errorTensor);

        previousOutput = NetworkUtils.converToNormalFloat(network.layers.get(network.layers.size() - 2).states);
        deltaW = delta.multiply(previousOutput, 0);
        deltaW.multiply(network.learningRate);

        actualConnection = network.connections.get(network.connections.size() - 1);
        actualW = NetworkUtils.converToNormalFloat(actualConnection.weights);
        actualW.add(deltaW);
        actualConnection.weights
                = NetworkUtils.convertToType(actualW, actualConnection.weights.type);

        for (int i = network.layers.size() - 2; i > 0; i--) {
            actualOutput = NetworkUtils.converToNormalFloat(network.layers.get(i).states);
            delta = delta.multiply(actualW, network.layers.get(i + 1).states.dimensions.length);
            processedOutput = actualOutput;
            processedOutput.processFloat((x) -> ActivationLogic.deactivate(x, network.activation));
            delta.hadamardProduct(actualOutput);

            previousOutput = NetworkUtils.converToNormalFloat(network.layers.get(i - 1).states);
            deltaW = delta.multiply(previousOutput, 0);
            deltaW.multiply(network.learningRate);

            actualConnection = network.connections.get(i - 1);
            actualW = NetworkUtils.converToNormalFloat(actualConnection.weights);
            actualW.add(deltaW);
            actualConnection.weights
                    = NetworkUtils.convertToType(actualW, actualConnection.weights.type);

            newBias = NetworkUtils.converToNormalFloat(actualConnection.bias);
            delta.multiply(network.learningRate);
            newBias.add(delta);
            actualConnection.bias = NetworkUtils.convertToType(newBias, actualConnection.bias.type);
        }
    }
}
