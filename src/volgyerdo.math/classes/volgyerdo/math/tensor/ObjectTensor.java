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
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import volgyerdo.math.primitive.ArrayUtils;
import volgyerdo.math.primitive.ByteSupplier;
import volgyerdo.math.primitive.ByteUnaryOperator;
import volgyerdo.math.primitive.FloatSupplier;
import volgyerdo.math.primitive.FloatUnaryOperator;
import volgyerdo.math.primitive.ShortSupplier;
import volgyerdo.math.primitive.ShortUnaryOperator;

/**
 *
 * @author Pocze Zsolt
 */
public class ObjectTensor extends Tensor {

    public final Object[] values;

    public ObjectTensor(int... dimensions) {
        super(TYPE.OBJECT, dimensions);
        values = new Object[ArrayUtils.product(dimensions)];
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
        throw new RuntimeException("Object tensor cannot be converted to byte tensor.");
    }

    private Tensor convertToShortTensor() {
        throw new RuntimeException("Object tensor cannot be converted to short tensor.");
    }

    private Tensor convertToFloatTensor() {
        throw new RuntimeException("Object tensor cannot be converted to float tensor.");
    }

    private Tensor convertToObjectTensor() {
        try {
            return clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public void set(Tensor tensor) {
        throw new RuntimeException("Object tensor cannot be set.");
    }

    @Override
    public void setFloatValue(float value, int... indices) {
        values[index(indices)] = (short) value;
    }

    @Override
    public void setByteValue(byte value, int... indices) {
        values[index(indices)] = value;
    }

    @Override
    public void setShortValue(short value, int... indices) {
        values[index(indices)] = value;
    }

    @Override
    public void setObjectValue(Object value, int... indices) {
        values[index(indices)] = value;
    }

    @Override
    public byte getByteValue(int... indices) {
        Object value = values[index(indices)];
        if (value instanceof Number) {
            values[index(indices)] = ((Number) value).byteValue();
        } else {
            throw new RuntimeException("Object tensor doesn't have a byte value.");
        }
        return 0;
    }

    @Override
    public short getShortValue(int... indices) {
        Object value = values[index(indices)];
        if (value instanceof Number) {
            values[index(indices)] = ((Number) value).shortValue();
        } else {
            throw new RuntimeException("Object tensor doesn't have a short value.");
        }
        return 0;
    }

    @Override
    public float getFloatValue(int... indices) {
        Object value = values[index(indices)];
        if (value instanceof Number) {
            values[index(indices)] = ((Number) value).floatValue();
        } else {
            throw new RuntimeException("Object tensor doesn't have a float value.");
        }
        return 0;
    }

    @Override
    public Object getObjectValue(int... indices) {
        return values[index(indices)];
    }

    @Override
    public void setByteArray(byte[] values) {
        throw new RuntimeException("Can't set a byte array into an object tensor.");
    }

    @Override
    public void setShortArray(short[] values) {
        throw new RuntimeException("Can't set a short array into an object tensor.");
    }

    @Override
    public void setFloatArray(float[] values) {
        throw new RuntimeException("Can't set a float array into an object tensor.");
    }

    @Override
    public void setObjectArray(Object[] values) {
        if (dimensions.length != 1 || dimensions[0] != values.length) {
            throw new IllegalArgumentException("Array dimension is different.");
        }
        System.arraycopy(values, 0, this.values, 0, values.length);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Arrays.hashCode(this.values);
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
        final ObjectTensor other = (ObjectTensor) obj;
        if (!Arrays.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }

    @Override
    public void randomize(byte min, byte max) {
        throw new RuntimeException("Object tensor can not be randomized.");
    }

    @Override
    public void randomize(short min, short max) {
        throw new RuntimeException("Object tensor can not be randomized.");
    }

    @Override
    public void randomize(float min, float max) {
        throw new RuntimeException("Object tensor can not be randomized.");
    }

    @Override
    public void fill(byte x) {
        throw new RuntimeException("Object tensor can not be filled.");
    }

    @Override
    public void fill(short x) {
        throw new RuntimeException("Object tensor can not be filled.");
    }

    @Override
    public void fill(float x) {
        throw new RuntimeException("Object tensor can not be filled.");
    }

    @Override
    public void add(byte x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void add(short x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void add(float x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void add(Tensor tensor) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void substract(byte x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void substract(short x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void substract(float x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    public void substract(Tensor tensor) {
        throw new RuntimeException("Object tensor doesn't have substract function.");
    }

    @Override
    public void multiply(byte x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void multiply(short x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void multiply(float x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void divide(byte x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void divide(short x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void divide(float x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public Tensor sum() {
        throw new RuntimeException("Object tensor doesn't have sum function.");
    }

    @Override
    public byte byteSum() {
        throw new RuntimeException("Object tensor doesn't have byteSum function.");
    }

    @Override
    public short shortSum() {
        throw new RuntimeException("Object tensor doesn't have shortSum function.");
    }

    @Override
    public float floatSum() {
        throw new RuntimeException("Object tensor doesn't have floatSum function.");
    }

    @Override
    public byte byteMin() {
        throw new RuntimeException("Object tensor doesn't have byteMin function.");
    }

    @Override
    public short shortMin() {
        throw new RuntimeException("Object tensor doesn't have shortMin function.");
    }

    @Override
    public float floatMin() {
        throw new RuntimeException("Object tensor doesn't have floatMin function.");
    }

    @Override
    public byte byteMax() {
        throw new RuntimeException("Object tensor doesn't have byteMax function.");
    }

    @Override
    public short shortMax() {
        throw new RuntimeException("Object tensor doesn't have shortMax function.");
    }

    @Override
    public float floatMax() {
        throw new RuntimeException("Object tensor doesn't have floatMax function.");
    }

    @Override
    public byte byteAverage() {
        throw new RuntimeException("Object tensor doesn't have byteAverage function.");
    }

    @Override
    public short shortAverage() {
        throw new RuntimeException("Object tensor doesn't have shortAverage function.");
    }

    @Override
    public float floatAverage() {
        throw new RuntimeException("Object tensor doesn't have floatAverage function.");
    }

    @Override
    public void processByte(ByteUnaryOperator operator) {
        throw new RuntimeException("Object tensor doesn't have byte process function.");
    }

    @Override
    public void processShort(ShortUnaryOperator operator) {
        throw new RuntimeException("Object tensor doesn't have short process function.");
    }

    @Override
    public void processFloat(FloatUnaryOperator operator) {
        throw new RuntimeException("Object tensor doesn't have float process function.");
    }

    @Override
    public void processObject(UnaryOperator operator) {
        for (int i = 0; i < values.length; i++) {
            values[i] = operator.apply(values[i]);
        }
    }

    @Override
    public void fillWithByte(ByteSupplier operator) {
        throw new RuntimeException("Object tensor doesn't have byte filler function.");
    }

    @Override
    public void fillWithShort(ShortSupplier operator) {
        throw new RuntimeException("Object tensor doesn't have short filler function.");
    }

    @Override
    public void fillWithFloat(FloatSupplier operator) {
        throw new RuntimeException("Object tensor doesn't have float filler function.");
    }

    @Override
    public void fillWithObject(Supplier operator) {
        for (int i = 0; i < values.length; i++) {
            values[i] = operator.get();
        }
    }

    @Override
    public void negate() {
        throw new RuntimeException("Object tensor doesn't have negate function.");
    }

    @Override
    public void abs() {
        throw new RuntimeException("Object tensor doesn't have abs function.");
    }

    @Override
    public Tensor transpose() {
        try {
            FloatTensor clone = (FloatTensor) clone();
            int[] indices = new int[dimensions.length];
            Arrays.fill(indices, 0);
            transposeRecursive(clone, 0, indices);
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
    }

    private void transposeRecursive(FloatTensor tensor, int current, int[] indices) {
        if (current == indices.length) {
            tensor.setObjectValue(getObjectValue(indices), ArrayUtils.reverse(indices));
        } else {
            int next = current + 1;
            for (int i = 0; i < dimensions[current]; i++) {
                indices[current] = i;
                transposeRecursive(tensor, next, indices);
            }
        }
    }

    @Override
    public void hadamardProduct(Tensor tensor) {
        throw new RuntimeException("Object tensor doesn't have product function.");
    }

    @Override
    protected void sumProductRecursive(Tensor multiplier, Tensor target,
            int[] commonDimensions, int[] multiplierDimensions, int[] outputDimensions,
            int depth, int[] pos, int n, int[] indices, int[] rd1, int[] rd2) {
        throw new RuntimeException("Object tensor doesn't have sumProductRecursive function.");
    }

    @Override
    protected void convolveRecursive(Tensor kernel, Tensor result, int k, int[] d) {
        throw new RuntimeException("Object tensor doesn't have convolution function.");
    }

    @Override
    protected void convolvePartialRecursive(Tensor kernel, Tensor result, int k, int[] kd, int[] d) {
        throw new RuntimeException("Object tensor doesn't have a partial convolution function.");
    }

    @Override
    public Tensor clone() throws CloneNotSupportedException {
        ObjectTensor clone = new ObjectTensor(dimensions);
        System.arraycopy(values, 0, clone.values, 0, values.length);
        return clone;
    }

    @Override
    public void toStringRecursive(StringBuilder sb, int n, int[] indices, boolean newLine) {
        if (n < indices.length) {
            for (int i = 0; i < dimensions[n]; i++) {
                indices[n] = i;
                toStringRecursive(sb, n + 1, indices, newLine);
            }
            if (newLine) {
                sb.append("\n");
            }
        } else {
            sb.append("[").append(String.valueOf(getObjectValue(indices))).append("]");
        }
    }

}
