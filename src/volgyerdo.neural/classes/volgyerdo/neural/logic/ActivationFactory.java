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
 * @author Volgyerdo Nonprofit Kft.
 */
public class ActivationFactory {

    private ActivationFactory() {
    }
        
    public static Activation create(
            float shiftX, float shiftY,
            float stretchX, float stretchY,
            float swish, float slope) {
        Activation activation = new Activation();
        activation.shiftX = shiftX;
        activation.shiftY = shiftY;
        activation.stretchX = stretchX;
        activation.stretchY = stretchY;
        activation.swish = swish;
        activation.slope = slope;
        return activation;
    }

    public static Activation createSigmoid() {
        Activation activation = new Activation();
        activation.shiftX = 0f;
        activation.shiftY = 0f;
        activation.stretchX = 1f;
        activation.stretchY = 1f;
        activation.swish = 0f;
        activation.slope = 0f;
        return activation;
    }
    
    public static Activation createSwish() {
        Activation activation = new Activation();
        activation.shiftX = 0f;
        activation.shiftY = 0f;
        activation.stretchX = 1f;
        activation.stretchY = 1f;
        activation.swish = 1f;
        activation.slope = 0f;
        return activation;
    }
    
    
    public static Activation createTanH() {
        Activation activation = new Activation();
        activation.shiftX = 0f;
        activation.shiftY = -1f;
        activation.stretchX = 1f;
        activation.stretchY = 1f;
        activation.swish = 2f;
        activation.slope = 0f;
        return activation;
    }
    
    public static Activation createReLu() {
        Activation activation = new Activation();
        activation.shiftX = 0f;
        activation.shiftY = 0f;
        activation.stretchX = 100f;
        activation.stretchY = 0.01f;
        activation.swish = 1f;
        activation.slope = 0f;        
        return activation;
    }
    
    public static Activation createLeakyReLu() {
        Activation activation = new Activation();        
        activation.shiftX = 0f;
        activation.shiftY = 0f;
        activation.stretchX = 100f;
        activation.stretchY = 0.0077f;
        activation.swish = 1f;
        activation.slope = 0.3f;
        return activation;
    }
    
    public static Activation createStep() {
        Activation activation = new Activation();
        activation.shiftX = 0f;
        activation.shiftY = 0f;
        activation.stretchX = 1000f;
        activation.stretchY = 1f;
        activation.swish = 0f;
        activation.slope = 0f;
        return activation;
    }
    
    public static Activation createLinear() {
        Activation activation = new Activation();
        activation.shiftX = 0f;
        activation.shiftY = -0.5f;
        activation.stretchX = 1f;
        activation.stretchY = 1f;
        activation.swish = 0f;
        activation.slope = 1f;
        return activation;
    }

}
