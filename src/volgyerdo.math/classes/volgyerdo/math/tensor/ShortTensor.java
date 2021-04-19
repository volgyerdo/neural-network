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
class ShortTensor extends Tensor {

    public final short[] values;

    public ShortTensor(int... dimensions) {
        super(dimensions);
        values = new short[ArrayUtils.product(dimensions)];
    }
    
    @Override
    public Tensor convertToByteTensor(){
        ByteTensor byteTensor = (ByteTensor)Tensor.createByteTensor(dimensions);
        for (int i = 0; i < values.length; i++) {
            byteTensor.values[i] = PrimitiveUtils.toByte(values[i]);
        }
        return byteTensor;
    }
    
    @Override
    public Tensor convertToShortTensor(){
        try {
            return clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    @Override
    public Tensor convertToFloatTensor(){
        FloatTensor floatTensor = (FloatTensor)Tensor.createFloatTensor(dimensions);
        for (int i = 0; i < values.length; i++) {
            floatTensor.values[i] = values[i];
        }
        return floatTensor;
    }
    
    @Override
    public Tensor convertToObjectTensor(){
        ObjectTensor objectTensor = (ObjectTensor)Tensor.createObjectTensor(dimensions);
        for (int i = 0; i < values.length; i++) {
            objectTensor.values[i] = values[i];
        }
        return objectTensor;
    }

    @Override
    public void setFloatValue(float value, int... indices) {
        values[index(indices)] = PrimitiveUtils.toShort(value);
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
        throw new RuntimeException("Can't store an object in a byte tensor.");
    }

    @Override
    public byte getByteValue(int... indices) {
        return PrimitiveUtils.toByte(values[index(indices)]);
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
        throw new RuntimeException("Can't get an object from a short tensor.");
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
        final ShortTensor other = (ShortTensor) obj;
        if (!Arrays.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }

    @Override
    public void randomize(byte min, byte max) {
        randomize((short) min, (short) max);
    }

    @Override
    public void randomize(short min, short max) {
        if(max < min){
            throw new RuntimeException("Max < min in randomize parameters.");
        }
        double interval = max - min;
        for (int i = 0; i < values.length; i++) {
            values[i] = PrimitiveUtils.toShort((Math.random() * interval + min));
        }
    }

    @Override
    public void randomize(float min, float max) {
        randomize(PrimitiveUtils.toShort(min), PrimitiveUtils.toShort(max));
    }

    @Override
    public void add(byte scaler) {
        add((short) scaler);
    }

    @Override
    public void add(short scaler) {
        for (int i = 0; i < values.length; i++) {
            values[i] += scaler;
        }
    }

    @Override
    public void add(float scaler) {
        for (int i = 0; i < values.length; i++) {
            values[i] = PrimitiveUtils.toShort((float) values[i] + scaler);
        }
    }

    @Override
    protected Tensor add(Tensor tensor) {
        checkNull(tensor);
        checkClass(tensor);
        checkDimensionCount(tensor.dimensions);
        checkDimensions(tensor);
        try {
            ShortTensor clone = (ShortTensor) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] += ((ShortTensor)tensor).values[i];
            }
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
    }

    @Override
    public Tensor negate() {
        try {
            ShortTensor clone = (ShortTensor)clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] = (short)-clone.values[i];
            }
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
    }

    @Override
    public Tensor transpose() {
        ShortTensor transposed = (ShortTensor) Tensor.createShortTensor(ArrayUtils.reverse(dimensions));
        int[] indices = new int[dimensions.length];
        Arrays.fill(indices, 0);
        transposeRecursive(transposed, 0, indices);
        return transposed;
    }

    private void transposeRecursive(ShortTensor tensor, int current, int[] indices) {
        if (current == indices.length) {
            tensor.setShortValue(getShortValue(indices), ArrayUtils.reverse(indices));
        } else {
            int next = current + 1;
            for (int i = 0; i < dimensions[current]; i++) {
                indices[current] = i;
                transposeRecursive(tensor, next, indices);
            }
        }
    }

    @Override
    protected void multiplyRecursive(Tensor multiplier, Tensor result, int a, int b, int c, int z, int n, int[] d) {
        if (n < z - 1) {
            for (int i = 0; i < result.dimensions[n]; i++) {
                d[n] = i;
                multiplyRecursive(multiplier, result, a, b, c, z, n + 1, d);
            }
        } else {
            result.setShortValue(multiplicationSum(multiplier, a, b, c, d, 0, new int[c]), d);
        }
    }

    private short multiplicationSum(Tensor multiplier, int a, int b, int c, int[] d, int n, int[] e) {
        if (n < c - 1) {
            short s = 0;
            for (int i = 0; i < dimensions[n]; i++) {
                e[n] = i;
                s += multiplicationSum(multiplier, a, b, c, d, n, e);
            }
            return s;
        } else {
            int[] rd1 = new int[c + a];
            System.arraycopy(e, 0, rd1, 0, c);
            System.arraycopy(d, c, rd1, c, a);
            int[] rd2 = new int[b + c];
            System.arraycopy(d, 0, rd2, 0, b);
            System.arraycopy(e, b, rd2, 0, c);
            return PrimitiveUtils.toShort(getShortValue(rd1) * multiplier.getShortValue(rd2));
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
            result.setShortValue(convolutionSum(kernel, d, 0, new int[dimensions.length]), d);
        }
    }

    private short convolutionSum(Tensor kernel, int[] d, int k, int[] e) {
        if (k < dimensions.length - 1) {
            short s = 0;
            for (int i = 0; i < kernel.dimensions[k]; i++) {
                e[k] = i;
                s += convolutionSum(kernel, d, k + 1, e);
            }
            return s;
        } else {
            int[] rd = new int[dimensions.length];
            for (int i = 0; i < dimensions.length; i++) {
                rd[i] = d[i] + e[i] - kernel.dimensions[i] / 2;
                if(rd[i] < 0 || rd[i] > dimensions[i] -1){
                    return 0;
                }
            }
            return PrimitiveUtils.toShort(getShortValue(rd) * kernel.getShortValue(e));
        }
    }
    
    @Override
    public Tensor clone() throws CloneNotSupportedException {
        ShortTensor clone = new ShortTensor(dimensions);
        System.arraycopy(values, 0, clone.values, 0, values.length);
        return clone;
    }
}
