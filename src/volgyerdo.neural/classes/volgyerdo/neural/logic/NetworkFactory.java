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
package volgyerdo.neural.logic;

import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.ConnectionType;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.LayeredNetwork;


/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class NetworkFactory {
    
    public static LayeredNetwork createLayeredNetwork(
            Tensor.TYPE dataType){
        LayeredNetwork layeredNetwork = new LayeredNetwork();
        layeredNetwork.dataType = dataType;
        return null;
    }
    
    public static LayeredNetwork createLayeredNetwork(
            Tensor.TYPE dataType, int[] dimensions, int layerCount, ConnectionType connectionType){
        
    }

    public static void addFullyConnectedLayer(LayeredNetwork network, Layer layer){
        if(network.layers.size() > 0){
            Layer previousLayer = network.layers.get(network.layers.size()-1);
            network.connections.add(
                    ConnectionFactory.createFullyConnectedConnection(
                            previousLayer, layer));
        }
        network.layers.add(layer);
    }
        
    public static void addConvolutionalLayer(LayeredNetwork network, Layer layer, int[] kernelDimensions){
        if(network.layers.size() > 0){
            network.connections.add(
                    ConnectionFactory.createConvolutionalConnection(
                            network.dataType, kernelDimensions));
        }
        network.layers.add(layer);
    }
   
}
