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
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Sample;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class SimpleNetworkTest {

    private static final DecimalFormat FORMAT = new DecimalFormat("0.000");
    
    @Test
    public void layeredSimpleTest() {
        double maximumError = 0.005;

        Network network = NetworkFactory.createDenseNetwork(Tensor.TYPE.FLOAT, new int[]{2}, 4);
        NetworkLogic.setLearningRate(network, 0.1f);
        NetworkLogic.setActivation(network, ActivationFactory.createTanH());
        NetworkLogic.randomizeWeights(network);

        List<Sample> samples = new ArrayList<>();
        samples.add(SampleFactory.createSample(new float[]{0f, 1f}, new float[]{0.9f, -0.5f}));
        samples.add(SampleFactory.createSample(new float[]{1f, 0f}, new float[]{0.4f, 0.1f}));

        NetworkLogic.fit(network, samples, 2000);

        System.out.println("\nAfter training:\n");

        Layer outputLayer = NetworkUtils.getOutputLayer(network);

        for (Sample sample : samples) {
            NetworkLogic.propagate(network, sample.input);
            double error1 = outputLayer.states.getFloatValue(0) - sample.target.getFloatValue(0);
            double error2 = outputLayer.states.getFloatValue(0) - sample.target.getFloatValue(0);
            System.out.println(sample.target.getFloatValue(0)
                    + " -> " + FORMAT.format(outputLayer.states.getFloatValue(0)) + " (error="
                    + (FORMAT.format(error1)) + ")");
            System.out.println(sample.target.getFloatValue(1) + " -> " + FORMAT.format(outputLayer.states.getFloatValue(1))
                    + " (error=" + (FORMAT.format(error2)) + ")");
            assertTrue("Sample error 1 is too large (" + error1 + ")", maximumError > Math.abs(error1));
            assertTrue("Sample error 2 is too large (" + error2 + ")", maximumError > Math.abs(error2));
        }
    }

    @Test
    public void layeredXorTest() {
        double maximumError = 0.005;
        
        Network network = NetworkFactory.createNetwork();
        
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 2));
        NetworkFactory.addDenseLayer(network, 
                LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 10));
        NetworkFactory.addDenseLayer(network, 
                LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 1));

        NetworkLogic.setLearningRate(network, 0.1f);
        NetworkLogic.setActivation(network, ActivationFactory.createTanH());
        NetworkLogic.randomizeWeights(network);

        List<Sample> samples = new ArrayList<>();
        samples.add(SampleFactory.createSample(new float[]{0f, 0f}, new float[]{0f}));
        samples.add(SampleFactory.createSample(new float[]{1f, 0f}, new float[]{1f}));
        samples.add(SampleFactory.createSample(new float[]{0f, 1f}, new float[]{1f}));
        samples.add(SampleFactory.createSample(new float[]{1f, 1f}, new float[]{0f}));

        NetworkLogic.fit(network, samples, 50000);

        System.out.println("\nAfter training:\n");
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        for (Sample sample : samples) {
            NetworkLogic.propagate(network, sample.input);
            double error = sample.target.getFloatValue(0) - outputLayer.states.getFloatValue(0);
            System.out.println(sample.input.getFloatValue(0) + " XOR " + sample.input.getFloatValue(1) + " = "
                    + FORMAT.format(outputLayer.states.getFloatValue(0))
                    + " (error=" + FORMAT.format(error) + ")");
            assertTrue("Sample error is too large (" + error + ")", maximumError > Math.abs(error));
        }
    }

    @Test
    public void layeredRandomArrayTest() {
        double maximumTrainError = 0.08;
        double maximumControlError = 0.2;
        
        Network network = NetworkFactory.createNetwork();

        NetworkFactory.addDenseLayer(network, 
                LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 30));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 90));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 60));
        NetworkFactory.addDenseLayer(network, 
                LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 2));

        NetworkLogic.setLearningRate(network, 0.01f);
        NetworkLogic.setActivation(network, ActivationFactory.createSigmoid());
        NetworkLogic.randomizeWeights(network);

        List<Sample> samples = new ArrayList<>();
        // Handwritten random strings
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "sdkfjrlaklfnlasdkfhlfhiwuilfgs"), new float[]{1f, 0f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "lasdfwalerfhaskjfailwufhlasjcv"), new float[]{1f, 0f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "alsdfkhnaweilurhklsdrhwekfhnoq"), new float[]{1f, 0f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "sldfajkfhskajdfnawfklsaklsdiek"), new float[]{1f, 0f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "asdfkljhawerkltujhasdjkmfbhasl"), new float[]{1f, 0f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "sdkfjrlaklfnlasdkfhlfhiwuilfgs"), new float[]{1f, 0f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "dfasdfklhsiaufzhaslfhjsfhjsdfh"), new float[]{1f, 0f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "ajshflkjashfjksdhfjhjkghdfjkgh"), new float[]{1f, 0f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "asdjkfhjkhsdjkfhsdjkfhsdjkfdsf"), new float[]{1f, 0f}));
        // Real random strings
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "hutspdbzygnpwwyywferdksnznopjg"), new float[]{0f, 1f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "jptwkbvinatpnkxkxgfsenpxxxakqy"), new float[]{0f, 1f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "oygjcuvgbavrpkjqhhhjimmxmpgmed"), new float[]{0f, 1f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "fiibxbbwtqvylsghtywowsajopujcu"), new float[]{0f, 1f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "qdoqxgxnaxuawcxmohwjsszbisqyeq"), new float[]{0f, 1f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "sonzmxvfhimwvsuwjneycufmozwzlg"), new float[]{0f, 1f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "fxixigyzltjwhjzbxbwvhxxtbqdsqr"), new float[]{0f, 1f}));
        samples.add(SampleFactory.createSample(convertStrToFloat(
                "icqjiojlewodspjyeiyroltrffbslz"), new float[]{0f, 1f}));


        List<Sample> controlSamples = new ArrayList<>();
        // Handwritten random strings
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "asdfjkhsdkjfhajkfhkjdfhjkshskj"), new float[]{1f, 0f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "asdfhlawrfhsdklfaweiorjhakfjdf"), new float[]{1f, 0f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "jkwedrhilasdfhasdkjfhasdjfhsdf"), new float[]{1f, 0f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "asdfjashfjkfjkasdfhasdgfjhsdgf"), new float[]{1f, 0f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "asdlfkjhasdjklfhasjklfhjsdfhdj"), new float[]{1f, 0f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "sajkfhsadjfhjksdfhsdjkfhjksdfh"), new float[]{1f, 0f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "kjsfhjkwehfksdjafhksdjafhjklsf"), new float[]{1f, 0f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "jkasdfhsjkdfhjksdafhsjdfhflsdk"), new float[]{1f, 0f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "sdjkhsajklfhsdfjhsafhkfdnjkdje"), new float[]{1f, 0f}));
        // Real random strings
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "tinponefwevaxcnzbsrgylvsdggwnz"), new float[]{0f, 1f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "zjnhpkzuxgdvglczprztofdapyldpf"), new float[]{0f, 1f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "jvqnwfzpxribkdfvlmoelehgqonrvl"), new float[]{0f, 1f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "ufxrygoeemyzocktqjblayuzgysilm"), new float[]{0f, 1f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "klnxwvvaqfzvmufmemojxjmghjxwye"), new float[]{0f, 1f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "ocldqdspovenxiphmtnqmikwjlmevj"), new float[]{0f, 1f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "sqmdkxboykujmzmmgrwriawozeesuo"), new float[]{0f, 1f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "loegubmuwlpbnpkafhdycevkslhjfx"), new float[]{0f, 1f}));
        controlSamples.add(SampleFactory.createSample(convertStrToFloat(
                "ugufzgvvqdandagjjzzbbztserzawc"), new float[]{0f, 1f}));

        NetworkLogic.fit(network, samples, 50000);

        System.out.println("\nAfter training:\n");
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        double errorHand = 0;
        double errorReal = 0;
        int n = 0;
        for (Sample sample : samples) {
            NetworkLogic.propagate(network, sample.input);
            System.out.println("Original: " + sample.target.getFloatValue(0) + " - " + sample.target.getFloatValue(1) + " > "
                    + FORMAT.format(outputLayer.states.getFloatValue(0)) + " - "
                    + FORMAT.format(outputLayer.states.getFloatValue(1))
                    + " (error=" + FORMAT.format(sample.target.getFloatValue(0) - outputLayer.states.getFloatValue(0))
                    + " - " + FORMAT.format(sample.target.getFloatValue(1) - outputLayer.states.getFloatValue(1)) +")");
            errorHand += Math.abs(sample.target.getFloatValue(0) - outputLayer.states.getFloatValue(0));
            errorReal += Math.abs(sample.target.getFloatValue(1) - outputLayer.states.getFloatValue(1));
            n++;
        }
        errorHand /= n;
        errorReal /= n;
        System.out.println("Average error: " + errorHand + " - " + errorReal);
        assertTrue("Train hand set error is too large (" + errorHand + ")", maximumTrainError > errorHand);
        assertTrue("Train real set error is too large (" + errorReal + ")", maximumTrainError > errorReal);

        System.out.println();

        for (Sample sample : controlSamples) {
            NetworkLogic.propagate(network, sample.input);
            System.out.println("Control: " + sample.target.getFloatValue(0) + " - " + sample.target.getFloatValue(1) + " > "
                    + FORMAT.format(outputLayer.states.getFloatValue(0)) + " - "
                    + FORMAT.format(outputLayer.states.getFloatValue(1))
                    + " (error=" + FORMAT.format(sample.target.getFloatValue(0) - outputLayer.states.getFloatValue(0))
                    + " - " + FORMAT.format(sample.target.getFloatValue(1) - outputLayer.states.getFloatValue(1)) +")");
            errorHand += Math.abs(sample.target.getFloatValue(0) - outputLayer.states.getFloatValue(0));
            errorReal += Math.abs(sample.target.getFloatValue(1) - outputLayer.states.getFloatValue(1));
            n++;
        }
        errorHand /= n;
        errorReal /= n;
        System.out.println("Average error: " + errorHand + " - " + errorReal);
        assertTrue("Control hand set error is too large (" + errorHand + ")", maximumControlError > errorHand);
        assertTrue("Control real set error is too large (" + errorReal + ")", maximumControlError > errorReal);
    }
    
    private static float[] convertStrToFloat(String str) {
        float[] floats = new float[str.length()];
        for (int i = 0; i < floats.length; i++) {
            floats[i] = (str.charAt(i) - 'a') / (float) ('z' - 'a');
        }
        return floats;
    }

}
