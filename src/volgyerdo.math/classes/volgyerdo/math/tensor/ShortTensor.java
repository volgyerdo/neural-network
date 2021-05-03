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
class ShortTensor extends Tensor {

    public final short[] values;

    public ShortTensor(int... dimensions) {
        super(TYPE.SHORT, dimensions);
        values = new short[ArrayUtils.product(dimensions)];
    }

    @Override
    public Tensor convertTo(TYPE type) {
        return switch (type) {
            case BYTE ->
                convertToByteTensor();
            case SHORT ->
                convertToShortTensor();
            case FLOAT ->
                convertToFloatTensor();
            case OBJECT ->
                convertToObjectTensor();
            default ->
                null;
        };
    }

    private Tensor convertToByteTensor() {
        ByteTensor byteTensor = (ByteTensor) Tensor.create(TYPE.BYTE, dimensions);
        for (int i = 0; i < values.length; i++) {
            byteTensor.values[i] = PrimitiveUtils.toByte(values[i]);
        }
        return byteTensor;
    }

    private Tensor convertToShortTensor() {
        try {
            return clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    private Tensor convertToFloatTensor() {
        FloatTensor floatTensor = (FloatTensor) Tensor.create(TYPE.FLOAT, dimensions);
        for (int i = 0; i < values.length; i++) {
            floatTensor.values[i] = values[i];
        }
        return floatTensor;
    }

    private Tensor convertToObjectTensor() {
        ObjectTensor objectTensor = (ObjectTensor) Tensor.create(TYPE.OBJECT, dimensions);
        for (int i = 0; i < values.length; i++) {
            objectTensor.values[i] = values[i];
        }
        return objectTensor;
    }

    @Override
    public void setFloatValue(float value, int... indices) {
        values[index(indices)] = PrimitiveUtils.toShort(value);
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
        return values[index(indices)];
    }

    @Override
    public float getFloatValue(int... indices) {
        return values[index(indices)];
    }

    @Override
    public Object getObjectValue(int... indices) {
        throw new RuntimeException("Can't get an object from a short tensor.");
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
        final ShortTensor other = (ShortTensor) obj;
        if (!Arrays.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }

    @Override
    public void randomize(byte min, byte max) {
        randomize((short) min, (short) max);
    }

    @Override
    public void randomize(short min, short max) {
        if (max < min) {
            throw new RuntimeException("Max < min in randomize parameters.");
        }
        double interval = max - min;
        for (int i = 0; i < values.length; i++) {
            values[i] = PrimitiveUtils.toShort((Math.random() * interval + min));
        }
    }

    @Override
    public void randomize(float min, float max) {
        randomize(PrimitiveUtils.toShort(min), PrimitiveUtils.toShort(max));
    }

    @Override
    public void add(byte x) {
        add((short) x);
    }

    @Override
    public void add(short x) {
        for (int i = 0; i < values.length; i++) {
            values[i] += x;
        }
    }

    @Override
    public void add(float x) {
        for (int i = 0; i < values.length; i++) {
            values[i] = PrimitiveUtils.toShort((float) values[i] + x);
        }
    }

    @Override
    public void add(Tensor tensor) {
        checkNull(tensor);
        checkClass(tensor);
        checkDimensionCount(tensor.dimensions);
        checkDimensions(tensor);
        for (int i = 0; i < values.length; i++) {
            values[i] += ((ShortTensor) tensor).values[i];
        }
    }

    @Override
    public void substract(byte x) {
        substract((short) x);
    }

    @Override
    public void substract(short x) {
        for (int i = 0; i < values.length; i++) {
            values[i] -= x;
        }
    }

    @Override
    public void substract(float x) {
        for (int i = 0; i < values.length; i++) {
            values[i] = PrimitiveUtils.toShort((float) values[i] - x);
        }
    }

    @Override
    public void multiply(byte x) {
        multiply((short) x);
    }

    @Override
    public void multiply(short x) {
        for (int i = 0; i < values.length; i++) {
            values[i] *= x;
        }
    }

    @Override
    public void multiply(float x) {
        for (int i = 0; i < values.length; i++) {
            values[i] = PrimitiveUtils.toShort((float) values[i] * x);
        }
    }

    @Override
    public void divide(byte x) {
        divide((short) x);
    }

    @Override
    public void divide(short x) {
        for (int i = 0; i < values.length; i++) {
            values[i] /= x;
        }
    }

    @Override
    public void divide(float x) {
        for (int i = 0; i < values.length; i++) {
            values[i] = PrimitiveUtils.toShort((float) values[i] / x);
        }
    }
    
    @Override
    public void processByte(ByteProcessor processor){
        for (int i = 0; i < values.length; i++) {
            values[i] = processor.process(PrimitiveUtils.toByte(values[i]));
        }
    }
    
    @Override
    public void processShort(ShortProcessor processor){
        for (int i = 0; i < values.length; i++) {
            values[i] = processor.process(values[i]);
        }
    }
    
    @Override
    public void processFloat(FloatProcessor processor){
        for (int i = 0; i < values.length; i++) {
            values[i] = PrimitiveUtils.toShort(processor.process((float)values[i]));
        }
    }
    
    @Override
    public void processObject(ObjectProcessor processor){
        throw new RuntimeException("Short tensor doesn't have object processor function.");
    }

    @Override
    public void negate() {
        for (int i = 0; i < values.length; i++) {
            values[i] = (short) -values[i];
        }
    }

    @Override
    public Tensor transpose() {
        ShortTensor transposed = (ShortTensor) Tensor.create(TYPE.SHORT, ArrayUtils.reverse(dimensions));
        int[] indices = new int[dimensions.length];
        Arrays.fill(indices, 0);
        transposeRecursive(transposed, 0, indices);
        return transposed;
    }

    private void transposeRecursive(ShortTensor tensor, int current, int[] indices) {
        if (current == indices.length) {
            tensor.setShortValue(getShortValue(indices), ArrayUtils.reverse(indices));
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
            int[] commonDimensions, int[] multiplierDimensions, int[] outputDimensions, int depth, int[] pos, int n, int[] indices) {
        if (n < commonDimensions.length) {
            for (int i = 0; i < commonDimensions[n]; i++) {
                indices[n] = i;
                sumProductRecursive(multiplier, target, commonDimensions,
                        multiplierDimensions, outputDimensions, depth, pos, n + 1, indices);
            }
        } else {
            int[] rd1 = new int[dimensions.length];
            System.arraycopy(pos, 0, rd1, 0, dimensions.length - depth);
            System.arraycopy(indices, 0, rd1, dimensions.length - depth, depth);
            int[] rd2 = new int[multiplierDimensions.length];
            System.arraycopy(indices, 0, rd2, 0, depth);
            System.arraycopy(pos, dimensions.length - depth, rd2, depth, multiplier.dimensions.length - depth);
            float value = target.getShortValue(pos);
            target.setShortValue(PrimitiveUtils.toShort(value + getShortValue(rd1) * multiplier.getShortValue(rd2)), pos);
        }
    }

    @Override
    protected void convolveRecursive(Tensor kernel, Tensor result, int k, int[] d) {
        if (k < dimensions.length - 1) {
            for (int i = 0; i < dimensions[k]; i++) {
                d[k] = i;
                convolveRecursive(kernel, result, k + 1, d);
            }
        } else {
            result.setShortValue(convolutionSum(kernel, d, 0, new int[dimensions.length]), d);
        }
    }

    private short convolutionSum(Tensor kernel, int[] d, int k, int[] e) {
        if (k < dimensions.length - 1) {
            short s = 0;
            for (int i = 0; i < kernel.dimensions[k]; i++) {
                e[k] = i;
                s += convolutionSum(kernel, d, k + 1, e);
            }
            return s;
        } else {
            int[] rd = new int[dimensions.length];
            for (int i = 0; i < dimensions.length; i++) {
                rd[i] = d[i] + e[i] - kernel.dimensions[i] / 2;
                if (rd[i] < 0 || rd[i] > dimensions[i] - 1) {
                    return 0;
                }
            }
            return PrimitiveUtils.toShort(getShortValue(rd) * kernel.getShortValue(e));
        }
    }

    @Override
    public Tensor clone() throws CloneNotSupportedException {
        ShortTensor clone = new ShortTensor(dimensions);
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
            sb.append("[").append(getShortValue(indices)).append("]");
        }
    }
}
