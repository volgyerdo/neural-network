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
import volgyerdo.neural.structure.Connection;
import volgyerdo.neural.structure.LayeredNetwork;
import volgyerdo.neural.structure.GeneralNetwork;
import volgyerdo.neural.structure.FullConnection;
import volgyerdo.neural.structure.ConvolutionalConnection;
import volgyerdo.neural.structure.PartialConnection;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class NetworkLogic {

    private NetworkLogic() {
    }
    
    public static void randomize(LayeredNetwork network){
        
        //Az elso layernek nincs sulya, ezert lehetne i=1
        for (int i = 0; i < network.layers.size(); i++) {
            
            if(network.layers.get(i).dataType == network.layers.get(i).dataType.BYTE){
            network.layers.get(i).states.randomize(Byte.MIN_VALUE, Byte.MAX_VALUE);
            }
        
            if(network.layers.get(i).dataType == network.layers.get(i).dataType.SHORT){
            network.layers.get(i).states.randomize(Short.MIN_VALUE, Short.MAX_VALUE);
            }
            else{
            network.layers.get(i).states.randomize(-1, 1);
            }
        }
    }
    //teljesen kapcsolt prop.
    public static void propagateFullconnected(LayeredNetwork network){
        
        //suly normalizalas
        
        
        //propagation
        for(int i = 0; i < network.layers.size() - 1; i++){
            Layer inputLayer = network.layers.get(i);
            Layer outputLayer = network.layers.get(i+1);
            Connection connection = network.connections.get(i);
            Tensor inputStates = inputLayer.states.convertTo(Tensor.TYPE.FLOAT);
            Tensor weights = connection.weights.convertTo(Tensor.TYPE.FLOAT);
            switch(connection.weights.type){
                case BYTE:
                   weights.divide(Byte.MAX_VALUE); 
                case SHORT:
                   weights.divide(Short.MAX_VALUE); 
            }
            outputLayer.states = inputLayer.states.multiply(connection.weights, 1);
        } 
    }

    //Konvolucios prop.
    public static void propagateConvol(LayeredNetwork network){
        
        //suly normalizalas
        
        //prop
        for(int i = 0; i < network.layers.size() - 1; i++){
            Layer inputLayer = network.layers.get(i);
            Layer outputLayer = network.layers.get(i+1);
            Connection connection = network.connections.get(i);
            outputLayer.states = inputLayer.states.convolve(connection.weights);
        } 
    }
    
    public static void backPropagate(ConvolutionalConnection connection, LayeredNetwork network, Tensor target){
        
    }
    
    public static void backPropagate(FullConnection connection, LayeredNetwork network, Tensor target){
        
    }
    
    public static void backPropagate(PartialConnection connection, LayeredNetwork network, Tensor target){
        
    }
}
