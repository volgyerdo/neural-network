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
package volgyerdo.math.array;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class ArrayUtils {

    private ArrayUtils() {
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
