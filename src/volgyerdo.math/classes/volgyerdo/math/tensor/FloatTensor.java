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
import java.util.Random;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
class FloatTensor extends Tensor {

    public final float[] values;

    public FloatTensor(int... dimensions) {
        int size = 0;
        for (int i : dimensions) {
            size += i;
        }
        values = new float[size];
    }

    @Override
    public void setValue(float value, int... indices) {
        values[index(indices)] = value;
    }

    @Override
    public void setValue(byte value, int... indices) {
        values[index(indices)] = value;
    }

    @Override
    public void setValue(short value, int... indices) {
        values[index(indices)] = value;
    }
    
    @Override
    public void setValue(Object value, int... indices) {
        if (value instanceof Number) {
            values[index(indices)] = ((Number) value).floatValue();
        }else{
            throw new RuntimeException("Can't store an object in a byte tensor.");
        }
    }

    @Override
    public byte getByteValue(int... indices) {
        return (byte) values[index(indices)];
    }

    @Override
    public short getShortValue(int... indices) {
        return (short) values[index(indices)];
    }

    @Override
    public float getFloatValue(int... indices) {
        return values[index(indices)];
    }
    
    @Override
    public Object getObjectValue(int... indices) {
        return values[index(indices)];
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
        Random randomizer = new Random();
        for (int i = 0; i < values.length; i++) {
            values[i] = randomizer.nextFloat();
        }
    }

    @Override
    public void add(byte scaler) {
        add((float)scaler);
    }

    @Override
    public void add(short scaler) {
        add((float)scaler);
    }

    @Override
    public void add(float scaler) {
        for (int i = 0; i < values.length; i++) {
            values[i] += scaler;
        }
    }

    Tensor addMatrix(FloatTensor matrix) {
        try {
            FloatTensor clone = (FloatTensor) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] += matrix.values[i];
            }
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
        return null;
    }

    Tensor substractMatrix(FloatTensor matrix) {
        try {
            FloatTensor clone = (FloatTensor) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] -= matrix.values[i];
            }
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
        return null;
    }

    Tensor transposeMatrix() {
        try {
            FloatTensor clone = (FloatTensor) clone();
            int[] indices = new int[dimensions.length];
            Arrays.fill(indices, 0);
            transposeRecursive(clone, 0, indices);
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
        return null;
    }

    void transposeRecursive(FloatTensor matrix, int current, int[] indices) {
        if (current == indices.length) {
            matrix.setValue(getReversedValue(indices), indices);
        } else {
            int next = current + 1;
            for (int i = 0; i < dimensions[current]; i++) {
                indices[current] = i;
                transposeRecursive(matrix, next, indices);
            }
        }
    }

    private float getReversedValue(int... indices) {
        return values[index(indices)];
    }
}
