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

    protected Matrix(int... dimensions) {
        if (dimensions.length == 0) {
            throw new IllegalArgumentException("Dimensions element count is zero.");
        }
        this.dimensions = dimensions;
    }

    protected int index(int... indices) {
        if (indices.length != dimensions.length) {
            throw new IllegalArgumentException("Indices element count does not equal with dimensions element count.");
        }
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

}
