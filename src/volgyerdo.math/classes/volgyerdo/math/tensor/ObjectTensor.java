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

/**
 *
 * @author Pocze Zsolt
 */
class ObjectTensor extends Tensor {

    public final Object[] values;

    public ObjectTensor(int... dimensions) {
        int size = 0;
        for (int i : dimensions) {
            size += i;
        }
        values = new Object[size];
    }

    @Override
    public void setValue(float value, int... indices) {
        values[index(indices)] = (short) value;
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
    public void add(byte scaler) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void add(short scaler) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void add(float scaler) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    Tensor addMatrix(ObjectTensor matrix) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    Tensor substractMatrix(ObjectTensor matrix) {
        throw new RuntimeException("Object tensor doesn't have substract function.");
    }

    Tensor transposeMatrix() {
        throw new RuntimeException("Object tensor doesn't have transpose function.");
    }


}
