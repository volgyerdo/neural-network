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
package volgyerdo.test;

import static java.lang.Float.NaN;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.logic.ActivationFactory;
import volgyerdo.neural.logic.LayerFactory;
import volgyerdo.neural.logic.NetworkFactory;
import volgyerdo.neural.logic.NetworkLogic;
import volgyerdo.neural.logic.NetworkUtils;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.LayeredNetwork;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class XorTest {

    public static void main(String[] args) {
        LayeredNetwork network = NetworkFactory.createLayeredNetwork(Tensor.TYPE.FLOAT);

        Layer inputLayer = LayerFactory.createLayer(Tensor.TYPE.FLOAT, 2);
        NetworkFactory.addFullyConnectedLayer(network, inputLayer);

        for (int i = 0; i < 1; i++) {
            Layer hiddenLayer = LayerFactory.createLayer(Tensor.TYPE.FLOAT, 10);
            NetworkFactory.addFullyConnectedLayer(network, hiddenLayer);
        }

        Layer outputLayer = LayerFactory.createLayer(Tensor.TYPE.FLOAT, 1);
        NetworkFactory.addFullyConnectedLayer(network, outputLayer);

        network.activation = ActivationFactory.createSigmoid();
        NetworkLogic.randomizeWeights(network);
        List<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair(new float[]{0f, 0f}, new float[]{0f}));
        pairs.add(new Pair(new float[]{1f, 0f}, new float[]{1f}));
        pairs.add(new Pair(new float[]{0f, 1f}, new float[]{1f}));
        pairs.add(new Pair(new float[]{1f, 1f}, new float[]{0f}));
        System.out.println("\nBefore training:\n");

        for (Pair pair : pairs) {
            inputLayer.states.setFloatValue(pair.input[0], 0);
            inputLayer.states.setFloatValue(pair.input[1], 1);

            NetworkLogic.propagate(network);

            System.out.println(outputLayer.states.getFloatValue(0));
        }
        Tensor target = Tensor.create(Tensor.TYPE.FLOAT, 1);
        for (int i = 0; i < 50000; i++) {
            int pairNumber = (int) (Math.random() * pairs.size());
            Pair pair = pairs.get(pairNumber);
            inputLayer.states.setFloatValue(pair.input[0], 0);
            inputLayer.states.setFloatValue(pair.input[1], 1);
            NetworkLogic.propagate(network);
            target.setFloatValue(pair.output[0], 0);
            NetworkLogic.backPropagate(network, target);
        }
        System.out.println("\nAfter training:\n");

        DecimalFormat format = new DecimalFormat("0.000");

        for (Pair pair : pairs) {
            inputLayer.states.setFloatValue(pair.input[0], 0);
            inputLayer.states.setFloatValue(pair.input[1], 1);

            NetworkLogic.propagate(network);

            //debug
            for (int i = 0; i < network.layers.size(); i++) {
                for (int j = 0; j < network.layers.get(i).dimensions.length; j++) {
                    if (network.layers.get(i).states.getFloatValue(j) == NaN) {
                        throw new InvalidParameterException(i + 1 + ". layer, " + j + 1 + ". state is Not a number");
                    }
                }
            }

            format.format(outputLayer.states.getFloatValue(0));
            System.out.println(pair.input[0] + " + " + pair.input[1] + " = "
                    + format.format(outputLayer.states.getFloatValue(0)) + "(" + pair.output[0] + ")");
        }
    }

    private static class Pair {

        float[] input;
        float[] output;

        public Pair(float[] input, float[] output) {
            this.input = input;
            this.output = output;
        }

    }

}
