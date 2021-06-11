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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.logic.ActivationFactory;
import volgyerdo.neural.logic.NetworkFactory;
import volgyerdo.neural.logic.NetworkLogic;
import volgyerdo.neural.logic.NetworkUtils;
import volgyerdo.neural.structure.ConnectionType;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.LayeredNetwork;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class SimpleLayeredTest2 {

    public static void main(String[] args) {
        LayeredNetwork network = NetworkFactory.createLayeredNetwork(Tensor.TYPE.FLOAT, new int[]{1}, 3, ConnectionType.FULL_CONNECTION);
        network.learningRate = 1f;
        network.activation = ActivationFactory.createSigmoid();
        
//        NetworkLogic.randomizeWeights(network);
        List<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair(new float[]{0f}, new float[]{0f}));
        pairs.add(new Pair(new float[]{1f}, new float[]{1f}));
//        System.out.println("\nBefore training:\n");
        Layer inputLayer = NetworkUtils.getInputLayer(network);
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
//        for (Pair pair : pairs) {
//            inputLayer.states.setFloatValue(pair.input[0], 0);
//
//            NetworkLogic.propagate(network);
//
//            System.out.println(outputLayer.states.getFloatValue(0));
//        }
        Tensor target = Tensor.create(Tensor.TYPE.FLOAT, 1);
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j<pairs.size(); j++) {
                Pair pair = pairs.get(j);
//            int pairNumber = (int) (Math.random() * pairs.size());
//            Pair pair = pairs.get(pairNumber);
                inputLayer.states.setFloatValue(pair.input[0], 0);
                NetworkLogic.propagate(network);
                if(j == 0){
                    NetworkLogic.print(network);
                    System.out.println();
                }
                target.setFloatValue(pair.output[0], 0);
                NetworkLogic.backPropagate(network, target);
                NetworkLogic.print(network);
                System.out.println();
            }
        }

//        System.out.println("\nAfter training:\n");
//
//        DecimalFormat format = new DecimalFormat("0.0000000");
//        for (Pair pair : pairs) {
//            inputLayer.states.setFloatValue(pair.input[0], 0);
//
//            NetworkLogic.propagate(network);
//
//            System.out.println(pair.output[0] + " -> " + format.format(outputLayer.states.getFloatValue(0)) + " ("
//                    + (format.format(outputLayer.states.getFloatValue(0) - pair.output[0])) + ")");
//        }
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
