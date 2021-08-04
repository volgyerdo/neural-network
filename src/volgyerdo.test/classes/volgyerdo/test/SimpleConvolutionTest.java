/*
 * Copyright 2021 User.
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
package volgyerdo.test;

import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.logic.LayerFactory;
import volgyerdo.neural.logic.NetworkFactory;
import volgyerdo.neural.logic.NetworkLogic;
import volgyerdo.neural.logic.NetworkUtils;
import volgyerdo.neural.logic.SampleFactory;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Sample;

/**
 *
 * @author User
 */
public class SimpleConvolutionTest {

    public static void main(String[] args) {

        //prefix
        int dimensions[];
        dimensions = new int[]{4, 4};
        int kernelDimensions[];
        kernelDimensions = new int[]{3,3};
        Tensor inputStates = Tensor.create(Tensor.TYPE.FLOAT, 4);
        inputStates.fill(0.5f);
        
        //network
        Network network = NetworkFactory.createNetwork();
        NetworkLogic.setLearningRate(network, 0.01f);
        //NetworkLogic.randomizeWeights(network);
        
        //layers
        NetworkFactory.addInputLayer(network,
                LayerFactory.createInputLayer(4));
        NetworkFactory.addConvolutionalLayer(network, 
                LayerFactory.createConvolutionalLayer(4), 3);
        
        //prop
        Tensor input = Tensor.create(Tensor.TYPE.FLOAT, 3);
        input.fill(1f);
        NetworkLogic.propagate(network, input);
        Tensor target = Tensor.create(Tensor.TYPE.FLOAT, 3);
        
        Sample sample = SampleFactory.createSample(input, target);
        
        NetworkLogic.train(network, sample);
        
        //print
        System.out.println("1st\nInput:\n");
        System.out.println(NetworkUtils.getInputDimensions(network).toString(true));
        System.out.println("Output\n");
        System.out.println(NetworkUtils.getOutputDimensions(network).toString(true));
    }
}
