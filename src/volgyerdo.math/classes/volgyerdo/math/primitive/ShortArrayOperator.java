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
package volgyerdo.math.primitive;

import java.util.Objects;


@FunctionalInterface
public interface ShortArrayOperator {


    short applyAsShort(short operand, int i);


    default ShortArrayOperator compose(ShortArrayOperator before) {
        Objects.requireNonNull(before);
        return (short v, int i) -> applyAsShort(before.applyAsShort(v, i), i);
    }

    default ShortArrayOperator andThen(ShortArrayOperator after) {
        Objects.requireNonNull(after);
        return (short t, int i) -> after.applyAsShort(applyAsShort(t, i), i);
    }

    static ShortArrayOperator identity() {
        return (t, i) -> t;
    }
}
