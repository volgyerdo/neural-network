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

import java.util.Arrays;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class GraphLayer extends Layer{
    
    public Neuron[] neurons;
    public Link[] links;
    public float[] biases;
    public int[] inputIds;
    public int[] outputIds;

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 79 * hash + Arrays.deepHashCode(this.neurons);
        hash = 79 * hash + Arrays.deepHashCode(this.links);
        hash = 79 * hash + Arrays.hashCode(this.biases);
        hash = 79 * hash + Arrays.hashCode(this.inputIds);
        hash = 79 * hash + Arrays.hashCode(this.outputIds);
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
        if(!super.equals(obj)){
            return false;
        }
        final GraphLayer other = (GraphLayer) obj;
        if (!Arrays.deepEquals(this.neurons, other.neurons)) {
            return false;
        }
        if (!Arrays.deepEquals(this.links, other.links)) {
            return false;
        }
        if (!Arrays.equals(this.biases, other.biases)) {
            return false;
        }
        if (!Arrays.equals(this.inputIds, other.inputIds)) {
            return false;
        }
        if (!Arrays.equals(this.outputIds, other.outputIds)) {
            return false;
        }
        return true;
    }
    
}
