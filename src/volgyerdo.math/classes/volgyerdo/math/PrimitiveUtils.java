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
public class PrimitiveUtils {
    
    public static byte toByte(short x){
        return (byte)min(max(x, Byte.MIN_VALUE), Byte.MAX_VALUE);
    }
    
    public static byte toByte(float x){
        return (byte)min(max(x, Byte.MIN_VALUE), Byte.MAX_VALUE);
    }
    
    public static byte toByte(double x){
        return (byte)min(max(x, Byte.MIN_VALUE), Byte.MAX_VALUE);
    }
    
    public static short toShort(float x){
        return (short)min(max(x, Short.MIN_VALUE), Short.MAX_VALUE);
    }
    
    public static short toShort(double x){
        return (short)min(max(x, Short.MIN_VALUE), Short.MAX_VALUE);
    }
    
    public static float toFloat(double x){
        return (float)min(max(x, Float.MIN_VALUE), Float.MAX_VALUE);
    }
    
    public static byte min(byte a, byte b){
        return a < b ? a : b;
    }
    
    public static short min(short a, short b){
        return a < b ? a : b;
    }
    
    public static float min(float a, float b){
        return a < b ? a : b;
    }
    
    public static double min(double a, double b){
        return a < b ? a : b;
    }
    
    public static byte max(byte a, byte b){
        return a > b ? a : b;
    }
    
    public static short max(short a, short b){
        return a > b ? a : b;
    }
    
    public static float max(float a, float b){
        return a > b ? a : b;
    }
    
    public static double max(double a, double b){
        return a > b ? a : b;
    }
}
