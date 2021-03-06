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

import java.util.Random;
import volgyerdo.math.PrimitiveUtils;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Link;
import volgyerdo.neural.structure.TestResults;

/**
 *
 * @author antal
 */
public class NetworkUtils {

    public static Tensor getInputDimensions(Network network) {
        return getInputLayer(network).states;
    }

    public static Tensor getOutputDimensions(Network network) {
        return getOutputLayer(network).states;
    }

    public static Layer getInputLayer(Network network) {
        return network.layers.get(0);
    }

    public static Layer getOutputLayer(Network network) {
        return network.layers.get(network.layers.size() - 1);
    }

    public static void randomizeWeigths(Tensor weights) {
        if (weights == null) {
            return;
        }
        weights.randomize(
                -NetworkConstants.DEFAULT_WEIGHT_RADIUS, NetworkConstants.DEFAULT_WEIGHT_RADIUS);
    }

    public static void randomizeWeigths(Link[] links) {
        for (int i = 0; i < links.length; i++) {
            links[i].weight = PrimitiveUtils.random(
                    -NetworkConstants.DEFAULT_WEIGHT_RADIUS, NetworkConstants.DEFAULT_WEIGHT_RADIUS);
        }
    }

    public static int[] randomizeCoordinates(int[] bounds) {
        Random rand = new Random();
        int[] randomArray = new int[bounds.length];
        for (int i = 0; i < bounds.length; i++) {
            randomArray[i] = rand.nextInt(bounds[i]);
        }
        return randomArray;
    }

    public static void printTestResults(TestResults res){
        System.out.println("");
        System.out.println("");
        System.out.print("Avg: " + res.avgError + ", ");
        System.out.print("Min: " + res.minError + ", ");
        System.out.print("Max: " + res.maxError + ", ");
        System.out.print("Runtime: " + res.runTime + " ms ");
        System.out.println("");
        System.out.println("");
    }
    
    private NetworkUtils() {

    }
}
