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
public class NeuralNetwork {

    Matrix weights_ih, weights_ho, bias_h, bias_o;
    float l_rate = 0.01f;
    Matrix input, hidden, output;

    public NeuralNetwork(int i, int h, int o) {
        weights_ih = new Matrix(h, i);
        weights_ho = new Matrix(o, h);

        bias_h = new Matrix(h, 1);
        bias_o = new Matrix(o, 1);

    }

    public void randomize() {
        weights_ih.randomize();
        weights_ho.randomize();
    }

    public List<Float> predict(float[] X) {
        input = Matrix.fromArray(X);
        hidden = Matrix.multiply(weights_ih, input);
        hidden.add(bias_h);
        hidden.sigmoid();

        output = Matrix.multiply(weights_ho, hidden);
        output.add(bias_o);
        output.sigmoid();

        return output.toArray();
    }

    public void train(float[] X, float[] Y) {
        input = Matrix.fromArray(X);
        hidden = Matrix.multiply(weights_ih, input);
        hidden.add(bias_h);
        hidden.sigmoid();

        output = Matrix.multiply(weights_ho, hidden);
        output.add(bias_o);
        output.sigmoid();

        Matrix target = Matrix.fromArray(Y);

        Matrix error = Matrix.subtract(target, output);
        Matrix gradient = output.dsigmoid();
        gradient.multiply(error);
        gradient.multiply(l_rate);

        Matrix hidden_T = Matrix.transpose(hidden);
        Matrix who_delta = Matrix.multiply(gradient, hidden_T);

        weights_ho.add(who_delta);
        bias_o.add(gradient);

        Matrix who_T = Matrix.transpose(weights_ho);
        Matrix hidden_errors = Matrix.multiply(who_T, error);

        Matrix h_gradient = hidden.dsigmoid();
        h_gradient.multiply(hidden_errors);
        h_gradient.multiply(l_rate);

        Matrix i_T = Matrix.transpose(input);
        Matrix wih_delta = Matrix.multiply(h_gradient, i_T);

        weights_ih.add(wih_delta);
        bias_h.add(h_gradient);

    }

    public void fit(float[][] X, float[][] Y, int epochs, boolean random) {
//        System.out.println();
//        print();
//        System.out.println();
        for (int i = 0; i < epochs; i++) {
            if (random) {
                int sampleN = (int) (Math.random() * X.length);
                this.train(X[sampleN], Y[sampleN]);
            } else {
                for (int j = 0; j < X.length; j++) {
                    this.train(X[j], Y[j]);
//                    print();
//                    System.out.println();
                }
            }
        }
    }

    public void print() {
        System.out.print("(" + input.data[0][0] + ") - [" + weights_ih.data[0][0] + "]/[" + bias_h.data[0][0]
                + "] - (" + hidden.data[0][0] + ") - [" + weights_ho.data[0][0] + "]/[" + bias_o.data[0][0]
                + "] - (" + output.data[0][0] + ")");
    }
}
