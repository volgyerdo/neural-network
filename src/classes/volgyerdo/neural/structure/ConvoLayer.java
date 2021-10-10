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

import java.util.Objects;
import volgyerdo.commons.math.tensor.Tensor;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class ConvoLayer extends Layer{

    public Tensor kernel;
    public Tensor kernelLearningRates;
    public Tensor learningRate;
    public float bias;
    public float biasLearningRate;
    public Tensor activations;

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 89 * hash + Objects.hashCode(this.kernel);
        hash = 89 * hash + Objects.hashCode(this.kernelLearningRates);
        hash = 89 * hash + Objects.hashCode(this.learningRate);
        hash = 89 * hash + Float.floatToIntBits(this.bias);
        hash = 89 * hash + Float.floatToIntBits(this.biasLearningRate);
        hash = 89 * hash + Objects.hashCode(this.activations);
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
        final ConvoLayer other = (ConvoLayer) obj;
        if (Float.floatToIntBits(this.bias) != Float.floatToIntBits(other.bias)) {
            return false;
        }
        if (Float.floatToIntBits(this.biasLearningRate) != Float.floatToIntBits(other.biasLearningRate)) {
            return false;
        }
        if (!Objects.equals(this.kernel, other.kernel)) {
            return false;
        }
        if (!Objects.equals(this.kernelLearningRates, other.kernelLearningRates)) {
            return false;
        }
        if (!Objects.equals(this.learningRate, other.learningRate)) {
            return false;
        }
        if (!Objects.equals(this.activations, other.activations)) {
            return false;
        }
        return true;
    }
    
}
