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

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public abstract class Matrix {

    public final int[] dimensions;

    public static Matrix createByteMatrix(int... dimensions) {
        return new ByteMatrix();
    }

    public static Matrix createShortMatrix(int... dimensions) {
        return new ShortMatrix();
    }

    public static Matrix createFloatMatrix(int... dimensions) {
        return new ShortMatrix();
    }

    protected Matrix(int... dimensions) {
        checkNewDimensions(dimensions);
        this.dimensions = dimensions;
    }
    
    @Override
    public Matrix clone() throws CloneNotSupportedException{
        return (Matrix)super.clone();
    }

    protected int index(int... indices) {
        checkDimensionCount(indices);
        if (indices.length == 0) {
            return indices[0];
        }
        int index = indices[0];
        int multiplier = 1;
        for (int i = 1; i < indices.length; i++) {
            multiplier *= dimensions[i - 1];
            index += multiplier * indices[i];
        }
        return index;
    }

    public abstract void setValue(byte value, int... indices);

    public abstract void setValue(short value, int... indices);

    public abstract void setValue(float value, int... indices);

    public abstract byte getByteValue(int... indices);

    public abstract short getShortValue(int... indices);

    public abstract float getFloatValue(int... indices);

    public abstract void randomize(byte min, byte max);

    public abstract void randomize(short min, short max);

    public abstract void randomize(float min, float max);

    public abstract void add(byte scaler);

    public abstract void add(short scaler);

    public abstract void add(float scaler);

    public Matrix add(Matrix matrix) {
        checkDimensionCount(matrix.dimensions);
        checkDimensions(matrix);
        if (matrix instanceof ByteMatrix) {
            return add((ByteMatrix) matrix);
        }else if (matrix instanceof ShortMatrix) {
            return add((ShortMatrix) matrix);
        }else if (matrix instanceof FloatMatrix) {
            return add((FloatMatrix) matrix);
        }
        return null;
    }

    abstract Matrix add(ByteMatrix matrix);

    abstract Matrix add(ShortMatrix matrix);

    abstract Matrix add(FloatMatrix matrix);
    
    public Matrix substract(Matrix matrix) {
        checkDimensionCount(matrix.dimensions);
        checkDimensions(matrix);
        if (matrix instanceof ByteMatrix) {
            return substract((ByteMatrix) matrix);
        }else if (matrix instanceof ShortMatrix) {
            return substract((ShortMatrix) matrix);
        }else if (matrix instanceof FloatMatrix) {
            return substract((FloatMatrix) matrix);
        }
        return null;
    }

    abstract Matrix substract(ByteMatrix matrix);

    abstract Matrix substract(ShortMatrix matrix);

    abstract Matrix substract(FloatMatrix matrix);

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
    
    private void checkDimensions(Matrix matrix){
        for (int i = 0; i < dimensions.length; i++) {
            if (matrix.dimensions[i] != dimensions[i]) {
                throw new IllegalArgumentException("Matrix dimensions does not match.");
            }
        }
    }
}
