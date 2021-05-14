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

    //ez valoszinu a ConnectionLogic backProp metodustorzse
    public static void backPropagate(LayerConnection connection, LayeredNetwork network,
            Tensor target) {
        
        //for ciklusba
        float l_rate = (float) 0.01;

        Tensor input = network.layers.get(0).states;
        Tensor hidden = input.multiply(connection.weights, 0);
        //bias hozzadasa
        //hidden.sigmoid()

        Tensor output = hidden.multiply(connection.weights, 0);
        //output add bias
        //output.sigmoid

        //error = target - output
        Tensor error = target.substract(output);

        //tensor gradient = output . deactivate sigmoid
        Tensor gradient = null;
        for (int i = 0; i < output.dimensions.length; i++) {
            Activation activation = ActivationFactory.createSigmoid();
            float x = ActivationLogic.deactivate(output.getFloatValue(i), activation);
            gradient.setFloatValue(x, i);
        }
        gradient.multiply(error, 0);
        gradient.multiply(l_rate);

        Tensor hidden_T = hidden.transpose();
        Tensor who_delta = gradient.multiply(hidden_T, 0);

        //weights_ho.add(who_delta)
        Tensor weights_ho = connection.weights;
        weights_ho.add(who_delta);
        //bias_o.add(gradient)
        //
        Tensor who_T = weights_ho.transpose();
        Tensor hidden_errors = who_T.multiply(error, 0);

        //matrix h_gradient = hidden. deactivate sigmoid
        Tensor h_gradient = null;
        for (int i = 0; i < hidden.dimensions.length; i++) {
            Activation activation = ActivationFactory.createSigmoid();
            float x = ActivationLogic.deactivate(hidden.getFloatValue(i), activation);
            gradient.setFloatValue(x, i);
        }
        h_gradient.multiply(hidden_errors, 0);
        h_gradient.multiply(l_rate);

        Tensor i_T = input.transpose();
        Tensor wih_delta = h_gradient.multiply(i_T, 0);

        //weights_ih.add(wih_delta)
        Tensor weights_ih = connection.weights;
        weights_ih.add(wih_delta);
        //bias_h.add(h_gradient)
    }

    public static Tensor getInputDimensions(LayeredNetwork network) {
        return network.layers.get(0).states;
    }

    public static Tensor getOutputDimensions(LayeredNetwork network) {
        return network.layers.get(network.layers.size()).states;
    }

}
