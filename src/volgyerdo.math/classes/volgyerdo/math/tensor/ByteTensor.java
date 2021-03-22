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
        super(dimensions);
        values = new byte[ArrayUtils.product(dimensions)];
    }
    
    @Override
    public Tensor convertToByteTensor(){
        try {
            return clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    @Override
    public Tensor convertToShortTensor(){
        ShortTensor shortTensor = (ShortTensor)Tensor.createShortTensor(dimensions);
        for (int i = 0; i < values.length; i++) {
            shortTensor.values[i] = values[i];
        }
        return shortTensor;
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
    protected Tensor addTensor(Tensor tensor) {
        try {
            ByteTensor clone = (ByteTensor) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] += ((ByteTensor)tensor).values[i];
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
        ByteTensor transposed = (ByteTensor) Tensor.createByteTensor(ArrayUtils.reverse(dimensions));
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
    public Tensor clone() throws CloneNotSupportedException {
        ByteTensor clone = new ByteTensor(dimensions);
        System.arraycopy(values, 0, clone.values, 0, values.length);
        return clone;
    }
}
