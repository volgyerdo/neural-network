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
import java.util.Arrays;
import volgyerdo.neural.logic.NetworkFactory;
import volgyerdo.neural.structure.Network;
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
 * @author antal
 */
public class LineRecognitionTest {

    private static int LINE_LENGHT = 3; //min0 max5
    private static int LINE_COUNT = 2; //max4 min0
    private static int TRAINING_SAMPLE_COUNT = 10;
    private static int ROWCOUNT = 5;
    private static final DecimalFormat FORMAT = new DecimalFormat("0.000");

    public static void main(String[] args) {

        Network network = NetworkFactory.createNetwork(Tensor.TYPE.FLOAT);
        System.out.println("Start...");

        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 25));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 10, 10));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 10, 10));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 10, 10));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 5));
        System.out.println("Layers Added...");

        NetworkLogic.setLearningRate(network, 0.01f);
        NetworkLogic.setActivation(network, ActivationFactory.createSigmoid());
        NetworkLogic.randomizeWeights(network);
        System.out.println("LR, Activation, weights set...");

        List<Sample> samples = new ArrayList<>();
        for (int i = 0; i < TRAINING_SAMPLE_COUNT; i++) {
            samples.add(SampleFactory.createSample(generateMatrixWithLines(0), new float[]{1f, 0f, 0f, 0f, 0f}));
            samples.add(SampleFactory.createSample(generateMatrixWithLines(1), new float[]{0f, 1f, 0f, 0f, 0f}));
            samples.add(SampleFactory.createSample(generateMatrixWithLines(2), new float[]{0f, 0f, 1f, 0f, 0f}));
            samples.add(SampleFactory.createSample(generateMatrixWithLines(3), new float[]{0f, 0f, 0f, 1f, 0f}));
            samples.add(SampleFactory.createSample(generateMatrixWithLines(4), new float[]{0f, 0f, 0f, 0f, 1f}));
        }

        List<Sample> controlsamples = new ArrayList<>();
        for (int i = 0; i < TRAINING_SAMPLE_COUNT; i++) {
            controlsamples.add(SampleFactory.createSample(generateMatrixWithLines(0), new float[]{1f, 0f, 0f, 0f, 0f}));
            controlsamples.add(SampleFactory.createSample(generateMatrixWithLines(1), new float[]{0f, 1f, 0f, 0f, 0f}));
            controlsamples.add(SampleFactory.createSample(generateMatrixWithLines(2), new float[]{0f, 0f, 1f, 0f, 0f}));
            controlsamples.add(SampleFactory.createSample(generateMatrixWithLines(3), new float[]{0f, 0f, 0f, 1f, 0f}));
            controlsamples.add(SampleFactory.createSample(generateMatrixWithLines(4), new float[]{0f, 0f, 0f, 0f, 1f}));
        }
        System.out.println("Samples added...");

        System.out.println("fitting...");
        NetworkLogic.fit(network, samples, 6000);

        System.out.println("\nAfter training:\n");
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        double errorZero = 0;
        double errorOne = 0;
        double errorTwo = 0;
        double errorThree = 0;
        double errorFour = 0;

        int n = 0;
        for (Sample sample : samples) {
            NetworkLogic.propagate(network, sample.input);
            System.out.println("Oirinal: " + sample.target.getFloatValue(0) + ", " + sample.target.getFloatValue(1)
                    + ", " + sample.target.getFloatValue(2)
                    + ", " + sample.target.getFloatValue(3)
                    + ", " + sample.target.getFloatValue(4)
                    + " -- "
                    + FORMAT.format(outputLayer.states.getFloatValue(0)) + ", "
                    + FORMAT.format(outputLayer.states.getFloatValue(1)) + ", "
                    + FORMAT.format(outputLayer.states.getFloatValue(2)) + ", "
                    + FORMAT.format(outputLayer.states.getFloatValue(3)) + ", "
                    + FORMAT.format(outputLayer.states.getFloatValue(4)) + ", "
                    + " (error= " + FORMAT.format(sample.target.getFloatValue(0) - outputLayer.states.getFloatValue(0))
                    + ", " + FORMAT.format(sample.target.getFloatValue(1) - outputLayer.states.getFloatValue(1))
                    + ", " + FORMAT.format(sample.target.getFloatValue(2) - outputLayer.states.getFloatValue(2))
                    + ", " + FORMAT.format(sample.target.getFloatValue(3) - outputLayer.states.getFloatValue(3))
                    + ", " + FORMAT.format(sample.target.getFloatValue(4) - outputLayer.states.getFloatValue(4)) + ")"
            );
            errorZero += Math.abs(sample.target.getFloatValue(0) - outputLayer.states.getFloatValue(0));
            errorOne += Math.abs(sample.target.getFloatValue(1) - outputLayer.states.getFloatValue(1));
            errorTwo += Math.abs(sample.target.getFloatValue(2) - outputLayer.states.getFloatValue(2));
            errorThree += Math.abs(sample.target.getFloatValue(3) - outputLayer.states.getFloatValue(3));
            errorFour += Math.abs(sample.target.getFloatValue(4) - outputLayer.states.getFloatValue(4));
            n++;
        }
        errorZero /= n;
        errorOne /= n;
        errorTwo /= n;
        errorThree /= n;
        errorFour /= n;
        double avg = (errorZero + errorOne + errorTwo + errorThree + errorFour) / 5;

        System.out.println("Average error: " + avg);
        System.out.println("0: " + errorZero);
        System.out.println("1: " + errorOne);
        System.out.println("2: " + errorTwo);
        System.out.println("3: " + errorThree);
        System.out.println("4: " + errorFour);

        for (Sample sample : controlsamples) {
            NetworkLogic.propagate(network, sample.input);
            System.out.println("Control: " + sample.target.getFloatValue(0) + ", " + sample.target.getFloatValue(1)
                    + ", " + sample.target.getFloatValue(2)
                    + ", " + sample.target.getFloatValue(3)
                    + ", " + sample.target.getFloatValue(4)
                    + " -- "
                    + FORMAT.format(outputLayer.states.getFloatValue(0)) + ", "
                    + FORMAT.format(outputLayer.states.getFloatValue(1)) + ", "
                    + FORMAT.format(outputLayer.states.getFloatValue(2)) + ", "
                    + FORMAT.format(outputLayer.states.getFloatValue(3)) + ", "
                    + FORMAT.format(outputLayer.states.getFloatValue(4)) + ", "
                    + " (error= " + FORMAT.format(sample.target.getFloatValue(0) - outputLayer.states.getFloatValue(0))
                    + ", " + FORMAT.format(sample.target.getFloatValue(1) - outputLayer.states.getFloatValue(1))
                    + ", " + FORMAT.format(sample.target.getFloatValue(2) - outputLayer.states.getFloatValue(2))
                    + ", " + FORMAT.format(sample.target.getFloatValue(3) - outputLayer.states.getFloatValue(3))
                    + ", " + FORMAT.format(sample.target.getFloatValue(4) - outputLayer.states.getFloatValue(4)) + ")"
            );

            errorZero += Math.abs(sample.target.getFloatValue(0) - outputLayer.states.getFloatValue(0));
            errorOne += Math.abs(sample.target.getFloatValue(1) - outputLayer.states.getFloatValue(1));
            errorTwo += Math.abs(sample.target.getFloatValue(2) - outputLayer.states.getFloatValue(2));
            errorThree += Math.abs(sample.target.getFloatValue(3) - outputLayer.states.getFloatValue(3));
            errorFour += Math.abs(sample.target.getFloatValue(4) - outputLayer.states.getFloatValue(4));
            n++;
        }
        errorZero /= n;
        errorOne /= n;
        errorTwo /= n;
        errorThree /= n;
        errorFour /= n;
        avg = (errorZero + errorOne + errorTwo + errorThree + errorFour) / 5;

        System.out.println("Average error: " + avg);
        System.out.println("0: " + errorZero);
        System.out.println("1: " + errorOne);
        System.out.println("2: " + errorTwo);
        System.out.println("3: " + errorThree);
        System.out.println("4: " + errorFour);

        //Live teszt
        Random rand = new Random();
        for (int j = 0; j < 10; j++) {
            System.out.println("Vizualis teszt: ");
            Layer testoutputLayer = NetworkUtils.getOutputLayer(network);
            float[] testMatrix = generateMatrixWithLines(rand.nextInt(5));  //max 4
            writeMatrixToConsole(testMatrix, 5, 5);
            NetworkLogic.propagate(network, convertToTensor(testMatrix));
            for (int i = 0; i < 5; i++) {
                System.out.println(i + ":");
                System.out.println(testoutputLayer.states.getFloatValue(i) * 100 + " %");
            }
        }

        guessNumber(network, 10);
