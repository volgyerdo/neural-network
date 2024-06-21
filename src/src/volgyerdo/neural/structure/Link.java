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
 * @author Volgyerdo Nonprofit Kft.
 */
public class Link implements Serializable{
    
    public static final long versionUID = 1L;
    
    public int inputId;
    public int outputId;
    public float weight;
    public float learningRate;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.inputId;
        hash = 29 * hash + this.outputId;
        hash = 29 * hash + Float.floatToIntBits(this.weight);
        hash = 29 * hash + Float.floatToIntBits(this.learningRate);
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
        final Link other = (Link) obj;
        if (this.inputId != other.inputId) {
            return false;
        }
        if (this.outputId != other.outputId) {
            return false;
        }
        if (Float.floatToIntBits(this.weight) != Float.floatToIntBits(other.weight)) {
            return false;
        }
        if (Float.floatToIntBits(this.learningRate) != Float.floatToIntBits(other.learningRate)) {
            return false;
        }
        return true;
    }
    
}
