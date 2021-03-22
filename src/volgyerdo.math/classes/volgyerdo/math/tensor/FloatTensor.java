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
 * @author Volgyerdo Nonprofit Kft.
 */
class FloatTensor extends Tensor {

    public final float[] values;

    public FloatTensor(int... dimensions) {
        super(dimensions);
        values = new float[ArrayUtils.product(dimensions)];
    }

    @Override
    public void setFloatValue(float value, int... indices) {
        values[index(indices)] = value;
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
        return PrimitiveUtils.toShort(values[index(indices)]);
    }

    @Override
    public float getFloatValue(int... indices) {
        return values[index(indices)];
    }

    @Override
    public Object getObjectValue(int... indices) {
        throw new RuntimeException("Can't get an object from a tensor.");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Arrays.hashCode(this.values);
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
        final FloatTensor other = (FloatTensor) obj;
        if (!Arrays.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }

    @Override
    public void randomize(byte min, byte max) {
        randomize((float) min, (float) max);
    }

    @Override
    public void randomize(short min, short max) {
        randomize((float) min, (float) max);
    }

    @Override
    public void randomize(float min, float max) {
        if(max < min){
            throw new RuntimeException("Max < min in randomize parameters.");
        }
        double interval = max - min;
        for (int i = 0; i < values.length; i++) {
            values[i] = PrimitiveUtils.toFloat(Math.random() * interval + min);
        }
    }

    @Override
    public void add(byte scaler) {
        add((float) scaler);
    }

    @Override
    public void add(short scaler) {
        add((float) scaler);
    }

    @Override
    public void add(float scaler) {
        for (int i = 0; i < values.length; i++) {
            values[i] += scaler;
        }
    }

    protected Tensor addTensor(Tensor tensor) {
        try {
            FloatTensor clone = (FloatTensor) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] += ((FloatTensor)tensor).values[i];
            }
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
    }

    protected Tensor negate() {
        try {
            FloatTensor clone = (FloatTensor)clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] = (float)-clone.values[i];
            }
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
    }

    protected Tensor transpose() {
        FloatTensor transposed = (FloatTensor) Tensor.createFloatTensor(ArrayUtils.reverse(dimensions));
        int[] indices = new int[dimensions.length];
        Arrays.fill(indices, 0);
        transposeRecursive(transposed, 0, indices);
        return transposed;
    }

    private void transposeRecursive(FloatTensor tensor, int current, int[] indices) {
        if (current == indices.length) {
            tensor.setFloatValue(getFloatValue(indices), ArrayUtils.reverse(indices));
        } else {
            int next = current + 1;
            for (int i = 0; i < dimensions[current]; i++) {
                indices[current] = i;
                transposeRecursive(tensor, next, indices);
            }
        }
    }

    @Override
    public Tensor clone() throws CloneNotSupportedException{
        FloatTensor clone = new FloatTensor(dimensions);
        System.arraycopy(values, 0, clone.values, 0, values.length);
        return clone;
    }
}
