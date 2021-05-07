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

import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.LayerConnection;
import volgyerdo.neural.structure.ConnectionType;

/**
 *
 * @author antal
 */
public class ConnectionLogic {

    public static void propagate(Layer inputLayer, Layer outputLayer, LayerConnection connection) {
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
    
    public static void backPropagate(Layer inputLayer, Layer outputLayer, LayerConnection connection) {
        
    }
}
