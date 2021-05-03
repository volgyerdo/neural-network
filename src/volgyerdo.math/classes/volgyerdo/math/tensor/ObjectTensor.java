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
class ObjectTensor extends Tensor {

    public final Object[] values;

    public ObjectTensor(int... dimensions) {
        super(TYPE.OBJECT, dimensions);
        values = new Object[ArrayUtils.product(dimensions)];
    }
    
    @Override
    public Tensor convertTo(TYPE type) {
        return switch (type) {
            case BYTE -> convertToByteTensor();
            case SHORT -> convertToShortTensor();
            case FLOAT -> convertToFloatTensor();
            case OBJECT -> convertToObjectTensor();
            default -> null;
        };
    }
    
    private Tensor convertToByteTensor(){
        throw new RuntimeException("Object tensor cannot be converted to byte tensor.");
    }
    
    private Tensor convertToShortTensor(){
        throw new RuntimeException("Object tensor cannot be converted to short tensor.");
    }
    
    private Tensor convertToFloatTensor(){
        throw new RuntimeException("Object tensor cannot be converted to float tensor.");
    }
    
    private Tensor convertToObjectTensor(){
        try {
            return clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    @Override
    public void setFloatValue(float value, int... indices) {
        values[index(indices)] = (short) value;
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
    public void add(byte x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void add(short x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void add(float x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    public void add(Tensor tensor) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }
    
    @Override
    public void substract(byte x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void substract(short x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void substract(float x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }
    
    @Override
    public void multiply(byte x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void multiply(short x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void multiply(float x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }
    
    @Override
    public void divide(byte x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void divide(short x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }

    @Override
    public void divide(float x) {
        throw new RuntimeException("Object tensor doesn't have add function.");
    }
    
    @Override
    public void processByte(ByteProcessor processor){
        throw new RuntimeException("Object tensor doesn't have byte process function.");
    }
    
    @Override
    public void processShort(ShortProcessor processor){
        throw new RuntimeException("Object tensor doesn't have short process function.");
    }
    
    @Override
    public void processFloat(FloatProcessor processor){
        throw new RuntimeException("Object tensor doesn't have float process function.");
    }
    
    @Override
    public void processObject(ObjectProcessor processor){
        for (int i = 0; i < values.length; i++) {
            values[i] = processor.process(values[i]);
        }
    }

    @Override
    public void negate() {
        throw new RuntimeException("Object tensor doesn't have negate function.");
    }
    
    @Override
    public Tensor transpose() {
        try {
            FloatTensor clone = (FloatTensor) clone();
            int[] indices = new int[dimensions.length];
            Arrays.fill(indices, 0);
            transposeRecursive(clone, 0, indices);
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning is not supported.");
        }
    }

    private void transposeRecursive(FloatTensor tensor, int current, int[] indices) {
        if (current == indices.length) {
            tensor.setObjectValue(getObjectValue(indices), ArrayUtils.reverse(indices));
        } else {
            int next = current + 1;
            for (int i = 0; i < dimensions[current]; i++) {
                indices[current] = i;
                transposeRecursive(tensor, next, indices);
            }
        }
    }

    @Override
    protected void sumProductRecursive(Tensor multiplier, Tensor target,
            int[] commonDimensions, int[] multiplierDimensions, int[] outputDimensions, int depth, int[] pos, int n, int[] indices){
        
    }
    
    @Override
    protected void convolveRecursive(Tensor kernel, Tensor result, int k, int[] d) {
        throw new RuntimeException("Object tensor doesn't have convolution function.");
    }

    @Override
    public Tensor clone() throws CloneNotSupportedException{
        ObjectTensor clone = new ObjectTensor(dimensions);
        System.arraycopy(values, 0, clone.values, 0, values.length);
        return clone;
    }
    
    @Override
    public void toStringRecursive(StringBuilder sb, int n, int[] indices) {
        if (n < indices.length) {
            for(int i = 0; i< dimensions[n]; i++){
                indices[n] = i;
                toStringRecursive(sb, n+1, indices);
            }
            sb.append("\n");
        } else {
            sb.append("[").append(String.valueOf(getObjectValue(indices))).append("]");
        }
    }
}
