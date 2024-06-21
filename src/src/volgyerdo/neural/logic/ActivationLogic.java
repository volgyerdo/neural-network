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
package volgyerdo.neural.logic;

import volgyerdo.neural.structure.Activation;
import volgyerdo.commons.math.fast.FastMath;
import volgyerdo.commons.math.tensor.FloatTensor;
import volgyerdo.commons.math.tensor.ObjectTensor;
import volgyerdo.commons.math.tensor.Tensor;

/**
 *
 * @author Pocze Zsolt
 */
public class ActivationLogic {

    private ActivationLogic() {
    }

    public static void activate(Tensor states, Tensor activations) {
        FloatTensor floatStates = (FloatTensor)states;
        ObjectTensor activationObjects = (ObjectTensor)activations;
        for (int i = 0; i < floatStates.values.length; i++) {
            floatStates.values[i] = 
                    activate(floatStates.values[i], (Activation)activationObjects.values[i]);
        }
    }
    
    public static void deactivate(Tensor states, Tensor activations) {
        FloatTensor floatStates = (FloatTensor)states;
        ObjectTensor activationObjects = (ObjectTensor)activations;
        for (int i = 0; i < floatStates.values.length; i++) {
            floatStates.values[i] = 
                    deactivate(floatStates.values[i], (Activation)activationObjects.values[i]);
        }
    }

    // f(x) = [(1 + (x + shiftX) * stretchX * swish)) / (1 + exp(-(x + shiftX) * stretchX)) 
    //        + slope * (x + shiftX) * stretchX] * stretchY + shiftY
    public static float activate(float x, Activation parameters) {
        double y = (x + parameters.shiftX) * parameters.stretchX;
        y = (1. + parameters.swish * y) / (1. + Math.exp(-y)) + parameters.slope * y;
        y = y * parameters.stretchY + parameters.shiftY;
        return (float)y;
    }

    // Derivative of activate()
    public static float deactivate(float x, Activation parameters) {
        double transX = Math.exp(parameters.stretchX * (x + parameters.shiftX));
        double stretchSlope = parameters.stretchX * parameters.slope;
        return (float)((parameters.stretchX * parameters.stretchY
                * transX * ((parameters.swish - parameters.slope) * transX
                + parameters.stretchX * parameters.swish * x
                + (parameters.shiftX * parameters.stretchX + 1.) * parameters.swish + 1.))
                / FastMath.pow2((stretchSlope * x
                        + parameters.shiftX * stretchSlope + 1.)
                        * transX + 1.));
    }

}
