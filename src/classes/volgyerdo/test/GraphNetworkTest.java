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
package volgyerdo.test;

import volgyerdo.math.tensor.Tensor;
import volgyerdo.neural.logic.NetworkFactory;
import volgyerdo.neural.logic.NetworkLogic;
import volgyerdo.neural.structure.Network;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class GraphNetworkTest {
    
    public static void main(String[] args){
        Network network = NetworkFactory.createGraphNetwork(100, 5, 3);
        NetworkLogic.randomizeWeights(network);
        Tensor input = Tensor.create(Tensor.TYPE.FLOAT, 5);
        input.randomize(-1, 1);
        NetworkLogic.propagate(network, input);
    }
    
}
