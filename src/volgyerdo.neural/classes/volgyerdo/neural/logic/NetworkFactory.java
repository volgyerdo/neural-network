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
//import volgyerdo.neural.structure.ConvolutionalNetwork;
//import volgyerdo.neural.structure.FullyConnectedNetwork;
import volgyerdo.neural.structure.GeneralNetwork;
import volgyerdo.neural.structure.Layer;
//import volgyerdo.neural.structure.PartiallyConnectedNetwork;


/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class NetworkFactory {

//    public static ConvolutionalNetwork createConvolutionalNetwork(
//            Tensor.TYPE dataType){
//        ConvolutionalNetwork cn = new ConvolutionalNetwork();
//        cn.dataType = dataType;
//        return cn;
//    }
//    
//    public static FullyConnectedNetwork createFullyConnectedNetwork(
//            Tensor.TYPE dataType){
//        FullyConnectedNetwork fcn = new FullyConnectedNetwork();
//        fcn.dataType = dataType;
//        return fcn;
//    }
//    
//    public static PartiallyConnectedNetwork createPartiallyConnectedNetwork(
//            Tensor.TYPE dataType){
//        
//        return null;
//    }
    
    public static GeneralNetwork createGeneralNetwork(
            Tensor.TYPE dataType){
        GeneralNetwork gn = new GeneralNetwork();
        gn.dataType = dataType;
        return null;
    }

//    public static void addLayer(FullyConnectedNetwork network, Layer layer){
//        if(network.layers.size() > 0){
//            Layer previousLayer = network.layers.get(network.layers.size()-1);
//            network.connections.add(
//                    ConnectionFactory.createFullyConnectedNetworkConnection(
//                            previousLayer, layer));
//        }
//        network.layers.add(layer);
//    }
//        
//    public static void addLayer(ConvolutionalNetwork network, Layer layer, int[] kernelDimensions){
//        if(network.layers.size() > 0){
//            Layer previousLayer = network.layers.get(network.layers.size()-1);
//            network.connections.add(
//                    ConnectionFactory.createConvolutionalNetworkConnection(
//                            network.dataType, kernelDimensions));
//        }
//        network.layers.add(layer);
//    }
   
}
