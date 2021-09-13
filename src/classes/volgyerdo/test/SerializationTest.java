/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volgyerdo.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import volgyerdo.commons.math.tensor.FloatTensor;
import volgyerdo.commons.math.tensor.Tensor;
import volgyerdo.neural.logic.ActivationFactory;
import volgyerdo.neural.logic.LayerFactory;
import volgyerdo.neural.logic.NetworkFactory;
import volgyerdo.neural.logic.NetworkLogic;
import volgyerdo.neural.logic.NetworkUtils;
import volgyerdo.neural.structure.Network;

/**
 *
 * @author antal
 */
public class SerializationTest {
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException{
        Network network = NetworkFactory.createNetwork();

        NetworkFactory.addInputLayer(network, 
                LayerFactory.createInputLayer(5));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(10));
        NetworkFactory.addConvolutionalLayer(network,
                LayerFactory.createConvolutionalLayer(10),1);
        NetworkFactory.addDenseLayer(network, 
                LayerFactory.createDenseLayer(2));

        NetworkLogic.setLearningRate(network, 0.1f);
        NetworkLogic.setActivation(network, ActivationFactory.createSigmoid());
        NetworkLogic.randomizeWeights(network);
        
        //NetworkUtils.randomizeWeigths(weights);
        Tensor inputtensor = FloatTensor.create(Tensor.TYPE.FLOAT, 5);
        inputtensor.fill(1f);
        NetworkLogic.propagate(network, inputtensor);
        
        for (int i = 0; i < network.layers.size(); i++) {
            //System.out.println(network.layers.toString());
            System.out.println(network.layers.get(i).states.toString(true));
        }
        System.out.println("----Serialization-----\n");
    
        NetworkLogic.serialize(network, new FileOutputStream("file.txt"));
        
        Network serializedNetwork = NetworkLogic.deserializeNetwork(new FileInputStream("file.txt"));
        
        for (int i = 0; i < serializedNetwork.layers.size(); i++) {
            //System.out.println(network.layers.toString());
            System.out.println(serializedNetwork.layers.get(i).states.toString(true));
        }
        
        System.out.println("----Training Serialized network (dif. weights)-----\n");
        NetworkLogic.randomizeWeights(serializedNetwork);
        NetworkLogic.propagate(serializedNetwork, inputtensor);
        
        for (int i = 0; i < serializedNetwork.layers.size(); i++) {
            //System.out.println(network.layers.toString());
            System.out.println(serializedNetwork.layers.get(i).states.toString(true));
        }
    }
}
