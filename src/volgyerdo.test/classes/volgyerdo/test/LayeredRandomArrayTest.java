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
package volgyerdo.test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.logic.ActivationFactory;
import volgyerdo.neural.logic.LayerFactory;
import volgyerdo.neural.logic.NetworkFactory;
import volgyerdo.neural.logic.NetworkLogic;
import volgyerdo.neural.logic.NetworkUtils;
import volgyerdo.neural.logic.SampleFactory;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Sample;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class LayeredRandomArrayTest {

    private static final DecimalFormat FORMAT = new DecimalFormat("0.000");
    
    public static void main(String[] args) {
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
        Layer outputLayer = NetworkUtils.getInputLayer(network);
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
    }

    private static float[] convertStrToFloat(String str) {
        float[] floats = new float[str.length()];
        for (int i = 0; i < floats.length; i++) {
            floats[i] = (str.charAt(i) - 'a') / (float) ('z' - 'a');
        }
        return floats;
    }

}
