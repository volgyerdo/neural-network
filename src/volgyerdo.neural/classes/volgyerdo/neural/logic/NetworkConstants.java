/*
 * Copyright 2021 antal.
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
 * @author antal
 */
public class NetworkConstants {
    
    public static final float DEFAULT_LEARNING_RATE = 0.01f;
    
    public static final Activation DEFAULT_ACTIVATION = ActivationFactory.createTanH();
    
    public static final float DEFAULT_WEIGHT_RADIUS = 1f;
    
    public static final int ANALYSES_ROW_LENGTH = 50;
    
}
