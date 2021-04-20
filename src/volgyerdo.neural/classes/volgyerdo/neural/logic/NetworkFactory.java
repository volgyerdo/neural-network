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
import volgyerdo.neural.structure.ConvolutionalNetwork;
import volgyerdo.neural.structure.FullyConnectedNetwork;
import volgyerdo.neural.structure.GeneralNetwork;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.LayeredNetwork;
import volgyerdo.neural.structure.PartiallyConnectedNetwork;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class NetworkFactory {

    public static ConvolutionalNetwork createConvolutionalNetwork(
            Tensor.TYPE dataType){
        return null;
    }
    
    public static FullyConnectedNetwork createFullyConnectedNetwork(
            Tensor.TYPE dataType,
            int layercount,
            int... inputdimensions
    ){
        
        //hol adjuk meg a retegek szamat, bennuk levo neuronok szamat?
        //feltétel: automatikusan sulytenzort tartalmazo Connection
        //          ^ ez osszekoti az uj reteget az elozo reteggel
        //          ha nincs reteg nem kell Connection
        //         sulytenzor dimenzioja = L(i) lenght + L(i+1) lenght
        
        //network letrehozasa
        FullyConnectedNetwork FCN = new FullyConnectedNetwork();
        FCN.dataType = dataType;
        FCN.inputDimensions = inputdimensions;
        
        //retegek letrehozas
        for (int i = 0; i < layercount; i++) {
        FCN.layers.add(new Layer());
        }
        
        //kapcsolat letrehozasa
        for (int i = 0; i < layercount-1; i++) {
        FCN.connections.add(new Connection());
        FCN.connections.get(i).weights = Tensor.create(dataType,
                                                        FCN.layers.get(i).dimensions.length 
                                                      + FCN.layers.get(i+1).dimensions.length);
        }
       
        return null;
    }
    
    public static PartiallyConnectedNetwork createPartiallyConnectedNetwork(
            Tensor.TYPE dataType){
        return null;
    }
    
    public static GeneralNetwork createGeneralNetwork(
            Tensor.TYPE dataType){
        return null;
    }
    
        
    public static void addLayer(LayeredNetwork network, Layer layer){
        
    }
        
}