//        //Matrix test
//        for (int i = 0; i < 10; i++) {
//            float[] tesztmatrix = generateMatrixWithLines(2);
//            writeMatrixToConsole(tesztmatrix, 5, 5);
//            System.out.println("");
//        }
    }

    public static float[] generateMatrixWithLines(int linecount) {
        int linelenght = LINE_LENGHT;

        float[][] matrix = new float[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = 0;
            }
        }

        int[] linedrows = new int[linecount];
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
                matrix[x][y + j]
                        = 1f;
            }
        }

        //flatten dimensions
        ArrayList<Float> list = new ArrayList<Float>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                list.add(matrix[i][j]);
            }
        }

        float[] toReturn = new float[list.size()];
        int i = 0;

        for (Float f : list) {
            toReturn[i++] = (f != null ? f : Float.NaN);
        }
        return toReturn;
    }

    public static void writeMatrixToConsole(float[][] matrix, int x, int y) {
        for (int i = 0; i < x; i++) {
            System.out.print("[ ");
            for (int j = 0; j < y; j++) {
                System.out.print((int) matrix[i][j]);
                System.out.print(" ");
            }
            System.out.println("]");
        }
    }

    public static void writeMatrixToConsole(float[] matrix, int x, int y) {
        int counter = 0;
        for (int i = 0; i < x; i++) {
            System.out.print("[ ");
            for (int j = 0; j < y; j++) {
                System.out.print((int) matrix[counter++]);
                System.out.print(" ");
            }
            System.out.println("]");
        }
    }

    public static Tensor convertToTensor(float[] matrix) {
        Tensor Test = Tensor.create(Tensor.TYPE.FLOAT, 25);
        Test.setFloatArray(matrix);
        return Test;
    }

    public static void guessNumber(Network network, int numberOfMatrices) {
        System.out.println("guessNumber teszt: ");

        Random rand = new Random();
        int generatedNumber;
        int guessedRight = 0;

        for (int j = 0; j < numberOfMatrices; j++) {

            Layer testoutputLayer = NetworkUtils.getOutputLayer(network);
            generatedNumber = rand.nextInt(5);  //max 4
            float[] testMatrix = generateMatrixWithLines(generatedNumber);
            writeMatrixToConsole(testMatrix, 5, 5);
            NetworkLogic.propagate(network, convertToTensor(testMatrix));

            float max = testoutputLayer.states.getFloatValue(0);
            int maxi = 0;
            for (int i = 0; i < 4; i++) {
                if (testoutputLayer.states.getFloatValue(i + 1) > max) {
                    max = testoutputLayer.states.getFloatValue(i + 1);
                    maxi = i + 1;
                }
            }
//            System.out.println("");
//            for (int i = 0; i < 5; i++) {
//                System.out.println(testoutputLayer.states.getFloatValue(i));
//            }

            System.out.println("I see " + maxi + " line(s). (" + FORMAT.format(testoutputLayer.states.getFloatValue(maxi) * 100) + " %)");
//            for (int i = 0; i < 5; i++) {
//                System.out.println(i + ":");
//                System.out.println(testoutputLayer.states.getFloatValue(i) * 100 + " %");
//            }

            if (generatedNumber == maxi) {
                guessedRight++;
            }

        }
        System.out.println("I guessed " + guessedRight + "/" + numberOfMatrices + " right.");
    }
}
