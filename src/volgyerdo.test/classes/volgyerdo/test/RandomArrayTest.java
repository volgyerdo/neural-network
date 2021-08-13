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

import java.util.ArrayList;
import java.util.List;
import volgyerdo.neural.logic.ActivationFactory;
import volgyerdo.neural.logic.LayerFactory;
import volgyerdo.neural.logic.NetworkFactory;
import volgyerdo.neural.logic.NetworkLogic;
import volgyerdo.neural.logic.NetworkUtils;
import volgyerdo.neural.logic.SampleFactory;
import volgyerdo.neural.logic.TestAnalysesLogic;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Sample;
import volgyerdo.neural.structure.TestAnalyses;
import volgyerdo.neural.structure.TestRecord;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class RandomArrayTest {

    public static void main(String[] args) {
        Network network = NetworkFactory.createNetwork();

        NetworkFactory.addInputLayer(network, 
                LayerFactory.createInputLayer(30));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(90));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(60));
        NetworkFactory.addDenseLayer(network, 
                LayerFactory.createDenseLayer(2));

        NetworkLogic.setLearningRate(network, 0.1f);
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

        NetworkLogic.train(network, samples, 10000);
        
        System.out.println("\nTraining:\n");
        List<TestRecord> testData = NetworkLogic.test(network, samples);
        TestAnalyses analysis = TestAnalysesLogic.analyze(testData);
        NetworkUtils.printAnalysis(analysis);

        System.out.println("\nControl:\n");
        List<TestRecord> controlData = NetworkLogic.test(network, controlSamples);
        TestAnalyses controlAnalysis = TestAnalysesLogic.analyze(controlData);
        NetworkUtils.printAnalysis(controlAnalysis);
    }

    private static float[] convertStrToFloat(String str) {
        float[] floats = new float[str.length()];
        for (int i = 0; i < floats.length; i++) {
            floats[i] = (str.charAt(i) - 'a') / (float) ('z' - 'a');
        }
        return floats;
    }

}
