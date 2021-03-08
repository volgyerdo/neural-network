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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class TensorTest {
    
    public TensorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreateByteTensor() {
        System.out.println("createByteTensor");
        int[] dimensions = null;
        Tensor expResult = null;
        Tensor result = Tensor.createByteTensor(dimensions);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCreateShortTensor() {
        System.out.println("createShortTensor");
        int[] dimensions = null;
        Tensor expResult = null;
        Tensor result = Tensor.createShortTensor(dimensions);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCreateFloatTensor() {
        System.out.println("createFloatTensor");
        int[] dimensions = null;
        Tensor expResult = null;
        Tensor result = Tensor.createFloatTensor(dimensions);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCreateObjectTensor() {
        System.out.println("createObjectTensor");
        int[] dimensions = null;
        Tensor expResult = null;
        Tensor result = Tensor.createObjectTensor(dimensions);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testClone() throws Exception {
        System.out.println("clone");
        Tensor instance = null;
        Tensor expResult = null;
        Tensor result = instance.clone();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testIndex() {
        System.out.println("index");
        int[] indices = null;
        Tensor instance = null;
        int expResult = 0;
        int result = instance.index(indices);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testReversedIndex() {
        System.out.println("reversedIndex");
        int[] indices = null;
        Tensor instance = null;
        int expResult = 0;
        int result = instance.reversedIndex(indices);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetValue_byte_intArr() {
        System.out.println("setValue");
        byte value = 0;
        int[] indices = null;
        Tensor instance = null;
        instance.setValue(value, indices);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetValue_short_intArr() {
        System.out.println("setValue");
        short value = 0;
        int[] indices = null;
        Tensor instance = null;
        instance.setValue(value, indices);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetValue_float_intArr() {
        System.out.println("setValue");
        float value = 0.0F;
        int[] indices = null;
        Tensor instance = null;
        instance.setValue(value, indices);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetValue_Object_intArr() {
        System.out.println("setValue");
        Object value = null;
        int[] indices = null;
        Tensor instance = null;
        instance.setValue(value, indices);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetByteValue() {
        System.out.println("getByteValue");
        int[] indices = null;
        Tensor instance = null;
        byte expResult = 0;
        byte result = instance.getByteValue(indices);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetShortValue() {
        System.out.println("getShortValue");
        int[] indices = null;
        Tensor instance = null;
        short expResult = 0;
        short result = instance.getShortValue(indices);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetFloatValue() {
        System.out.println("getFloatValue");
        int[] indices = null;
        Tensor instance = null;
        float expResult = 0.0F;
        float result = instance.getFloatValue(indices);
        assertEquals(expResult, result, 0.0);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetObjectValue() {
        System.out.println("getObjectValue");
        int[] indices = null;
        Tensor instance = null;
        Object expResult = null;
        Object result = instance.getObjectValue(indices);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRandomize_byte_byte() {
        System.out.println("randomize");
        byte min = 0;
        byte max = 0;
        Tensor instance = null;
        instance.randomize(min, max);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRandomize_short_short() {
        System.out.println("randomize");
        short min = 0;
        short max = 0;
        Tensor instance = null;
        instance.randomize(min, max);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRandomize_float_float() {
        System.out.println("randomize");
        float min = 0.0F;
        float max = 0.0F;
        Tensor instance = null;
        instance.randomize(min, max);
        fail("The test case is a prototype.");
    }

    @Test
    public void testAdd_byte() {
        System.out.println("add");
        byte scaler = 0;
        Tensor instance = null;
        instance.add(scaler);
        fail("The test case is a prototype.");
    }

    @Test
    public void testAdd_short() {
        System.out.println("add");
        short scaler = 0;
        Tensor instance = null;
        instance.add(scaler);
        fail("The test case is a prototype.");
    }

    @Test
    public void testAdd_float() {
        System.out.println("add");
        float scaler = 0.0F;
        Tensor instance = null;
        instance.add(scaler);
        fail("The test case is a prototype.");
    }

    @Test
    public void testAdd_Tensor() {
        System.out.println("add");
        Tensor matrix = null;
        Tensor instance = null;
        Tensor expResult = null;
        Tensor result = instance.add(matrix);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSubstract() {
        System.out.println("substract");
        Tensor matrix = null;
        Tensor instance = null;
        Tensor expResult = null;
        Tensor result = instance.substract(matrix);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testTranspose() {
        System.out.println("transpose");
        Tensor instance = null;
        Tensor expResult = null;
        Tensor result = instance.transpose();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testProduct() {
        System.out.println("product");
        Tensor tensor = null;
        Tensor instance = null;
        Tensor expResult = null;
        Tensor result = instance.product(tensor);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    public class TensorImpl extends Tensor {

        public TensorImpl() {
            super(null);
        }

        public void setValue(byte value, int[] indices) {
        }

        public void setValue(short value, int[] indices) {
        }

        public void setValue(float value, int[] indices) {
        }

        public void setValue(Object value, int[] indices) {
        }

        public byte getByteValue(int[] indices) {
            return 0;
        }

        public short getShortValue(int[] indices) {
            return 0;
        }

        public float getFloatValue(int[] indices) {
            return 0.0F;
        }

        public Object getObjectValue(int[] indices) {
            return null;
        }

        public void randomize(byte min, byte max) {
        }

        public void randomize(short min, short max) {
        }

        public void randomize(float min, float max) {
        }

        public void add(byte scaler) {
        }

        public void add(short scaler) {
        }

        public void add(float scaler) {
        }
    }
    
}
