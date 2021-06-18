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
import volgyerdo.neural.structure.ConvolutionalLayer;
import volgyerdo.neural.structure.DenseLayer;

/**
 *
 * @author antal
 */
public class LayerFactory {
    
    public static DenseLayer createDenseLayer(Tensor.TYPE dataType, int... dimensions){
        DenseLayer layer = new DenseLayer();
        layer.dataType = dataType;
        layer.states = Tensor.create(dataType, dimensions);
        layer.activation = ActivationFactory.createCopy(NetworkConstants.DEFAULT_ACTIVATION);
        layer.learningRate = NetworkConstants.DEFAULT_LEARNING_RATE;
        return layer; 
    }
    
    public static ConvolutionalLayer createConvolutionalLayer(Tensor.TYPE dataType, int... dimensions){
        ConvolutionalLayer layer = new ConvolutionalLayer();
        layer.dataType = dataType;
        layer.states = Tensor.create(dataType, dimensions);
        layer.activation = ActivationFactory.createCopy(NetworkConstants.DEFAULT_ACTIVATION);
        layer.learningRate = NetworkConstants.DEFAULT_LEARNING_RATE;
        return layer; 
    }
    
}
