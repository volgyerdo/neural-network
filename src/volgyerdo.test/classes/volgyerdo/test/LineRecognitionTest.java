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

    private static int LINE_LENGHT = 3; //max3 min2
    private static int LINE_COUNT = 2; //max4 min0
    private static final DecimalFormat FORMAT = new DecimalFormat("0.000");

    public static void main(String[] args) {

        Network network = NetworkFactory.createNetwork();

        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 25));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 50));
        NetworkFactory.addDenseLayer(network, LayerFactory.createDenseLayer(Tensor.TYPE.FLOAT, 5));

        NetworkLogic.setLearningRate(network, 0.01f);
        NetworkLogic.setActivation(network, ActivationFactory.createSigmoid());
        NetworkLogic.randomizeWeights(network);

        List<Sample> samples = new ArrayList<>();

        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(0)), new float[]{1f, 0f, 0f, 0f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(0)), new float[]{1f, 0f, 0f, 0f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(0)), new float[]{1f, 0f, 0f, 0f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(0)), new float[]{1f, 0f, 0f, 0f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(0)), new float[]{1f, 0f, 0f, 0f, 0f}));

        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(1)), new float[]{0f, 1f, 0f, 0f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(1)), new float[]{0f, 1f, 0f, 0f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(1)), new float[]{0f, 1f, 0f, 0f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(1)), new float[]{0f, 1f, 0f, 0f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(1)), new float[]{0f, 1f, 0f, 0f, 0f}));

        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(2)), new float[]{0f, 0f, 1f, 0f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(2)), new float[]{0f, 0f, 1f, 0f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(2)), new float[]{0f, 0f, 1f, 0f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(2)), new float[]{0f, 0f, 1f, 0f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(2)), new float[]{0f, 0f, 1f, 0f, 0f}));

        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(3)), new float[]{0f, 0f, 0f, 1f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(3)), new float[]{0f, 0f, 0f, 1f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(3)), new float[]{0f, 0f, 0f, 1f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(3)), new float[]{0f, 0f, 0f, 1f, 0f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(3)), new float[]{0f, 0f, 0f, 1f, 0f}));

        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(4)), new float[]{0f, 0f, 0f, 0f, 1f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(4)), new float[]{0f, 0f, 0f, 0f, 1f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(4)), new float[]{0f, 0f, 0f, 0f, 1f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(4)), new float[]{0f, 0f, 0f, 0f, 1f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(4)), new float[]{0f, 0f, 0f, 0f, 1f}));
        samples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(4)), new float[]{0f, 0f, 0f, 0f, 1f}));

        List<Sample> controlsamples = new ArrayList<>();

        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(0)), new float[]{1f, 0f, 0f, 0f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(0)), new float[]{1f, 0f, 0f, 0f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(0)), new float[]{1f, 0f, 0f, 0f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(0)), new float[]{1f, 0f, 0f, 0f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(0)), new float[]{1f, 0f, 0f, 0f, 0f}));

        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(1)), new float[]{0f, 1f, 0f, 0f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(1)), new float[]{0f, 1f, 0f, 0f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(1)), new float[]{0f, 1f, 0f, 0f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(1)), new float[]{0f, 1f, 0f, 0f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(1)), new float[]{0f, 1f, 0f, 0f, 0f}));

        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(2)), new float[]{0f, 0f, 1f, 0f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(2)), new float[]{0f, 0f, 1f, 0f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(2)), new float[]{0f, 0f, 1f, 0f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(2)), new float[]{0f, 0f, 1f, 0f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(2)), new float[]{0f, 0f, 1f, 0f, 0f}));

        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(3)), new float[]{0f, 0f, 0f, 1f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(3)), new float[]{0f, 0f, 0f, 1f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(3)), new float[]{0f, 0f, 0f, 1f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(3)), new float[]{0f, 0f, 0f, 1f, 0f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(3)), new float[]{0f, 0f, 0f, 1f, 0f}));

        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(4)), new float[]{0f, 0f, 0f, 0f, 1f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(4)), new float[]{0f, 0f, 0f, 0f, 1f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(4)), new float[]{0f, 0f, 0f, 0f, 1f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(4)), new float[]{0f, 0f, 0f, 0f, 1f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(4)), new float[]{0f, 0f, 0f, 0f, 1f}));
        controlsamples.add(SampleFactory.createSample(convertTo1D(generateMatrixWithLines(4)), new float[]{0f, 0f, 0f, 0f, 1f}));

        
        NetworkLogic.fit(network, samples, 50000);

        System.out.println("\nAfter training:\n");
        Layer outputLayer = NetworkUtils.getOutputLayer(network);
        double errorFirst = 0;
        double errorSecond = 0;
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
            errorFirst += Math.abs(sample.target.getFloatValue(0) - outputLayer.states.getFloatValue(0));
            errorSecond += Math.abs(sample.target.getFloatValue(1) - outputLayer.states.getFloatValue(1));
            n++;
        }
        errorFirst /= n;
        errorSecond /= n;
        System.out.println("Average error: " + errorFirst + " - " + errorSecond);
        System.out.println("");
        
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
            
            
            errorFirst += Math.abs(sample.target.getFloatValue(0) - outputLayer.states.getFloatValue(0));
            errorSecond += Math.abs(sample.target.getFloatValue(1) - outputLayer.states.getFloatValue(1));
            n++;
        }
        errorFirst /= n;
        errorSecond /= n;
        System.out.println("Average error: " + errorFirst + " - " + errorSecond);
        
        Random rand = new Random();
        for (int j = 0; j < 10; j++) {
           System.out.println("Vizualis teszt: ");
            Layer testoutputLayer = NetworkUtils.getOutputLayer(network);
            float[][] testMatrix = generateMatrixWithLines(rand.nextInt(4));
            writeMatrixToConsole(testMatrix,5,5);
            NetworkLogic.propagate(network, convertToTensor(testMatrix));
            for (int i = 0; i < 4; i++) {
               System.out.println(i + ":");
               System.out.println(testoutputLayer.states.getFloatValue(i)*100 + " %"); 
            } 
        }
        /*
        for (int i = 0; i < 10; i++) {
            float[][] tesztmatrix = generateMatrixWithLines(2);
            writeMatrixToConsole(tesztmatrix, 5, 5);
            System.out.println("");
        }
            */
    }

    public static float[][] generateMatrixWithLines(int linecount) {
        int linelenght = LINE_LENGHT;

        float[][] matrix = new float[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = 0;
            }
        }

        
        int[] linedrows = new int[linecount];

        Random rand = new Random();
        for (int i = 0; i < linecount; i++) {
            linedrows[i] = rand.nextInt(5);
        }

        if (linecount == 0) {
            return matrix;
        } else if (linecount == 1) {
            int pos = rand.nextInt(3);
            for (int i = 0; i < linelenght; i++) {
                matrix[linedrows[0]][pos + i] = 1f;
            }
        } else if (linecount == 2) {
            while (linedrows[0] == linedrows[1]) {
                linedrows[1] = rand.nextInt(5);
            }
            for (int i = 0; i < linecount; i++) {
                int pos = rand.nextInt(3);
                for (int j = 0; j < linelenght; j++) {
                    matrix[linedrows[i]][pos + j] = 1f;
                }
            }
        } else if (linecount == 3) {
            while (linedrows[0] == linedrows[1]) {
                linedrows[1] = rand.nextInt(5);
            }
            while (linedrows[2] == linedrows[0] || linedrows[2] == linedrows[1]) {
                linedrows[2] = rand.nextInt(5);
            }
            for (int i = 0; i < linecount; i++) {
                int pos = rand.nextInt(3);
                for (int j = 0; j < linelenght; j++) {
                    matrix[linedrows[i]][pos + j] = 1f;
                }
            }
        } else if (linecount == 4) {
            while (linedrows[0] == linedrows[1]) {
                linedrows[1] = rand.nextInt(5);
            }
            while (linedrows[2] == linedrows[0] || linedrows[2] == linedrows[1]) {
                linedrows[2] = rand.nextInt(5);
            }
            while (linedrows[3] == linedrows[0] || linedrows[3] == linedrows[1] || linedrows[3] == linedrows[2]) {
                linedrows[3] = rand.nextInt(5);
            }
            for (int i = 0; i < linecount; i++) {
                int pos = rand.nextInt(3);
                for (int j = 0; j < linelenght; j++) {
                    matrix[linedrows[i]][pos + j] = 1f;
                }
            }
        }

        return matrix;
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

    //csak 5x5
    public static float[] convertTo1D(float[][] matrix) {
        float[] linematrix = new float[25];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                linematrix[i + j] = matrix[i][j];
            }
        }
        return linematrix;
    }

    public static Tensor convertToTensor(float[][] matrix) {
        Tensor Test = Tensor.create(Tensor.TYPE.FLOAT, 25);
        Test.setFloatArray(convertTo1D(matrix));
        return Test;
    }

}
