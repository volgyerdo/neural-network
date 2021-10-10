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
public class DenseLayer extends Layer{
   
    public Tensor weights;
    public Tensor weightsLearningRates;
    public Tensor bias;
    public Tensor biasLearningRates;
    public Tensor activations;

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 67 * hash + Objects.hashCode(this.weights);
        hash = 67 * hash + Objects.hashCode(this.weightsLearningRates);
        hash = 67 * hash + Objects.hashCode(this.bias);
        hash = 67 * hash + Objects.hashCode(this.biasLearningRates);
        hash = 67 * hash + Objects.hashCode(this.activations);
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
        final DenseLayer other = (DenseLayer) obj;
        if (!Objects.equals(this.weights, other.weights)) {
            return false;
        }
        if (!Objects.equals(this.weightsLearningRates, other.weightsLearningRates)) {
            return false;
        }
        if (!Objects.equals(this.bias, other.bias)) {
            return false;
        }
        if (!Objects.equals(this.biasLearningRates, other.biasLearningRates)) {
            return false;
        }
        if (!Objects.equals(this.activations, other.activations)) {
            return false;
        }
        return true;
    }
    
}
