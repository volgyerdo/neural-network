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

    public static void randomizeWeights(LayeredNetwork network) {
        for (LayerConnection connection : network.connections) {
            switch (connection.weights.type) {
                case BYTE ->
                    connection.weights.randomize(Byte.MIN_VALUE, Byte.MAX_VALUE);
                case SHORT ->
                    connection.weights.randomize(Short.MIN_VALUE, Short.MAX_VALUE);
                case FLOAT ->
                    connection.weights.randomize(-1, 1);
            }
        }
    }

    public static void setFixWeights(LayeredNetwork network, float weigth) {
        for (LayerConnection connection : network.connections) {
            connection.weights.fill(weigth);
        }
    }

    public static void propagate(LayeredNetwork network) {
        for (int i = 0; i < network.layers.size() - 1; i++) {
            Layer inputLayer = network.layers.get(i);
            Layer outputLayer = network.layers.get(i + 1);
            LayerConnection connection = network.connections.get(i);

            Tensor weights = NetworkUtils.converToNormalFloat(connection.weights);
            Tensor bias = NetworkUtils.converToNormalFloat(connection.bias);
            //states konvertálás
            Tensor instates = NetworkUtils.converToNormalFloat(inputLayer.states);
            Tensor outstates;

            if (connection.type == ConnectionType.CONVOLUTION) {
                outstates = instates.convolve(weights);
            } else {
                outstates = weights.multiply(instates, inputLayer.dimensions.length);

            }

            outstates.add(bias);
            outstates.processFloat((x) -> ActivationLogic.activate(x, network.activation));

            //visszakonv.
            inputLayer.states = NetworkUtils.converToNormalFloat(instates);
            outputLayer.states = NetworkUtils.converToNormalFloat(outstates);
        }
    }

    public static void backPropagate(LayeredNetwork network, Tensor target) {
        Tensor actualOutput = NetworkUtils.converToNormalFloat(network.layers.get(network.layers.size() - 1).states);

        Tensor error = NetworkUtils.converToNormalFloat(target);
        error.substract(actualOutput);

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
        delta.hadamardProduct(error);

        previousOutput = NetworkUtils.converToNormalFloat(network.layers.get(network.layers.size() - 2).states);
        deltaW = delta.multiply(previousOutput, 0);
        deltaW.multiply(network.learningRate);

        actualConnection = network.connections.get(network.connections.size() - 1);
        actualW = NetworkUtils.converToNormalFloat(actualConnection.weights);
        actualW.add(deltaW);
        actualConnection.weights
                = NetworkUtils.convertToType(actualW, actualConnection.weights.type);

        newBias = NetworkUtils.converToNormalFloat(actualConnection.bias);
        delta.multiply(network.learningRate);
        newBias.add(delta);
        actualConnection.bias = NetworkUtils.convertToType(newBias, actualConnection.bias.type);

        for (int i = network.layers.size() - 2; i > 0; i--) {
            actualOutput = NetworkUtils.converToNormalFloat(network.layers.get(i).states);
            delta = error.multiply(actualW, network.layers.get(i + 1).states.dimensions.length);
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

    public static void print(LayeredNetwork network) {
        for (int i = 0; i < network.layers.size() - 1; i++) {
            Layer inputLayer = network.layers.get(i);
            Layer outputLayer = network.layers.get(i + 1);
            LayerConnection connection = network.connections.get(i);
            if (i == 0) {
                System.out.print("(" + inputLayer.states + ")");
            }
            System.out.print(" - " + connection.weights + "/" + connection.bias + " - ");
            System.out.print("(" + outputLayer.states + ")");
        }
    }
}
