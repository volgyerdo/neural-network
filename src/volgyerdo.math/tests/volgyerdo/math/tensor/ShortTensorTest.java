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
public class ShortTensorTest {

    private static final double FLOATING_VALUE_ACCURACY = 0.0001;

    public ShortTensorTest() {
    }

    @Test
    public void testSetByteValue() {
        byte[][][] values = new byte[4][5][6];
        ShortTensor t = new ShortTensor(4, 5, 6);
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
                            (short)values[i][j][k], t.values[i + 4 * j + 4 * 5 * k]);
                }
            }
        }
    }

    @Test
    public void testSetFloatValue() {
        float[][][] values = new float[8][4][3];
        ShortTensor t = new ShortTensor(8, 4, 3);
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
                    short shortValue = PrimitiveUtils.toShort(values[i][j][k]);
                    assertEquals("Float value match (" + i + "," + j + "," + k + ")",
                            shortValue, t.values[i + 8 * j + 8 * 4 * k]);
                }
            }
        }
    }

    @Test
    public void testSetShortValue() {
        short[][][] values = new short[8][4][3];
        ShortTensor t = new ShortTensor(8, 4, 3);
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
                            values[i][j][k], t.values[i + 8 * j + 8 * 4 * k]);
                }
            }
        }
    }

    @Test
    public void testSetObjectValue() {
        try {
            ShortTensor t = new ShortTensor(1);
            t.setObjectValue(new Object(), 0);
            fail("Byte tensor accepts object");
        } catch (Exception e) {
            assertTrue("Put object into short tensor.", e instanceof RuntimeException);
        }
    }

    @Test
    public void testGetByteValue() {
        short[][][] values = new short[4][5][6];
        ShortTensor t = new ShortTensor(4, 5, 6);
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    values[i][j][k] = (short) (Math.random() * (Short.MAX_VALUE - Short.MIN_VALUE) - Short.MAX_VALUE - 1);
                    t.setShortValue(values[i][j][k], i, j, k);
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
        short[][][] values = new short[4][5][6];
        ShortTensor t = new ShortTensor(4, 5, 6);
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    values[i][j][k] = (short) (Math.random() * (Short.MAX_VALUE - Short.MIN_VALUE) - Short.MAX_VALUE - 1);
                    t.setShortValue(values[i][j][k], i, j, k);
                }
            }
        }
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    assertEquals("Short value match (" + i + "," + j + "," + k + ")",
                            t.getShortValue(i, j, k), t.values[i + 4 * j + 4 * 5 * k]);
                }
            }
        }
    }

    @Test
    public void testGetFloatValue() {
        short[][][] values = new short[4][5][6];
        ShortTensor t = new ShortTensor(4, 5, 6);
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    values[i][j][k] = (short) (Math.random() * (Short.MAX_VALUE - Short.MIN_VALUE) - Short.MAX_VALUE - 1);
                    t.setShortValue(values[i][j][k], i, j, k);
                }
            }
        }
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 5; j++) {
                for (byte k = 0; k < 6; k++) {
                    assertEquals("Float value match (" + i + "," + j + "," + k + ")",
                            t.getFloatValue(i, j, k), PrimitiveUtils.toFloat(t.values[i + 4 * j + 4 * 5 * k]), FLOATING_VALUE_ACCURACY);
                }
            }
        }
    }

    @Test
    public void testGetObjectValue() {
        try {
            ShortTensor t = new ShortTensor(1);
            t.setObjectValue(new Object(), 0);
            fail("Short tensor returns object");
        } catch (Exception e) {
            assertTrue("Get object from short tensor.", e instanceof RuntimeException);
        }
    }

    @Test
    public void testEquals() {
        ShortTensor t1 = new ShortTensor(1);
        t1.setShortValue((short) 0, 0);
        ShortTensor t2 = new ShortTensor(1);
        t2.setShortValue((short) 0, 0);
        assertTrue("One element tensors equal", t1.equals(t2));
        t2.setShortValue((short) 5, 0);
        assertFalse("One element tensors differ", t1.equals(t2));
        ShortTensor t3 = new ShortTensor(3, 2);
        assertFalse("Different dimension tenzors differ", t1.equals(t3));
        FloatTensor t4 = new FloatTensor(3, 2);
        assertFalse("Different type tenzors differ", t1.equals(t4));
        ShortTensor t5 = new ShortTensor(3, 2);
        t3.setShortValue((short) -1, 0, 0);
        t3.setShortValue((short) 0, 1, 0);
        t3.setShortValue((short) 10, 2, 0);
        t3.setShortValue((short) 15, 0, 1);
        t3.setShortValue((short) 20, 1, 1);
        t3.setShortValue((short) 99, 2, 1);
        assertFalse("Multidimensional tensors differ", t3.equals(t5));
        t5.setShortValue((short) -1, 0, 0);
        t5.setShortValue((short) 0, 1, 0);
        t5.setShortValue((short) 10, 2, 0);
        t5.setShortValue((short) 15, 0, 1);
        t5.setShortValue((short) 20, 1, 1);
        t5.setShortValue((short) 99, 2, 1);
        assertTrue("Multidimensional tensors equals", t3.equals(t5));
    }

    @Test
    public void testRandomizeByte() {
        ShortTensor t = new ShortTensor(3, 5, 4);
        try {
            t.randomize((byte) 43, (byte) 3);
            fail("Bad random bounds.");
        } catch (Exception e) {
            assertTrue("Bad random bounds.", e instanceof RuntimeException);
        }
        t.randomize((byte) -55, (byte) 43);
        short min = Short.MAX_VALUE, max = Short.MIN_VALUE;
        for (byte i = 0; i < 3 * 2 * 4; i++) {
            if (t.values[i] < min) {
                min = t.values[i];
            }
            if (t.values[i] > max) {
                max = t.values[i];
            }
        }
        assertTrue("Randomized byte value in bounds (" + min + "," + max + ")",
                min >= -55 && max <= 43 && min < max);
    }

    @Test
    public void testRandomizeShort() {
        ShortTensor t = new ShortTensor(3, 5, 4);
        try {
            t.randomize((short) -45, (short) -98);
            fail("Bad random bounds.");
        } catch (Exception e) {
            assertTrue("Bad random bounds.", e instanceof RuntimeException);
        }
        t.randomize((short) -550, (short) 4300);
        short min = Short.MAX_VALUE, max = Short.MIN_VALUE;
        for (byte i = 0; i < 3 * 2 * 4; i++) {
            if (t.values[i] < min) {
                min = t.values[i];
            }
            if (t.values[i] > max) {
                max = t.values[i];
            }
        }
        assertTrue("Randomized short value in bounds (" + min + "," + max + ")",
                min >= Short.MIN_VALUE && max <= Short.MAX_VALUE && min < max);
    }

    @Test
    public void testRandomizeFloat() {
        ShortTensor t = new ShortTensor(3, 5, 4);
        try {
            t.randomize((float) 33, (float) 32);
            fail("Bad random bounds.");
        } catch (Exception e) {
            assertTrue("Bad random bounds.", e instanceof RuntimeException);
        }
        t.randomize((float) -7693.2234, (float) 9933.232);
        short min = Short.MAX_VALUE, max = Short.MIN_VALUE;
        for (byte i = 0; i < 3 * 2 * 4; i++) {
            if (t.values[i] < min) {
                min = t.values[i];
            }
            if (t.values[i] > max) {
                max = t.values[i];
            }
        }
        assertTrue("Randomized short value in bounds (" + min + "," + max + ")",
                min >= Short.MIN_VALUE && max <= Short.MAX_VALUE && min < max);
    }

    @Test
    public void testAddByte() {
        ShortTensor t = new ShortTensor(3, 2);
        t.setShortValue((short) -1, 0, 0);
        t.setShortValue((short) 0, 1, 0);
        t.setShortValue((short) 10, 2, 0);
        t.setShortValue((short) 15, 0, 1);
        t.setShortValue((short) 20, 1, 1);
        t.setShortValue((short) 99, 2, 1);
        t.add((short) 0);
        assertEquals("Add 0 value", (byte) -1, t.getShortValue(0, 0));
        assertEquals("Add 0 value", (byte) 0, t.getShortValue(1, 0));
        assertEquals("Add 0 value", (byte) 10, t.getShortValue(2, 0));
        assertEquals("Add 0 value", (byte) 15, t.getShortValue(0, 1));
        assertEquals("Add 0 value", (byte) 20, t.getShortValue(1, 1));
        assertEquals("Add 0 value", (byte) 99, t.getShortValue(2, 1));
        t.add((short) 10);
        assertEquals("Add 10 value", (byte) 9, t.getShortValue(0, 0));
        assertEquals("Add 10 value", (byte) 10, t.getShortValue(1, 0));
        assertEquals("Add 10 value", (byte) 20, t.getShortValue(2, 0));
        assertEquals("Add 10 value", (byte) 25, t.getShortValue(0, 1));
        assertEquals("Add 10 value", (byte) 30, t.getShortValue(1, 1));
        assertEquals("Add 10 value", (byte) 109, t.getShortValue(2, 1));
        t.add((short) -5);
        assertEquals("Add 10 value", (byte) 4, t.getShortValue(0, 0));
        assertEquals("Add 10 value", (byte) 5, t.getShortValue(1, 0));
        assertEquals("Add 10 value", (byte) 15, t.getShortValue(2, 0));
        assertEquals("Add 10 value", (byte) 20, t.getShortValue(0, 1));
        assertEquals("Add 10 value", (byte) 25, t.getShortValue(1, 1));
        assertEquals("Add 10 value", (byte) 104, t.getShortValue(2, 1));
    }

    @Test
    public void testAddShort() {
        ShortTensor t = new ShortTensor(3, 2);
        t.setShortValue((short) -1, 0, 0);
        t.setShortValue((short) 0, 1, 0);
        t.setShortValue((short) 10, 2, 0);
        t.setShortValue((short) 15, 0, 1);
        t.setShortValue((short) 20, 1, 1);
        t.setShortValue((short) 99, 2, 1);
        t.add((short) 0);
        assertEquals("Add 0 value", (byte) -1, t.getShortValue(0, 0));
        assertEquals("Add 0 value", (byte) 0, t.getShortValue(1, 0));
        assertEquals("Add 0 value", (byte) 10, t.getShortValue(2, 0));
        assertEquals("Add 0 value", (byte) 15, t.getShortValue(0, 1));
        assertEquals("Add 0 value", (byte) 20, t.getShortValue(1, 1));
        assertEquals("Add 0 value", (byte) 99, t.getShortValue(2, 1));
        t.add((short) 10);
        assertEquals("Add 10 value", (byte) 9, t.getShortValue(0, 0));
        assertEquals("Add 10 value", (byte) 10, t.getShortValue(1, 0));
        assertEquals("Add 10 value", (byte) 20, t.getShortValue(2, 0));
        assertEquals("Add 10 value", (byte) 25, t.getShortValue(0, 1));
        assertEquals("Add 10 value", (byte) 30, t.getShortValue(1, 1));
        assertEquals("Add 10 value", (byte) 109, t.getShortValue(2, 1));
        t.add((short) -5);
        assertEquals("Add 10 value", (byte) 4, t.getShortValue(0, 0));
        assertEquals("Add 10 value", (byte) 5, t.getShortValue(1, 0));
        assertEquals("Add 10 value", (byte) 15, t.getShortValue(2, 0));
        assertEquals("Add 10 value", (byte) 20, t.getShortValue(0, 1));
        assertEquals("Add 10 value", (byte) 25, t.getShortValue(1, 1));
        assertEquals("Add 10 value", (byte) 104, t.getShortValue(2, 1));
    }

    @Test
    public void testAddFloat() {
        ShortTensor t = new ShortTensor(3, 2);
        t.setShortValue((short) -1, 0, 0);
        t.setShortValue((short) 0, 1, 0);
        t.setShortValue((short) 10, 2, 0);
        t.setShortValue((short) 15, 0, 1);
        t.setShortValue((short) 20, 1, 1);
        t.setShortValue((short) 99, 2, 1);
        t.add((float) 0);
        assertEquals("Add 0 value", (byte) -1, t.getShortValue(0, 0));
        assertEquals("Add 0 value", (byte) 0, t.getShortValue(1, 0));
        assertEquals("Add 0 value", (byte) 10, t.getShortValue(2, 0));
        assertEquals("Add 0 value", (byte) 15, t.getShortValue(0, 1));
        assertEquals("Add 0 value", (byte) 20, t.getShortValue(1, 1));
        assertEquals("Add 0 value", (byte) 99, t.getShortValue(2, 1));
        t.add((float) 10);
        assertEquals("Add 10 value", (byte) 9, t.getShortValue(0, 0));
        assertEquals("Add 10 value", (byte) 10, t.getShortValue(1, 0));
        assertEquals("Add 10 value", (byte) 20, t.getShortValue(2, 0));
        assertEquals("Add 10 value", (byte) 25, t.getShortValue(0, 1));
        assertEquals("Add 10 value", (byte) 30, t.getShortValue(1, 1));
        assertEquals("Add 10 value", (byte) 109, t.getShortValue(2, 1));
        t.add((float) -5);
        assertEquals("Add 10 value", (byte) 4, t.getShortValue(0, 0));
        assertEquals("Add 10 value", (byte) 5, t.getShortValue(1, 0));
        assertEquals("Add 10 value", (byte) 15, t.getShortValue(2, 0));
        assertEquals("Add 10 value", (byte) 20, t.getShortValue(0, 1));
        assertEquals("Add 10 value", (byte) 25, t.getShortValue(1, 1));
        assertEquals("Add 10 value", (byte) 104, t.getShortValue(2, 1));
    }

    @Test
    public void testAddTensor() {
        ShortTensor t1 = new ShortTensor(3, 2);
        try {
            t1.add(null);
            fail("Add null tensor");
        } catch (Exception e) {
            assertTrue("Add null tensor.", e instanceof RuntimeException);
        }
        ByteTensor t2 = new ByteTensor(3, 2);
        try {
            t1.add(t2);
            fail("Add different type of tensor");
        } catch (Exception e) {
            assertTrue("Add different type of tensor.", e instanceof RuntimeException);
        }
        ShortTensor t3 = new ShortTensor(3, 2, 5);
        try {
            t1.add(t3);
            fail("Add different size of tensor");
        } catch (Exception e) {
            assertTrue("Add different size of tensor.", e instanceof RuntimeException);
        }
        t1.setShortValue((short) -34, 0, 0);
        t1.setShortValue((short) -67, 1, 0);
        t1.setShortValue((short) 6, 2, 0);
        t1.setShortValue((short) 34, 0, 1);
        t1.setShortValue((short) 2, 1, 1);
        t1.setShortValue((short) 78, 2, 1);
        ShortTensor t4 = new ShortTensor(3, 2);
        t4.setShortValue((short) 2, 0, 0);
        t4.setShortValue((short) -3, 1, 0);
        t4.setShortValue((short) 10, 2, 0);
        t4.setShortValue((short) -10, 0, 1);
        t4.setShortValue((short) 7, 1, 1);
        t4.setShortValue((short) -22, 2, 1);
        Tensor sum = t1.add(t4);
        assertEquals("Tensor sum", (byte) -32, sum.getShortValue(0, 0));
        assertEquals("Tensor sum", (byte) -70, sum.getShortValue(1, 0));
        assertEquals("Tensor sum", (byte) 16, sum.getShortValue(2, 0));
        assertEquals("Tensor sum", (byte) 24, sum.getShortValue(0, 1));
        assertEquals("Tensor sum", (byte) 9, sum.getShortValue(1, 1));
        assertEquals("Tensor sum", (byte) 56, sum.getShortValue(2, 1));
    }

    @Test
    public void testNegateTensor() {
        ShortTensor t1 = new ShortTensor(3, 2);
        t1.setShortValue((short) 2, 0, 0);
        t1.setShortValue((short) -3, 1, 0);
        t1.setShortValue((short) 10, 2, 0);
        t1.setShortValue((short) -10, 0, 1);
        t1.setShortValue((short) 7, 1, 1);
        t1.setShortValue((short) -22, 2, 1);
        Tensor negated = t1.negate();
        assertEquals("Tensor negate", (byte) -2, negated.getShortValue(0, 0));
        assertEquals("Tensor negate", (byte) 3, negated.getShortValue(1, 0));
        assertEquals("Tensor negate", (byte) -10, negated.getShortValue(2, 0));
        assertEquals("Tensor negate", (byte) 10, negated.getShortValue(0, 1));
        assertEquals("Tensor negate", (byte) -7, negated.getShortValue(1, 1));
        assertEquals("Tensor negate", (byte) 22, negated.getShortValue(2, 1));
    }

    @Test
    public void testTransposeTensor() {
        ShortTensor t1 = new ShortTensor(3, 2);
        t1.setShortValue((short) -34, 0, 0);
        t1.setShortValue((short) -67, 1, 0);
        t1.setShortValue((short) 6, 2, 0);
        t1.setShortValue((short) 34, 0, 1);
        t1.setShortValue((short) 2, 1, 1);
        t1.setShortValue((short) 78, 2, 1);
        Tensor transposed = t1.transpose();
        assertEquals("Transposed tensor", (byte) -34, transposed.getShortValue(0, 0));
        assertEquals("Transposed tensor", (byte) -67, transposed.getShortValue(0, 1));
        assertEquals("Transposed tensor", (byte) 6, transposed.getShortValue(0, 2));
        assertEquals("Transposed tensor", (byte) 34, transposed.getShortValue(1, 0));
        assertEquals("Transposed tensor", (byte) 2, transposed.getShortValue(1, 1));
        assertEquals("Transposed tensor", (byte) 78, transposed.getShortValue(1, 2));
    }

    @Test
    public void testCloneTensor() throws CloneNotSupportedException {
        ShortTensor t1 = new ShortTensor(3, 2);
        t1.setShortValue((short) -34, 0, 0);
        t1.setShortValue((short) -67, 1, 0);
        t1.setShortValue((short) 6, 2, 0);
        t1.setShortValue((short) 34, 0, 1);
        t1.setShortValue((short) 2, 1, 1);
        t1.setShortValue((short) 78, 2, 1);
        Tensor transposed = t1.clone();
        assertEquals("Transposed tensor", (byte) -34, transposed.getShortValue(0, 0));
        assertEquals("Transposed tensor", (byte) -67, transposed.getShortValue(1, 0));
        assertEquals("Transposed tensor", (byte) 6, transposed.getShortValue(2, 0));
        assertEquals("Transposed tensor", (byte) 34, transposed.getShortValue(0, 1));
        assertEquals("Transposed tensor", (byte) 2, transposed.getShortValue(1, 1));
        assertEquals("Transposed tensor", (byte) 78, transposed.getShortValue(2, 1));
    }

}