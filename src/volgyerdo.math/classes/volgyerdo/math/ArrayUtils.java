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
package volgyerdo.math;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class ArrayUtils {

    private ArrayUtils() {
    }

    public static int sum(int array[]) {
        int product = 1;
        for (int i : array) {
            product += i;
        }
        return product;
    }

    public static int product(int array[]) {
        int product = 1;
        for (int i : array) {
            product *= i;
        }
        return product;
    }

    public static void add(byte array[], byte x) {
        for (int i = 0; i < array.length; i++) {
            array[i] += x;
        }
    }
    
    public static void add(short array[], short x) {
        for (int i = 0; i < array.length; i++) {
            array[i] += x;
        }
    }
    
    public static void add(float array[], float x) {
        for (int i = 0; i < array.length; i++) {
            array[i] += x;
        }
    }
    
    public static void substract(byte array[], byte x) {
        for (int i = 0; i < array.length; i++) {
            array[i] -= x;
        }
    }
    
    public static void substract(short array[], short x) {
        for (int i = 0; i < array.length; i++) {
            array[i] -= x;
        }
    }
    
    public static void substract(float array[], float x) {
        for (int i = 0; i < array.length; i++) {
            array[i] -= x;
        }
    }
    
    public static void multiply(byte array[], byte x) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= x;
        }
    }
    
    public static void multiply(short array[], short x) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= x;
        }
    }
    
    public static void multiply(float array[], float x) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= x;
        }
    }
    
    public static void devide(byte array[], byte x) {
        for (int i = 0; i < array.length; i++) {
            array[i] /= x;
        }
    }
    
    public static void devide(short array[], short x) {
        for (int i = 0; i < array.length; i++) {
            array[i] /= x;
        }
    }
    
    public static void devide(float array[], float x) {
        for (int i = 0; i < array.length; i++) {
            array[i] /= x;
        }
    }

    public static byte[] reverse(byte a[]) {
        byte[] b = new byte[a.length];
        int n = a.length - 1;
        for (int i = 0; i < a.length; i++) {
            b[n - i] = a[i];

        }
        return b;
    }

    public static short[] reverse(short a[]) {
        short[] b = new short[a.length];
        int n = a.length - 1;
        for (int i = 0; i < a.length; i++) {
            b[n - i] = a[i];

        }
        return b;
    }

    public static int[] reverse(int a[]) {
        int[] b = new int[a.length];
        int n = a.length - 1;
        for (int i = 0; i < a.length; i++) {
            b[n - i] = a[i];
        }
        return b;
    }

    public static float[] reverse(float a[]) {
        float[] b = new float[a.length];
        int n = a.length - 1;
        for (int i = 0; i < a.length; i++) {
            b[n - i] = a[i];
        }
        return b;
    }

}
