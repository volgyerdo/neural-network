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

/**
 *
 * @author antal
 */
public class LayerFactory {
    
    public Layer createLayer(Tensor.TYPE dataType, int... dimensions){
        Layer currentLayer = new Layer();
        currentLayer.dataType = dataType;
        currentLayer.dimensions = dimensions;
        currentLayer.states = Tensor.create(dataType, dimensions);
        return currentLayer; 
    }
    
}
