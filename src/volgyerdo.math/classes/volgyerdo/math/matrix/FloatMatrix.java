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

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class FloatMatrix extends Matrix{
   
    public final float[] values;

    public FloatMatrix(int... dimensions) {
        int size = 0;
        for(int i : dimensions){
            size += i;
        }
        values = new float[size];
    }

    public float getValue(int... indices){
        return values[index(indices)];
    }
    
    public void setValue(float value, int... indices){
        values[index(indices)] = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Arrays.hashCode(this.values);
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
        final FloatMatrix other = (FloatMatrix) obj;
        if (!Arrays.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }
    
    
}
