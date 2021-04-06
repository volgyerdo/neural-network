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

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class ByteTensorTest {

    private static final double FLOATING_VALUE_ACCURACY = 0.0001;

    public ByteTensorTest() {
    }

    @Test
    public void testSetByteValue() {
        byte[][][] values = new byte[4][5][6];
        ByteTensor t = new ByteTensor(4, 5, 6);
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
                            values[i][j][k], t.values[i + 4 * j + 4 * 5 * k]);
                }
            }
        }
    }

    @Test
    public void testSetFloatValue() {
        float[][][] values = new float[8][4][3];
        ByteTensor t = new ByteTensor(8, 4, 3);
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
                    byte byteValue = values[i][j][k] < Byte.MIN_VALUE ? Byte.MIN_VALUE
                            : (values[i][j][k] > Byte.MAX_VALUE ? Byte.MAX_VALUE : (byte) values[i][j][k]);
                    assertEquals("Float value match (" + i + "," + j + "," + k + ")",
                            byteValue, t.values[i + 8 * j + 8 * 4 * k]);
                }
            }
        }
    }

    @Test
    public void testSetShortValue() {
        short[][][] values = new short[8][4][3];
        ByteTensor t = new ByteTensor(8, 4, 3);
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
                    byte byteValue = values[i][j][k] < Byte.MIN_VALUE ? Byte.MIN_VALUE
                            : (values[i][j][k] > Byte.MAX_VALUE ? Byte.MAX_VALUE : (byte) values[i][j][k]);
                    assertEquals("Short value match (" + i + "," + j + "," + k + ")",
                            byteValue, t.values[i + 8 * j + 8 * 4 * k]);
                }
            }
        }
    }

    @Test
    public void testSetObjectValue() {
        try {
            ByteTensor t = new ByteTensor(1);
            t.setObjectValue(new Object(), 0);
            fail("Byte tensor accepts object");
        } catch (Exception e) {
            assertTrue("Put object into byte tensor.", e instanceof RuntimeException);
        }
    }

    @Test
    public void testGetByteValue() {
        byte[][][] values = new byte[4][5][6];
        ByteTensor t = new ByteTensor(4, 5, 6);
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
                            t.getByteValue(i, j, k), t.values[i + 4 * j + 4 * 5 * k]);
                }
            }
        }
    }

    @Test
    public void testGetShortValue() {
        byte[][][] values = new byte[4][5][6];
        ByteTensor t = new ByteTensor(4, 5, 6);
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
                            t.getShortValue(i, j, k), (short) t.values[i + 4 * j + 4 * 5 * k]);
                }
            }
        }
    }

    @Test
    public void testGetFloatValue() {
        byte[][][] values = new byte[4][5][6];
        ByteTensor t = new ByteTensor(4, 5, 6);
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
                            t.getFloatValue(i, j, k), (float) t.values[i + 4 * j + 4 * 5 * k], FLOATING_VALUE_ACCURACY);
                }
            }
        }
    }

    @Test
    public void testGetObjectValue() {
        try {
            ByteTensor t = new ByteTensor(1);
            t.setObjectValue(new Object(), 0);
            fail("Byte tensor returns object");
        } catch (Exception e) {
            assertTrue("Get object from byte tensor.", e instanceof RuntimeException);
        }
    }

    @Test
    public void testEquals() {
        ByteTensor t1 = new ByteTensor(1);
        t1.setByteValue((byte) 0, 0);
        ByteTensor t2 = new ByteTensor(1);
        t2.setByteValue((byte) 0, 0);
        assertTrue("One element tensors equal", t1.equals(t2));
        t2.setByteValue((byte) 5, 0);
        assertFalse("One element tensors differ", t1.equals(t2));
        ByteTensor t3 = new ByteTensor(3, 2);
        assertFalse("Different dimension tenzors differ", t1.equals(t3));
        FloatTensor t4 = new FloatTensor(3, 2);
        assertFalse("Different type tenzors differ", t1.equals(t4));
        ByteTensor t5 = new ByteTensor(3, 2);
        t3.setByteValue((byte) -1, 0, 0);
        t3.setByteValue((byte) 0, 1, 0);
        t3.setByteValue((byte) 10, 2, 0);
        t3.setByteValue((byte) 15, 0, 1);
        t3.setByteValue((byte) 20, 1, 1);
        t3.setByteValue((byte) 99, 2, 1);
        assertFalse("Multidimensional tensors differ", t3.equals(t5));
        t5.setByteValue((byte) -1, 0, 0);
        t5.setByteValue((byte) 0, 1, 0);
        t5.setByteValue((byte) 10, 2, 0);
        t5.setByteValue((byte) 15, 0, 1);
        t5.setByteValue((byte) 20, 1, 1);
        t5.setByteValue((byte) 99, 2, 1);
        assertTrue("Multidimensional tensors equals", t3.equals(t5));
    }

    @Test
    public void testRandomizeByte() {
        ByteTensor t = new ByteTensor(3, 5, 4);
        try {
            t.randomize((byte) 43, (byte) 3);
            fail("Bad random bounds.");
        } catch (Exception e) {
            assertTrue("Bad random bounds.", e instanceof RuntimeException);
        }
        t.randomize((byte) -55, (byte) 43);
        byte min = Byte.MAX_VALUE, max = Byte.MIN_VALUE;
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
        ByteTensor t = new ByteTensor(3, 5, 4);
        try {
            t.randomize((short) -45, (short) -98);
            fail("Bad random bounds.");
        } catch (Exception e) {
            assertTrue("Bad random bounds.", e instanceof RuntimeException);
        }
        t.randomize((short) -550, (short) 4300);
        byte min = Byte.MAX_VALUE, max = Byte.MIN_VALUE;
        for (byte i = 0; i < 3 * 2 * 4; i++) {
            if (t.values[i] < min) {
                min = t.values[i];
            }
            if (t.values[i] > max) {
                max = t.values[i];
            }
        }
        assertTrue("Randomized short value in bounds (" + min + "," + max + ")",
                min >= Byte.MIN_VALUE && max <= Byte.MAX_VALUE && min < max);
    }

    @Test
    public void testRandomizeFloat() {
        ByteTensor t = new ByteTensor(3, 5, 4);
        try {
            t.randomize((float) 33, (float) 32);
            fail("Bad random bounds.");
        } catch (Exception e) {
            assertTrue("Bad random bounds.", e instanceof RuntimeException);
        }
        t.randomize((float) -7693.2234, (float) 993213.232);
        byte min = Byte.MAX_VALUE, max = Byte.MIN_VALUE;
        for (byte i = 0; i < 3 * 2 * 4; i++) {
            if (t.values[i] < min) {
                min = t.values[i];
            }
            if (t.values[i] > max) {
                max = t.values[i];
            }
        }
        assertTrue("Randomized short value in bounds (" + min + "," + max + ")",
                min >= Byte.MIN_VALUE && max <= Byte.MAX_VALUE && min < max);
    }

    @Test
    public void testAddByte() {
        ByteTensor t = new ByteTensor(3, 2);
        t.setByteValue((byte) -1, 0, 0);
        t.setByteValue((byte) 0, 1, 0);
        t.setByteValue((byte) 10, 2, 0);
        t.setByteValue((byte) 15, 0, 1);
        t.setByteValue((byte) 20, 1, 1);
        t.setByteValue((byte) 99, 2, 1);
        t.add((byte) 0);
        assertEquals("Add 0 value", (byte) -1, t.getByteValue(0, 0));
        assertEquals("Add 0 value", (byte) 0, t.getByteValue(1, 0));
        assertEquals("Add 0 value", (byte) 10, t.getByteValue(2, 0));
        assertEquals("Add 0 value", (byte) 15, t.getByteValue(0, 1));
        assertEquals("Add 0 value", (byte) 20, t.getByteValue(1, 1));
        assertEquals("Add 0 value", (byte) 99, t.getByteValue(2, 1));
        t.add((byte) 10);
        assertEquals("Add 10 value", (byte) 9, t.getByteValue(0, 0));
        assertEquals("Add 10 value", (byte) 10, t.getByteValue(1, 0));
        assertEquals("Add 10 value", (byte) 20, t.getByteValue(2, 0));
        assertEquals("Add 10 value", (byte) 25, t.getByteValue(0, 1));
        assertEquals("Add 10 value", (byte) 30, t.getByteValue(1, 1));
        assertEquals("Add 10 value", (byte) 109, t.getByteValue(2, 1));
        t.add((byte) -5);
        assertEquals("Add 10 value", (byte) 4, t.getByteValue(0, 0));
        assertEquals("Add 10 value", (byte) 5, t.getByteValue(1, 0));
        assertEquals("Add 10 value", (byte) 15, t.getByteValue(2, 0));
        assertEquals("Add 10 value", (byte) 20, t.getByteValue(0, 1));
        assertEquals("Add 10 value", (byte) 25, t.getByteValue(1, 1));
        assertEquals("Add 10 value", (byte) 104, t.getByteValue(2, 1));
    }

    @Test
    public void testAddShort() {
        ByteTensor t = new ByteTensor(3, 2);
        t.setByteValue((byte) -1, 0, 0);
        t.setByteValue((byte) 0, 1, 0);
        t.setByteValue((byte) 10, 2, 0);
        t.setByteValue((byte) 15, 0, 1);
        t.setByteValue((byte) 20, 1, 1);
        t.setByteValue((byte) 99, 2, 1);
        t.add((short) 0);
        assertEquals("Add 0 value", (byte) -1, t.getByteValue(0, 0));
        assertEquals("Add 0 value", (byte) 0, t.getByteValue(1, 0));
        assertEquals("Add 0 value", (byte) 10, t.getByteValue(2, 0));
        assertEquals("Add 0 value", (byte) 15, t.getByteValue(0, 1));
        assertEquals("Add 0 value", (byte) 20, t.getByteValue(1, 1));
        assertEquals("Add 0 value", (byte) 99, t.getByteValue(2, 1));
        t.add((short) 10);
        assertEquals("Add 10 value", (byte) 9, t.getByteValue(0, 0));
        assertEquals("Add 10 value", (byte) 10, t.getByteValue(1, 0));
        assertEquals("Add 10 value", (byte) 20, t.getByteValue(2, 0));
        assertEquals("Add 10 value", (byte) 25, t.getByteValue(0, 1));
        assertEquals("Add 10 value", (byte) 30, t.getByteValue(1, 1));
        assertEquals("Add 10 value", (byte) 109, t.getByteValue(2, 1));
        t.add((short) -5);
        assertEquals("Add 10 value", (byte) 4, t.getByteValue(0, 0));
        assertEquals("Add 10 value", (byte) 5, t.getByteValue(1, 0));
        assertEquals("Add 10 value", (byte) 15, t.getByteValue(2, 0));
        assertEquals("Add 10 value", (byte) 20, t.getByteValue(0, 1));
        assertEquals("Add 10 value", (byte) 25, t.getByteValue(1, 1));
        assertEquals("Add 10 value", (byte) 104, t.getByteValue(2, 1));
    }

    @Test
    public void testAddFloat() {
        ByteTensor t = new ByteTensor(3, 2);
        t.setByteValue((byte) -1, 0, 0);
        t.setByteValue((byte) 0, 1, 0);
        t.setByteValue((byte) 10, 2, 0);
        t.setByteValue((byte) 15, 0, 1);
        t.setByteValue((byte) 20, 1, 1);
        t.setByteValue((byte) 99, 2, 1);
        t.add((float) 0);
        assertEquals("Add 0 value", (byte) -1, t.getByteValue(0, 0));
        assertEquals("Add 0 value", (byte) 0, t.getByteValue(1, 0));
        assertEquals("Add 0 value", (byte) 10, t.getByteValue(2, 0));
        assertEquals("Add 0 value", (byte) 15, t.getByteValue(0, 1));
        assertEquals("Add 0 value", (byte) 20, t.getByteValue(1, 1));
        assertEquals("Add 0 value", (byte) 99, t.getByteValue(2, 1));
        t.add((float) 10);
        assertEquals("Add 10 value", (byte) 9, t.getByteValue(0, 0));
        assertEquals("Add 10 value", (byte) 10, t.getByteValue(1, 0));
        assertEquals("Add 10 value", (byte) 20, t.getByteValue(2, 0));
        assertEquals("Add 10 value", (byte) 25, t.getByteValue(0, 1));
        assertEquals("Add 10 value", (byte) 30, t.getByteValue(1, 1));
        assertEquals("Add 10 value", (byte) 109, t.getByteValue(2, 1));
        t.add((float) -5);
        assertEquals("Add 10 value", (byte) 4, t.getByteValue(0, 0));
        assertEquals("Add 10 value", (byte) 5, t.getByteValue(1, 0));
        assertEquals("Add 10 value", (byte) 15, t.getByteValue(2, 0));
        assertEquals("Add 10 value", (byte) 20, t.getByteValue(0, 1));
        assertEquals("Add 10 value", (byte) 25, t.getByteValue(1, 1));
        assertEquals("Add 10 value", (byte) 104, t.getByteValue(2, 1));
    }

    @Test
    public void testAddTensor() {
        ByteTensor t1 = new ByteTensor(3, 2);
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
        ByteTensor t3 = new ByteTensor(3, 2, 5);
        try {
            t1.add(t3);
            fail("Add different size of tensor");
        } catch (Exception e) {
            assertTrue("Add different size of tensor.", e instanceof RuntimeException);
        }
        t1.setByteValue((byte) -34, 0, 0);
        t1.setByteValue((byte) -67, 1, 0);
        t1.setByteValue((byte) 6, 2, 0);
        t1.setByteValue((byte) 34, 0, 1);
        t1.setByteValue((byte) 2, 1, 1);
        t1.setByteValue((byte) 78, 2, 1);
        ByteTensor t4 = new ByteTensor(3, 2);
        t4.setByteValue((byte) 2, 0, 0);
        t4.setByteValue((byte) -3, 1, 0);
        t4.setByteValue((byte) 10, 2, 0);
        t4.setByteValue((byte) -10, 0, 1);
        t4.setByteValue((byte) 7, 1, 1);
        t4.setByteValue((byte) -22, 2, 1);
        Tensor sum = t1.add(t4);
        assertEquals("Tensor sum", (byte) -32, sum.getByteValue(0, 0));
        assertEquals("Tensor sum", (byte) -70, sum.getByteValue(1, 0));
        assertEquals("Tensor sum", (byte) 16, sum.getByteValue(2, 0));
        assertEquals("Tensor sum", (byte) 24, sum.getByteValue(0, 1));
        assertEquals("Tensor sum", (byte) 9, sum.getByteValue(1, 1));
        assertEquals("Tensor sum", (byte) 56, sum.getByteValue(2, 1));
    }

    @Test
    public void testNegateTensor() {
        ByteTensor t1 = new ByteTensor(3, 2);
        t1.setByteValue((byte) 2, 0, 0);
        t1.setByteValue((byte) -3, 1, 0);
        t1.setByteValue((byte) 10, 2, 0);
        t1.setByteValue((byte) -10, 0, 1);
        t1.setByteValue((byte) 7, 1, 1);
        t1.setByteValue((byte) -22, 2, 1);
        Tensor negated = t1.negate();
        assertEquals("Tensor negate", (byte) -2, negated.getByteValue(0, 0));
        assertEquals("Tensor negate", (byte) 3, negated.getByteValue(1, 0));
        assertEquals("Tensor negate", (byte) -10, negated.getByteValue(2, 0));
        assertEquals("Tensor negate", (byte) 10, negated.getByteValue(0, 1));
        assertEquals("Tensor negate", (byte) -7, negated.getByteValue(1, 1));
        assertEquals("Tensor negate", (byte) 22, negated.getByteValue(2, 1));
    }

    @Test
    public void testTransposeTensor() {
        ByteTensor t1 = new ByteTensor(3, 2);
        t1.setByteValue((byte) -34, 0, 0);
        t1.setByteValue((byte) -67, 1, 0);
        t1.setByteValue((byte) 6, 2, 0);
        t1.setByteValue((byte) 34, 0, 1);
        t1.setByteValue((byte) 2, 1, 1);
        t1.setByteValue((byte) 78, 2, 1);
        Tensor transposed = t1.transpose();
        assertEquals("Transposed tensor", (byte) -34, transposed.getByteValue(0, 0));
        assertEquals("Transposed tensor", (byte) -67, transposed.getByteValue(0, 1));
        assertEquals("Transposed tensor", (byte) 6, transposed.getByteValue(0, 2));
        assertEquals("Transposed tensor", (byte) 34, transposed.getByteValue(1, 0));
        assertEquals("Transposed tensor", (byte) 2, transposed.getByteValue(1, 1));
        assertEquals("Transposed tensor", (byte) 78, transposed.getByteValue(1, 2));
    }

    @Test
    public void testCloneTensor() throws CloneNotSupportedException {
        ByteTensor t1 = new ByteTensor(3, 2);
        t1.setByteValue((byte) -34, 0, 0);
        t1.setByteValue((byte) -67, 1, 0);
        t1.setByteValue((byte) 6, 2, 0);
        t1.setByteValue((byte) 34, 0, 1);
        t1.setByteValue((byte) 2, 1, 1);
        t1.setByteValue((byte) 78, 2, 1);
        Tensor transposed = t1.clone();
        assertEquals("Transposed tensor", (byte) -34, transposed.getByteValue(0, 0));
        assertEquals("Transposed tensor", (byte) -67, transposed.getByteValue(1, 0));
        assertEquals("Transposed tensor", (byte) 6, transposed.getByteValue(2, 0));
        assertEquals("Transposed tensor", (byte) 34, transposed.getByteValue(0, 1));
        assertEquals("Transposed tensor", (byte) 2, transposed.getByteValue(1, 1));
        assertEquals("Transposed tensor", (byte) 78, transposed.getByteValue(2, 1));
    }

}