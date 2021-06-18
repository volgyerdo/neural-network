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

    static Tensor converToNormalFloat(Tensor input) {
        Tensor output = input.convertTo(Tensor.TYPE.FLOAT);
        switch (input.type) {
            case BYTE:
                output.divide(Byte.MAX_VALUE);
                break;
            case SHORT:
                output.divide(Short.MAX_VALUE);
                break;
        }
        return output;
    }

    static Tensor convertToType(Tensor input, Tensor.TYPE type) {
        if (input.type != Tensor.TYPE.FLOAT) {
            throw new InvalidParameterException("Non-float input Tensor");
        }
        Tensor convertedInput = input.copy();
        switch (type) {
            case BYTE:
                convertedInput.multiply(Byte.MAX_VALUE);
                break;
            case SHORT:
                convertedInput.multiply(Short.MAX_VALUE);
                break;
        }
        return convertedInput.convertTo(type);
    }
    
    public static void randomizeWeigths(Tensor weights) {
        if(weights == null){
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

    private NetworkUtils() {

    }
}
