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

import java.util.List;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.Connection;
import volgyerdo.neural.structure.ConvolutionalNetwork;
import volgyerdo.neural.structure.FullyConnectedNetwork;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.LayeredNetwork;
import volgyerdo.neural.structure.Network;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class NetworkLogic {

    private NetworkLogic() {
    }
    
    public static void randomize(Network network){
        
    }
    
    public static void propagate(FullyConnectedNetwork network){
        for(int i = 0; i < network.layers.size() - 1; i++){
            Layer inputLayer = network.layers.get(i);
            Layer outputLayer = network.layers.get(i+1);
            Connection connection = network.connections.get(i);
            outputLayer.states = inputLayer.states.multiply(connection.weights, 1);
        } 
    }
    
    public static void propagate(ConvolutionalNetwork network){
        for(int i = 0; i < network.layers.size() - 1; i++){
            Layer inputLayer = network.layers.get(i);
            Layer outputLayer = network.layers.get(i+1);
            Connection connection = network.connections.get(i);
            outputLayer.states = inputLayer.states.convolve(connection.weights);
        } 
    }
    
    public static void backPropagate(FullyConnectedNetwork network, Tensor target){
        
    }
    
    public static void backPropagate(ConvolutionalNetwork network, Tensor target){
        
    }
}
