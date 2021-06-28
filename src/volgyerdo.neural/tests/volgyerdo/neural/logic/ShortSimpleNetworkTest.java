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
public class ShortSimpleNetworkTest {

    private static final DecimalFormat FORMAT = new DecimalFormat("0.000");
    
    @Test
    public void layeredSimpleTest() {
        double maximumError = 0.2;

        Network network = NetworkFactory.createDenseNetwork(Tensor.TYPE.SHORT, new int[]{2}, 4);
        NetworkLogic.setLearningRate(network, 0.1f);
        NetworkLogic.setActivation(network, ActivationFactory.createTanH());
        NetworkLogic.randomizeWeights(network);

        List<Sample> samples = new ArrayList<>();
        samples.add(SampleFactory.createSample(new short[]{0, Short.MAX_VALUE}, 
                new short[]{(short)(0.9 * Short.MAX_VALUE), (short)(-0.5 * Short.MAX_VALUE)}));
        samples.add(SampleFactory.createSample(new short[]{Short.MAX_VALUE, 0}, 
                new short[]{(short)(0.4 * Short.MAX_VALUE), (short)(0.1 * Short.MAX_VALUE)}));

        NetworkLogic.fit(network, samples, 2000);

        System.out.println("\nAfter training:\n");

        Layer outputLayer = NetworkUtils.getOutputLayer(network);

        for (Sample sample : samples) {
            NetworkLogic.propagate(network, sample.input);
            Tensor outputStates = NetworkUtils.converToNormalFloat(outputLayer.states);
            Tensor sampleTarget = NetworkUtils.converToNormalFloat(sample.target);
            double error1 = outputStates.getFloatValue(0) - sampleTarget.getFloatValue(0);
            double error2 = outputStates.getFloatValue(0) - sampleTarget.getFloatValue(0);
            System.out.println(sampleTarget.getFloatValue(0)
                    + " -> " + FORMAT.format(outputStates.getFloatValue(0)) + " (error="
                    + (FORMAT.format(error1)) + ")");
            System.out.println(sampleTarget.getFloatValue(1) + " -> " + FORMAT.format(outputStates.getFloatValue(1))
                    + " (error=" + (FORMAT.format(error2)) + ")");
            assertTrue("Sample error 1 is too large (" + error1 + ")", maximumError > Math.abs(error1));
            assertTrue("Sample error 2 is too large (" + error2 + ")", maximumError > Math.abs(error2));
        }
    }

    @Test
    public void layeredXorTest() {
        double maximumError = 0.2;
        
        Network network = NetworkFactory.createNetwork();
        
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(Tensor.TYPE.SHORT, 2));
        NetworkFactory.addDenseLayer(network, 
                LayerFactory.createDenseLayer(Tensor.TYPE.SHORT, 10));
        NetworkFactory.addDenseLayer(network, 
                LayerFactory.createDenseLayer(Tensor.TYPE.SHORT, 10));
        NetworkFactory.addDenseLayer(network, 
                LayerFactory.createDenseLayer(Tensor.TYPE.SHORT, 10));
        NetworkFactory.addDenseLayer(network, 
                LayerFactory.createDenseLayer(Tensor.TYPE.SHORT, 1));

        NetworkLogic.setLearningRate(network, 0.06f);
        NetworkLogic.setActivation(network, ActivationFactory.createTanH());
        NetworkLogic.randomizeWeights(network);

        List<Sample> samples = new ArrayList<>();
        samples.add(SampleFactory.createSample(new short[]{0, 0}, new short[]{0}));
        samples.add(SampleFactory.createSample(new short[]{Short.MAX_VALUE, 0}, new short[]{Short.MAX_VALUE}));
        samples.add(SampleFactory.createSample(new short[]{0, Short.MAX_VALUE}, new short[]{Short.MAX_VALUE}));
        samples.add(SampleFactory.createSample(new short[]{Short.MAX_VALUE, Short.MAX_VALUE}, new short[]{0}));

        NetworkLogic.fit(network, samples, 50000);

