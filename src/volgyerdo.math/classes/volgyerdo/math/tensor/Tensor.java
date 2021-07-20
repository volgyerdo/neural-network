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

import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import volgyerdo.math.primitive.ByteSupplier;
import volgyerdo.math.primitive.ByteUnaryOperator;
import volgyerdo.math.primitive.FloatSupplier;
import volgyerdo.math.primitive.FloatUnaryOperator;
import volgyerdo.math.primitive.ShortSupplier;
import volgyerdo.math.primitive.ShortUnaryOperator;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public abstract class Tensor {

    public static enum TYPE {
        BYTE, SHORT, FLOAT, OBJECT
    };

    public final TYPE type;
    public final int[] dimensions;
    public final int[] multipliers;

    protected Tensor(TYPE type, int... dimensions) {
        checkNewDimensions(dimensions);
        this.type = type;
        this.dimensions = dimensions;
        multipliers = new int[dimensions.length];
        multipliers[0] = 1;
        for (int i = 0; i < dimensions.length - 1; i++) {
            multipliers[i + 1] = multipliers[i] * dimensions[i];
        }
    }

    public Tensor createSimilar(int... dimensions) {
        return create(type, dimensions);
    }

    public Tensor createSimilar() {
        return create(type, dimensions);
    }

    public static Tensor create(TYPE type, int... dimensions) {
        return switch (type) {
            case BYTE ->
                new ByteTensor(dimensions);
            case SHORT ->
                new ShortTensor(dimensions);
            case FLOAT ->
                new FloatTensor(dimensions);
            case OBJECT ->
                new ObjectTensor(dimensions);
            default ->
                null;
        };
    }

    abstract public Tensor convertTo(TYPE type);

    public abstract int size();

    abstract public void set(Tensor tensor);

    public abstract void setByteValue(byte value, int... indices);

    public abstract void setShortValue(short value, int... indices);

    public abstract void setFloatValue(float value, int... indices);

    public abstract void setObjectValue(Object value, int... indices);

    public abstract byte getByteValue(int... indices);

    public abstract short getShortValue(int... indices);

    public abstract float getFloatValue(int... indices);

    public abstract Object getObjectValue(int... indices);

    public abstract void setByteArray(byte[] values);

    public abstract void setShortArray(short[] values);

    public abstract void setFloatArray(float[] values);

    public abstract void setObjectArray(Object[] values);

    public abstract void randomize(byte min, byte max);

    public abstract void randomize(short min, short max);

    public abstract void randomize(float min, float max);

    public abstract void fill(byte x);

    public abstract void fill(short x);

    public abstract void fill(float x);

    public abstract void add(byte scaler);

    public abstract void add(short scaler);

    public abstract void add(float scaler);

    public abstract void add(Tensor tensor);

    public abstract void substract(byte scaler);

    public abstract void substract(short scaler);

    public abstract void substract(float scaler);

    public abstract void substract(Tensor tensor);

    public abstract void multiply(byte scaler);

    public abstract void multiply(short scaler);

    public abstract void multiply(float scaler);

    public abstract void divide(byte scaler);

    public abstract void divide(short scaler);

    public abstract void divide(float scaler);

    public abstract Tensor sum();

    public abstract byte byteSum();

    public abstract short shortSum();

    public abstract float floatSum();

    public abstract byte byteMin();

    public abstract short shortMin();

    public abstract float floatMin();

    public abstract byte byteMax();

    public abstract short shortMax();

    public abstract float floatMax();

    public abstract byte byteAverage();

    public abstract short shortAverage();

    public abstract float floatAverage();

    public abstract void processByte(ByteUnaryOperator operator);

    public abstract void processShort(ShortUnaryOperator operator);

    public abstract void processFloat(FloatUnaryOperator operator);

    public abstract void processObject(UnaryOperator operator);

    public abstract void fillWithByte(ByteSupplier supplier);

    public abstract void fillWithShort(ShortSupplier supplier);

    public abstract void fillWithFloat(FloatSupplier supplier);

    public abstract void fillWithObject(Supplier supplier);

    public abstract void negate();

    public abstract void abs();

    public abstract Tensor transpose();

    public abstract void hadamardProduct(Tensor multiplier);

    public Tensor multiply(Tensor multiplier, int depth) {
        checkNull(multiplier);
        checkClass(multiplier);
        int[] sourceDimensions = dimensions;
        int[] multiplierDimensions = multiplier.dimensions;
        int sourceDimensionLength = sourceDimensions.length;
        int multiplierDimensionLength = multiplierDimensions.length;
        int outputDimensionLength = sourceDimensionLength + multiplierDimensionLength - 2 * depth;
        if (depth > Math.min(sourceDimensionLength, multiplierDimensionLength)) {
            throw new IllegalArgumentException("Dimensions length smaller than depth.");
        }
        for (int i = 0; i < depth; i++) {
            if (sourceDimensions[sourceDimensionLength - depth + i] != multiplierDimensions[i]) {
                throw new IllegalArgumentException("Dimensions does not match in depth.");
            }
        }
        int[] commonDimensions = new int[depth];
        System.arraycopy(multiplierDimensions, 0, commonDimensions, 0, depth);
        int[] outputDimensions = new int[outputDimensionLength];
        System.arraycopy(sourceDimensions, 0, outputDimensions, 0, sourceDimensionLength - depth);
        System.arraycopy(multiplierDimensions, depth, outputDimensions, sourceDimensionLength - depth, multiplierDimensionLength - depth);
        if (outputDimensionLength == 0) {
            outputDimensions = new int[]{1};
            outputDimensionLength = 1;
        }
        Tensor target = Tensor.create(type, outputDimensions);
        int[] rd1 = new int[dimensions.length];
        int[] rd2 = new int[multiplierDimensions.length];
        multiplyRecursive(multiplier, target, commonDimensions, multiplierDimensions,
                outputDimensions, depth, 0, new int[outputDimensionLength], rd1, rd2);
        return target;
    }

    private void multiplyRecursive(Tensor multiplier, Tensor target,
            int[] commonDimensions, int[] multiplierDimensions, int[] outputDimensions,
            int depth, int n, int[] indices, int[] rd1, int[] rd2) {
        if (n < indices.length) {
            for (int i = 0; i < outputDimensions[n]; i++) {
                indices[n] = i;
                multiplyRecursive(multiplier, target, commonDimensions,
                        multiplierDimensions, outputDimensions, depth, n + 1, indices, rd1, rd2);
            }
        } else {
            System.arraycopy(indices, 0, rd1, 0, dimensions.length - depth);
            System.arraycopy(indices, dimensions.length - depth, rd2, depth, multiplier.dimensions.length - depth);
            sumProductRecursive(multiplier, target, commonDimensions,
                    multiplierDimensions, outputDimensions, depth, indices,
                    0, new int[commonDimensions.length],
                    rd1, rd2);
        }
    }

    protected abstract void sumProductRecursive(Tensor multiplier, Tensor target,
            int[] commonDimensions, int[] multiplierDimensions, int[] outputDimensions,
            int depth, int[] pos, int n, int[] indices, int[] rd1, int[] rd2);

    public Tensor convolve(Tensor kernel) {
        checkNull(kernel);
        checkClass(kernel);
        Tensor result = copy();
        convolveRecursive(kernel, result, 0, new int[dimensions.length]);
        return result;
    }

    protected abstract void convolveRecursive(Tensor kernel, Tensor result, int k, int[] d);

    public Tensor convolvePartial(Tensor kernel, int... outputDimensions) {
        checkNull(kernel);
        checkClass(kernel);
        Tensor result = Tensor.create(type, outputDimensions);
        convolvePartialRecursive(kernel, result, 0, new int[outputDimensions.length], new int[dimensions.length]);
        return result;
    }

    protected abstract void convolvePartialRecursive(Tensor kernel, Tensor result, int k, int[] kd, int[] d);

    protected final int index(int... indices) {
        if (indices.length == 0) {
            return indices[0];
        }
        int index = indices[0];
        for (int i = 1; i < indices.length; i++) {
            index += multipliers[i] * indices[i];
        }
        return index;
    }

    protected final void checkNull(Tensor tensor) {
        if (tensor == null) {
            throw new IllegalArgumentException("Tensor is null.");
        }
    }

    protected final void checkClass(Tensor tensor) {
        if (!tensor.getClass().equals(getClass())) {
            throw new IllegalArgumentException("Tensor classes does not match.");
        }
    }

    protected final void checkNewDimensions(int... dimensions) {
        if (dimensions.length == 0) {
            throw new IllegalArgumentException("Dimensions element count is zero.");
        }
    }

    protected final void checkDimensionCount(int... dimensions) {
        if (this.dimensions.length != dimensions.length) {
            throw new IllegalArgumentException("Tensor dimension element count does not equal.");
        }
    }

    protected final void checkDimensions(Tensor tensor) {
        for (int i = 0; i < dimensions.length; i++) {
            if (tensor.dimensions[i] != dimensions[i]) {
                throw new IllegalArgumentException("Tensor dimensions does not match.");
            }
        }
    }

    public Tensor copy() {
        Tensor clone = null;
        if (this instanceof ByteTensor) {
            clone = ((ByteTensor) this).copy();
        } else if (this instanceof ShortTensor) {
            clone = ((ShortTensor) this).copy();
        } else if (this instanceof FloatTensor) {
            clone = ((FloatTensor) this).copy();
        }
        System.arraycopy(dimensions, 0, clone.dimensions, 0, dimensions.length);
        System.arraycopy(multipliers, 0, clone.multipliers, 0, multipliers.length);
        return clone;
    }
    
    public abstract IndexIterator indexIterator();

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean newLine) {
        StringBuilder sb = new StringBuilder();
        toStringRecursive(sb, 0, new int[dimensions.length], newLine);
        return sb.toString();
    }

    public abstract void toStringRecursive(StringBuilder sb, int n, int[] indices, boolean newLine);
}
