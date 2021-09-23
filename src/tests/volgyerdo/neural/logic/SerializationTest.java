/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volgyerdo.neural.logic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import volgyerdo.commons.math.tensor.FloatTensor;
import volgyerdo.commons.math.tensor.Tensor;
import volgyerdo.neural.structure.Network;

/**
 *
 * @author antal
 */
public class SerializationTest {

    public SerializationTest() {
    }

    @Test
    public void checkNetworks() throws IOException, ClassNotFoundException {

        Network network = NetworkFactory.createNetwork();

        NetworkFactory.addInputLayer(network,
                LayerFactory.createInputLayer(5));
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(10));
        NetworkFactory.addConvolutionalLayer(network,
                LayerFactory.createConvolutionalLayer(10), 1);
        NetworkFactory.addDenseLayer(network,
                LayerFactory.createDenseLayer(2));

        NetworkLogic.setLearningRate(network, 0.1f);
        NetworkLogic.setActivation(network, ActivationFactory.createSigmoid());
        NetworkLogic.randomizeWeights(network);

        Tensor inputtensor = FloatTensor.create(Tensor.TYPE.FLOAT, 5);
        inputtensor.fill(1f);
        NetworkLogic.propagate(network, inputtensor);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        NetworkLogic.serializeNetwork(network, bos);

        byte[] data = bos.toByteArray();
        Network serializedNetwork = NetworkLogic.deserializeNetwork(
                new ByteArrayInputStream(data));

        assertEquals("Networks don't match.", network, serializedNetwork);
        assertEquals("Input layers don't match.", NetworkUtils.getInputLayer(network), NetworkUtils.getInputLayer(serializedNetwork));
        assertEquals("Input states don't match.", NetworkUtils.getInputStates(network), NetworkUtils.getInputStates(serializedNetwork));
        assertEquals("Output layers don't match.", NetworkUtils.getOutputLayer(network), NetworkUtils.getOutputLayer(serializedNetwork));
        assertEquals("Output states don't match.", NetworkUtils.getOutputStates(network), NetworkUtils.getOutputStates(serializedNetwork));

    }
}
