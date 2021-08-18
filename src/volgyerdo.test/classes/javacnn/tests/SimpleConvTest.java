/*
 * Copyright 2021 User.
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
package javacnn.tests;

import javacnn.data.OutputDefinition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javacnn.JavaCNN;
import javacnn.data.DataBlock;
import javacnn.layers.InputLayer;
import javacnn.layers.Layer;
import javacnn.layers.ConvolutionLayer;
import javacnn.layers.MaxoutLayer;
import javacnn.trainers.AdaDeltaTrainer;
import javacnn.trainers.Trainer;

/**
 *
 * @author User
 */
public class SimpleConvTest {

    public static void main(String[] args) {
        List<Layer> layers = new ArrayList<>();
        OutputDefinition def = new OutputDefinition();
        def.setOutX(4);
        def.setOutY(4);

        layers.add(new InputLayer(def, 4, 4, 1));
        layers.add(new ConvolutionLayer(def, 3, 1, 1, 2)); //sx, filters, stride, padding
        layers.add(new ConvolutionLayer(def, 3, 1, 1, 2)); //sx, filters, stride, padding
        layers.add(new MaxoutLayer(def)); //sx, filters, stride, padding

        JavaCNN net = new JavaCNN(layers);

        DataBlock db = new DataBlock(4, 4, 1, 0.0f); //int sx, int sy, int depth, double c

        
        //net.backward(1);
        db.setGradient(1, 1);
        db.setWeight(1, 1);
        int n = net.getBackPropagationResult().size();

//        System.out.println(Arrays.toString(net.getBackPropagationResult().get(0).getWeights()));
//        System.out.println("/////");
        for (int i = 0; i < n; i++) {
            System.out.println("-");
            System.out.println(layers.get(i));

        }
        net.forward(db, true);
        net.backward(0);
        System.out.println(Arrays.toString(db.getGradients()));
        System.out.println(Arrays.toString(db.getWeights()));
        System.out.println("--");
        

    }
}
