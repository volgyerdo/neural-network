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
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Connection;
import volgyerdo.neural.structure.ConvolutionalConnection;
import volgyerdo.neural.structure.ConnectionType;
/**
 *
 * @author antal
 */
public class ConnectionLogic {
    
    //Ha nem mukodik a connection valtozoval, kulon propagate minden fajtahoz
    public void propagate(Layer inputlayer, Layer outputlayer, Connection connection){
        if(connection.type == ConnectionType.CONVOLUTION){
        outputlayer.states = inputlayer.states.convolve(connection.weights);
        } else {
        outputlayer.states = inputlayer.states.multiply(connection.weights, 1);
        }
    }    
}
