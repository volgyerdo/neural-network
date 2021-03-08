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

/**
 *
 * @author Pocze Zsolt
 */
public class ActivationLogic {

    private ActivationLogic() {
    }
    
    // f(x) = [(1 + (x + shiftX) * stretchX * swish)) / (1 + exp(-(x + shiftX) * stretchX)) 
    //        + slope * (x + shiftX) * stretchX] * stretchY + shiftY
    public static float activate(float x, Activation parameters) {
        x = (x + parameters.shiftX) * parameters.stretchX;
        x = (1 + parameters.swish * x) / (1 + (float)Math.exp(-x)) + parameters.slope * x;
        x = x * parameters.stretchY + parameters.shiftY;
        return x;
    }
    
    // Derivative of activate()
    // f(x)' = 
    public static float deactivate(float y, Activation parameters) {
        return y;
    }

}
