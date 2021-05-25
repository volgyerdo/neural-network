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
import volgyerdo.neural.logic.ActivationLogic;
import volgyerdo.neural.logic.ActivationFactory;
import volgyerdo.neural.structure.Activation;
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

            Tensor weights = connection.weights.convertTo(Tensor.TYPE.FLOAT);
            switch (connection.weights.type) {
                case BYTE:
                    weights.divide(Byte.MAX_VALUE);
                case SHORT:
                    weights.divide(Short.MAX_VALUE);
            }
            if (connection.type == ConnectionType.CONVOLUTION) {
                outputLayer.states = inputLayer.states.convolve(weights);
            } else {
                outputLayer.states = inputLayer.states.multiply(weights, inputLayer.dimensions.length);
            }
        }
    }

    public static void backPropagate(LayerConnection connection, LayeredNetwork network,
            Tensor target) {

//        try {
        float l_rate = (float) 0.01; //metodusparameternek kellene lennie
        Tensor ActualOutput = network.layers.get(network.layers.size()).states;
        Tensor ErrorTensor = target.substract(ActualOutput);

        Tensor lastLayerDelta = null;
        Activation parameters = network.activation;
        Tensor LastLayerDerivate = null;
        Tensor Delta[] = null;
        Tensor WDelta[] = null;
        Tensor newBias[] = null;
        Tensor Bias[] = null; //meg a halozatunkban nem szamoltunk biassal

        for (int i = network.layers.size() - 1; i > 0; i--) {

            //utolso és i. reteg deltaja
            if (i == network.layers.size() - 1) {
                for (int j = 0; j < ActualOutput.dimensions.length; j++) {
                    LastLayerDerivate.dimensions[j] = ActivationLogic.deactivate(ActualOutput.dimensions[j], parameters);
                }
                lastLayerDelta = ErrorTensor.hadamardProduct(LastLayerDerivate);
                Delta[network.layers.size() - 1] = lastLayerDelta;
            } else {

                for (int j = 0; j < network.layers.get(i).dimensions.length; j++) {
                    Delta[i] = Delta[i + 1].multiply(network.connections.get(i + 1).weights, 2);
                    Delta[i] = Delta[i].hadamardProduct(ActivationLogic.deactivate(network.layers.get(i).states.dimensions[j], parameters));

                }
            }

            //i.reteg sulyvalrozas
            WDelta[i] = Delta[i].multiply(network.layers.get(i - 1).states, 0);

            //i.reteg uj suly
            network.connections.get(i).weights = network.connections.get(i).weights.add(WDelta[i].multiply(l_rate));

            //i.reteg uj erositesi tenyezoi
            newBias[i] = Bias[i].add(WDelta[i].multiply(l_rate));

            }
//        } catch (CloneNotSupportedException ex) {
//            ex.printStackTrace();
//        }
    }

    public static Tensor getInputDimensions(LayeredNetwork network) {
        return network.layers.get(0).states;
    }

    public static Tensor getOutputDimensions(LayeredNetwork network) {
        return network.layers.get(network.layers.size()).states;
    }

}
