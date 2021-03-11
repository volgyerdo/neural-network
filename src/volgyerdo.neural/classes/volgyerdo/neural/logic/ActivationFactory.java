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
    
    
    //f(x)=((1+0.5*a)/(1+exp(-x))+b*x)+c
//    a = swish
//    b = slope
//    c = shifty
    public static Activation createTanH() {
        Activation activation = new Activation();
        //    a = swish: 2
        //    b = slope: 0
        //    c = shifty: -1
        activation.shiftX = 0f;
        activation.shiftY = -1f;
        activation.stretchX = 1f; //mi is a deafult ertek? 1 vagy 0
        activation.stretchY = 1f; // vagy ha nem kotelezo parameter nem is kell
        activation.swish = 2f;
        activation.slope = 0f;
        
        return activation;
    }
    
    public static Activation createReLu() {
        Activation activation = new Activation();
        //    a = swish: 100 (minél nagyobb annál jobb?)
        //    b = slope: 0
        //    c = shifty: 0
        //      = shiftx: +11.6 (?)
        activation.shiftX = 11.6f;
        activation.shiftY = 0f;
        activation.stretchX = 1f;
        activation.stretchY = 1f;
        activation.swish = 100f;
        activation.slope = 0f;        
        
        return activation;
    }
    
    public static Activation createLeakyReLu() {
        Activation activation = new Activation();        
        //    a = swish: 100 (minél nagyobb annál jobb?)
        //    b = slope: 0 // "leakyness" ( 0.1 <= b <= 0.2 )
        //    c = shifty: 0
        //      = shiftx: +11.6 (?)
        activation.shiftX = +11.6f;
        activation.shiftY = 0f;
        activation.stretchX = 1f;
        activation.stretchY = 1f;
        activation.swish = 1f;
        activation.slope = 0f;
        
        return activation;
    }
    
    //f(x)=((((1+0.5*a)/(1+exp(-x))+b*x)+c) * d)
    public static Activation createStep() {
        Activation activation = new Activation();
        //    a = swish: 100
        //    b = slope: 0
        //    c = shifty: 0
        //    d = (jobb oldali slope maximum? skalár) = 0.019
        // TODO: x sikon röviditeni 
        activation.shiftX = 0f;
        activation.shiftY = 0f;
        activation.stretchX = 1f;
        activation.stretchY = 1f;
        activation.swish = 100f;
        activation.slope = 0f;
        
        return activation;
    }
    
    public static Activation createLinear() {
        Activation activation = new Activation();
        //    a = swish: -2
        //    b = slope: 1
        //    c = shifty: 0
        activation.shiftX = 0f;
        activation.shiftY = 0f;
        activation.stretchX = 1f;
        activation.stretchY = 1f;
        activation.swish = -2f;
        activation.slope = 1f;
        
        return activation;
    }

}
