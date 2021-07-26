/*
 * Copyright 2021 antal.
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
import java.util.Random;
import java.util.Collection;
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
import volgyerdo.neural.structure.TestResults;

/**
 *
 * @author antal
 */
public class LineRecognitionTest {

    private static final Random RANDOM_INT = new Random();
    private static int LINE_LENGHT = 2; //min0 max5
    private static int LINE_COUNT = 4; //min 0 max4
    private static int TRAINING_SAMPLE_COUNT = 10;
    private static int ROWCOUNT = 5;
    private static int TRAIN_CYCLES = 5000; //forward and back prop (4000-8000)
    private static final DecimalFormat FORMAT = new DecimalFormat("0.000");

    public static void main(String[] args) {

        //network
        Network network = NetworkFactory.createNetwork();
        System.out.println("Start...");

        NetworkFactory.addInputLayer(network, LayerFactory.createInputLayer(5,5));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(10, 10));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(10, 10));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(10, 10));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(10, 10));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(10, 10));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(10, 10));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(5));
        System.out.println("Layers Added...");

        NetworkLogic.setLearningRate(network, 0.01f);
        NetworkLogic.setActivation(network, ActivationFactory.createSigmoid());
        NetworkLogic.randomizeWeights(network);
        System.out.println("LR, Activation, weights set...");

        //samples
        List<Sample> trainingSamples = new ArrayList<>();
        generateSamples(trainingSamples, TRAINING_SAMPLE_COUNT);
        
        List<Sample> controlSamples = new ArrayList<>();
        generateSamples(controlSamples, TRAINING_SAMPLE_COUNT);
        System.out.println("Samples added...");

        //training
        System.out.println("fitting...");
        NetworkLogic.train(network, trainingSamples, TRAIN_CYCLES);
        
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        System.out.println("\nTraining:\n");
        TestResults training = NetworkLogic.test(network, trainingSamples);
        NetworkUtils.printTestResults(training);

        System.out.println("\nControl:\n");
        TestResults control = NetworkLogic.test(network, controlSamples);
        NetworkUtils.printTestResults(control);
        //Live teszt
        //guessNumber(network, 100);
        

//        //mátrix teszt
//        int lines = 2;
//        for (int i = 0; i < 10; i++) {
//            System.out.println(lines);
//            Tensor teszt = generateTensorWithLines(lines);
//            System.out.println(teszt.toString(true));
//        }
    }
    
    private static void generateSamples(Collection<Sample> samples, int count){
        for (int i = 0; i < count; i++) {
            samples.add(createLineSample());
        }
    }
    
    private static Sample createLineSample(){ 
        int r = RANDOM_INT.nextInt(LINE_COUNT + 1); 
        Tensor input = generateTensorWithLines(r);
        Tensor target = Tensor.create(Tensor.TYPE.FLOAT, 5);
        target.setFloatValue(1, r);
        
        return SampleFactory.createSample(input, target);
    }
    
    public static void guessNumber(Network network, int numberOfMatrices) {
        System.out.println("");
        System.out.println("guessNumber teszt: ");

        Random rand = new Random();
        int generatedNumber;
        int guessedRight = 0;

        for (int j = 0; j < numberOfMatrices; j++) {

            Layer testoutputLayer = NetworkUtils.getOutputLayer(network);
            generatedNumber = rand.nextInt(5);  //max 4
            Tensor testMatrix = generateTensorWithLines(generatedNumber);
            System.out.println(testMatrix.toString(true));
            NetworkLogic.propagate(network, testMatrix);

            float max = testoutputLayer.states.getFloatValue(0);
            int maxi = 0;
            for (int i = 0; i < 4; i++) {
                if (testoutputLayer.states.getFloatValue(i + 1) > max) {
                    max = testoutputLayer.states.getFloatValue(i + 1);
                    maxi = i + 1;
                }
            }

            System.out.println("I see " + maxi + " line(s). (" + FORMAT.format(testoutputLayer.states.getFloatValue(maxi) * 100) + " %)");
            System.out.println("//////////////");
            if (generatedNumber == maxi) {
                guessedRight++;
            }
        }
        System.out.println("");
        System.out.println("I guessed " + guessedRight + "/" + numberOfMatrices + " right.");
    }
    
    public static Tensor generateTensorWithLines(int linecount) {
        int linelenght = LINE_LENGHT;

//        Tensor matrix = Tensor.create(Tensor.TYPE.FLOAT, 5,5);
//        matrix.fill(0f);
        float[][] matrix = new float[5][5];
        List<Integer> rows = new ArrayList<Integer>();

        Random rand = new Random();
        int n;
        for (int i = 0; i < linecount;) {
            n = rand.nextInt(ROWCOUNT);
            if (!rows.contains(n)) {
                rows.add(n);
                i++;
            }
        }

        List<Integer> columns = new ArrayList<Integer>();
        for (int i = 0; i < linecount; i++) {
            columns.add(rand.nextInt(ROWCOUNT + 1 - linelenght));
        }

        int x;
        int y;
        for (int i = 0; i < linecount; i++) {
            for (int j = 0; j < linelenght; j++) {
                
                x = rows.get(i);
                y = columns.get(i);
//                System.out.println(x+" "+y);
//                matrix.setFloatValue(1f, ?); //????
                matrix[x][y + j]
                        = 1f;
            }
        }
        
        int counter = 0;
        Tensor toReturn = Tensor.create(Tensor.TYPE.FLOAT, 5, 5);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                toReturn.setFloatValue(matrix[j][i], counter++);
            }
        }
        return toReturn;
    }
}
