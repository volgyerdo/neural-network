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
public abstract class Tensor{

    public final int[] dimensions;
    public final int[] multipliers;

    public static Tensor createByteTensor(int... dimensions) {
        return new ByteTensor();
    }

    public static Tensor createShortTensor(int... dimensions) {
        return new ShortTensor();
    }

    public static Tensor createFloatTensor(int... dimensions) {
        return new ShortTensor();
    }
    
    public static Tensor createObjectTensor(int... dimensions) {
        return new ObjectTensor();
    }

    protected Tensor(int... dimensions) {
        checkNewDimensions(dimensions);
        this.dimensions = dimensions;
        multipliers = new int[dimensions.length-1];
        multipliers[0] = 1;
        for (int i = 0; i < dimensions.length - 1; i++) {
            multipliers[i + 1] = multipliers[i] * dimensions[i];
    }
    }
    
    @Override
    public Tensor clone() throws CloneNotSupportedException{
        return (Tensor)super.clone();
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
    
    protected int reversedIndex(int... indices) {
        checkDimensionCount(indices);
        if (indices.length == 0) {
            return indices[0];
        }
        int index = indices[0];
        for (int i = indices.length - 1; i > 0; i--) {
            index += multipliers[i] * indices[i];
        }
        return index;
    }

    public abstract void setValue(byte value, int... indices);

    public abstract void setValue(short value, int... indices);

    public abstract void setValue(float value, int... indices);
    
    public abstract void setValue(Object value, int... indices);

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

    public Tensor add(Tensor matrix) {
        checkClass(matrix);
        checkDimensionCount(matrix.dimensions);
        checkDimensions(matrix);
        if (this instanceof ByteTensor) {
            return ((ByteTensor) this).addMatrix((ByteTensor) matrix);
        }else if (this instanceof ShortTensor) {
            return ((ShortTensor) this).addMatrix((ShortTensor) matrix);
        }else if (this instanceof FloatTensor) {
            return ((FloatTensor) this).addMatrix((FloatTensor) matrix);
        }
        return null;
    }
    
    public Tensor substract(Tensor matrix) {
        checkClass(matrix);
        checkDimensionCount(matrix.dimensions);
        checkDimensions(matrix);
        if (this instanceof ByteTensor) {
            return ((ByteTensor) this).substractMatrix((ByteTensor) matrix);
        }else if (this instanceof ShortTensor) {
            return ((ShortTensor) this).substractMatrix((ShortTensor) matrix);
        }else if (this instanceof FloatTensor) {
            return ((FloatTensor) this).substractMatrix((FloatTensor) matrix);
        }
        return null;
    }
    
    public Tensor transpose() {
        if (this instanceof ByteTensor) {
            return ((ByteTensor) this).transposeMatrix();
        }else if (this instanceof ShortTensor) {
            return ((ShortTensor) this).transposeMatrix();
        }else if (this instanceof FloatTensor) {
            return ((FloatTensor) this).transposeMatrix();
        }
        return null;
    }
    
    public Tensor product(Tensor tensor) {
        throw new IllegalArgumentException("Tensor product is not implemented.");
    }

    private void checkClass(Tensor matrix){
        if (!matrix.getClass().equals(getClass())) {
            throw new IllegalArgumentException("Matrix classes does not match.");
        }
    }
    
    private void checkNewDimensions(int... dimensions){
        if (dimensions.length == 0) {
            throw new IllegalArgumentException("Dimensions element count is zero.");
        }
    }
    
    private void checkDimensionCount(int... dimensions){
        if (dimensions.length != dimensions.length) {
            throw new IllegalArgumentException("Matrix dimension element count does not equal.");
        }
    }
    
    private void checkDimensions(Tensor matrix){
        for (int i = 0; i < dimensions.length; i++) {
            if (matrix.dimensions[i] != dimensions[i]) {
                throw new IllegalArgumentException("Matrix dimensions does not match.");
            }
        }
    }
}
