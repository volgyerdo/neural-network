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
                case SHORT:
                    connection.weights.randomize(Short.MIN_VALUE, Short.MAX_VALUE);
                case FLOAT:
                    connection.weights.randomize(-1, 1);
            }
        }
    }

    public static void propagate(LayeredNetwork network) {
        for (int i = 0; i < network.layers.size() - 1; i++) {
            Layer inputLayer = network.layers.get(i);
            Layer outputLayer = network.layers.get(i + 1);
            LayerConnection connection = network.connections.get(i);
            ConnectionLogic.propagate(inputLayer, outputLayer, connection);
        }
    }

    public static void backPropagate(LayerConnection connection, LayeredNetwork network, Tensor target) {

    }

}
