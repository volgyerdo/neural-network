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

import java.util.Objects;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.ConnectionType;
import volgyerdo.neural.structure.LayerConnection;
import volgyerdo.neural.structure.Layer;

/**
 *
 * @author antal
 */
public class ConnectionFactory {

    public static LayerConnection createFullyConnectedConnection(Layer layer1, Layer layer2) {
        if (!Objects.equals(layer1.dataType, layer2.dataType)) {
            throw new IllegalArgumentException("Layers data type does not match.");
        }
        LayerConnection connection = new LayerConnection();
        connection.type = ConnectionType.FULL_CONNECTION;
        int[] weightsDimensions = new int[layer1.dimensions.length + layer2.dimensions.length];
        System.arraycopy(layer2.dimensions, 0, weightsDimensions, 0, layer2.dimensions.length);
        System.arraycopy(layer1.dimensions, 0, weightsDimensions, layer2.dimensions.length, layer1.dimensions.length);
        connection.weights = Tensor.create(layer1.dataType, weightsDimensions);
        connection.bias = initDefaultBias(layer2.dataType, layer2.dimensions.length);
        return connection;
    }

    public static LayerConnection createConvolutionalConnection(Tensor.TYPE dataType, int[] kernelDimensions) {
        LayerConnection connection = new LayerConnection();
        connection.type = ConnectionType.CONVOLUTION;
        connection.weights = Tensor.create(dataType, kernelDimensions);
        connection.bias = initDefaultBias(dataType, kernelDimensions.length);
        return connection;
    }

    public static Tensor initDefaultBias(Tensor.TYPE t, int dlength) {
        Tensor dbias = Tensor.create(t, dlength);
        NetworkUtils.convertToType(dbias, Tensor.TYPE.FLOAT);
        for (int i = 0; i < dlength; i++) {
            dbias.setFloatValue(1f, i);
        }
        return NetworkUtils.convertToType(dbias, t);
    }

}
