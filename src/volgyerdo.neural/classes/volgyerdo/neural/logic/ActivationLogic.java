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
    // f(x)' = -((a/2+1)*(b-e^(-x)))/(e^(-x)+b*x+1)^2
    //  ahol    a: swish valtozo
    //          b: slope valtozo
    // x-et itt y reprezentalja?
    public static float deactivate(float y, Activation parameters) {
        
       //a fuggvenyben negyzeten van nevezo es Math.pow() elso valtozoja nem fogad el fuggvenyt
       //ezert ezt emeljuk majd negyzetre
        double SqrtDenominator = (float)Math.exp(-y) + parameters.slope * y + 1;
        
        y = (y + parameters.shiftX) * parameters.stretchX;
        y = -(parameters.swish/2+1) * (parameters.slope - (float)Math.exp(-y))
             / (float)Math.pow(SqrtDenominator, 2) ;
        y = y * parameters.stretchY + parameters.shiftY;
        return y;
    }

}