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

import java.security.InvalidParameterException;
import java.util.Random;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;

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
        switch (weights.type) {
            case BYTE ->
                weights.randomize(Byte.MIN_VALUE, Byte.MAX_VALUE);
            case SHORT ->
                weights.randomize(Short.MIN_VALUE, Short.MAX_VALUE);
            case FLOAT ->
                weights.randomize(-1, 1);
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

    private NetworkUtils() {

    }
}
