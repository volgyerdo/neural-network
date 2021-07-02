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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.ConvolutionalLayer;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Sample;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class SimpleConvolutionalTests {

    private static final DecimalFormat FORMAT = new DecimalFormat("0.000");
    
    @Test
    public void convolutionalTest1() {
        double maximumError = 0.001;
        
        Network network = NetworkFactory.createNetwork(Tensor.TYPE.FLOAT);

        NetworkFactory.addConvolutionalLayer(network,
                LayerFactory.createConvolutionalLayer(Tensor.TYPE.FLOAT, 20), 0);
        NetworkFactory.addConvolutionalLayer(network, 
                LayerFactory.createConvolutionalLayer(Tensor.TYPE.FLOAT, 20), 3);

        NetworkLogic.setLearningRate(network, 0.1f);
        NetworkLogic.setActivation(network, ActivationFactory.createTanH());
        NetworkLogic.randomizeWeights(network);

        List<Sample> samples = new ArrayList<>();
        samples.add(SampleFactory.createSample(
                new float[]{0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0}, 
                new float[]{1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, 
                new float[]{0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, 
                new float[]{0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, 
                new float[]{0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                new float[]{0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                new float[]{0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, 
                new float[]{0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, 
                new float[]{0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, 
                new float[]{0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, 
                new float[]{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}));

        System.out.println("\nBefore training:\n");
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        for (Sample sample : samples) {
            NetworkLogic.propagate(network, sample.input);
            double error = 0;
            for(int i = 0; i<sample.target.dimensions[0]; i++){
                error += sample.target.getFloatValue(i) - outputLayer.states.getFloatValue(i);
            }
            error /= sample.target.dimensions[0];
            System.out.println("Error: " + FORMAT.format(error));
        }
        
        NetworkLogic.fit(network, samples, 2000);

        System.out.println("\nAfter training:\n");
        for (Sample sample : samples) {
            NetworkLogic.propagate(network, sample.input);
            double error = 0;
            for(int i = 0; i<sample.target.dimensions[0]; i++){
                error += sample.target.getFloatValue(i) - outputLayer.states.getFloatValue(i);
            }
            error /= sample.target.dimensions[0];
            System.out.println(FORMAT.format(error));
            assertTrue("Sample error is too large (" + error + ")", maximumError > Math.abs(error));
        }
        
        System.out.println("\n" + ((ConvolutionalLayer)outputLayer).kernel.toString(true));
    }
    
    @Test
    public void convolutionalTest2() {
        double maximumError = 0.01;
        
        Network network = NetworkFactory.createNetwork(Tensor.TYPE.FLOAT);

        NetworkFactory.addConvolutionalLayer(network,
                LayerFactory.createConvolutionalLayer(Tensor.TYPE.FLOAT, 20), 0);
        NetworkFactory.addConvolutionalLayer(network, 
                LayerFactory.createConvolutionalLayer(Tensor.TYPE.FLOAT, 20), 3);
        NetworkFactory.addConvolutionalLayer(network, 
                LayerFactory.createConvolutionalLayer(Tensor.TYPE.FLOAT, 20), 3);

        NetworkLogic.setLearningRate(network, 0.1f);
        NetworkLogic.setActivation(network, ActivationFactory.createTanH());
        NetworkLogic.randomizeWeights(network);

        List<Sample> samples = new ArrayList<>();
        samples.add(SampleFactory.createSample(
                new float[]{0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0}, 
                new float[]{0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, 
                new float[]{0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, 
                new float[]{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, 
                new float[]{0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                new float[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                new float[]{0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, 
                new float[]{0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, 
                new float[]{0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, 
                new float[]{0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0}));
        samples.add(SampleFactory.createSample(
                new float[]{0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, 
                new float[]{0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0}));

        System.out.println("\nBefore training:\n");
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        for (Sample sample : samples) {
            NetworkLogic.propagate(network, sample.input);
            double error = 0;
            for(int i = 0; i<sample.target.dimensions[0]; i++){
                error += sample.target.getFloatValue(i) - outputLayer.states.getFloatValue(i);
            }
            error /= sample.target.dimensions[0];
            System.out.println("Error: " + FORMAT.format(error));
        }
        
        NetworkLogic.fit(network, samples, 6000);

        System.out.println("\nAfter training:\n");
        for (Sample sample : samples) {
            NetworkLogic.propagate(network, sample.input);
            double error = 0;
            for(int i = 0; i<sample.target.dimensions[0]; i++){
                error += sample.target.getFloatValue(i) - outputLayer.states.getFloatValue(i);
            }
            error /= sample.target.dimensions[0];
            System.out.println("Error" + FORMAT.format(error));
            assertTrue("Sample error is too large (" + error + ")", maximumError > Math.abs(error));
        }
        
        System.out.println("\nW1 = " + ((ConvolutionalLayer)network.layers.get(1)).kernel.toString(true));
        System.out.println("\nB1 = " + ((ConvolutionalLayer)network.layers.get(1)).bias.toString(true));
        System.out.println("\nW2 = " + ((ConvolutionalLayer)network.layers.get(2)).kernel.toString(true));
        System.out.println("\nB2 = " + ((ConvolutionalLayer)network.layers.get(2)).bias.toString(true));
    }

}
