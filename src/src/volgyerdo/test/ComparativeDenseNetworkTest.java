/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volgyerdo.test;

import volgyerdo.neural.logic.ActivationLogic;
import volgyerdo.neural.logic.NetworkConstants;
import volgyerdo.neural.logic.NetworkFactory;
import volgyerdo.neural.logic.NetworkLogic;
import volgyerdo.neural.logic.NetworkUtils;
import volgyerdo.neural.logic.SampleFactory;
import volgyerdo.neural.structure.DenseLayer;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Sample;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class ComparativeDenseNetworkTest {
    
    public static void main(String[] args){
        
        System.out.println();
        
        activate(0.75);
        deactivate(0.3583574);
        
        Network network = NetworkFactory.createDenseNetwork(new int[]{2}, 1);
        
        NetworkLogic.setLearningRate(network, 0.1f);
        
        for(Layer layer : network.layers){
            if(layer instanceof DenseLayer){
                DenseLayer dLayer = (DenseLayer)layer;
                dLayer.weights.fill(0.5f);
            }
        }
        
        System.out.println("\nBefore backpropagation:\n");
        
        NetworkUtils.printWeights(network);
        
        Sample sample = SampleFactory.createSample(new float[]{1f, 0.5f}, new float[]{0.3f, 0.2f});
        
        NetworkUtils.getInputLayer(network).states.set(sample.input);
        
        NetworkLogic.propagate(network, sample.input);
        
        NetworkLogic.backPropagate(network, sample.target);
        
        System.out.println("\nAfter backpropagation:\n");
        
        NetworkUtils.printWeights(network);
    }
    
    
    private static void activate(double d){
        System.out.println("Activate " + ((float)d) + " : " 
                + ActivationLogic.activate((float)d, NetworkConstants.DEFAULT_ACTIVATION));
    }
    
    private static void deactivate(double d){
        System.out.println("Deactivate " + ((float)d) + " : " 
                + ActivationLogic.deactivate((float)d, NetworkConstants.DEFAULT_ACTIVATION));
    }
}
