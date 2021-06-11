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
package volgyerdo.test.suyashssonawane;

import java.util.List;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class SuyashSonawaneTest2 {

    public static void main(String[] args) {
        float[][] X = {
            {0},
            {1},};
        float[][] Y = {
            {0}, {1}
        };

        NeuralNetwork network = new NeuralNetwork(1, 1, 1);

        System.out.println("\nBefore training:\n");

        for (float d[] : X) {
            List<Float> output = network.predict(d);
            System.out.println(output.toString());
        }

        network.fit(X, Y, 1, false);
        
        System.out.println("\nAfter training:\n");

        for (float d[] : X) {
            List<Float> output = network.predict(d);
            System.out.println(output.toString());
        }
    }

}
