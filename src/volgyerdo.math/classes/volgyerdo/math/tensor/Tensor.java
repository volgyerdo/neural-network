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

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public abstract class Tensor {

    public final int[] dimensions;
    public final int[] multipliers;

    public static Tensor createByteTensor(int... dimensions) {
        return new ByteTensor(dimensions);
    }

    public static Tensor createShortTensor(int... dimensions) {
        return new ShortTensor(dimensions);
    }

    public static Tensor createFloatTensor(int... dimensions) {
        return new FloatTensor(dimensions);
    }

    public static Tensor createObjectTensor(int... dimensions) {
        return new ObjectTensor(dimensions);
    }
    
    abstract public Tensor convertToByteTensor();
    
    abstract public Tensor convertToShortTensor();
    
    abstract public Tensor convertToFloatTensor();
    
    abstract public Tensor convertToObjectTensor();

    protected Tensor(int... dimensions) {
        checkNewDimensions(dimensions);
        this.dimensions = dimensions;
        multipliers = new int[dimensions.length];
        multipliers[0] = 1;
        for (int i = 0; i < dimensions.length - 1; i++) {
            multipliers[i + 1] = multipliers[i] * dimensions[i];
        }
    }

    protected int index(int... indices) {
        checkDimensionCount(indices);
        if (indices.length == 0) {
            return indices[0];
        }
        int index = indices[0];
        for (int i = 1; i < indices.length; i++) {
            index += multipliers[i] * indices[i];
        }
        return index;
    }

    public abstract void setByteValue(byte value, int... indices);

    public abstract void setShortValue(short value, int... indices);

    public abstract void setFloatValue(float value, int... indices);

    public abstract void setObjectValue(Object value, int... indices);

    public abstract byte getByteValue(int... indices);

    public abstract short getShortValue(int... indices);

    public abstract float getFloatValue(int... indices);

    public abstract Object getObjectValue(int... indices);

    public abstract void randomize(byte min, byte max);

    public abstract void randomize(short min, short max);

    public abstract void randomize(float min, float max);

    public abstract void add(byte scaler);

    public abstract void add(short scaler);

    public abstract void add(float scaler);

    public Tensor add(Tensor tensor) {
        checkNull(tensor);
        checkClass(tensor);
        checkDimensionCount(tensor.dimensions);
        checkDimensions(tensor);
        return addTensor(tensor);
    }
    
    protected abstract Tensor addTensor(Tensor tensor);

    public abstract Tensor negate();
    
    public abstract Tensor transpose();

    public Tensor convolution(Tensor kernel) {
        checkNull(kernel);
        checkClass(kernel);
        throw new IllegalArgumentException("Tensor product is not implemented.");
    }

    private void checkNull(Tensor tensor) {
        if (!tensor.getClass().equals(getClass())) {
            throw new IllegalArgumentException("Tensor is null.");
        }
    }

    private void checkClass(Tensor tensor) {
        if (!tensor.getClass().equals(getClass())) {
            throw new IllegalArgumentException("Tensor classes does not match.");
        }
    }

    private void checkNewDimensions(int... dimensions) {
        if (dimensions.length == 0) {
            throw new IllegalArgumentException("Dimensions element count is zero.");
        }
    }

    private void checkDimensionCount(int... dimensions) {
        if (this.dimensions.length != dimensions.length) {
            throw new IllegalArgumentException("Tensor dimension element count does not equal.");
        }
    }

    private void checkDimensions(Tensor tensor) {
        for (int i = 0; i < dimensions.length; i++) {
            if (tensor.dimensions[i] != dimensions[i]) {
                throw new IllegalArgumentException("Tensor dimensions does not match.");
            }
        }
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
        if (this instanceof ByteTensor) {
            return ((ByteTensor) this).equals(obj);
        } else if (this instanceof ShortTensor) {
            return ((ShortTensor) this).equals(obj);
        } else if (this instanceof FloatTensor) {
            return ((FloatTensor) this).equals(obj);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (this instanceof ByteTensor) {
            return ((ByteTensor) this).hashCode();
        } else if (this instanceof ShortTensor) {
            return ((ShortTensor) this).hashCode();
        } else if (this instanceof FloatTensor) {
            return ((FloatTensor) this).hashCode();
        }
        return 0;
    }

    @Override
    public Tensor clone() throws CloneNotSupportedException {
        Tensor clone = null;
        if (this instanceof ByteTensor) {
            clone = ((ByteTensor) this).clone();
        } else if (this instanceof ShortTensor) {
            clone = ((ShortTensor) this).clone();
        } else if (this instanceof FloatTensor) {
            clone = ((FloatTensor) this).clone();
        }
        System.arraycopy(dimensions, 0, clone.dimensions, 0, dimensions.length);
        System.arraycopy(multipliers, 0, clone.multipliers, 0, multipliers.length);
        return clone;
    }
}
