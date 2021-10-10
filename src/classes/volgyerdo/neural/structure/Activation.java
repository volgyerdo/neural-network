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
package volgyerdo.neural.structure;

import java.io.Serializable;

/**
 *
 * @author Pocze Zsolt
 */
public class Activation implements Serializable{

    public static final long versionUID = 1L;
    
    public float shiftX;
    public float shiftY;
    public float stretchX;
    public float stretchY;
    public float swish;
    public float slope;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Float.floatToIntBits(this.shiftX);
        hash = 97 * hash + Float.floatToIntBits(this.shiftY);
        hash = 97 * hash + Float.floatToIntBits(this.stretchX);
        hash = 97 * hash + Float.floatToIntBits(this.stretchY);
        hash = 97 * hash + Float.floatToIntBits(this.swish);
        hash = 97 * hash + Float.floatToIntBits(this.slope);
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
        final Activation other = (Activation) obj;
        if (Float.floatToIntBits(this.shiftX) != Float.floatToIntBits(other.shiftX)) {
            return false;
        }
        if (Float.floatToIntBits(this.shiftY) != Float.floatToIntBits(other.shiftY)) {
            return false;
        }
        if (Float.floatToIntBits(this.stretchX) != Float.floatToIntBits(other.stretchX)) {
            return false;
        }
        if (Float.floatToIntBits(this.stretchY) != Float.floatToIntBits(other.stretchY)) {
            return false;
        }
        if (Float.floatToIntBits(this.swish) != Float.floatToIntBits(other.swish)) {
            return false;
        }
        if (Float.floatToIntBits(this.slope) != Float.floatToIntBits(other.slope)) {
            return false;
        }
        return true;
    }

}
