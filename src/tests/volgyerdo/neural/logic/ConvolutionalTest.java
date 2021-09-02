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

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Sample;
import volgyerdo.neural.structure.TestAnalyses;
import volgyerdo.neural.structure.TestRecord;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class ConvolutionalTest {

    @Test
    public void test1(){
        double maximumError = 0.1;

        Network network = NetworkFactory.createNetwork();

        NetworkFactory.addInputLayer(network,
                LayerFactory.createInputLayer(20));
        NetworkFactory.addConvolutionalLayer(network,
                LayerFactory.createConvolutionalLayer(20), 3);
        NetworkFactory.addConvolutionalLayer(network,
                LayerFactory.createConvolutionalLayer(20), 3);

        NetworkLogic.setLearningRate(network, 0.01f);
        NetworkLogic.setActivation(network, ActivationFactory.createTanH());
        NetworkLogic.randomizeWeights(network);

        List<Sample> samples = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            samples.add(generateSample1(20));
        }

        NetworkLogic.train(network, samples, 50000, maximumError / 2, 10);

        List<TestRecord> testResults = NetworkLogic.test(network, samples);
        TestAnalyses analyzes = TestAnalysesLogic.analyze(testResults);
        NetworkUtils.printAnalysis(analyzes);
        assertTrue("Error: " + analyzes.errorArithmeticMean, analyzes.errorArithmeticMean < maximumError);
    }
    
    private Sample generateSample1(int n) {
        float[] input = new float[n];
        float[] target = new float[n];
        for (int i = 0; i < n; i++) {
            if (Math.random() < 0.1) {
                input[i] = 1;
                if (i < n - 2) {
                    target[i + 2] = 1;
                }
            }
        }
        return SampleFactory.createSample(input, target);
    }
    
    @Test
    public void test2() {
        double maximumError = 0.1;

        Network network = NetworkFactory.createNetwork();

        NetworkFactory.addInputLayer(network,
                LayerFactory.createInputLayer(20));
        NetworkFactory.addConvolutionalLayer(network,
                LayerFactory.createConvolutionalLayer(20), 3);

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

        NetworkLogic.train(network, samples, 2000, maximumError / 2, 10);

        List<TestRecord> testResults = NetworkLogic.test(network, samples);
        TestAnalyses analyzes = TestAnalysesLogic.analyze(testResults);
        NetworkUtils.printAnalysis(analyzes);
        assertTrue("Error: " + analyzes.errorArithmeticMean, analyzes.errorArithmeticMean < maximumError);
    }

    @Test
    public void test3() {
        double maximumError = 0.1;

        Network network = NetworkFactory.createNetwork();

        NetworkFactory.addInputLayer(network,
                LayerFactory.createInputLayer(20));
        NetworkFactory.addConvolutionalLayer(network,
                LayerFactory.createConvolutionalLayer(20), 3);
        NetworkFactory.addConvolutionalLayer(network,
                LayerFactory.createConvolutionalLayer(20), 3);

        NetworkLogic.setLearningRate(network, 0.01f);
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

        NetworkLogic.train(network, samples, 10000, maximumError / 2, 10);

        List<TestRecord> testResults = NetworkLogic.test(network, samples);
        TestAnalyses analyzes = TestAnalysesLogic.analyze(testResults);
        NetworkUtils.printAnalysis(analyzes);
        assertTrue("Error: " + analyzes.errorArithmeticMean, analyzes.errorArithmeticMean < maximumError);
    }
    
    public ConvolutionalTest() {
    }

}