        System.out.println("\nAfter training:\n");
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        for (Sample sample : samples) {
            NetworkLogic.propagate(network, sample.input);
            Tensor outputStates = NetworkUtils.converToNormalFloat(outputLayer.states);
            Tensor sampleTarget = NetworkUtils.converToNormalFloat(sample.target);
            Tensor sampleInput = NetworkUtils.converToNormalFloat(sample.input);
            double error = sampleTarget.getFloatValue(0) - outputStates.getFloatValue(0);
            System.out.println(sampleInput.getFloatValue(0) + " XOR " + sampleInput.getFloatValue(1) + " = "
                    + FORMAT.format(outputStates.getFloatValue(0))
                    + " (error=" + FORMAT.format(error) + ")");
            assertTrue("Sample error is too large (" + error + ")", maximumError > Math.abs(error));
        }
    }

    @Test
    public void layeredRandomArrayTest() {
        double maximumTrainError = 0.2;
        double maximumControlError = 0.2;
        
        Network network = NetworkFactory.createNetwork();

        NetworkFactory.addDenseLayer(network, 
                LayerFactory.createDenseLayer(Tensor.TYPE.SHORT, 30));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(Tensor.TYPE.SHORT, 90));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(Tensor.TYPE.SHORT, 60));
        NetworkFactory.addDenseLayer(network, 
                LayerFactory.createDenseLayer(Tensor.TYPE.SHORT, 2));

        NetworkLogic.setLearningRate(network, 0.01f);
        NetworkLogic.setActivation(network, ActivationFactory.createSigmoid());
        NetworkLogic.randomizeWeights(network);

        List<Sample> samples = new ArrayList<>();
        // Handwritten random strings
        samples.add(SampleFactory.createSample(convertStrToShort(
                "sdkfjrlaklfnlasdkfhlfhiwuilfgs"), new short[]{Short.MAX_VALUE, 0}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "lasdfwalerfhaskjfailwufhlasjcv"), new short[]{Short.MAX_VALUE, 0}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "alsdfkhnaweilurhklsdrhwekfhnoq"), new short[]{Short.MAX_VALUE, 0}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "sldfajkfhskajdfnawfklsaklsdiek"), new short[]{Short.MAX_VALUE, 0}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "asdfkljhawerkltujhasdjkmfbhasl"), new short[]{Short.MAX_VALUE, 0}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "sdkfjrlaklfnlasdkfhlfhiwuilfgs"), new short[]{Short.MAX_VALUE, 0}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "dfasdfklhsiaufzhaslfhjsfhjsdfh"), new short[]{Short.MAX_VALUE, 0}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "ajshflkjashfjksdhfjhjkghdfjkgh"), new short[]{Short.MAX_VALUE, 0}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "asdjkfhjkhsdjkfhsdjkfhsdjkfdsf"), new short[]{Short.MAX_VALUE, 0}));
        // Real random strings
        samples.add(SampleFactory.createSample(convertStrToShort(
                "hutspdbzygnpwwyywferdksnznopjg"), new short[]{0, Short.MAX_VALUE}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "jptwkbvinatpnkxkxgfsenpxxxakqy"), new short[]{0, Short.MAX_VALUE}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "oygjcuvgbavrpkjqhhhjimmxmpgmed"), new short[]{0, Short.MAX_VALUE}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "fiibxbbwtqvylsghtywowsajopujcu"), new short[]{0, Short.MAX_VALUE}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "qdoqxgxnaxuawcxmohwjsszbisqyeq"), new short[]{0, Short.MAX_VALUE}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "sonzmxvfhimwvsuwjneycufmozwzlg"), new short[]{0, Short.MAX_VALUE}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "fxixigyzltjwhjzbxbwvhxxtbqdsqr"), new short[]{0, Short.MAX_VALUE}));
        samples.add(SampleFactory.createSample(convertStrToShort(
                "icqjiojlewodspjyeiyroltrffbslz"), new short[]{0, Short.MAX_VALUE}));


        List<Sample> controlSamples = new ArrayList<>();
        // Handwritten random strings
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "asdfjkhsdkjfhajkfhkjdfhjkshskj"), new short[]{Short.MAX_VALUE, 0}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "asdfhlawrfhsdklfaweiorjhakfjdf"), new short[]{Short.MAX_VALUE, 0}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "jkwedrhilasdfhasdkjfhasdjfhsdf"), new short[]{Short.MAX_VALUE, 0}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "asdfjashfjkfjkasdfhasdgfjhsdgf"), new short[]{Short.MAX_VALUE, 0}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "asdlfkjhasdjklfhasjklfhjsdfhdj"), new short[]{Short.MAX_VALUE, 0}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "sajkfhsadjfhjksdfhsdjkfhjksdfh"), new short[]{Short.MAX_VALUE, 0}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "kjsfhjkwehfksdjafhksdjafhjklsf"), new short[]{Short.MAX_VALUE, 0}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "jkasdfhsjkdfhjksdafhsjdfhflsdk"), new short[]{Short.MAX_VALUE, 0}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "sdjkhsajklfhsdfjhsafhkfdnjkdje"), new short[]{Short.MAX_VALUE, 0}));
        // Real random strings
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "tinponefwevaxcnzbsrgylvsdggwnz"), new short[]{0, Short.MAX_VALUE}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "zjnhpkzuxgdvglczprztofdapyldpf"), new short[]{0, Short.MAX_VALUE}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "jvqnwfzpxribkdfvlmoelehgqonrvl"), new short[]{0, Short.MAX_VALUE}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "ufxrygoeemyzocktqjblayuzgysilm"), new short[]{0, Short.MAX_VALUE}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "klnxwvvaqfzvmufmemojxjmghjxwye"), new short[]{0, Short.MAX_VALUE}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "ocldqdspovenxiphmtnqmikwjlmevj"), new short[]{0, Short.MAX_VALUE}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "sqmdkxboykujmzmmgrwriawozeesuo"), new short[]{0, Short.MAX_VALUE}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "loegubmuwlpbnpkafhdycevkslhjfx"), new short[]{0, Short.MAX_VALUE}));
        controlSamples.add(SampleFactory.createSample(convertStrToShort(
                "ugufzgvvqdandagjjzzbbztserzawc"), new short[]{0, Short.MAX_VALUE}));

        System.out.println("\nBefore training:\n");
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        double errorHand = 0;
        double errorReal = 0;
        int n = 0;
        for (Sample sample : samples) {
            NetworkLogic.propagate(network, sample.input);
            Tensor outputStates = NetworkUtils.converToNormalFloat(outputLayer.states);
            Tensor sampleTarget = NetworkUtils.converToNormalFloat(sample.target);
            System.out.println("Original: " + sampleTarget.getFloatValue(0) + " - " + sampleTarget.getFloatValue(1) + " > "
                    + FORMAT.format(outputStates.getFloatValue(0)) + " - "
                    + FORMAT.format(outputStates.getFloatValue(1))
                    + " (error=" + FORMAT.format(sampleTarget.getFloatValue(0) - outputStates.getFloatValue(0))
                    + " - " + FORMAT.format(sampleTarget.getFloatValue(1) - outputStates.getFloatValue(1)) +")");
            errorHand += Math.abs(sampleTarget.getFloatValue(0) - outputStates.getFloatValue(0));
            errorReal += Math.abs(sampleTarget.getFloatValue(1) - outputStates.getFloatValue(1));
            n++;
        }
        errorHand /= n;
        errorReal /= n;
        System.out.println("Average error: " + errorHand + " - " + errorReal);
        
        NetworkLogic.fit(network, samples, 50000);

        System.out.println("\nAfter training:\n");
        outputLayer = NetworkUtils.getOutputLayer(network);
        errorHand = 0;
        errorReal = 0;
        n = 0;
        for (Sample sample : samples) {
            NetworkLogic.propagate(network, sample.input);
            Tensor outputStates = NetworkUtils.converToNormalFloat(outputLayer.states);
            Tensor sampleTarget = NetworkUtils.converToNormalFloat(sample.target);
            System.out.println("Original: " + sampleTarget.getFloatValue(0) + " - " + sampleTarget.getFloatValue(1) + " > "
                    + FORMAT.format(outputStates.getFloatValue(0)) + " - "
                    + FORMAT.format(outputStates.getFloatValue(1))
                    + " (error=" + FORMAT.format(sampleTarget.getFloatValue(0) - outputStates.getFloatValue(0))
                    + " - " + FORMAT.format(sampleTarget.getFloatValue(1) - outputStates.getFloatValue(1)) +")");
            errorHand += Math.abs(sampleTarget.getFloatValue(0) - outputStates.getFloatValue(0));
            errorReal += Math.abs(sampleTarget.getFloatValue(1) - outputStates.getFloatValue(1));
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
            Tensor outputStates = NetworkUtils.converToNormalFloat(outputLayer.states);
            Tensor sampleTarget = NetworkUtils.converToNormalFloat(sample.target);
            System.out.println("Control: " + sampleTarget.getFloatValue(0) + " - " + sampleTarget.getFloatValue(1) + " > "
                    + FORMAT.format(outputStates.getFloatValue(0)) + " - "
                    + FORMAT.format(outputStates.getFloatValue(1))
                    + " (error=" + FORMAT.format(sampleTarget.getFloatValue(0) - outputStates.getFloatValue(0))
                    + " - " + FORMAT.format(sampleTarget.getFloatValue(1) - outputStates.getFloatValue(1)) +")");
            errorHand += Math.abs(sampleTarget.getFloatValue(0) - outputStates.getFloatValue(0));
            errorReal += Math.abs(sampleTarget.getFloatValue(1) - outputStates.getFloatValue(1));
            n++;
        }
        errorHand /= n;
        errorReal /= n;
        System.out.println("Average error: " + errorHand + " - " + errorReal);
        assertTrue("Control hand set error is too large (" + errorHand + ")", maximumControlError > errorHand);
        assertTrue("Control real set error is too large (" + errorReal + ")", maximumControlError > errorReal);
    }
    
    private static short[] convertStrToShort(String str) {
        short[] shorts = new short[str.length()];
        for (int i = 0; i < shorts.length; i++) {
            shorts[i] = (short)((str.charAt(i) - 'a') / (float) ('z' - 'a') * Short.MAX_VALUE);
        }
        return shorts;
    }

}
