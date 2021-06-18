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
import volgyerdo.neural.structure.ConnectionType;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.logic.LayerFactory;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class NetworkFactory {

    public static Network createLayeredNetwork(
            Tensor.TYPE dataType) {
        Network layeredNetwork = new Network();
        layeredNetwork.dataType = dataType;
        layeredNetwork.layers = new ArrayList<>();
        layeredNetwork.connections = new ArrayList<>();
        layeredNetwork.activation = ActivationFactory.createSigmoid();
        return layeredNetwork;
    }

    public static Network createLayeredNetwork(
            Tensor.TYPE dataType,
            int[] dimensions,
            int layerCount,
            ConnectionType connectionType) {
        Network layeredNetwork = createLayeredNetwork(dataType);

        switch (connectionType) {
            case FULL_CONNECTION:
                for (int i = 0; i < layerCount; i++) {
                    Layer layer;
                    layer = LayerFactory.createLayer(dataType, dimensions);
                    addFullyConnectedLayer(layeredNetwork, layer);
                    layeredNetwork.layers.get(i).dataType = dataType;
                    layeredNetwork.layers.get(i).dimensions = dimensions;
                }
                break;

            case CONVOLUTION:
                for (int i = 0; i < layerCount; i++) {
                    Layer layer;
                    layer = LayerFactory.createLayer(dataType, dimensions);
                    addConvolutionalLayer(layeredNetwork, layer, dimensions);
                    layeredNetwork.layers.get(i).dataType = dataType;
                    layeredNetwork.layers.get(i).dimensions = dimensions;
                }
                break;
                
        }
        return layeredNetwork;
    }

    public static void addFullyConnectedLayer(Network network, Layer layer) {
        if (network.layers.size() > 0) {
            Layer previousLayer = network.layers.get(network.layers.size() - 1);
            network.connections.add(
                    ConnectionFactory.createFullyConnectedConnection(
                            previousLayer, layer));
        }
        network.layers.add(layer);
    }

    public static void addConvolutionalLayer(Network network, Layer layer, int[] kernelDimensions) {
        if (network.layers.size() > 0) {
            network.connections.add(
                    ConnectionFactory.createConvolutionalConnection(
                            network.dataType, kernelDimensions, layer));
        }
        network.layers.add(layer);
    }

}
