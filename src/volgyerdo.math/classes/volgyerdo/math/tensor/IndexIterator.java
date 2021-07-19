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
package volgyerdo.math.tensor;

import java.util.Arrays;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class IndexIterator {

    private final int[] dimensions;
    private final int[] index;
    private int length;
    private int pos;

    public IndexIterator(int... dimensions) {
        this.dimensions = dimensions;
        index = new int[dimensions.length];
        index[dimensions.length - 1] = -1;
        length = 1;
        for (int i = 0; i < dimensions.length; i++) {
            length *= dimensions[i];
        }
    }

    public boolean hasNext() {
        return pos < length;
    }

    public int[] next() {
        pos++;
        for (int i = dimensions.length - 1; i >= 0; i--) {
            index[i]++;
            if (index[i] < dimensions[i]) {
                break;
            } else {
                index[i] = 0;
                if (i == 0) {
                    break;
                }
            }
        }
        return index;
    }

}
