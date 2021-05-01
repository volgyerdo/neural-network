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
package volgyerdo.math.tensor;

import org.junit.Test;
import static org.junit.Assert.*;
import volgyerdo.math.PrimitiveUtils;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class FloatTensorTest {

    private static final double FLOATING_VALUE_ACCURACY = 0.0001;

    public FloatTensorTest() {
    }

    @Test
    public void testSetByteValue() {
        byte[][][] values = new byte[4][5][6];
        FloatTensor t = new FloatTensor(4, 5, 6);
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    values[i][j][k] = (byte) (Math.random() * (Byte.MAX_VALUE - Byte.MIN_VALUE) - Byte.MAX_VALUE - 1);
                    t.setByteValue(values[i][j][k], i, j, k);
                }
            }
        }
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    assertEquals("Byte value match (" + i + "," + j + "," + k + ")",
                            (float) values[i][j][k], t.values[i + 4 * j + 4 * 5 * k], FLOATING_VALUE_ACCURACY);
                }
            }
        }
    }

    @Test
    public void testSetFloatValue() {
        float[][][] values = new float[8][4][3];
        FloatTensor t = new FloatTensor(8, 4, 3);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 3; k++) {
                    values[i][j][k] = (float) (Math.random()
                            * ((double) Float.MAX_VALUE * 2) - Float.MAX_VALUE);
                    t.setFloatValue(values[i][j][k], i, j, k);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 3; k++) {
                    assertEquals("Float value match (" + i + "," + j + "," + k + ")",
                            values[i][j][k], t.values[i + 8 * j + 8 * 4 * k], FLOATING_VALUE_ACCURACY);
                }
            }
        }
    }

    @Test
    public void testSetShortValue() {
        short[][][] values = new short[8][4][3];
        FloatTensor t = new FloatTensor(8, 4, 3);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 3; k++) {
                    values[i][j][k] = (short) (Math.random() * (Short.MAX_VALUE - Short.MIN_VALUE) - Short.MAX_VALUE);
                    t.setShortValue(values[i][j][k], i, j, k);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 3; k++) {
                    assertEquals("Short value match (" + i + "," + j + "," + k + ")",
                            (float) values[i][j][k], t.values[i + 8 * j + 8 * 4 * k], FLOATING_VALUE_ACCURACY);
                }
            }
        }
    }

    @Test
    public void testSetObjectValue() {
        try {
            FloatTensor t = new FloatTensor(1);
            t.setObjectValue(new Object(), 0);
            fail("Float tensor accepts object");
        } catch (Exception e) {
            assertTrue("Put object into float tensor.", e instanceof RuntimeException);
        }
    }

    @Test
    public void testGetByteValue() {
        byte[][][] values = new byte[4][5][6];
        FloatTensor t = new FloatTensor(4, 5, 6);
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    values[i][j][k] = (byte) (Math.random() * (Byte.MAX_VALUE - Byte.MIN_VALUE) - Byte.MAX_VALUE - 1);
                    t.setByteValue(values[i][j][k], i, j, k);
                }
            }
        }
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    assertEquals("Byte value match (" + i + "," + j + "," + k + ")",
                            t.getByteValue(i, j, k), PrimitiveUtils.toByte(t.values[i + 4 * j + 4 * 5 * k]));
                }
            }
        }
    }

    @Test
    public void testGetShortValue() {
        byte[][][] values = new byte[4][5][6];
        FloatTensor t = new FloatTensor(4, 5, 6);
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    values[i][j][k] = (byte) (Math.random() * (Byte.MAX_VALUE - Byte.MIN_VALUE) - Byte.MAX_VALUE - 1);
                    t.setByteValue(values[i][j][k], i, j, k);
                }
            }
        }
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    assertEquals("Short value match (" + i + "," + j + "," + k + ")",
                            t.getShortValue(i, j, k), PrimitiveUtils.toShort(t.values[i + 4 * j + 4 * 5 * k]));
                }
            }
        }
    }

    @Test
    public void testGetFloatValue() {
        byte[][][] values = new byte[4][5][6];
        FloatTensor t = new FloatTensor(4, 5, 6);
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    values[i][j][k] = (byte) (Math.random() * (Byte.MAX_VALUE - Byte.MIN_VALUE) - Byte.MAX_VALUE - 1);
                    t.setByteValue(values[i][j][k], i, j, k);
                }
            }
        }
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    assertEquals("Float value match (" + i + "," + j + "," + k + ")",
                            t.getFloatValue(i, j, k), t.values[i + 4 * j + 4 * 5 * k], FLOATING_VALUE_ACCURACY);
                }
            }
        }
    }

    @Test
    public void testGetObjectValue() {
        try {
            FloatTensor t = new FloatTensor(1);
            t.setObjectValue(new Object(), 0);
            fail("Float tensor returns object");
        } catch (Exception e) {
            assertTrue("Get object from float tensor.", e instanceof RuntimeException);
        }
    }

    @Test
    public void testEquals() {
        FloatTensor t1 = new FloatTensor(1);
        t1.setFloatValue(0, 0);
        FloatTensor t2 = new FloatTensor(1);
        t2.setFloatValue(0, 0);
        assertTrue("One element tensors equal", t1.equals(t2));
        t2.setFloatValue(5.4f, 0);
        assertFalse("One element tensors differ", t1.equals(t2));
        FloatTensor t3 = new FloatTensor(3, 2);
        assertFalse("Different dimension tenzors differ", t1.equals(t3));
        ShortTensor t4 = new ShortTensor(3, 2);
        assertFalse("Different type tenzors differ", t1.equals(t4));
        FloatTensor t5 = new FloatTensor(3, 2);
        t3.setFloatValue(-134.564f, 0, 0);
        t3.setFloatValue(0, 1, 0);
        t3.setFloatValue(-Float.MAX_VALUE, 2, 0);
        t3.setFloatValue(150000f, 0, 1);
        t3.setFloatValue(Float.MAX_VALUE, 1, 1);
        t3.setFloatValue(99.4533f, 2, 1);
        assertFalse("Multidimensional tensors differ", t3.equals(t5));
        t5.setFloatValue(-134.564f, 0, 0);
        t5.setFloatValue(0, 1, 0);
        t5.setFloatValue(-Float.MAX_VALUE, 2, 0);
        t5.setFloatValue(150000f, 0, 1);
        t5.setFloatValue(Float.MAX_VALUE, 1, 1);
        t5.setFloatValue(99.4533f, 2, 1);
        assertTrue("Multidimensional tensors equals", t3.equals(t5));
    }
    
    @Test
    public void testRandomizeByte() {
        FloatTensor t = new FloatTensor(3, 5, 4);
        try {
            t.randomize(43f, 3f);
            fail("Bad random bounds.");
        } catch (Exception e) {
            assertTrue("Bad random bounds.", e instanceof RuntimeException);
        }
        t.randomize((byte)-55, (byte)100);
        float min = Float.MAX_VALUE, max = -Float.MAX_VALUE;
        for (byte i = 0; i < 3 * 2 * 4; i++) {
            if (t.values[i] < min) {
                min = t.values[i];
            }
            if (t.values[i] > max) {
                max = t.values[i];
            }
        }
        assertTrue("Randomized float value in bounds (" + min + "," + max + ")",
                min >= -55 - FLOATING_VALUE_ACCURACY
                && max <= 100 + FLOATING_VALUE_ACCURACY && min < max); 
    }
    
    @Test
    public void testRandomizeShort() {
        FloatTensor t = new FloatTensor(3, 5, 4);
        try {
            t.randomize(43f, 3f);
            fail("Bad random bounds.");
        } catch (Exception e) {
            assertTrue("Bad random bounds.", e instanceof RuntimeException);
        }
        t.randomize((short)-555, (short)3200);
        float min = Float.MAX_VALUE, max = -Float.MAX_VALUE;
        for (byte i = 0; i < 3 * 2 * 4; i++) {
            if (t.values[i] < min) {
                min = t.values[i];
            }
            if (t.values[i] > max) {
                max = t.values[i];
            }
        }
        assertTrue("Randomized float value in bounds (" + min + "," + max + ")",
                min >= -555 - FLOATING_VALUE_ACCURACY
                && max <= 3200 + FLOATING_VALUE_ACCURACY && min < max); 
    }

    @Test
    public void testRandomizeFloat() {
        FloatTensor t = new FloatTensor(3, 5, 4);
        try {
            t.randomize(43f, 3f);
            fail("Bad random bounds.");
        } catch (Exception e) {
            assertTrue("Bad random bounds.", e instanceof RuntimeException);
        }
        t.randomize(-10000.78f, 43.6788f);
        float min = Float.MAX_VALUE, max = -Float.MAX_VALUE;
        for (byte i = 0; i < 3 * 2 * 4; i++) {
            if (t.values[i] < min) {
                min = t.values[i];
            }
            if (t.values[i] > max) {
                max = t.values[i];
            }
        }
        assertTrue("Randomized float value in bounds (" + min + "," + max + ")",
                min >= -10000.78f - FLOATING_VALUE_ACCURACY
                && max <= 43.6788f + FLOATING_VALUE_ACCURACY && min < max); 
    }

    @Test
    public void testAddByte() {
        FloatTensor t = new FloatTensor(3, 2);
        t.setByteValue((byte) -1, 0, 0);
        t.setByteValue((byte) 0, 1, 0);
        t.setByteValue((byte) 10, 2, 0);
        t.setByteValue((byte) 15, 0, 1);
        t.setByteValue((byte) 20, 1, 1);
        t.setByteValue((byte) 99, 2, 1);
        t.add((byte) 0);
        assertEquals("Add 0 value", (float) -1, t.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 0, t.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 10, t.getFloatValue(2, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 15, t.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 20, t.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 99, t.getFloatValue(2, 1), FLOATING_VALUE_ACCURACY);
        t.add((byte) 10);
        assertEquals("Add 10 value", (float) 9, t.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 10, t.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 20, t.getFloatValue(2, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 25, t.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 30, t.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 109, t.getFloatValue(2, 1), FLOATING_VALUE_ACCURACY);
        t.add((byte) -5);
        assertEquals("Add 10 value", (float) 4, t.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 5, t.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 15, t.getFloatValue(2, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 20, t.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 25, t.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 104, t.getFloatValue(2, 1), FLOATING_VALUE_ACCURACY);
    }

    @Test
    public void testAddShort() {
        FloatTensor t = new FloatTensor(3, 2);
        t.setFloatValue((float) -1, 0, 0);
        t.setFloatValue((float) 0, 1, 0);
        t.setFloatValue((float) 10, 2, 0);
        t.setFloatValue((float) 15, 0, 1);
        t.setFloatValue((float) 20, 1, 1);
        t.setFloatValue((float) 99, 2, 1);
        t.add((short) 0);
        assertEquals("Add 0 value", (float) -1, t.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 0, t.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 10, t.getFloatValue(2, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 15, t.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 20, t.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 99, t.getFloatValue(2, 1), FLOATING_VALUE_ACCURACY);
        t.add((short) 10);
        assertEquals("Add 10 value", (float) 9, t.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 10, t.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 20, t.getFloatValue(2, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 25, t.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 30, t.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 109, t.getFloatValue(2, 1), FLOATING_VALUE_ACCURACY);
        t.add((short) -5);
        assertEquals("Add 10 value", (float) 4, t.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 5, t.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 15, t.getFloatValue(2, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 20, t.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 25, t.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 104, t.getFloatValue(2, 1), FLOATING_VALUE_ACCURACY);
    }

    @Test
    public void testAddFloat() {
        FloatTensor t = new FloatTensor(3, 2);
        t.setFloatValue((byte) -1, 0, 0);
        t.setFloatValue((byte) 0, 1, 0);
        t.setFloatValue((byte) 10, 2, 0);
        t.setFloatValue((byte) 15, 0, 1);
        t.setFloatValue((byte) 20, 1, 1);
        t.setFloatValue((byte) 99, 2, 1);
        t.add((float) 0);
        assertEquals("Add 0 value", (float) -1, t.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 0, t.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 10, t.getFloatValue(2, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 15, t.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 20, t.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 0 value", (float) 99, t.getFloatValue(2, 1), FLOATING_VALUE_ACCURACY);
        t.add((float) 10);
        assertEquals("Add 10 value", (float) 9, t.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 10, t.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 20, t.getFloatValue(2, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 25, t.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 30, t.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 109, t.getFloatValue(2, 1), FLOATING_VALUE_ACCURACY);
        t.add((float) -5);
        assertEquals("Add 10 value", (float) 4, t.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 5, t.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 15, t.getFloatValue(2, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 20, t.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 25, t.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Add 10 value", (float) 104, t.getFloatValue(2, 1), FLOATING_VALUE_ACCURACY);
    }

    @Test
    public void testAddTensor() {
        FloatTensor t1 = new FloatTensor(3, 2);
        try {
            t1.add(null);
            fail("Add null tensor");
        } catch (Exception e) {
            assertTrue("Add null tensor.", e instanceof RuntimeException);
        }
        ShortTensor t2 = new ShortTensor(3, 2);
        try {
            t1.add(t2);
            fail("Add different type of tensor");
        } catch (Exception e) {
            assertTrue("Add different type of tensor.", e instanceof RuntimeException);
        }
        FloatTensor t3 = new FloatTensor(3, 2, 5);
        try {
            t1.add(t3);
            fail("Add different size of tensor");
        } catch (Exception e) {
            assertTrue("Add different size of tensor.", e instanceof RuntimeException);
        }
        t1.setFloatValue((float) -34.1111, 0, 0);
        t1.setFloatValue((float) -67, 1, 0);
        t1.setFloatValue((float) 6, 2, 0);
        t1.setFloatValue((float) 34, 0, 1);
        t1.setFloatValue((float) 2, 1, 1);
        t1.setFloatValue((float) 78, 2, 1);
        FloatTensor t4 = new FloatTensor(3, 2);
        t4.setFloatValue((float) 2.1111, 0, 0);
        t4.setFloatValue((float) -3, 1, 0);
        t4.setFloatValue((float) 10, 2, 0);
        t4.setFloatValue((float) -10, 0, 1);
        t4.setFloatValue((float) 7, 1, 1);
        t4.setFloatValue((float) -22, 2, 1);
        t1.add(t4);
        assertEquals("Tensor sum", (float) -32, t1.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Tensor sum", (float) -70, t1.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Tensor sum", (float) 16, t1.getFloatValue(2, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Tensor sum", (float) 24, t1.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Tensor sum", (float) 9, t1.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Tensor sum", (float) 56, t1.getFloatValue(2, 1), FLOATING_VALUE_ACCURACY);
    }

    @Test
    public void testNegateTensor() {
        FloatTensor t1 = new FloatTensor(3, 2);
        t1.setFloatValue((float) 2, 0, 0);
        t1.setFloatValue((float) -3, 1, 0);
        t1.setFloatValue((float) 10.456, 2, 0);
        t1.setFloatValue((float) -10, 0, 1);
        t1.setFloatValue((float) 7, 1, 1);
        t1.setFloatValue((float) -22.6444, 2, 1);
        t1.negate();
        assertEquals("Tensor negate", (float) -2, t1.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Tensor negate", (float) 3, t1.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Tensor negate", (float) -10.456, t1.getFloatValue(2, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Tensor negate", (float) 10, t1.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Tensor negate", (float) -7, t1.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Tensor negate", (float) 22.6444, t1.getFloatValue(2, 1), FLOATING_VALUE_ACCURACY);
    }

    @Test
    public void testTransposeTensor() {
        FloatTensor t1 = new FloatTensor(3, 2);
        t1.setFloatValue((float) -34, 0, 0);
        t1.setFloatValue((float) -67.345, 1, 0);
        t1.setFloatValue((float) 6, 2, 0);
        t1.setFloatValue((float) 34, 0, 1);
        t1.setFloatValue((float) 2, 1, 1);
        t1.setFloatValue((float) 78.234, 2, 1);
        Tensor transposed = t1.transpose();
        assertEquals("Transposed tensor", (float) -34, transposed.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Transposed tensor", (float) -67.345, transposed.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Transposed tensor", (float) 6, transposed.getFloatValue(0, 2), FLOATING_VALUE_ACCURACY);
        assertEquals("Transposed tensor", (float) 34, transposed.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Transposed tensor", (float) 2, transposed.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Transposed tensor", (float) 78.234, transposed.getFloatValue(1, 2), FLOATING_VALUE_ACCURACY);
    }

    @Test
    public void testCloneTensor() throws CloneNotSupportedException {
        FloatTensor t1 = new FloatTensor(3, 2);
        t1.setFloatValue((float) -34.3423, 0, 0);
        t1.setFloatValue((float) -67, 1, 0);
        t1.setFloatValue((float) 6, 2, 0);
        t1.setFloatValue((float) 34, 0, 1);
        t1.setFloatValue((float) 2.2334, 1, 1);
        t1.setFloatValue((float) 78, 2, 1);
        Tensor transposed = t1.clone();
        assertEquals("Transposed tensor", (float) -34.3423, transposed.getFloatValue(0, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Transposed tensor", (float) -67, transposed.getFloatValue(1, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Transposed tensor", (float) 6, transposed.getFloatValue(2, 0), FLOATING_VALUE_ACCURACY);
        assertEquals("Transposed tensor", (float) 34, transposed.getFloatValue(0, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Transposed tensor", (float) 2.2334, transposed.getFloatValue(1, 1), FLOATING_VALUE_ACCURACY);
        assertEquals("Transposed tensor", (float) 78, transposed.getFloatValue(2, 1), FLOATING_VALUE_ACCURACY);
    }

}
