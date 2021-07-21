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
import volgyerdo.neural.logic.ActivationFactory;
import volgyerdo.neural.logic.NetworkFactory;
import volgyerdo.neural.logic.NetworkLogic;
import volgyerdo.neural.logic.NetworkUtils;
import volgyerdo.neural.logic.SampleFactory;
import volgyerdo.neural.logic.TestAnalysesLogic;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Sample;
import volgyerdo.neural.structure.TestAnalyses;
import volgyerdo.neural.structure.TestRowAnalyses;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class SimpleTest {

    private static final DecimalFormat FORMAT = new DecimalFormat("0.000");
    
    public static void main(String[] args) {
        Network network = NetworkFactory.createDenseNetwork(new int[]{2}, 4);
        NetworkLogic.setLearningRate(network, 0.1f);
        NetworkLogic.setActivation(network, ActivationFactory.createTanH());
        NetworkLogic.randomizeWeights(network);

        List<Sample> samples = new ArrayList<>();
        samples.add(SampleFactory.createSample(new float[]{0f, 1f}, new float[]{0.9f, -0.5f}));
        samples.add(SampleFactory.createSample(new float[]{1f, 0f}, new float[]{0.4f, 0.1f}));

        NetworkLogic.train(network, samples, 5000);

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
        }
        
        TestAnalyses analysis = TestAnalysesLogic.analyze(network.testData);
        NetworkUtils.printAnalysis(analysis);
        
        TestRowAnalyses rowAnalysis = TestAnalysesLogic.rowAnalyze(network.testData);
        NetworkUtils.printRowAnalysis(rowAnalysis);
    }


}
