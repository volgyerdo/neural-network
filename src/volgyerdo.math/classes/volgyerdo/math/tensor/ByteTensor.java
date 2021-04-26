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

import java.util.Arrays;
import volgyerdo.math.ArrayUtils;
import volgyerdo.math.PrimitiveUtils;

/**
 *
 * @author Pocze Zsolt
 */
class ByteTensor extends Tensor {

    public final byte[] values;

    public ByteTensor(int... dimensions) {
        super(TYPE.BYTE, dimensions);
        values = new byte[ArrayUtils.product(dimensions)];
    }

    @Override
    public Tensor convertTo(TYPE type) {
        return switch (type) {
            case BYTE ->
                convertToByteTensor();
            case SHORT ->
                convertToShortTensor();
            case FLOAT ->
                convertToFloatTensor();
            case OBJECT ->
                convertToObjectTensor();
            default ->
                null;
        };
    }

    private Tensor convertToByteTensor() {
        try {
            return clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    private Tensor convertToShortTensor() {
        ShortTensor shortTensor = (ShortTensor) Tensor.create(TYPE.SHORT, dimensions);
        for (int i = 0; i < values.length; i++) {
            shortTensor.values[i] = values[i];
        }
        return shortTensor;
    }

    private Tensor convertToFloatTensor() {
        FloatTensor floatTensor = (FloatTensor) Tensor.create(TYPE.FLOAT, dimensions);
        for (int i = 0; i < values.length; i++) {
            floatTensor.values[i] = values[i];
        }
        return floatTensor;
    }

    private Tensor convertToObjectTensor() {
        ObjectTensor objectTensor = (ObjectTensor) Tensor.create(TYPE.OBJECT, dimensions);
        for (int i = 0; i < values.length; i++) {
            objectTensor.values[i] = values[i];
        }
        return objectTensor;
    }

    @Override
    public void setByteValue(byte value, int... indices) {
        values[index(indices)] = value;
    }

    @Override
    public void setFloatValue(float value, int... indices) {
        values[index(indices)] = PrimitiveUtils.toByte(value);
    }

    @Override
    public void setShortValue(short value, int... indices) {
        values[index(indices)] = PrimitiveUtils.toByte(value);
    }

    @Override
    public void setObjectValue(Object value, int... indices) {
        throw new RuntimeException("Can't store an object in a byte tensor.");
    }

    @Override
    public byte getByteValue(int... indices) {
        return values[index(indices)];
    }

    @Override
    public short getShortValue(int... indices) {
        return values[index(indices)];
    }

    @Override
    public float getFloatValue(int... indices) {
        return values[index(indices)];
    }

    @Override
    public Object getObjectValue(int... indices) {
        throw new RuntimeException("Can't get an object from a byte tensor.");
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Arrays.hashCode(this.values);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ByteTensor other = (ByteTensor) obj;
        if (!Arrays.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }

    @Override
    public void randomize(byte min, byte max) {
        if (max < min) {
            throw new RuntimeException("Max < min in randomize parameters.");
        }
        double interval = max - min;
        for (int i = 0; i < values.length; i++) {
            values[i] = PrimitiveUtils.toByte((Math.random() * interval + min));
        }
    }

    @Override
    public void randomize(short min, short max) {
        randomize(PrimitiveUtils.toByte(min), PrimitiveUtils.toByte(max));
    }

    @Override
    public void randomize(float min, float max) {
        randomize(PrimitiveUtils.toByte(min), PrimitiveUtils.toByte(max));
    }

    @Override
    public void add(byte scaler) {
        for (int i = 0; i < values.length; i++) {
            values[i] += scaler;
        }
    }

    @Override
    public void add(short scaler) {
        for (int i = 0; i < values.length; i++) {
            values[i] = PrimitiveUtils.toByte((short) values[i] + scaler);
        }
    }

    @Override
    public void add(float scaler) {
        for (int i = 0; i < values.length; i++) {
            values[i] = PrimitiveUtils.toByte((float) values[i] + scaler);
        }
    }

    @Override
    public Tensor add(Tensor tensor) {
        checkNull(tensor);
        checkClass(tensor);
        checkDimensionCount(tensor.dimensions);
        checkDimensions(tensor);
        try {
            ByteTensor clone = (ByteTensor) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] += ((ByteTensor) tensor).values[i];
            }
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
    }

    @Override
    public Tensor negate() {
        try {
            ByteTensor clone = (ByteTensor) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] = (byte) -clone.values[i];
            }
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
    }

    @Override
    public Tensor transpose() {
        ByteTensor transposed = (ByteTensor) Tensor.create(TYPE.BYTE, ArrayUtils.reverse(dimensions));
        int[] indices = new int[dimensions.length];
        Arrays.fill(indices, 0);
        transposeRecursive(transposed, 0, indices);
        return transposed;
    }

    private void transposeRecursive(ByteTensor tensor, int current, int[] indices) {
        if (current == indices.length) {
            tensor.setByteValue(getByteValue(indices), ArrayUtils.reverse(indices));
        } else {
            int next = current + 1;
            for (int i = 0; i < dimensions[current]; i++) {
                indices[current] = i;
                transposeRecursive(tensor, next, indices);
            }
        }
    }

    @Override
    protected void multiplyRecursive(Tensor multiplier, Tensor result, int a, int b, int common, int difference, int n, int[] d) {
        if (n < (difference == 0 ? common : difference)) {
            for (int i = 0; i < result.dimensions[n]; i++) {
                int[] d1 = Arrays.copyOf(d, d.length);
                d1[n] = i;
                multiplyRecursive(multiplier, result, a, b, common, difference, n + 1, d1);
            }
        } else {
            result.setByteValue(multiplicationSum(multiplier, a, b, common, difference, d, 0, new int[difference == 0 ? common : difference]), d);
        }
    }

    private byte multiplicationSum(Tensor multiplier, int a, int b, int common, int difference, int[] d, int n, int[] e) {
        if (n <  a + b) {
            byte s = 0;
            for (int i = 0; i < dimensions[n]; i++) {
                int[] e1 = Arrays.copyOf(e, e.length);
                e1[n] = i;
                s += multiplicationSum(multiplier, a, b, common, difference, d, n + 1, e1);
            }
            return s;
        } else {
            if (a + b != 0) {
                int[] rd1 = new int[common + a];
                System.arraycopy(e, 0, rd1, 0, common);
                System.arraycopy(d, common, rd1, common, a);
                int[] rd2 = new int[b + common];
                System.arraycopy(d, 0, rd2, 0, b);
                System.arraycopy(e, b, rd2, 0, common);
                return PrimitiveUtils.toByte(getByteValue(rd1) * multiplier.getByteValue(rd2));
            } else {
                return PrimitiveUtils.toByte(getByteValue(d) * multiplier.getByteValue(d));
            }
        }
    }

    @Override
    protected void convolveRecursive(Tensor kernel, Tensor result, int k, int[] d) {
        if (k < dimensions.length - 1) {
            for (int i = 0; i < dimensions[k]; i++) {
                d[k] = i;
                convolveRecursive(kernel, result, k + 1, d);
            }
        } else {
            result.setByteValue(convolutionSum(kernel, d, 0, new int[dimensions.length]), d);
        }
    }

    private byte convolutionSum(Tensor kernel, int[] d, int k, int[] e) {
        if (k < dimensions.length - 1) {
            byte s = 0;
            for (int i = 0; i < kernel.dimensions[k]; i++) {
                e[k] = i;
                s += convolutionSum(kernel, d, k + 1, e);
            }
            return s;
        } else {
            int[] rd = new int[dimensions.length];
            for (int i = 0; i < dimensions.length; i++) {
                rd[i] = d[i] + e[i] - kernel.dimensions[i] / 2;
                if (rd[i] < 0 || rd[i] > dimensions[i] - 1) {
                    return 0;
                }
            }
            return PrimitiveUtils.toByte(getByteValue(rd) * kernel.getByteValue(e));
        }
    }

    @Override
    public Tensor clone() throws CloneNotSupportedException {
        ByteTensor clone = new ByteTensor(dimensions);
        System.arraycopy(values, 0, clone.values, 0, values.length);
        return clone;
    }
}
