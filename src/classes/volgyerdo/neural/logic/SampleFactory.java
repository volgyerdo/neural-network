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

import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.structure.Sample;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class SampleFactory {
    
    public static Sample createSample(Tensor input, Tensor target){
        Sample sample = new Sample();
        sample.input = input;
        sample.target = target;
        return sample;
    }
  
    public static Sample createSample(byte[] input, byte[] target){
        Sample sample = new Sample();
        sample.input = Tensor.create(Tensor.TYPE.BYTE, input.length);
        sample.input.setByteArray(input);
        sample.target = Tensor.create(Tensor.TYPE.BYTE, target.length);
        sample.target.setByteArray(target);
        return sample;
    }
    
    public static Sample createSample(short[] input, short[] target){
        Sample sample = new Sample();
        sample.input = Tensor.create(Tensor.TYPE.SHORT, input.length);
        sample.input.setShortArray(input);
        sample.target = Tensor.create(Tensor.TYPE.SHORT, target.length);
        sample.target.setShortArray(target);
        return sample;
    }
    
    public static Sample createSample(float[] input, float[] target){
        Sample sample = new Sample();
        sample.input = Tensor.create(Tensor.TYPE.FLOAT, input.length);
        sample.input.setFloatArray(input);
        sample.target = Tensor.create(Tensor.TYPE.FLOAT, target.length);
        sample.target.setFloatArray(target);
        return sample;
    }
    
}
