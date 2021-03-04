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
package volgyerdo.math.matrix;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Pocze Zsolt
 */
class ByteMatrix extends Matrix {

    public final byte[] values;

    public ByteMatrix(int... dimensions) {
        int size = 0;
        for (int i : dimensions) {
            size += i;
        }
        values = new byte[size];
    }

    @Override
    public void setValue(byte value, int... indices) {
        values[index(indices)] = value;
    }

    @Override
    public void setValue(float value, int... indices) {
        values[index(indices)] = (byte) value;
    }

    @Override
    public void setValue(short value, int... indices) {
        values[index(indices)] = (byte) value;
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
        final ByteMatrix other = (ByteMatrix) obj;
        if (!Arrays.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }

    @Override
    public void randomize(byte min, byte max) {
        Random randomizer = new Random();
        randomizer.nextBytes(values);
    }

    @Override
    public void randomize(short min, short max) {
        randomize((byte) min, (byte) max);
    }

    @Override
    public void randomize(float min, float max) {
        randomize((byte) min, (byte) max);
    }

    @Override
    public void add(byte scaler) {
        for (int i = 0; i < values.length; i++) {
            values[i] += scaler;
        }
    }

    @Override
    public void add(short scaler) {
        add((byte) scaler);
    }

    @Override
    public void add(float scaler) {
        add((byte) scaler);
    }

    @Override
    Matrix add(ByteMatrix matrix) {
        try {
            ByteMatrix clone = (ByteMatrix) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] = (byte) (values[i] + matrix.values[i]);
            }
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
        return null;
    }

    @Override
    Matrix add(ShortMatrix matrix) {
        try {
            ShortMatrix clone = (ShortMatrix) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] = (byte) (values[i] + matrix.values[i]);
            }
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
        return null;
    }

    @Override
    Matrix add(FloatMatrix matrix) {
        try {
            FloatMatrix clone = (FloatMatrix) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] = (byte) (values[i] + matrix.values[i]);
            }
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
        return null;
    }

    @Override
    Matrix substract(ByteMatrix matrix) {
        try {
            ByteMatrix clone = (ByteMatrix) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] = (byte) (values[i] - matrix.values[i]);
            }
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
        return null;
    }

    @Override
    Matrix substract(ShortMatrix matrix) {
        try {
            ShortMatrix clone = (ShortMatrix) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] = (byte) (values[i] - matrix.values[i]);
            }
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
        return null;
    }

    @Override
    Matrix substract(FloatMatrix matrix) {
        try {
            FloatMatrix clone = (FloatMatrix) clone();
            for (int i = 0; i < values.length; i++) {
                clone.values[i] = (byte) (values[i] - matrix.values[i]);
            }
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
        return null;
    }

}
