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
import volgyerdo.neural.logic.SampleFactory;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Sample;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class LayeredXorTest {

    public static void main(String[] args) {
        Network network = NetworkFactory.createLayeredNetwork(Tensor.TYPE.FLOAT);

        Layer inputLayer = LayerFactory.createLayer(Tensor.TYPE.FLOAT, 2);
        NetworkFactory.addFullyConnectedLayer(network, inputLayer);

        Layer hiddenLayer = LayerFactory.createLayer(Tensor.TYPE.FLOAT, 10);
        NetworkFactory.addFullyConnectedLayer(network, hiddenLayer);

        Layer outputLayer = LayerFactory.createLayer(Tensor.TYPE.FLOAT, 1);
        NetworkFactory.addFullyConnectedLayer(network, outputLayer);

        network.activation = ActivationFactory.createTanH();
        NetworkLogic.randomizeWeights(network);

        List<Sample> samples = new ArrayList<>();
        samples.add(SampleFactory.createSample(new float[]{0f, 0f}, new float[]{0f}));
        samples.add(SampleFactory.createSample(new float[]{1f, 0f}, new float[]{1f}));
        samples.add(SampleFactory.createSample(new float[]{0f, 1f}, new float[]{1f}));
        samples.add(SampleFactory.createSample(new float[]{1f, 1f}, new float[]{0f}));

        NetworkLogic.fit(network, samples, 50000);

        System.out.println("\nAfter training:\n");

        DecimalFormat format = new DecimalFormat("0.000");

        for (Sample sample : samples) {
            inputLayer.states = sample.input;
            NetworkLogic.propagate(network);
            System.out.println(sample.input.getFloatValue(0) + " XOR " + sample.input.getFloatValue(1) + " = "
                    + format.format(outputLayer.states.getFloatValue(0))
                    + " (error=" + format.format(sample.target.getFloatValue(0) - outputLayer.states.getFloatValue(0)) + ")");
        }
    }

}
