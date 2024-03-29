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
package volgyerdo.neural.logic;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import volgyerdo.commons.primitive.ArrayUtils;
import volgyerdo.commons.primitive.PrimitiveUtils;
import volgyerdo.commons.math.tensor.Tensor;
import volgyerdo.neural.structure.ConvoLayer;
import volgyerdo.neural.structure.DenseLayer;
import volgyerdo.neural.structure.GraphLayer;
import volgyerdo.neural.structure.Layer;
import volgyerdo.neural.structure.Network;
import volgyerdo.neural.structure.Link;
import volgyerdo.neural.structure.TestAnalyses;

/**
 *
 * @author antal
 */
public class NetworkUtils {
    
    private static final DecimalFormat FORMAT = new DecimalFormat("0.0000");

    public static Tensor getInputStates(Network network) {
        return getInputLayer(network).states;
    }

    public static Tensor getOutputStates(Network network) {
        return getOutputLayer(network).states;
    }

    public static Layer getInputLayer(Network network) {
        return network.layers.get(0);
    }

    public static Layer getOutputLayer(Network network) {
        return network.layers.get(network.layers.size() - 1);
    }

    public static void randomizeWeigths(Tensor weights) {
        if (weights == null) {
            return;
        }
        weights.randomize(
                -NetworkConstants.DEFAULT_WEIGHT_RADIUS, NetworkConstants.DEFAULT_WEIGHT_RADIUS);
    }
    
    public static void randomizeBias(Tensor bias) {
        if (bias == null) {
            return;
        }
        bias.randomize(
                -NetworkConstants.DEFAULT_WEIGHT_RADIUS, NetworkConstants.DEFAULT_WEIGHT_RADIUS);
    }
    
    public static float randomizeBias() {
        return PrimitiveUtils.random(
                -NetworkConstants.DEFAULT_WEIGHT_RADIUS, NetworkConstants.DEFAULT_WEIGHT_RADIUS);
    }

    public static void randomizeWeigths(Link[] links) {
        for (int i = 0; i < links.length; i++) {
            links[i].weight = PrimitiveUtils.random(
                    -NetworkConstants.DEFAULT_WEIGHT_RADIUS, NetworkConstants.DEFAULT_WEIGHT_RADIUS);
        }
    }
    
    public static void randomizeBias(float[] bias) {
        ArrayUtils.randomize(bias, -NetworkConstants.DEFAULT_WEIGHT_RADIUS, NetworkConstants.DEFAULT_WEIGHT_RADIUS);
    }

    public static int[] randomizeCoordinates(int[] bounds) {
        Random rand = new Random();
        int[] randomArray = new int[bounds.length];
        for (int i = 0; i < bounds.length; i++) {
            randomArray[i] = rand.nextInt(bounds[i]);
        }
        return randomArray;
    }
    
    public static void printAnalysis(TestAnalyses analysis){
        System.out.println();
        System.out.println("Sample size: " + analysis.sampleSize);
        System.out.println("Error arithmetic mean: " + FORMAT.format(analysis.errorArithmeticMean));
        System.out.println("Error geometric mean: " + FORMAT.format(analysis.errorGeometricMean));
        System.out.println("Error median: " + FORMAT.format(analysis.errorMedian));
        System.out.println("Error standard deviation: " + FORMAT.format(analysis.errorStandardDeviation));
        System.out.println("Error minimum: " + FORMAT.format(analysis.minError));
        System.out.println("Error maximum: " + FORMAT.format(analysis.maxError));
        System.out.println("Running period: " + FORMAT.format(analysis.runPeriod));
        System.out.println("Running time: " + FORMAT.format(analysis.runTime));
        System.out.println();
    }
    
    public static void printRowAnalysis(List<TestAnalyses> rowAnalysis){
        System.out.println();
        System.out.println(
                "Error arithmetic mean;Error geometric mean;Error median;Error standard deviation;"
                        + "Error minimum;Error maximum;Running period;Running time");
        for(TestAnalyses analysis : rowAnalysis){
            System.out.print(FORMAT.format(analysis.errorArithmeticMean) + ";");
            System.out.print(FORMAT.format(analysis.errorGeometricMean) + ";");
            System.out.print(FORMAT.format(analysis.errorMedian) + ";");
            System.out.print(FORMAT.format(analysis.errorStandardDeviation) + ";");
            System.out.print(FORMAT.format(analysis.minError) + ";");
            System.out.print(FORMAT.format(analysis.maxError) + ";");
            System.out.print(FORMAT.format(analysis.runPeriod) + ";");
            System.out.print(FORMAT.format(analysis.runTime));
            System.out.println();
        }
        System.out.println();
    }
    
    public static void printWeights(Network network){
       for(Layer layer : network.layers){
            if(layer instanceof DenseLayer){
                DenseLayer dLayer = (DenseLayer)layer;
                System.out.println(dLayer.weights.toString(true));
            }else if(layer instanceof ConvoLayer){
                ConvoLayer cLayer = (ConvoLayer)layer;
                System.out.println(cLayer.kernel.toString(true));
            }else if(layer instanceof GraphLayer){
                GraphLayer gLayer = (GraphLayer)layer;
                System.out.println();
                for(Link link : gLayer.links){
                    System.out.print(link.weight + ", ");
                }
            }
        }
    }
    
    private NetworkUtils() {

    }
}
