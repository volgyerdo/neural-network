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
import volgyerdo.neural.structure.DenseLayer;
import volgyerdo.neural.structure.InputLayer;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.OutputLayer;

/**
 *
 * @author antal
 */
public class LayerFactory {
    
    public static Layer createInputLayer(Tensor.TYPE dataType, int... dimensions){
        InputLayer layer = new InputLayer();
        layer.dataType = dataType;
        layer.states = Tensor.create(dataType, dimensions);
        return layer;
    }
    
    public static Layer createOutputLayer(Tensor.TYPE dataType, int... dimensions){
        OutputLayer layer = new OutputLayer();
        layer.dataType = dataType;
        layer.states = Tensor.create(dataType, dimensions);
        return layer;
    }
    
    public static Layer createDenseLayer(Tensor.TYPE dataType, int... dimensions){
        DenseLayer layer = new DenseLayer();
        layer.dataType = dataType;
        layer.states = Tensor.create(dataType, dimensions);
        return layer; 
    }
    
    public static Layer createConvolutionalLayer(Tensor.TYPE dataType, int... dimensions){
        DenseLayer layer = new DenseLayer();
        layer.dataType = dataType;
        layer.states = Tensor.create(dataType, dimensions);
        return layer; 
    }
    
}
