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

import volgyerdo.neural.logic.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Sample;
import volgyerdo.neural.structure.TestAnalyses;
import volgyerdo.neural.structure.TestRecord;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class PointCounterTest {

    private static final Random RANDOM_INT = new Random();
    private static final int MATRIX_SIZE = 5;
    private static final int HIDDEN_LAYER_SIZE = 5;
    private static final int MAX_POINT_COUNT = 5;
    private static final int TRAINING_SAMPLE_COUNT = 700;
    private static final int CONTROL_SAMPLE_COUNT = 700;

    public static void main(String[] args) {
        Network network = NetworkFactory.createNetwork();

        NetworkFactory.addInputLayer(network,
                LayerFactory.createInputLayer(MATRIX_SIZE, MATRIX_SIZE));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(HIDDEN_LAYER_SIZE, HIDDEN_LAYER_SIZE));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(HIDDEN_LAYER_SIZE, HIDDEN_LAYER_SIZE));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(HIDDEN_LAYER_SIZE, HIDDEN_LAYER_SIZE));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(HIDDEN_LAYER_SIZE, HIDDEN_LAYER_SIZE));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(MAX_POINT_COUNT + 1));

        NetworkLogic.setLearningRate(network, 0.008f);
        NetworkLogic.setActivation(network, ActivationFactory.createSigmoid());
        NetworkLogic.randomizeWeights(network);

        List<Sample> trainingSamples = new ArrayList<>();
        generateSamples(trainingSamples, TRAINING_SAMPLE_COUNT);

        NetworkLogic.train(network, trainingSamples, 100000);

        List<Sample> controlSamples = new ArrayList<>();
        generateSamples(controlSamples, CONTROL_SAMPLE_COUNT);

        System.out.println("\nTraining:\n");
        List<TestRecord> testData = NetworkLogic.test(network, trainingSamples);
        TestAnalyses analysis = TestAnalysesLogic.analyze(testData);
        NetworkUtils.printAnalysis(analysis);

        System.out.println("\nControl:\n");
        List<TestRecord> controlData = NetworkLogic.test(network, controlSamples);
        TestAnalyses controlAnalysis = TestAnalysesLogic.analyze(controlData);
        NetworkUtils.printAnalysis(controlAnalysis);
        
    }

    private static void generateSamples(Collection<Sample> samples, int count) {
        for (int i = 0; i < count; i++) {
            samples.add(createPointedSample());
        }
    }

    private static Sample createPointedSample() {
        int pointCount = RANDOM_INT.nextInt(MAX_POINT_COUNT + 1);
        Tensor input = createPointedTensor(pointCount, MATRIX_SIZE, MATRIX_SIZE);
        Tensor target = Tensor.create(Tensor.TYPE.FLOAT, MAX_POINT_COUNT + 1);
        target.setFloatValue(1, pointCount);
        return SampleFactory.createSample(input, target);
    }

    private static Tensor createPointedTensor(int pointCount, int... dimensions) {
        Tensor tensor = Tensor.create(Tensor.TYPE.FLOAT, dimensions);
        for (int i = 0; i < pointCount; i++) {
            int[] coordinates;
            do {
                coordinates = NetworkUtils.randomizeCoordinates(dimensions);
            } while (Float.compare(1.0f, tensor.getFloatValue(coordinates)) == 0);
            tensor.setFloatValue(1, coordinates);
        }
        return tensor;
    }

}
